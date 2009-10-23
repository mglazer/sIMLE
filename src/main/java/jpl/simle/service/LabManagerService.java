package jpl.simle.service;

import java.util.List;

import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;
import jpl.simle.domain.SIMLEUser;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Permission;

public interface LabManagerService {

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GROUP_ADMIN')")
	public abstract Lab saveLab(Lab lab);

	public abstract Lab findLabById(Long id);

	public abstract List<Lab> findLabs();
	
	@PreAuthorize("hasPermission(#lab, admin)")
	public abstract void addPermission(Lab lab, String recipientUsername, Permission permission);
	
	@PreAuthorize("hasPermission(#lab, admin)")
	public abstract void deletePermission(Lab lab, String recipientUsername, Permission permission);

	public abstract Host findHost(Long labId, Long hostId);

	public abstract List<Host> findHosts(Long labId);

	@PreAuthorize("hasPermission(#labId, 'jpl.simle.domain.Lab', admin)")
	public abstract Host saveHost(Long labId, Host host);
	
	@PreAuthorize("hasPermission(#lab, admin)")
	public abstract Host saveHost(Lab lab, Host host);

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