package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import services.GmailService;

/**
 *
 * @author Leila Nalivkina
 */
public class ForgotPasswordServlet extends HttpServlet {
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		UserDB userDB = new UserDB();
		User user = userDB.get(email);
		AccountService accService = new AccountService();
		String path = getServletContext().getRealPath("/WEB-INF");

		//check if filed is filled
		if(email == null || email.isEmpty()) {
			session.setAttribute("message", "Please enter your email");
		} else {
			session.setAttribute("message", "If the address you entered is valid, you will receive an email very soon. Please check your email for your password");
		}
		getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);

		//check if user exists in database
		if(email.equals(user.getEmail())) {
			//send an email to the specified email address
			String to = "leya.cheza@gmail.com";
			String subject = user + " forgot their password";
			String template = request.getParameter("forgot");

			try { 
				GmailService.sendMail(to, subject, template, true);
			}catch(Exception e) {
			}
		}

		//to send a new password to the user
		accService.forgotPassword(email, path);
	}
}
