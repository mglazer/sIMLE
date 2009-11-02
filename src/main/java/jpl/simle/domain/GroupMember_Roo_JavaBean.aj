package jpl.simle.domain;

privileged aspect GroupMember_Roo_JavaBean {
    
    public jpl.simle.domain.SIMLEGroup GroupMember.getGroup() {    
        return this.group;        
    }    
    
    public void GroupMember.setGroup(jpl.simle.domain.SIMLEGroup group) {    
        this.group = group;        
    }    
    
    public jpl.simle.domain.SIMLEUser GroupMember.getUser() {    
        return this.user;        
    }    
    
    public void GroupMember.setUser(jpl.simle.domain.SIMLEUser user) {    
        this.user = user;        
    }    
    
}
