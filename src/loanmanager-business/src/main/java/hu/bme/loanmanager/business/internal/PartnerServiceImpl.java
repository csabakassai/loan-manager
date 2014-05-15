package hu.bme.loanmanager.business.internal;

import hu.bme.loanmanager.business.MessageTemplates;
import hu.bme.loanmanager.business.PartnerService;
import hu.bme.loanmanager.business.UserService;
import hu.bme.loanmanager.domain.factory.LoanManagerDomainFactory;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.Partnership;
import hu.bme.loanmanager.domain.model.PartnershipStatus;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.query.BaseQueries;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

public class PartnerServiceImpl implements PartnerService{
	
	@Inject
	private BaseQueries baseQueries;
	
	@Inject
	private LoanManagerDomainFactory domainFactory;
	
	@Inject
	private UserService userService;

	@Override
	public void changePartnershipStatus(UUID acceptorId, UUID initiatorId, PartnershipStatus newStatus, PartnershipStatus... expectedStatuses) {
		Partnership partnership = baseQueries.loadPartnershipByUsers(initiatorId, acceptorId);
		Preconditions.checkNotNull(partnership);
		Preconditions.checkNotNull(expectedStatuses);
		List<PartnershipStatus> expectedStatusList = Arrays.asList(expectedStatuses);
		Preconditions.checkArgument(expectedStatusList.contains(partnership.getStatus()));
		partnership.setStatus(newStatus);
		String acceptorName = partnership.getAcceptor().getName();
		
		switch (newStatus) {
		case ACCEPTED:
			userService.addMessage(initiatorId, MessageTemplates.PARTNER_REQUEST_ACCEPTED, acceptorName);
			break;
		case REJECTED:
			userService.addMessage(initiatorId, MessageTemplates.PARTNER_REQUEST_REJECTED, acceptorName);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void partnershipRequest(UserVO userVO, UserVO addUser) {
		LoanManagerUser initiator = baseQueries.findEntityById(LoanManagerUser.class, userVO.getId());
		Preconditions.checkNotNull(initiator);
		
		LoanManagerUser acceptor = baseQueries.findEntityById(LoanManagerUser.class, addUser.getId());
		Preconditions.checkNotNull(acceptor);
		
		domainFactory.createPartnership(initiator, acceptor, new Date());
		
		userService.addMessage(acceptor.getId(), MessageTemplates.PARTNER_REQUEST, initiator.getName());
	}
	
	private class UserVOIdFunction implements Function<UserVO, UUID>{

		@Override
		public UUID apply(UserVO input) {
			return input.getId();
		}
		
	}

	@Override
	public List<UserVO> loadSelectablePartners(UUID identifier) {
		List<LoanManagerUser> users = baseQueries.loadAll(LoanManagerUser.class);
		Set<UUID> notSelectableIds = Sets.newHashSet(identifier);
		List<UserVO> usersByAcceptor = baseQueries.loadUsersByAcceptor(identifier, PartnershipStatus.ACCEPTED, PartnershipStatus.NEW);
		
		List<UUID> acceptorIds = Lists.transform(usersByAcceptor, new UserVOIdFunction());
		notSelectableIds.addAll(acceptorIds);
		List<UserVO> usersByInitator = baseQueries.loadUsersByInitator(identifier, PartnershipStatus.ACCEPTED, PartnershipStatus.REJECTED, PartnershipStatus.NEW);

		List<UUID> initiatorIds = Lists.transform(usersByInitator, new UserVOIdFunction());
		notSelectableIds.addAll(initiatorIds);
		
		List<UserVO> selectableUsers = Lists.newArrayList();
		for (LoanManagerUser user : users) {
			if(!notSelectableIds.contains(user.getId())){
				UserVO loggedInUserVO = new UserVO(user.getId(), user.getUserName(), user.getName(), user.getEmailAdress());
				selectableUsers.add(loggedInUserVO);
			}
			
		}
		
		return selectableUsers;
	}

	@Override
	public void removeUserFromPartnerList(UUID loggedInUserId,
			UUID removableUserId) {
		Partnership partnership = baseQueries.loadPartnershipByUsers(loggedInUserId, removableUserId);
		if(partnership == null){
			partnership = baseQueries.loadPartnershipByUsers(removableUserId, removableUserId);
		}
		Preconditions.checkNotNull(partnership);
		Preconditions.checkArgument(partnership.getStatus().equals(PartnershipStatus.ACCEPTED));
		
		partnership.setStatus(PartnershipStatus.DELETED);
		
	}

}
