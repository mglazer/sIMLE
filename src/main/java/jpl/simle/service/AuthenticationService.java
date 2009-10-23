package jpl.simle.service;

import jpl.simle.domain.DomainObject;
import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface AuthenticationService {

	public abstract String getAuthenticatedUsername();
	
	public abstract SIMLEUser getAuthenticatedUser();
	
	public abstract SIMLEGroup getAuthenticatedUserGroup();
	
	public abstract String encryptPassword(String clear);
	
	public abstract String encryptPassword(String clear, String salt);
	
	public abstract <T extends DomainObject> void addPermission(Class<T> clazz, T object, Sid recipient, Permission permission);
	
	public abstract <T extends DomainObject> void deletePermission(Class<T> clazz, T object, Sid recipient, Permission permission);

}