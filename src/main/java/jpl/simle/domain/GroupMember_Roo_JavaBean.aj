package jpl.simle.domain;

import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;

privileged aspect GroupMember_Roo_JavaBean {
    
    public SIMLEGroup GroupMember.getGroup() {    
        return this.group;        
    }    
    
    public void GroupMember.setGroup(SIMLEGroup group) {    
        this.group = group;        
    }    
    
    public SIMLEUser GroupMember.getUser() {    
        return this.user;        
    }    
    
    public void GroupMember.setUser(SIMLEUser user) {    
        this.user = user;        
    }    
    
}
