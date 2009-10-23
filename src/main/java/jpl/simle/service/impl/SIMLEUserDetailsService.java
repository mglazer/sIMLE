package jpl.simle.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import jpl.simle.domain.SIMLEUser;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class SIMLEUserDetailsService implements UserDetailsService
{
    @javax.persistence.PersistenceContext
    private transient javax.persistence.EntityManager entityManager_;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException
    {
        try
        {
            return SIMLEUser.findUserByUsername(username);
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
