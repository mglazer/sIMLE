package jpl.simle.domain;

privileged aspect Application_Roo_JavaBean {
    
    public java.util.Set<jpl.simle.domain.Protocol> Application.getProtocols() {    
        return this.protocols;        
    }    
    
    public void Application.setProtocols(java.util.Set<jpl.simle.domain.Protocol> protocols) {    
        this.protocols = protocols;        
    }    
    
    public java.lang.String Application.getName() {    
        return this.name;        
    }    
    
    public void Application.setName(java.lang.String name) {    
        this.name = name;        
    }    
    
    public java.lang.String Application.getNotes() {    
        return this.notes;        
    }    
    
    public void Application.setNotes(java.lang.String notes) {    
        this.notes = notes;        
    }    
    
    public java.lang.String Application.getAddedByUsername() {    
        return this.addedByUsername;        
    }    
    
    public void Application.setAddedByUsername(java.lang.String addedByUsername) {    
        this.addedByUsername = addedByUsername;        
    }    
    
}
