package jpl.simle.domain;

import java.lang.String;

privileged aspect SIMLEUser_Roo_ToString {
    
    public String SIMLEUser.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Username: ").append(getUsername()).append(", ");        
        sb.append("Password: ").append(getPassword()).append(", ");        
        sb.append("Enabled: ").append(getEnabled()).append(", ");        
        sb.append("Group: ").append(getGroup());        
        return sb.toString();        
    }    
    
}
