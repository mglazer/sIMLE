package jpl.simle.domain;

privileged aspect SIMLEUser_Roo_JavaBean {
    
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
    
    public jpl.simle.domain.SIMLEGroup SIMLEUser.getGroup() {    
        return this.group;        
    }    
    
    public void SIMLEUser.setGroup(jpl.simle.domain.SIMLEGroup group) {    
        this.group = group;        
    }    
    
    public java.lang.String SIMLEUser.getSalt() {    
        return this.salt;        
    }    
    
    public void SIMLEUser.setSalt(java.lang.String salt) {    
        this.salt = salt;        
    }    
    
    public java.util.Date SIMLEUser.getExpirationDate() {    
        return this.expirationDate;        
    }    
    
    public void SIMLEUser.setExpirationDate(java.util.Date expirationDate) {    
        this.expirationDate = expirationDate;        
    }    
    
}
