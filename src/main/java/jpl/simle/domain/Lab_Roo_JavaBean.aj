package jpl.simle.domain;

privileged aspect Lab_Roo_JavaBean {
    
    public java.util.Set<jpl.simle.domain.Host> Lab.getHosts() {    
        return this.hosts;        
    }    
    
    public void Lab.setHosts(java.util.Set<jpl.simle.domain.Host> hosts) {    
        this.hosts = hosts;        
    }    
    
    public java.lang.String Lab.getUsername() {    
        return this.username;        
    }    
    
    public void Lab.setUsername(java.lang.String username) {    
        this.username = username;        
    }    
    
    public java.lang.String Lab.getName() {    
        return this.name;        
    }    
    
    public void Lab.setName(java.lang.String name) {    
        this.name = name;        
    }    
    
    public java.lang.String Lab.getLocation() {    
        return this.location;        
    }    
    
    public void Lab.setLocation(java.lang.String location) {    
        this.location = location;        
    }    
    
    public java.lang.String Lab.getDomainName() {    
        return this.domainName;        
    }    
    
    public void Lab.setDomainName(java.lang.String domainName) {    
        this.domainName = domainName;        
    }    
    
}
