package hu.bme.loanmanager.business;

import hu.bme.loanmanager.business.internal.PartnerServiceImpl;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.PartnershipStatus;

import java.util.List;
import java.util.UUID;

import com.google.inject.ImplementedBy;

@ImplementedBy(PartnerServiceImpl.class)
public interface PartnerService {

	void changePartnershipStatus(UUID acceptorId, UUID initiatorId, PartnershipStatus newStatus, PartnershipStatus... expectedStatuses);

	void partnershipRequest(UserVO userVO, UserVO addUser);

	List<UserVO> loadSelectablePartners(UUID identifier);

	void removeUserFromPartnerList(UUID loggedInUserId, UUID removableUserId);

}
