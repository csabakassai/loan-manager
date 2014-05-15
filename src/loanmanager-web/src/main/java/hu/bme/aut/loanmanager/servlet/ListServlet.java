package hu.bme.aut.loanmanager.servlet;

import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.ejb.LoanManager;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "listServlet", urlPatterns="/listServlet")
public class ListServlet extends HttpServlet {

	@EJB(name = "LoanManagerEJB!hu.bme.loanmanager.ejb.LoanManager")
	private LoanManager loanManager;

	public ListServlet() {
		super();
		Context jndi;
		try {
			jndi = new InitialContext();
			loanManager = (LoanManager) jndi.lookup("java:global/loanmanager-app-0.0.1-SNAPSHOT/loanmanager-ejb-0.0.1-SNAPSHOT/LoanManagerEJB!hu.bme.loanmanager.ejb.LoanManager");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void doGet(final HttpServletRequest arg0, final HttpServletResponse arg1)
			throws ServletException, IOException {
		processRequest(arg0, arg1);
	}


	@Override
	protected void doPost(final HttpServletRequest arg0, final HttpServletResponse arg1)
			throws ServletException, IOException {
		processRequest(arg0, arg1);
	}

	private void processRequest(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String id = request.getParameter("id");

		System.out.println(username);
		System.out.println(password);

		UserVO userVO = new UserVO();
		userVO.setPassword(password);
		userVO.setUsername(username);
		userVO.setId(UUID.fromString(id));


		ServletOutputStream out = response.getOutputStream();
		try {
			List<LoanPartVO> waitedLoanList = new WebLoanManager().setManager(loanManager).waitedLoanList(userVO);
			for (LoanPartVO loanVO : waitedLoanList) {
				out.print(";"+loanVO.getDebter().getFullName());
				out.print(";"+loanVO.getLoanAmount());
				out.print(";Waited");
			}
			List<LoanPartVO> rejected = new WebLoanManager().setManager(loanManager).rejectedLoanList(userVO);
			for (LoanPartVO loanVO : rejected) {
				out.print(";"+loanVO.getDebter().getFullName());
				out.print(";"+loanVO.getLoanAmount());
				out.print(";Rejected");
			}
			List<LoanPartVO> accepted = new WebLoanManager().setManager(loanManager).acceptedLoanList(userVO);
			for (LoanPartVO loanVO : accepted) {
				out.print(";"+loanVO.getDebter().getFullName());
				out.print(";"+loanVO.getLoanAmount());
				out.print(";Accepted");
			}
			List<LoanPartVO> equaled = new WebLoanManager().setManager(loanManager).equalLoanList(userVO);
			for (LoanPartVO loanVO : equaled) {
				out.print(";"+loanVO.getDebter().getFullName());
				out.print(";"+loanVO.getLoanAmount());
				out.print(";Equaled");
			}
		} catch (Exception e) {
		}

		out.close();

	}

}
