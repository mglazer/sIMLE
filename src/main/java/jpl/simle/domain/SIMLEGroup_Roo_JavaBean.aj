package jpl.simle.domain;

import java.lang.String;

privileged aspect SIMLEGroup_Roo_JavaBean {
    
    public String SIMLEGroup.getGroupName() {    
        return this.groupName;        
    }    
    
    public void SIMLEGroup.setGroupName(String groupName) {    
        this.groupName = groupName;        
    }    
    
}
