package jpl.simle.service.impl;

import jpl.simle.service.AuthenticationService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationServiceImpl implements AuthenticationService 
{
	/* (non-Javadoc)
	 * @see jpl.simle.service.impl.AuthenticationService#getAuthenticatedUsername()
	 */
	public String getAuthenticatedUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if ( auth.getPrincipal() instanceof UserDetails )
		{
			return ((UserDetails) auth.getPrincipal()).getUsername();
		}
		else
		{
			return auth.getPrincipal().toString();
		}
		
	}
}
