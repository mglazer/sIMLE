package jpl.simle.domain;

import java.lang.String;
import java.util.Set;
import jpl.simle.domain.Protocol;

privileged aspect Application_Roo_JavaBean {
    
    public Set<Protocol> Application.getProtocols() {    
        return this.protocols;        
    }    
    
    public void Application.setProtocols(Set<Protocol> protocols) {    
        this.protocols = protocols;        
    }    
    
    public String Application.getName() {    
        return this.name;        
    }    
    
    public void Application.setName(String name) {    
        this.name = name;        
    }    
    
    public String Application.getNotes() {    
        return this.notes;        
    }    
    
    public void Application.setNotes(String notes) {    
        this.notes = notes;        
    }    
    
    public String Application.getAddedByUsername() {    
        return this.addedByUsername;        
    }    
    
    public void Application.setAddedByUsername(String addedByUsername) {    
        this.addedByUsername = addedByUsername;        
    }    
    
}
