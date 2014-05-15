package hu.bme.aut.loanmanager.util;

import hu.bme.loanmanager.domain.exception.LoanManagerBusinessException;
import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.io.MessageVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.ejb.LoanManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class WebLoanManager {

	private LoanManager manager;
	private static boolean isMocked = false;

	public WebLoanManager setManager(final LoanManager manager) {
		this.manager = manager;
		return this;
	}

	public void registrationUser(final UserVO userVO) throws LoanManagerBusinessException {
		if(isMocked) {
			MockFactory.registrationUser(userVO);
		} else {
			manager.registerUser(userVO);
		}
	}

	public List<MessageVO> messageList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.messageList(userVO);
		} else {
			return manager.messageList(userVO);
		}
	}

	public List<UserVO> undecidedUsers(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.undecidedUsers(userVO);
		} else {
			return manager.undecidedUsers(userVO);
		}
	}

	public void removeUndecidedUser(final UserVO userVO, final UserVO removeUser) {
		if(isMocked) {
			MockFactory.removeUndecidedUser(userVO, removeUser);
		} else {
			manager.rejectPartnership(userVO, removeUser);
		}
	}

	public void addUndecidedUser(final UserVO userVO, final UserVO addUser) {
		if(isMocked) {
			MockFactory.addUndecidedUser(userVO, addUser);
		} else {
			manager.acceptPartnership(userVO, addUser);
		}
	}

	public List<UserVO> partnerList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.partnerList(userVO);
		} else {
			return manager.partnerList(userVO);
		}
	}

	public List<UserVO> selectablePartners(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.selectablePartners(userVO);
		} else {
			return manager.selectablePartners(userVO);
		}
	}

	public void addUserToUndecidedList(final UserVO userVO, final UserVO addUser) {
		if(isMocked) {
			MockFactory.addUserToUndecidedList(userVO, addUser);
		} else {
			manager.partnershipRequest(userVO, addUser);
		}
	}

	public void removeUserFromPartnerList(final UserVO userVO, final UserVO removeUser) {
		if(isMocked) {
			MockFactory.removeUserFromPartnerList(userVO, removeUser);
		} else {
			manager.removeUserFromPartnerList(userVO, removeUser);
		}
	}

	public List<UserVO> yetUndecidedUsersList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.yetUndecidedUsersList(userVO);
		} else {
			return manager.yetUndecidedUsersList(userVO);
		}
	}

	public void addLoan(LoanVO loanVO) {
		if(isMocked) {
			MockFactory.addLoan(loanVO);
		} else {
			manager.addLoan(loanVO);
		}
	}

	public List<LoanPartVO> loanList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.loanList(userVO);
		} else {
			return manager.loanList(userVO);
		}
	}

	public List<LoanPartVO> acceptableLoanList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.acceptableLoanList(userVO);
		} else {
			return manager.acceptableLoanList(userVO);
		}
	}

	public void acceptLoan(final UserVO user, final LoanPartVO loan) {
		if(isMocked) {
			MockFactory.acceptLoan(user, loan);
		} else {
			manager.acceptLoan(user, loan);
		}
	}

	public void rejectLoan(final UserVO user, final LoanPartVO loan) {
		if(isMocked) {
			MockFactory.rejectLoan(user, loan);
		} else {
			manager.rejectLoan(user, loan);
		}
	}

	public void equalLoan(final UserVO user, final LoanPartVO loan) {
		if(isMocked) {
			MockFactory.equalLoan(user, loan);
		} else {
			manager.equalLoan(user, loan);
		}
	}

	public List<LoanPartVO> rejectedLoanList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.rejectedLoanList(userVO);
		} else {
			return manager.rejectedLoanList(userVO);
		}
	}

	public List<LoanPartVO> acceptedLoanList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.acceptedLoanList(userVO);
		} else {
			return manager.acceptedLoanList(userVO);
		}
	}

	public List<LoanPartVO> equalLoanList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.equalLoanList(userVO);
		} else {
			return manager.equalLoanList(userVO);
		}
	}

	public List<LoanPartVO> waitedLoanList(final UserVO userVO) {
		if(isMocked) {
			return MockFactory.waitedLoanList(userVO);
		} else {
			return manager.waitedLoanList(userVO);
		}
	}

	private static class MockFactory {

		private static List<UserVO> userList = new ArrayList<UserVO>();
		private static Map<UserVO, List<MessageVO>> messageMap = new HashMap<UserVO, List<MessageVO>>();
		private static Map<UserVO, List<UserVO>> undecidedUsersMap = new HashMap<UserVO, List<UserVO>>();
		private static Map<UserVO, List<UserVO>> partners = new HashMap<UserVO, List<UserVO>>();
		private static Map<UserVO, List<UserVO>> users = new HashMap<UserVO, List<UserVO>>();
		private static Map<UserVO, List<LoanPartVO>> rejectedLoans = new HashMap<UserVO, List<LoanPartVO>>();
		private static Map<UserVO, List<LoanPartVO>> loans2 = new HashMap<UserVO, List<LoanPartVO>>();
		private static Map<UserVO, List<LoanPartVO>> acceptedLoans = new HashMap<UserVO, List<LoanPartVO>>();
		private static Map<UserVO, List<LoanPartVO>> equaledLoans = new HashMap<UserVO, List<LoanPartVO>>();
		private static boolean generated = false;

		public static UserVO login(final UserVO userVO) throws LoanManagerBusinessException {
			System.out.println("Mocked login");
			if(!generated) {
				for(int i = 0; i < 100; i++) {
					UserVO user = new UserVO();
					user.setEmail("email" + i);
					user.setFullName("fullname" + i);
					user.setPassword("password" + i);
					user.setUsername("username" + i);
					registrationUser(user);
				}
				generated = true;
			}

			for (UserVO user : userList) {
				if(user.getUsername().equals(userVO.getUsername())
						&& user.getPassword().equals(userVO.getPassword())) {
					addMessage(user, "Login");
					return user;
				}
			}
			throw new LoanManagerBusinessException("User not found!");
		}

		public static void registrationUser(final UserVO userVO) {
			System.out.println("Mocked register");
			UserVO user = new UserVO();
			user.setEmail(userVO.getEmail());
			user.setFullName(userVO.getFullName());
			user.setId(UUID.randomUUID());
			user.setPassword(userVO.getPassword());
			user.setUsername(userVO.getUsername());
			userList.add(user);
			addMessage(user, "Registration");
			users.put(user, new ArrayList<UserVO>());
			Set<Entry<UserVO, List<UserVO>>> entrySet = users.entrySet();
			for (Entry<UserVO, List<UserVO>> entry : entrySet) {
				if(!entry.getKey().equals(user)) {
					entry.getValue().add(user);
					users.get(user).add(entry.getKey());
				}
			}
		}

		public static List<MessageVO> messageList(final UserVO vo) {
			System.out.println("Mocked messageList");
			List<MessageVO> list = new ArrayList<MessageVO>(messageMap.get(vo));
			return list;
		}

		public static void addMessage(final UserVO userVO, final String message) {
			System.out.println("Mocked message");
			if(!messageMap.containsKey(userVO)) {
				messageMap.put(userVO, new ArrayList<MessageVO>());
			}
			messageMap.get(userVO).add(new MessageVO(message));
		}

		public static List<UserVO> undecidedUsers(final UserVO userVO) {
			System.out.println("Mocked undecidedUsers");
			return undecidedUsersMap.get(userVO);
		}

		public static void removeUndecidedUser(final UserVO userVO,	final UserVO removeUser) {
			System.out.println("Mocked removeUndecidedUser");
			addMessage(userVO, "Remove user");
			undecidedUsersMap.get(userVO).remove(removeUser);
			users.get(userVO).add(removeUser);
			users.get(removeUser).add(userVO);
		}

		public static void addUndecidedUser(final UserVO userVO, final UserVO addUser) {
			System.out.println("Mocked addUndecidedUser");
			addMessage(userVO, "Add user");
			undecidedUsersMap.get(userVO).remove(addUser);
			if(!partners.containsKey(userVO)) {
				partners.put(userVO, new ArrayList<UserVO>());
			}
			if(!partners.containsKey(addUser)) {
				partners.put(addUser, new ArrayList<UserVO>());
			}
			partners.get(userVO).add(addUser);
			partners.get(addUser).add(userVO);
		}

		public static List<UserVO> partnerList(final UserVO userVO) {
			System.out.println("Mocked partnerList");
			return partners.get(userVO);
		}

		public static List<UserVO> selectablePartners(
				final UserVO userVO) {
			System.out.println("Mocked selectablePartners");
			return users.get(userVO);
		}

		public static void addUserToUndecidedList(final UserVO userVO,
				final UserVO addUser) {
			System.out.println("Mocked addUserToUndecidedList");
			addMessage(userVO, "Add undecided user");
			if(!undecidedUsersMap.containsKey(userVO)) {
				undecidedUsersMap.put(addUser, new ArrayList<UserVO>());
			}
			undecidedUsersMap.get(addUser).add(userVO);
			users.get(addUser).remove(userVO);
		}

		public static void removeUserFromPartnerList(final UserVO userVO,
				final UserVO removeUser) {
			System.out.println("Mocked removeUserFromPartnerList");
			addMessage(userVO, "Remove undecided user");
			partners.get(userVO).remove(removeUser);
		}


		public static List<UserVO> yetUndecidedUsersList(
				final UserVO userVO) {
			System.out.println("Mocked yetUndecidedUsersList");
			List<UserVO> list = new ArrayList<UserVO>();
			Set<Entry<UserVO, List<UserVO>>> entrySet = undecidedUsersMap.entrySet();
			for (Entry<UserVO, List<UserVO>> entry : entrySet) {
				if(entry.getValue().contains(userVO)) {
					list.add(entry.getKey());
				}
			}
			return list;
		}

		public static List<LoanPartVO> equalLoanList(final UserVO userVO) {
			List<LoanPartVO> list = new ArrayList<LoanPartVO>();
			Set<Entry<UserVO, List<LoanPartVO>>> entrySet = equaledLoans.entrySet();
			for (Entry<UserVO, List<LoanPartVO>> entry : entrySet) {
				List<LoanPartVO> value = entry.getValue();
				for (LoanPartVO loanVO : value) {
					if(loanVO.getDebter().equals(userVO)) {
						LoanPartVO loan = new LoanPartVO();
						loan.setEndDate(loanVO.getEndDate());
						loan.setLoanAmount(loanVO.getLoanAmount());
						loan.setDebter(entry.getKey());
						list.add(loan);
					}
				}
			}
			return list;
		}

		public static List<LoanPartVO> acceptedLoanList(final UserVO userVO) {
			List<LoanPartVO> list = new ArrayList<LoanPartVO>();
			Set<Entry<UserVO, List<LoanPartVO>>> entrySet = acceptedLoans.entrySet();
			for (Entry<UserVO, List<LoanPartVO>> entry : entrySet) {
				List<LoanPartVO> value = entry.getValue();
				for (LoanPartVO loanVO : value) {
					if(loanVO.getDebter().equals(userVO)) {
						LoanPartVO loan = new LoanPartVO();
						loan.setEndDate(loanVO.getEndDate());
						loan.setLoanAmount(loanVO.getLoanAmount());
						loan.setDebter(entry.getKey());
						list.add(loan);
					}
				}
			}
			return list;
		}

		public static List<LoanPartVO> rejectedLoanList(final UserVO userVO) {
			List<LoanPartVO> list = new ArrayList<LoanPartVO>();
			Set<Entry<UserVO, List<LoanPartVO>>> entrySet = rejectedLoans.entrySet();
			for (Entry<UserVO, List<LoanPartVO>> entry : entrySet) {
				List<LoanPartVO> value = entry.getValue();
				for (LoanPartVO loanVO : value) {
					if(loanVO.getDebter().equals(userVO)) {
						LoanPartVO loan = new LoanPartVO();
						loan.setEndDate(loanVO.getEndDate());
						loan.setLoanAmount(loanVO.getLoanAmount());
						loan.setDebter(entry.getKey());
						list.add(loan);
					}
				}
			}
			return list;
		}

		public static List<LoanPartVO> waitedLoanList(final UserVO userVO) {
			List<LoanPartVO> list = new ArrayList<LoanPartVO>();
			Set<Entry<UserVO, List<LoanPartVO>>> entrySet = loans2.entrySet();
			for (Entry<UserVO, List<LoanPartVO>> entry : entrySet) {
				List<LoanPartVO> value = entry.getValue();
				for (LoanPartVO loanVO : value) {
					if(loanVO.getDebter().equals(userVO)) {
						LoanPartVO loan = new LoanPartVO();
						loan.setEndDate(loanVO.getEndDate());
						loan.setLoanAmount(loanVO.getLoanAmount());
						loan.setDebter(entry.getKey());
						list.add(loan);
					}
				}
			}
			return list;
		}

		public static void equalLoan(final UserVO user, final LoanPartVO loan) {
			acceptedLoans.get(user).remove(loan);
			if(!equaledLoans.containsKey(user)) {
				equaledLoans.put(user, new ArrayList<LoanPartVO>());
			}
			equaledLoans.get(user).add(loan);
		}

		public static void rejectLoan(final UserVO user, final LoanPartVO loan) {
			addMessage(user, "Loan rejected");
			loans2.get(user).remove(loan);
			if(!rejectedLoans.containsKey(user)) {
				rejectedLoans.put(user, new ArrayList<LoanPartVO>());
			}
			rejectedLoans.get(user).add(loan);
		}

		public static void acceptLoan(final UserVO user, final LoanPartVO loan) {
			loans2.get(user).remove(loan);
			if(!acceptedLoans.containsKey(user)) {
				acceptedLoans.put(user, new ArrayList<LoanPartVO>());
			}
			acceptedLoans.get(user).add(loan);
		}

		public static List<LoanPartVO> acceptableLoanList(final UserVO userVO) {
			if(!loans2.containsKey(userVO)) {
				loans2.put(userVO, new ArrayList<LoanPartVO>());
			}
			return loans2.get(userVO);
		}

		public static List<LoanPartVO> loanList(final UserVO userVO) {
			if(!acceptedLoans.containsKey(userVO)) {
				acceptedLoans.put(userVO, new ArrayList<LoanPartVO>());
			}
			return acceptedLoans.get(userVO);
		}

		public static void addLoan(LoanVO loanVO) {
			
			List<LoanPartVO> loanParts = loanVO.getLoanParts();
			for (LoanPartVO loanPartVO : loanParts) {
				
				UserVO debter = loanPartVO.getDebter();
				if(!loans2.containsKey(debter)) {
					loans2.put(debter, new ArrayList<LoanPartVO>());
				}
				loans2.get(debter).add(loanPartVO);
			}
		}

	}

}
