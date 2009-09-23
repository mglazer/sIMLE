package jpl.simle.domain;

privileged aspect Host_Roo_JavaBean {
    
    public jpl.simle.domain.Lab Host.getLab() {    
        return this.lab;        
    }    
    
    public void Host.setLab(jpl.simle.domain.Lab lab) {    
        this.lab = lab;        
    }    
    
    public java.lang.String Host.getName() {    
        return this.name;        
    }    
    
    public void Host.setName(java.lang.String name) {    
        this.name = name;        
    }    
    
    public java.lang.String Host.getDnsNames() {    
        return this.dnsNames;        
    }    
    
    public void Host.setDnsNames(java.lang.String dnsNames) {    
        this.dnsNames = dnsNames;        
    }    
    
    public java.lang.String Host.getAddressIP() {    
        return this.addressIP;        
    }    
    
    public void Host.setAddressIP(java.lang.String addressIP) {    
        this.addressIP = addressIP;        
    }    
    
}
