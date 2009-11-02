package jpl.simle.domain;

privileged aspect HostApplication_Roo_JavaBean {
    
    public jpl.simle.domain.Host HostApplication.getHost() {    
        return this.host;        
    }    
    
    public void HostApplication.setHost(jpl.simle.domain.Host host) {    
        this.host = host;        
    }    
    
    public jpl.simle.domain.Application HostApplication.getApplication() {    
        return this.application;        
    }    
    
    public void HostApplication.setApplication(jpl.simle.domain.Application application) {    
        this.application = application;        
    }    
    
    public java.lang.String HostApplication.getComment() {    
        return this.comment;        
    }    
    
    public void HostApplication.setComment(java.lang.String comment) {    
        this.comment = comment;        
    }    
    
    public java.util.Date HostApplication.getDateAdded() {    
        return this.dateAdded;        
    }    
    
    public void HostApplication.setDateAdded(java.util.Date dateAdded) {    
        this.dateAdded = dateAdded;        
    }    
    
}
