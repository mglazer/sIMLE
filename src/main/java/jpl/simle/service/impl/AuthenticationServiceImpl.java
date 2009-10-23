package jpl.simle.service.impl;

import java.util.List;

import jpl.simle.domain.DomainObject;
import jpl.simle.domain.Lab;
import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationServiceImpl implements AuthenticationService 
{
    private PasswordEncoder passwordEncoder_;
    private MutableAclService mutableAclService_;
    private final static Logger logger_ = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    
    
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

    public String encryptPassword(String clear, String salt)
    {
        return passwordEncoder_.encodePassword(clear, salt);
    }
	
    @Autowired
    @Required
	public void setPasswordEncoder(PasswordEncoder encoder)
	{
        passwordEncoder_ = encoder;
	}

    public String encryptPassword(String clear)
    {
        return passwordEncoder_.encodePassword(clear, null);
    }

    public <T extends DomainObject> void addPermission(Class<T> clazz, T object, Sid recipient, Permission permission)
    {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz, object.getId());

        try
        {
            acl = (MutableAcl) mutableAclService_.readAclById(oid);
        } catch ( NotFoundException nfe )
        {
            acl = mutableAclService_.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        mutableAclService_.updateAcl(acl);

        logger_.debug("Added permission {} for Sid {} lab {}", new Object[] {permission, recipient, object});
        
    }

    public <T extends DomainObject> void deletePermission(Class<T> clazz, T object, Sid recipient,
            Permission permission)
    {
        ObjectIdentity oid = new ObjectIdentityImpl(clazz, object.getId());
        MutableAcl acl = (MutableAcl) mutableAclService_.readAclById(oid);
        
        // remove all permissions associated with this particular recipient (string equality to KISS)
        List<AccessControlEntry> entries = acl.getEntries();
        
        for ( int i = 0; i < entries.size(); ++i )
        {
            if ( entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission) )
            {
                acl.deleteAce(i);
            }
        }
        
        mutableAclService_.updateAcl(acl);
        
        logger_.debug("Deleted lab {} ACL permissions for recipient {}", new Object[] {object, recipient});   
    }
    
    @Autowired
    public void setMutableAclService(MutableAclService mutableAclService)
    {
        mutableAclService_ = mutableAclService;
    }

    public SIMLEUser getAuthenticatedUser()
    {
        return SIMLEUser.findUserByUsername(getAuthenticatedUsername());
    }

    public SIMLEGroup getAuthenticatedUserGroup()
    {
        SIMLEUser user = getAuthenticatedUser();
        return user == null ? null : user.getGroup();
    }
}
