package pt.uc.dei.paj.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.CredentialException;

import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.entity.User;

@Stateless
public class UserDao extends AbstractDao<User> {
	private static final long serialVersionUID = 1L;

	public UserDao() {
		super(User.class);
	}
	
	/*
	 * Return user by Id
	 */
	public User findById(int id) {
		try {
			TypedQuery<User> users = em.createNamedQuery("User.findById", User.class);
			users.setParameter("id", id);
			return users.getSingleResult();
		} catch (Exception e) {
			System.out.println("UserDao - findById: " + e);
			return null;
		}
	}
	
	/*
	 * Return user by email
	 */
	public User findByEmail(String email) {
		try {
			TypedQuery<User> u = em.createNamedQuery("User.findByEmail", User.class);
			u.setParameter("email", email);
			return u.getSingleResult();
		} catch (Exception e) {
			System.out.println("UserDao.findByEmail > " + e);
			return null;
		}
	}
	
	public User findByToken(String token) {
		try {
			TypedQuery<User> u = em.createNamedQuery("User.findByToken", User.class);
			u.setParameter("token", token);
			return u.getSingleResult();
		} catch (Exception e) {
			System.out.println("UserDao.findByToken > " + e);
			return null;
		}
	}
	
	public User findByEmailAndToken(String email, String token) {
		try {
			TypedQuery<User> u = em.createNamedQuery("User.findByEmailAndToken", User.class);
			u.setParameter("email", email);
			u.setParameter("token", token);
			return u.getSingleResult();
		} catch (Exception e) {
			System.out.println("UserDao.findByEmailAndToken > " + e);
			return null;
		}
	}
	
	public User validateSession(String email, String token) throws CredentialException {
		if (token != null && email != null) {
			User user = findByEmail(email);
			if (user != null && user.getToken().equalsIgnoreCase((token))) {
				return user;
			}
		}
		throw new CredentialException("Authentication failed.");
	}

	public int updateRole(boolean isAdmin, int userDto) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			// create update
			CriteriaUpdate<User> update = cb.createCriteriaUpdate(User.class);

			// set the root class
			Root<?> e = update.from(User.class);

			// set update and where clause
			update.set("isAdmin", isAdmin);

			update.where(cb.equal(e.get("id"), userDto));

			// perform update
			int result = this.em.createQuery(update).executeUpdate();
			return result;

		} catch (Exception e) {
			System.out.println("UserDao - updateRole: " + e);
			return 0;
		}
	}
	
	
	/*
	 * Return all users
	 */
	public List<User> findAll() {
		try {
			TypedQuery<User> users = em.createNamedQuery("User.findAll", User.class);
			return users.getResultList();
		} catch (Exception e) {
			System.out.println("User Dao - findAll: " + e);
			return null;
		}
	}
	
	/*
	 * Return all users with admin flag
	 */
	public List<User> findAllByAdmin(boolean isAdmin) {
		try {
			TypedQuery<User> users = em.createNamedQuery("User.findAllByAdmin", User.class);
			users.setParameter("isAdmin", isAdmin);
			return users.getResultList();
		} catch (Exception e) {
			System.out.println("User Dao - findAllByAdmin: " + e);
			return null;
		}
	}
	
	/*
	 * Return number of users
	 */
	public String countTotalUsers() {
		try {
			Query q = em.createNativeQuery("SELECT COUNT(usr.id) as '# Total Number Users' FROM tp6.user usr;");

			return String.valueOf(q.getSingleResult()); 
		} catch (Exception e) {
			System.out.println("WorkspaceDao - countTotalUsers: " + e);
			return null;
		}
	}
	
}
