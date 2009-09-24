package jpl.simle.dao;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

public class AuthenticationDAO 
{
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
