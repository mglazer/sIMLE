package jpl.simle.service.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;
import jpl.simle.domain.Application;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.AuthenticationService;
import jpl.simle.service.LabManagerService;
import jpl.simle.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.prepost.PreAuthorize;

public class LabManagerServiceImpl implements LabManagerService 
{
	@javax.persistence.PersistenceContext
	private	transient javax.persistence.EntityManager entityManager_;
	
	private UserService userService_;

	private final Logger logger_ = LoggerFactory.getLogger(getClass());
	
	
	/*
	 *****************************
	 * Lab specific functions
	 *****************************
	 */
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#saveLab(jpl.simle.domain.Lab)
	 */
	public Lab saveLab(Lab lab)
	{
		lab.setGroupName(userService_.getAuthenticatedUserGroup().getGroupName());
		
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
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findLabById(java.lang.Long)
	 */
	public Lab findLabById(Long id)
	{
		return Lab.findLabByIdAndGroupname(id, userService_.getAuthenticatedUserGroup().getGroupName());
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findLabs()
	 */
	@SuppressWarnings("unchecked")
	public List<Lab> findLabs()
	{
		return Lab.findLabsByGroupNameEquals(userService_.getAuthenticatedUserGroup().getGroupName()).getResultList();
		//return Lab.findAllLabs();
	}


	/*
	 *******************************
	 * Host specific functions
	 *******************************
	 */
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findHost(java.lang.Long, java.lang.Long)
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
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findHosts(java.lang.Long)
	 */
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
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#saveHost(java.lang.Long, jpl.simle.domain.Host)
	 */
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
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findApplication(java.lang.Long)
	 */
	public Application findApplication(Long applicationId)
	{
		return Application.findApplication(applicationId);
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#saveApplication(jpl.simle.domain.Application)
	 */
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
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findApplications()
	 */
	public List<Application> findApplications()
	{
		return Application.findAllApplications();
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#createHostApplicationLink(jpl.simle.domain.Application, jpl.simle.domain.Host)
	 */
	public HostApplication createHostApplicationLink(Application application, Host host)
	{
		HostApplication ha = new HostApplication();
		
		ha.setApplication(application);
		ha.setHost(host);
		
		ha.persist();
		
		return ha;
	}
	
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#updateHostApplicationLink(jpl.simle.domain.HostApplication, jpl.simle.domain.Application, jpl.simle.domain.Host)
	 */
	public HostApplication updateHostApplicationLink(HostApplication link, Application application, Host host)
	{
		if ( link.getId() == null )
		{
			throw new NoResultException("Host application link ID is null, cannot update it");
		}
			
		link.setApplication(application);
		link.setHost(host);
		
		link.merge();
		
		return link;
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findHostApplicationLink(java.lang.Long)
	 */
	public HostApplication findHostApplicationLink(Long linkId)
	{
		return HostApplication.findHostApplication(linkId);
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findHostApplicationsForHost(jpl.simle.domain.Host)
	 */
	@SuppressWarnings("unchecked")
	public List<HostApplication> findHostApplicationsForHost(Host host)
	{
		return HostApplication.findHostApplicationsByHost(host).getResultList();
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#deleteHostApplicationLink(jpl.simle.domain.HostApplication)
	 */
	public void deleteHostApplicationLink(HostApplication link)
	{
		link.remove();
	}
	
	/*
	 *******************************
	 * Protocol Methods
	 *******************************
	 */
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findProtocolsByApplicationId(java.lang.Long)
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
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#findProtocol(java.lang.Long, java.lang.Long)
	 */
	public Protocol findProtocol(Long applicationId, Long protocolId)
	{
		return Protocol.findProtocol(protocolId);
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#saveProtocol(java.lang.Long, jpl.simle.domain.Protocol)
	 */
	public Protocol saveProtocol(Long appId, Protocol protocol)
	{
		Application app = findApplication(appId);
		
		return saveProtocol(app, protocol);
	}
	
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.LabManagerService#saveProtocol(jpl.simle.domain.Application, jpl.simle.domain.Protocol)
	 */
	public Protocol saveProtocol(Application app, Protocol protocol)
	{
		if ( app == null )
		{
			throw new NoResultException("Application was null, please make sure that it is not");
		}
		
		app.addProtocol(protocol);
		
		protocol.persist();
		
		return protocol;
	}
	
	protected String getUsername() 
	{
		return userService_.getAuthenticatedUsername();
	}
	
	protected SIMLEUser getAuthenticatedUser()
	{
		return SIMLEUser.findUserByUsername(getUsername());
	}

	public void setEntityManager(javax.persistence.EntityManager entityManager) {
		entityManager_ = entityManager;
	}

	public javax.persistence.EntityManager getEntityManager() {
		return entityManager_;
	}
	
	@Autowired
    public void setUserService(UserService userService)
    {
        userService_ = userService;
    }


}
