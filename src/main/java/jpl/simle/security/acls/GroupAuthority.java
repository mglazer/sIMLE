package jpl.simle.security.acls;

import java.io.Serializable;

import jpl.simle.domain.SIMLEGroup;

import org.springframework.security.core.GrantedAuthority;

public class GroupAuthority implements GrantedAuthority, Comparable<GrantedAuthority>, Serializable
{
    public final static String GROUP_PREFIX = "GROUP_";
    
    private String authority_ = "";
    
    public GroupAuthority(String groupName)
    {
        authority_ = GROUP_PREFIX + groupName;
    }
    
    public GroupAuthority(SIMLEGroup group)
    {
        authority_ = GROUP_PREFIX + group.getGroupName();
    }

    public String getAuthority()
    {
        return authority_;
    }

    public int compareTo(GrantedAuthority o)
    {
        return authority_.compareTo(o.getAuthority());
    }
    
    public String toString()
    {
        return getAuthority();
    }

}
