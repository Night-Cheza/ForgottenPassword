package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;


public class UserDB {
	public User get(String email) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();

		try {
			User user = em.find(User.class, email);
			return user;
		} finally {
			em.close();
		}
	}

	public String updatePassword(String email, String newPassword) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();

		try {
			em.getTransaction().begin();
			em.createNamedQuery("User.updatePassword", User.class).setParameter(1, newPassword).executeUpdate();

			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
System.out.println("!! " +  newPassword + " !!");
		return newPassword;
	}
}
