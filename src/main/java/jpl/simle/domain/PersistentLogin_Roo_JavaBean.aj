package jpl.simle.domain;

import java.lang.String;
import java.util.Date;

privileged aspect PersistentLogin_Roo_JavaBean {
    
    public String PersistentLogin.getUsername() {    
        return this.username;        
    }    
    
    public void PersistentLogin.setUsername(String username) {    
        this.username = username;        
    }    
    
    public String PersistentLogin.getToken() {    
        return this.token;        
    }    
    
    public void PersistentLogin.setToken(String token) {    
        this.token = token;        
    }    
    
    public Date PersistentLogin.getTimestamp() {    
        return this.timestamp;        
    }    
    
    public void PersistentLogin.setTimestamp(Date timestamp) {    
        this.timestamp = timestamp;        
    }    
    
}
