package jpl.simle.domain;

privileged aspect SIMLEUser_Roo_JavaBean {
    
    public java.lang.String SIMLEUser.getUsername() {    
        return this.username;        
    }    
    
    public void SIMLEUser.setUsername(java.lang.String username) {    
        this.username = username;        
    }    
    
    public java.lang.String SIMLEUser.getPassword() {    
        return this.password;        
    }    
    
    public void SIMLEUser.setPassword(java.lang.String password) {    
        this.password = password;        
    }    
    
    public java.lang.Boolean SIMLEUser.getEnabled() {    
        return this.enabled;        
    }    
    
    public void SIMLEUser.setEnabled(java.lang.Boolean enabled) {    
        this.enabled = enabled;        
    }    
    
    public java.lang.String SIMLEUser.getSeries() {    
        return this.series;        
    }    
    
    public void SIMLEUser.setSeries(java.lang.String series) {    
        this.series = series;        
    }    
    
    public java.lang.String SIMLEUser.getToken() {    
        return this.token;        
    }    
    
    public void SIMLEUser.setToken(java.lang.String token) {    
        this.token = token;        
    }    
    
    public java.util.Date SIMLEUser.getLastUsed() {    
        return this.lastUsed;        
    }    
    
    public void SIMLEUser.setLastUsed(java.util.Date lastUsed) {    
        this.lastUsed = lastUsed;        
    }    
    
}
