package jpl.simle.security.acls;

import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;

import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class GroupSid extends GrantedAuthoritySid
{
    
    public GroupSid(String groupName) 
    {
        super(GroupAuthority.GROUP_PREFIX + groupName);
    }
    
    public GroupSid(SIMLEGroup group)
    {
        super(GroupAuthority.GROUP_PREFIX + group.getGroupName());
    }
    
    public GroupSid(GrantedAuthority grantedAuthority)
    {
        super(grantedAuthority);
    }
    
    public String toString()
    {
        return "GroupSid[" + getGrantedAuthority() + "]";
    }
    
}
