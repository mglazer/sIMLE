package jpl.simle.domain;

import java.lang.String;
import jpl.simle.domain.Lab;

privileged aspect Host_Roo_JavaBean {
    
    public Lab Host.getLab() {    
        return this.lab;        
    }    
    
    public void Host.setLab(Lab lab) {    
        this.lab = lab;        
    }    
    
    public String Host.getName() {    
        return this.name;        
    }    
    
    public void Host.setName(String name) {    
        this.name = name;        
    }    
    
    public String Host.getDnsNames() {    
        return this.dnsNames;        
    }    
    
    public void Host.setDnsNames(String dnsNames) {    
        this.dnsNames = dnsNames;        
    }    
    
    public String Host.getAddressIP() {    
        return this.addressIP;        
    }    
    
    public void Host.setAddressIP(String addressIP) {    
        this.addressIP = addressIP;        
    }    
    
}
