package jpl.simle.domain;

privileged aspect PersistentLogin_Roo_JavaBean {
    
    public java.lang.String PersistentLogin.getUsername() {    
        return this.username;        
    }    
    
    public void PersistentLogin.setUsername(java.lang.String username) {    
        this.username = username;        
    }    
    
    public java.lang.String PersistentLogin.getToken() {    
        return this.token;        
    }    
    
    public void PersistentLogin.setToken(java.lang.String token) {    
        this.token = token;        
    }    
    
    public java.util.Date PersistentLogin.getTimestamp() {    
        return this.timestamp;        
    }    
    
    public void PersistentLogin.setTimestamp(java.util.Date timestamp) {    
        this.timestamp = timestamp;        
    }    
    
}
