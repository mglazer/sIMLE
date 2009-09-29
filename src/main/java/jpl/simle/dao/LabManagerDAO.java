package jpl.simle.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;
import jpl.simle.domain.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

public class LabManagerDAO 
{
	@javax.persistence.PersistenceContext
	private	transient javax.persistence.EntityManager entityManager_;
	
	private AuthenticationDAO authenticationDAO_;

	private final Logger logger_ = LoggerFactory.getLogger(getClass());
	
	
	/*
	 *****************************
	 * Lab specific functions
	 *****************************
	 */
	
	public Lab saveLab(Lab lab)
	{
		lab.setUsername(getUsername());
		
		if ( lab.getId() != null )
		{
			lab.persist();
		}
		else
		{
			lab.merge();
		}
		
		return lab;
	}
	
	public Lab findLabById(Long id)
	{
		Query query = entityManager_.createQuery("SELECT Lab from Lab AS lab WHERE lab.id = :id AND lab.username = :username");
		query.setParameter("id", id);
		query.setParameter("username", getUsername());
		
		try
		{
			return (Lab)query.getSingleResult();
		}
		catch (NoResultException nre)
		{
			logger_.error("Could not find a lab with the id: " + id + " for user " + getUsername());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Lab> findLabs()
	{
		return Lab.findLabsByUsernameEquals(getUsername()).getResultList();
		//return Lab.findAllLabs();
	}


	/*
	 *******************************
	 * Host specific functions
	 *******************************
	 */
	
	public Host findHost(Long labId, Long hostId)
	{
		// find the lab first note that since they're supplying
		// the hostID we don't necessarily need to do this, however, we
		// do this because the findLabById method checks for the username information
		// so they will not be able to find the host if they can't get access
		// to the lab
		Lab lab = findLabById(labId);
		
		if ( lab == null )
		{
			throw new NoResultException("No lab found with id " + labId);
		}
		
		Host host = Host.findHost(hostId);
		
		return host;
	}
	
	@SuppressWarnings("unchecked")
	public List<Host> findHosts(Long labId)
	{
		Lab lab = findLabById(labId);
		
		if ( lab == null )
		{
			throw new NoResultException("No lab found with id " + labId);
		}
		
		return Host.findHostsByLab(lab).getResultList();
	}
	
	public Host saveHost(Long labId, Host host)
	{
		Lab lab = findLabById(labId);
		
		if ( lab == null )
		{
			throw new NoResultException("No lab found with id " + labId);
		}
		
		
		if ( host.getId() == null )
		{
			lab.addHost(host);
			host.persist();
		}
		else
		{
			host.merge();
		}
		
		
		return host;
	}
	
	
	/*
	 *******************************
	 * Application Methods
	 *******************************
	 */
	
	public Application findApplication(Long applicationId)
	{
		return Application.findApplication(applicationId);
	}
	
	public Application saveApplication(Application application)
	{
		if ( application.getId() == null )
		{
			application.setAddedByUsername(getUsername());
			application.persist();
		}
		else
		{
			application.merge();
		}
		
		return application;
	}
	
	public List<Application> findApplications()
	{
		return Application.findAllApplications();
	}
	
	public HostApplication createHostApplicationLink(Application application, Host host)
	{
		HostApplication ha = new HostApplication();
		
		ha.setApplication(application);
		ha.setHost(host);
		
		ha.persist();
		
		return ha;
	}
	
	
	public HostApplication updateHostApplicationLink(HostApplication link, Application application, Host host)
	{
		link.setApplication(application);
		link.setHost(host);
		
		link.persist();
		
		return link;
	}
	
	public HostApplication findHostApplicationLink(Long linkId)
	{
		return HostApplication.findHostApplication(linkId);
	}
	
	@SuppressWarnings("unchecked")
	public List<HostApplication> findHostApplicationsForHost(Host host)
	{
		return HostApplication.findHostApplicationsByHost(host).getResultList();
	}
	
	public void deleteHostApplicationLink(HostApplication link)
	{
		link.remove();
	}
	
	/*
	 *******************************
	 * Protocol Methods
	 *******************************
	 */
	
	@SuppressWarnings("unchecked")
	public List<Protocol> findProtocolsByApplicationId(Long id) 
	{
		Application app = findApplication(id);
		
		if ( app == null )
		{
			throw new NoResultException("No application found with id " + id);
		}
		
		return Protocol.findProtocolsByApplication(app).getResultList();
	}
	
	public Protocol findProtocol(Long applicationId, Long protocolId)
	{
		return Protocol.findProtocol(protocolId);
	}
	
	public Protocol saveProtocol(Long appId, Protocol protocol)
	{
		Application app = findApplication(appId);
		
		if ( app == null )
		{
			throw new NoResultException("No protocol found with id " + appId);
		}
		
		app.addProtocol(protocol);
		
		protocol.persist();
		
		return protocol;
	}
	
	protected String getUsername() 
	{
		return authenticationDAO_.getAuthenticatedUsername();
	}

	public void setEntityManager(javax.persistence.EntityManager entityManager) {
		entityManager_ = entityManager;
	}

	public javax.persistence.EntityManager getEntityManager() {
		return entityManager_;
	}
	
	public void setAuthenticationDAO(AuthenticationDAO authenticationDAO)
	{
		authenticationDAO_ = authenticationDAO;
	}


}
