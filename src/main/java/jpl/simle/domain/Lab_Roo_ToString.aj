package jpl.simle.domain;

import java.lang.String;

privileged aspect Lab_Roo_ToString {
    
    public String Lab.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Location: ").append(getLocation()).append(", ");        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Hosts: ").append(getHosts() == null ? "null" : getHosts().size()).append(", ");        
        sb.append("GroupName: ").append(getGroupName()).append(", ");        
        sb.append("Name: ").append(getName()).append(", ");        
        sb.append("DomainName: ").append(getDomainName()).append(", ");        
        sb.append("Latitude: ").append(getLatitude()).append(", ");        
        sb.append("Longitude: ").append(getLongitude());        
        return sb.toString();        
    }    
    
}
