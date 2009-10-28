package jpl.simle.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import jpl.simle.domain.Authority;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.security.acls.GroupAuthority;
import jpl.simle.utils.SIMLEUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class SIMLEUserDetailsService implements UserDetailsService
{
    @javax.persistence.PersistenceContext
    private transient javax.persistence.EntityManager entityManager_;
    
    private final static Logger logger_ = LoggerFactory.getLogger(SIMLEUserDetailsService.class);

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException
    {
        try
        {
            SIMLEUser user = SIMLEUser.findUserByUsername(username);
            List<Authority> authorities = Authority.findAuthoritiesByUsername(username);
            List<GrantedAuthority> grantedAuthorities = SIMLEUtils.createList();
            
            for ( Authority auth : authorities )
            {
                grantedAuthorities.add(new GrantedAuthorityImpl(auth.getAuthority()));
            }
            
            grantedAuthorities.add(new GroupAuthority(user.getGroup()));
            
            user.setAuthorities(grantedAuthorities);
     
            logger_.debug("Returning user: {}", user.getUsername());
            return user;
        }
        catch ( NoResultException e )
        {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public void setEntityManager(javax.persistence.EntityManager entityManager)
    {
        entityManager_ = entityManager;
    }

    public javax.persistence.EntityManager getEntityManager()
    {
        return entityManager_;
    }
}
