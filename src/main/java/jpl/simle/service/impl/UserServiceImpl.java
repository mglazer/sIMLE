package jpl.simle.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import jpl.simle.domain.Authority;
import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.security.acls.GroupSid;
import jpl.simle.service.AuthenticationService;
import jpl.simle.service.UserService;
import jpl.simle.service.exception.OperationNotAllowedException;
import jpl.simle.utils.SIMLEUtils;

@Transactional
public class UserServiceImpl implements UserService
{

    private AuthenticationService authenticationService_;
    private final static Logger logger_ = LoggerFactory.getLogger(UserServiceImpl.class);
    
    public SIMLEUser findGroupUser(String username)
    {
        return SIMLEUser.findUserByUsername(username);
    }

    public SIMLEUser saveGroupAdminUser(SIMLEUser user)
    {
        if ( user.getVersion() == null )
        {
            setUsersGroup(user);
            encryptPassword(user);
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
            encryptPassword(user);
            user.persist();

            Authority roleUser = new Authority();
            roleUser.setUsername(user.getUsername());
            roleUser.setAuthority(Authority.AuthorityTypes.ROLE_USER);
            roleUser.persist();
            
            authenticationService_.addPermission(SIMLEUser.class, user, new GroupSid(user.getGroup()), BasePermission.ADMINISTRATION);
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
        } 
        else
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
        return authenticationService_.getAuthenticatedUser();
    }

    public String getAuthenticatedUsername()
    {
        return authenticationService_.getAuthenticatedUsername();
    }

    public SIMLEGroup getAuthenticatedUserGroup()
    {
        return authenticationService_.getAuthenticatedUserGroup();
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

    public SIMLEUser saveEventAdminUser(SIMLEUser user)
    {
        if ( user.getVersion() == null )
        {
            SIMLEGroup eventAdminGroup = getEventAdminGroup();
            user.setGroup(eventAdminGroup);
            encryptPassword(user);
            user.persist();
            
            Authority roleEventAdmin = new Authority();
            roleEventAdmin.setAuthority(Authority.AuthorityTypes.ROLE_EVENT_ADMIN);
            roleEventAdmin.setUsername(user.getUsername());
            
            roleEventAdmin.persist();
        }
        else
        {
            user.merge();
        }
        
        return user;
    }
    
    private SIMLEGroup getEventAdminGroup()
    {
        SIMLEGroup group = null;
        try
        {
            group = SIMLEGroup.findGroupByGroupName(SIMLEGroup.EVENT_ADMIN_GROUP);
        }
        catch ( NoResultException nre )
        {
            logger_.info("We couldn't find the event admin group {}, we'll create it.", SIMLEGroup.EVENT_ADMIN_GROUP);
            group = new SIMLEGroup();
            group.setGroupName(SIMLEGroup.EVENT_ADMIN_GROUP);
            group.persist();
        }
        
        return group;
    }
    
    private SIMLEUser encryptPassword(SIMLEUser user)
    {
        String salt = SIMLEUtils.getRandomHexString(32);
        user.setSalt(salt);
        user.setPassword(authenticationService_.encryptPassword(user.getPassword(), salt));
        return user;
    }

    @Transactional(readOnly=true)
    public List<SIMLEUser> findAuthorizedUsers()
    {
        logger_.info("************************ FIND AUTHORIZED USERS***************************");
        return SIMLEUser.findAllSIMLEUsers();
    }

}
