package jpl.simle.domain;

privileged aspect Host_Roo_ToString {
    
    public java.lang.String Host.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("lab: ").append(getLab()).append(", ");        
        sb.append("name: ").append(getName()).append(", ");        
        sb.append("dnsNames: ").append(getDnsNames()).append(", ");        
        sb.append("addressIP: ").append(getAddressIP());        
        return sb.toString();        
    }    
    
}
