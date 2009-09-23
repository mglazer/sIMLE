package jpl.simle.domain;

privileged aspect Authority_Roo_JavaBean {
    
    public java.lang.String Authority.getUsername() {    
        return this.username;        
    }    
    
    public void Authority.setUsername(java.lang.String username) {    
        this.username = username;        
    }    
    
    public java.lang.String Authority.getAuthority() {    
        return this.authority;        
    }    
    
    public void Authority.setAuthority(java.lang.String authority) {    
        this.authority = authority;        
    }    
    
}
