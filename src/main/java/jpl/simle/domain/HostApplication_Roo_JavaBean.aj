package jpl.simle.domain;

import java.lang.String;
import java.util.Date;
import jpl.simle.domain.Application;
import jpl.simle.domain.Host;

privileged aspect HostApplication_Roo_JavaBean {
    
    public Host HostApplication.getHost() {    
        return this.host;        
    }    
    
    public void HostApplication.setHost(Host host) {    
        this.host = host;        
    }    
    
    public Application HostApplication.getApplication() {    
        return this.application;        
    }    
    
    public void HostApplication.setApplication(Application application) {    
        this.application = application;        
    }    
    
    public String HostApplication.getComment() {    
        return this.comment;        
    }    
    
    public void HostApplication.setComment(String comment) {    
        this.comment = comment;        
    }    
    
    public Date HostApplication.getDateAdded() {    
        return this.dateAdded;        
    }    
    
    public void HostApplication.setDateAdded(Date dateAdded) {    
        this.dateAdded = dateAdded;        
    }    
    
}
