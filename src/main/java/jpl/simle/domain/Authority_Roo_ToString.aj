package jpl.simle.domain;

import java.lang.String;

privileged aspect Authority_Roo_ToString {
    
    public String Authority.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Username: ").append(getUsername()).append(", ");        
        sb.append("Authority: ").append(getAuthority());        
        return sb.toString();        
    }    
    
}
