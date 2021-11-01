package pt.uc.dei.paj.dao;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import pt.uc.dei.paj.entity.Notification;

@Stateless
public class NotificationDao extends AbstractDao<Notification> {
	private static final long serialVersionUID = 1L;

	public NotificationDao() {
		super(Notification.class);
	}
	
	/*
	 * Return notification by Id
	 */
	public Notification findById(int id) {
		try {
			TypedQuery<Notification> notifications = em.createNamedQuery("Notification.findById", Notification.class);
			notifications.setParameter("id", id);
			return notifications.getSingleResult();
		} catch (Exception e) {
			System.out.println("NotificationDao - findById: " + e);
			return null;
		}
	}
}
