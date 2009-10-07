package jpl.simle.domain;

import java.lang.String;

privileged aspect SIMLEGroup_Roo_ToString {
    
    public String SIMLEGroup.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("GroupName: ").append(getGroupName());        
        return sb.toString();        
    }    
    
}
