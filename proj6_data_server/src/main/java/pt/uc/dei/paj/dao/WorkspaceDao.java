package pt.uc.dei.paj.dao;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.entity.Workspace;

@Stateless
public class WorkspaceDao extends AbstractDao<Workspace> {
	private static final long serialVersionUID = 1L;

	public WorkspaceDao() {
		super(Workspace.class);
	}

	/*
	 * Return workspace by Id
	 */
	public Workspace findById(int id) {
		try {
			TypedQuery<Workspace> workspaces = em.createNamedQuery("Workspace.findById", Workspace.class);
			workspaces.setParameter("id", id);
			return workspaces.getSingleResult();
		} catch (Exception e) {
			System.out.println("WorkspaceDao - findById: " + e);
			return null;
		}
	}

	/*
	 * Return all workspaces
	 */
	public ArrayList<Workspace> findAllWorkspaces() {
		try {
			TypedQuery<Workspace> workspaces = em.createNamedQuery("Workspace.findAll", Workspace.class);
			return (ArrayList<Workspace>) workspaces.getResultList();
		} catch (Exception e) {
			System.out.println("WorkspaceDao - findAllWorkspaces: " + e);
			return null;
		}
	}
	
	
	/*
	 * Return workspaces by userId
	 */
	public ArrayList<Workspace> findWorkspacesByUser(int userId) {
		try {
			TypedQuery<Workspace> workspaces = em.createNamedQuery("Workspace.findWorkspacesByUser", Workspace.class);
			workspaces.setParameter("userId", userId);
			return (ArrayList<Workspace>) workspaces.getResultList();
		} catch (Exception e) {
			System.out.println("WorkspaceDao - findWorkspacesByUser: " + e);
			return null;
		}
	}

	/*
	 * Return workspaces by creator and state
	 */
	public ArrayList<Workspace> findWorkspacesByCreatorAndState(int userId, String isActive) {
		try {
			TypedQuery<Workspace> workspaces = em.createNamedQuery("Workspace.findWorkspacesByCreatorAndState", Workspace.class);
			workspaces.setParameter("userId", userId);
			workspaces.setParameter("isActive", isActive);

			return (ArrayList<Workspace>) workspaces.getResultList();
		} catch (Exception e) {
			System.out.println("WorkspaceDao - findWorkspacesByCreatorAndState: " + e);
			return null;
		}
	}
	
	/*
	 * Return workspaces by userId and state
	 */
	public ArrayList<Workspace> findWorkspacesByUserAndState(User user, String isActive) {
		try {
			boolean isActiveParse = Boolean.parseBoolean(isActive);
			TypedQuery<Workspace> workspaces = em.createNamedQuery("Workspace.findWorkspacesByUserAndState", Workspace.class);
			workspaces.setParameter("user", user);
			workspaces.setParameter("isActive", isActiveParse);

			return (ArrayList<Workspace>) workspaces.getResultList();
		} catch (Exception e) {
			System.out.println("WorkspaceDao - findWorkspacesByUserAndState: " + e);
			return null;
		}
	}
	
	
	/*
	 * Return users by workspaceId
	 */
	public ArrayList<Workspace> findUsersByWorkspace(int workspaceId) {
		try {
			TypedQuery<Workspace> workspaces = em.createNamedQuery("Workspace.findUsersByWorkspace", Workspace.class);
			workspaces.setParameter("workspaceId", workspaceId);

			return  (ArrayList<Workspace>) workspaces.getResultList();
		} catch (Exception e) {
			System.out.println("WorkspaceDao - findUsersByWorkspace: " + e);
			return null;
		}
	}
	
	/*
	 * Return number of workspaces
	 */
	public String countTotalWorkspaces() {
		try {
			Query q = em.createNativeQuery("SELECT COUNT(wrk.id) as '# Total Number Workspaces' FROM tp6.workspace wrk;");

			return String.valueOf(q.getSingleResult()); 
		} catch (Exception e) {
			System.out.println("WorkspaceDao - countTotalWorkspaces: " + e);
			return null;
		}
	}

}
