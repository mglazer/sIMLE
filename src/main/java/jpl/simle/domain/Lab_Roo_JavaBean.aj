package jpl.simle.domain;

import java.lang.Double;
import java.lang.String;
import java.util.Set;
import jpl.simle.domain.Host;

privileged aspect Lab_Roo_JavaBean {
    
    public Set<Host> Lab.getHosts() {    
        return this.hosts;        
    }    
    
    public void Lab.setHosts(Set<Host> hosts) {    
        this.hosts = hosts;        
    }    
    
    public String Lab.getGroupName() {    
        return this.groupName;        
    }    
    
    public void Lab.setGroupName(String groupName) {    
        this.groupName = groupName;        
    }    
    
    public String Lab.getName() {    
        return this.name;        
    }    
    
    public void Lab.setName(String name) {    
        this.name = name;        
    }    
    
    public String Lab.getDomainName() {    
        return this.domainName;        
    }    
    
    public void Lab.setDomainName(String domainName) {    
        this.domainName = domainName;        
    }    
    
    public Double Lab.getLatitude() {    
        return this.latitude;        
    }    
    
    public void Lab.setLatitude(Double latitude) {    
        this.latitude = latitude;        
    }    
    
    public Double Lab.getLongitude() {    
        return this.longitude;        
    }    
    
    public void Lab.setLongitude(Double longitude) {    
        this.longitude = longitude;        
    }    
    
}
