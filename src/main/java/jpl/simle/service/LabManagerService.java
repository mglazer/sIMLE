package jpl.simle.service;

import java.util.List;

import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;
import jpl.simle.domain.SIMLEUser;

import org.springframework.security.access.prepost.PreAuthorize;

public interface LabManagerService {

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract Lab saveLab(Lab lab);

	public abstract Lab findLabById(Long id);

	public abstract List<Lab> findLabs();

	public abstract Host findHost(Long labId, Long hostId);

	public abstract List<Host> findHosts(Long labId);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract Host saveHost(Long labId, Host host);

	public abstract Application findApplication(Long applicationId);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract Application saveApplication(Application application);

	public abstract List<Application> findApplications();

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract HostApplication createHostApplicationLink(
			Application application, Host host);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract HostApplication updateHostApplicationLink(
			HostApplication link, Application application, Host host);

	public abstract HostApplication findHostApplicationLink(Long linkId);

	public abstract List<HostApplication> findHostApplicationsForHost(Host host);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract void deleteHostApplicationLink(HostApplication link);

	public abstract List<Protocol> findProtocolsByApplicationId(Long id);

	public abstract Protocol findProtocol(Long applicationId, Long protocolId);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract Protocol saveProtocol(Long appId, Protocol protocol);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract Protocol saveProtocol(Application app, Protocol protocol);
	
	public abstract void setUserService(UserService userService);

}