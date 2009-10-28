package jpl.simle.service;

import java.util.List;

import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.exception.OperationNotAllowedException;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService 
{
	@PreAuthorize("hasRole('ROLE_GROUP_ADMIN')")
	public abstract List<SIMLEUser> findGroupMembers(SIMLEGroup group);
	
	@PreAuthorize("hasRole('ROLE_GROUP_ADMIN')")
	public abstract SIMLEUser saveGroupUser(SIMLEUser user);
	
	@PreAuthorize("hasRole('ROLE_GROUP_ADMIN')")
	public abstract SIMLEUser findGroupUser(String username);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public abstract SIMLEUser saveGroupAdminUser(SIMLEUser user);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public abstract SIMLEGroup saveGroup(SIMLEGroup group);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public abstract SIMLEUser saveEventAdminUser(SIMLEUser user);
	
	public abstract void setAuthenticationService(AuthenticationService authenticationService);
	
	public SIMLEUser getAuthenticatedUser();
	
	public String getAuthenticatedUsername();
	
	public SIMLEGroup getAuthenticatedUserGroup();

	@PreAuthorize("hasRole('ROLE_GROUP_ADMIN')")
    public List<SIMLEUser> findCurrentGroupMembers();
	
	@PreAuthorize("hasRole('ROLE_GROUP_ADMIN')")
	public void remove(SIMLEUser user) throws OperationNotAllowedException;
	
	public void setUsersGroup(SIMLEUser user);
	
	@PreAuthorize("hasRole('ROLE_GROUP_ADMIN')")
	@PostFilter("hasPermission(filterObject, 'administration') or hasRole('ROLE_ADMIN')")
	public List<SIMLEUser> findAuthorizedUsers();
}
