package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		String email = request.getParameter("email");
		UserDB userDB = new UserDB();
		User user = userDB.get(email);

		if(email == null || email.isEmpty()) {
			request.setAttribute("message", "Please enter your email");
		} else {
			request.setAttribute("message", "If the address you entered is valid, you will receive an email very soon. Please check your email for your password");
		}

		// save email to a cookie
		Cookie cookie = new Cookie("email", email);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 3);
		response.addCookie(cookie);


		AccountService accService = new AccountService();
		String path = getServletContext().getRealPath("/WEB-INF");

		if(accService.forgotPassword(email, path)) {
			String to = "leya.cheza@gmail.com";
			String from = "password.wanted@sait.ca";
			String subject = user + " forgot their password";
			String template = request.getParameter("template");

			try { 
				GmailService.sendMail(to, subject, template, true);
			}catch(Exception e) {
			}
		}



//		HttpSession session = request.getSession();
		

		getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
	}
}
