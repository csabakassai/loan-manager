package hu.bme.aut.loanmanager.servlet;

import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.ejb.LoanManager;

import java.io.IOException;

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

@WebServlet(name = "loginServlet", urlPatterns="/loginServlet")
public class LoginServlet extends HttpServlet {

	@EJB(name = "LoanManagerEJB!hu.bme.loanmanager.ejb.LoanManager")
	private LoanManager loanManager;

	public LoginServlet() {
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

		System.out.println(username);
		System.out.println(password);

		UserVO userVO = new UserVO();
		userVO.setPassword(password);
		userVO.setUsername(username);

		ServletOutputStream out = response.getOutputStream();
//		try {
//			UserVO login = new WebLoanManager().setManager(loanManager).login(userVO);
//			out.print("OK"+ login.getId().toString());
//		} catch (LoanManagerBusinessException e) {
//			out.print(e.getMessage());
//		} catch (Exception e) {
//			out.print("Error happened with the server");
//		}

		out.close();

	}

}
