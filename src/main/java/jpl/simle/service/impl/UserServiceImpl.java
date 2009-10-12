package jpl.simle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import jpl.simle.domain.Authority;
import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.AuthenticationService;
import jpl.simle.service.UserService;
import jpl.simle.service.exception.OperationNotAllowedException;

public class UserServiceImpl implements UserService
{

    private AuthenticationService authenticationService_;
    
    public SIMLEUser findGroupUser(String username)
    {
        return SIMLEUser.findSIMLEUser(username);
    }

    public SIMLEUser saveGroupAdminUser(SIMLEUser user)
    {
        if ( user.getVersion() == null )
        {
            setUsersGroup(user);
            user.persist();

            Authority roleGroupAdmin = new Authority();
            roleGroupAdmin.setUsername(user.getUsername());
            roleGroupAdmin
                    .setAuthority(Authority.AuthorityTypes.ROLE_GROUP_ADMIN);
            roleGroupAdmin.persist();
        } 
        else
        {
            user.merge();
        }

        return user;
    }

    public SIMLEUser saveGroupUser(SIMLEUser user)
    {
        if ( user.getVersion() == null )
        {
            setUsersGroup(user);
            user.persist();

            Authority roleUser = new Authority();
            roleUser.setUsername(user.getUsername());
            roleUser.setAuthority(Authority.AuthorityTypes.ROLE_USER);
            roleUser.persist();
        } 
        else
        {
            user.merge();
        }

        return user;
    }

    @SuppressWarnings("unchecked")
    public List<SIMLEUser> findGroupMembers(SIMLEGroup group)
    {
        return SIMLEUser.findSIMLEUsersByGroup(group).getResultList();
    }
    
    public List<SIMLEUser> findCurrentGroupMembers()
    {
        return findGroupMembers(getAuthenticatedUserGroup());
    }

    public SIMLEGroup saveGroup(SIMLEGroup group)
    {
        if ( group.getId() != null )
        {
            group.persist();
        } else
        {
            group.merge();
        }

        return group;
    }

    @Autowired
    public void setAuthenticationService(
            AuthenticationService authenticationService)
    {
        authenticationService_ = authenticationService;
    }

    public SIMLEUser getAuthenticatedUser()
    {
        return SIMLEUser.findUserByUsername(getAuthenticatedUsername());
    }

    public String getAuthenticatedUsername()
    {
        return authenticationService_.getAuthenticatedUsername();
    }

    public SIMLEGroup getAuthenticatedUserGroup()
    {
        return getAuthenticatedUser().getGroup();
    }

    public void remove(SIMLEUser user) throws OperationNotAllowedException
    {
        if ( !user.getGroup().equals(getAuthenticatedUserGroup()) )
        {
            throw new OperationNotAllowedException("You cannot remove a user that's not in your group!");
        }
        
        user.remove();
    }

    public void setUsersGroup(SIMLEUser user)
    {
        user.setGroup(getAuthenticatedUserGroup());
    }

}
