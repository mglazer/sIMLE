package jpl.simle.domain;

import java.lang.String;

privileged aspect Application_Roo_ToString {
    
    public String Application.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Protocols: ").append(getProtocols() == null ? "null" : getProtocols().size()).append(", ");        
        sb.append("Name: ").append(getName()).append(", ");        
        sb.append("Notes: ").append(getNotes()).append(", ");        
        sb.append("AddedByUsername: ").append(getAddedByUsername());        
        return sb.toString();        
    }    
    
}
