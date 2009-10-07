package jpl.simle.domain;

import java.lang.String;

privileged aspect HostApplication_Roo_ToString {
    
    public String HostApplication.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Host: ").append(getHost()).append(", ");        
        sb.append("Application: ").append(getApplication()).append(", ");        
        sb.append("Comment: ").append(getComment()).append(", ");        
        sb.append("DateAdded: ").append(getDateAdded());        
        return sb.toString();        
    }    
    
}
