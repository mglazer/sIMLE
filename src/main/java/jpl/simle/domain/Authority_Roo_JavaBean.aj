package jpl.simle.domain;

import java.lang.String;

privileged aspect Authority_Roo_JavaBean {
    
    public String Authority.getUsername() {    
        return this.username;        
    }    
    
    public void Authority.setUsername(String username) {    
        this.username = username;        
    }    
    
    public String Authority.getAuthority() {    
        return this.authority;        
    }    
    
    public void Authority.setAuthority(String authority) {    
        this.authority = authority;        
    }    
    
}
