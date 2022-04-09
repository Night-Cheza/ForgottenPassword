package services;

import dataaccess.UserDB;
import java.util.Arrays;
import java.util.HashMap;
import models.User;

public class AccountService {
    
	public User login(String email, String password) {
		UserDB userDB = new UserDB();

		try {
			User user = userDB.get(email);
			if (password.equals(user.getPassword())) {
				return user;
			}
		} catch (Exception e) {
		}

		return null;
	}

	public boolean forgotPassword(String email, String path) {
		UserDB userDB = new UserDB();
		char[] password = new char[8];

		String capLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialChar = "!@#$";
		String num = "1234567890";
		String mix = capLetters + lowLetters + specialChar + num;

		for(int i = 0; i < 8 ; i++) {
			password[i] = mix.charAt(0 + (int) (Math.random() * mix.length()-1));
		}

		try {
			User user = userDB.get(email);
			String to = user.getEmail();
			String newPassword = Arrays.toString(password);
			user.setPassword(userDB.updatePassword(email, newPassword));
			String subject = "New Password";
			String template = path + "/emailTemplate/forgotPassword.html";

			HashMap<String, String> tags = new HashMap<>();
			tags.put("firstname", user.getFirstName());
			tags.put("lastname", user.getLastName());
			tags.put("password",  user.getPassword());

			GmailService.sendMail(to, subject, template, tags);
			return true;
		} catch (Exception e) {
		}

		return false;
	}
}
