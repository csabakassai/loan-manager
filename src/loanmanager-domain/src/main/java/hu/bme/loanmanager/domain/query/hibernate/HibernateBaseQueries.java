
package hu.bme.loanmanager.domain.query.hibernate;

import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.MessageVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.AbstractIdentifiable;
import hu.bme.loanmanager.domain.model.AbstractUuidIdentifiable;
import hu.bme.loanmanager.domain.model.AbstractUuidIdentifiable_;
import hu.bme.loanmanager.domain.model.LMMessage;
import hu.bme.loanmanager.domain.model.LMMessage_;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.model.LoanManagerUser_;
//import hu.bme.loanmanager.domain.model.LoanManagerUser_;
import hu.bme.loanmanager.domain.model.LoanPart;
import hu.bme.loanmanager.domain.model.LoanPart_;
import hu.bme.loanmanager.domain.model.LoanStatus;
import hu.bme.loanmanager.domain.model.Loan_;
import hu.bme.loanmanager.domain.model.Partnership;
import hu.bme.loanmanager.domain.model.PartnershipStatus;
import hu.bme.loanmanager.domain.model.Partnership_;
import hu.bme.loanmanager.domain.query.BaseQueries;
import hu.bme.loanmanager.domain.util.EntityFunctions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * 
 * @author cskassai
 */
public class HibernateBaseQueries implements BaseQueries {
	
	@Inject
	private EntityManager em;
	
	@Override
	public <ID extends Serializable, E extends AbstractIdentifiable<ID>> E findEntityById( Class<E> entityClass, ID id ) {
		return em.find(entityClass, id);
		
	}

	@Override
	public UserVO findUser(String userName, String password) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LoanManagerUser> criteriaQuery = criteriaBuilder.createQuery(LoanManagerUser.class);
		Root<LoanManagerUser> from = criteriaQuery.from(LoanManagerUser.class);
		Path<String> passwordPath = from.get(LoanManagerUser_.password);
		Path<String> userNamePath = from.get(LoanManagerUser_.userName);
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(passwordPath, password),
				criteriaBuilder.equal(userNamePath, userName) )
		);
		
		criteriaQuery.select(from);
		
		TypedQuery<LoanManagerUser> typedQuery = em.createQuery(criteriaQuery);
		LoanManagerUser singleResult = getSingleResult(typedQuery);
		
		UserVO userVO = EntityFunctions.getUserVOFunction().apply(singleResult);
		
		return userVO;
		
	}
	
	private<R> R getSingleResult(TypedQuery<R> typedQuery){
		List<R> resultList = typedQuery.getResultList();
		if(resultList.isEmpty()){
			return null;
		} else {
			Preconditions.checkArgument(resultList.size() == 1);
			return resultList.get(0);
		}
	}

	@Override
	public List<UserVO> loadUsersByInitator(UUID initiatorId,
			PartnershipStatus... statuses) {
		List<PartnershipStatus> partnershipStatusList = Lists.newArrayList();
		if(statuses == null){
			partnershipStatusList.addAll(Arrays.asList(PartnershipStatus.values()));
		} else {
			partnershipStatusList.addAll(Arrays.asList(statuses));
		}
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<UserVO> criteriaQuery = criteriaBuilder.createQuery(UserVO.class);
		Root<Partnership> partnershipRoot = criteriaQuery.from(Partnership.class);
		Path<LoanManagerUser> initiatorPaht = partnershipRoot.get(Partnership_.initiator);
		Path<LoanManagerUser> acceptorPath = partnershipRoot.get(Partnership_.acceptor);
		
		Path<String> aceptorLoanManagerUserNamePath = acceptorPath.get(LoanManagerUser_.userName);
		Path<String> acceptorFullNamePath = acceptorPath.get(LoanManagerUser_.name);
		Path<String> acceptorEmailPath = acceptorPath.get(LoanManagerUser_.emailAdress);
		
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(initiatorPaht.get(LoanManagerUser_.id), initiatorId),
				partnershipRoot.get(Partnership_.status).in(partnershipStatusList)) 
		);
		
		criteriaQuery.multiselect(
				acceptorPath.get(LoanManagerUser_.id),
				aceptorLoanManagerUserNamePath, 
				acceptorFullNamePath,
				acceptorEmailPath);
		
		TypedQuery<UserVO> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}

	@Override
	public List<UserVO> loadUsersByAcceptor(UUID acceptorId,
			PartnershipStatus... statuses) {
		List<PartnershipStatus> partnershipStatusList = Lists.newArrayList();
		if(statuses == null){
			partnershipStatusList.addAll(Arrays.asList(PartnershipStatus.values()));
		} else {
			partnershipStatusList.addAll(Arrays.asList(statuses));
		}
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<UserVO> criteriaQuery = criteriaBuilder.createQuery(UserVO.class);
		Root<Partnership> partnershipRoot = criteriaQuery.from(Partnership.class);
		Path<LoanManagerUser> acceptorPaht = partnershipRoot.get(Partnership_.acceptor);
		Path<LoanManagerUser> initiatorPath = partnershipRoot.get(Partnership_.initiator);
		
		Path<String> initiatorLoanManagerUserNamePath = initiatorPath.get(LoanManagerUser_.userName);
		Path<String> initiatorFullNamePath = initiatorPath.get(LoanManagerUser_.name);
		Path<String> initiatorEmailPath = initiatorPath.get(LoanManagerUser_.emailAdress);
		
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(acceptorPaht.get(LoanManagerUser_.id), acceptorId),
				partnershipRoot.get(Partnership_.status).in(partnershipStatusList)) 
		);
		
		criteriaQuery.multiselect(
				initiatorPath.get(LoanManagerUser_.id),
				initiatorLoanManagerUserNamePath, 
				initiatorFullNamePath,
				initiatorEmailPath);
		
		TypedQuery<UserVO> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}

	@Override
	public Partnership loadPartnershipByUsers(UUID initiatorId, UUID acceptorId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Partnership> criteriaQuery = criteriaBuilder.createQuery(Partnership.class);
		Root<Partnership> partnershipRoot = criteriaQuery.from(Partnership.class);
		Path<LoanManagerUser> acceptorPaht = partnershipRoot.get(Partnership_.acceptor);
		Path<LoanManagerUser> initiatorPath = partnershipRoot.get(Partnership_.initiator);
		
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(acceptorPaht.get(LoanManagerUser_.id), acceptorId),
				criteriaBuilder.equal(initiatorPath.get(LoanManagerUser_.id), initiatorId) )
		);
		
		TypedQuery<Partnership> typedQuery = em.createQuery(criteriaQuery);
		
		return getSingleResult(typedQuery);
	}

	@Override
	public <ID extends Serializable, E extends AbstractIdentifiable<ID>> List<E> loadAll(
			Class<E> entityClass) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> entityRoot = criteriaQuery.from(entityClass);
		
		criteriaQuery.select(entityRoot);
		
		TypedQuery<E> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<LoanPartVO> loadLoansByOwner(UUID ownerId,
			LoanStatus... statuses) {
		List<LoanStatus> statusList = Lists.newArrayList();
		if(statuses == null){
			statusList.addAll(Arrays.asList(LoanStatus.values()));
		} else {
			statusList.addAll(Arrays.asList(statuses));
		}
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LoanPartVO> criteriaQuery = criteriaBuilder.createQuery(LoanPartVO.class);
		Root<LoanPart> entityRoot = criteriaQuery.from(LoanPart.class);
		Path<LoanStatus> loanStatusPath = entityRoot.get(LoanPart_.status);
		criteriaQuery.where(criteriaBuilder.and(
				createEntityIdPredicate(entityRoot.get(LoanPart_.loan).get(Loan_.loanOwner), ownerId),
				loanStatusPath.in(statusList))
				);
		criteriaQuery.multiselect(entityRoot);
		
		TypedQuery<LoanPartVO> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	private<E extends AbstractUuidIdentifiable> 
		Predicate createEntityIdPredicate(Path<E> entityPath, UUID id){
		return em.getCriteriaBuilder().equal(entityPath.get(AbstractUuidIdentifiable_.id), id);
	}

	@Override
	public List<LoanPartVO> loadLoansByDepter(UUID depterId,
			LoanStatus... statuses) {
		List<LoanStatus> statusList = Lists.newArrayList();
		if(statuses == null){
			statusList.addAll(Arrays.asList(LoanStatus.values()));
		} else {
			statusList.addAll(Arrays.asList(statuses));
		}
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LoanPartVO> criteriaQuery = criteriaBuilder.createQuery(LoanPartVO.class);
		Root<LoanPart> entityRoot = criteriaQuery.from(LoanPart.class);
		Path<LoanStatus> loanStatusPath = entityRoot.get(LoanPart_.status);
		criteriaQuery.where(criteriaBuilder.and(
				createEntityIdPredicate(entityRoot.get(LoanPart_.depter), depterId),
				loanStatusPath.in(statusList))
				);
		criteriaQuery.multiselect(entityRoot);
		
		TypedQuery<LoanPartVO> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<MessageVO> loadMessagesByUserId(UUID userId) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<MessageVO> criteriaQuery = criteriaBuilder.createQuery(MessageVO.class);
		Root<LMMessage> messageRoot = criteriaQuery.from(LMMessage.class);
		Path<LoanManagerUser> messageOwner = messageRoot.get(LMMessage_.owner);
		
		criteriaQuery.where(
				criteriaBuilder.equal(messageOwner.get(LoanManagerUser_.id), userId)
		);
		
		criteriaQuery.multiselect(messageRoot);
		
		TypedQuery<MessageVO> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
}
