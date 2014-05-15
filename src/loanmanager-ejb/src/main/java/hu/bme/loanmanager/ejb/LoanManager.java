
package hu.bme.loanmanager.ejb;

import hu.bme.loanmanager.domain.exception.LoanManagerBusinessException;
import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.io.MessageVO;
import hu.bme.loanmanager.domain.io.UserVO;

import java.util.List;

public interface LoanManager {
	
	public void registerUser( final UserVO request ) throws LoanManagerBusinessException;
	
	//minden esetben hozz letre egy message-t az adott userekhez, minden funkcional

	//login - hibauzenetet ad ha nem jo username password
	public UserVO loginUser(final String userName, String password );

	//uzenetek listaja egy emberhez
	public List<MessageVO> messageList(final UserVO user);

	//mindig az elso parameter a bejelentkezett user

	//meg el nem fogadott emberek listaja akik szeretnenek a partnerunk lenni - ezzel a listaval ter vissza
	public List<UserVO> undecidedUsers(final UserVO userVO);

	//elutasitasa a felkeresnek
	//ekkor torolni kell a felkeresek kozul, illetve ekkor ujra partnerul felveheti egymast a ket fel
	public void rejectPartnership(final UserVO userVO,
			final UserVO removeUser);

	//add hozza mindket userhez egymast, mint partnerek
	public void acceptPartnership(final UserVO userVO,
			final UserVO addUser);

	//felkeres kuldese, hogy legyen a partnerunk a masik fel, de ilyenkor csak a felteteles listaba kerul bele
	// a masik userenek, akinek el kell fogadnia majd
	public void partnershipRequest(final UserVO userVO,
			final UserVO addUser);

	//partnereink listaja
	public List<UserVO> partnerList(final UserVO userVO);

	//partnereink kozul toroljuk az adott usert
	//figyelj hogy ilyenkor ujra felveheto kell legyen mind2 felnek
	public void removeUserFromPartnerList(final UserVO userVO,
			final UserVO removeUser);

	//minden olyan felhasznalo aki nem partnerunk es nem vagyunk egymas eldontendo partnerei kozott
	public List<UserVO> selectablePartners(final UserVO userVO);

	//akiknek elkuldtuk a felkeresunket, de meg nem adtak valaszt
	public List<UserVO> yetUndecidedUsersList(final UserVO userVO);

	//tartozasaink listazasa, amivel mi tartozunk
	public List<LoanPartVO> loanList(final UserVO userVO);

	//elfogadhato tartozasaink listazasa
	public List<LoanPartVO> acceptableLoanList(final UserVO userVO);

	//tartozasunk elfogadasa
	public void acceptLoan(final UserVO user, final LoanPartVO loan);

	//tartozasunk elutasitasa
	public void rejectLoan(final UserVO user, final LoanPartVO loan);

	//tartozasunk kiegyenlitese
	public void equalLoan(final UserVO user, final LoanPartVO loan);

	//bejovo penzunkrol van itt szo
	//amiket nem fogadtak el
	public List<LoanPartVO> rejectedLoanList(final UserVO userVO);

	//amiket elfogadtak
	public List<LoanPartVO> acceptedLoanList(final UserVO userVO);

	//amiket kiegyenlitettek
	public List<LoanPartVO> equalLoanList(final UserVO userVO);

	//aki meg semmit sem csinalt, elkuldtuk neki a tartozast
	public List<LoanPartVO> waitedLoanList(final UserVO userVO);

	public void addLoan(LoanVO loanVO);

	public List<UserVO> loadAllUser();


}
