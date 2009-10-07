package jpl.simle.domain;

import java.lang.String;

privileged aspect GroupMember_Roo_ToString {
    
    public String GroupMember.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Group: ").append(getGroup()).append(", ");        
        sb.append("User: ").append(getUser());        
        return sb.toString();        
    }    
    
}
