/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;
import db.ifaces.UserManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import pojos.users.User;

/**
 *
 * @author carme
 */
public class JPAUserManager implements UserManager {
    private EntityManager entman;

	@Override
	public void connect() {
		entman = Persistence.createEntityManagerFactory("user-company").createEntityManager();
		entman.getTransaction().begin();
		entman.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		entman.getTransaction().commit();
		
	}

	@Override
	public void disconnect() {
		entman.close();
	}

	@Override
	public void newUser(User u) {
		entman.getTransaction().begin();
		entman.persist(u);
		entman.getTransaction().commit();
	}

	


	@Override
	public User checkPassword(String email, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			Query q = entman.createNativeQuery("SELECT * FROM users WHERE email = ? AND password = ?", User.class);
			q.setParameter(1, email);
			q.setParameter(2, hash);
			return (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException nre) {
			return null;
		}
		return null;
	}
	



}
