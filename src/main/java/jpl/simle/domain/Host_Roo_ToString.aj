package jpl.simle.domain;

import java.lang.String;

privileged aspect Host_Roo_ToString {
    
    public String Host.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Lab: ").append(getLab()).append(", ");        
        sb.append("Name: ").append(getName()).append(", ");        
        sb.append("DnsNames: ").append(getDnsNames()).append(", ");        
        sb.append("AddressIP: ").append(getAddressIP());        
        return sb.toString();        
    }    
    
}
