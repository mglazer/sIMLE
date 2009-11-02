package jpl.simle.domain;

privileged aspect Lab_Roo_ToString {
    
    public java.lang.String Lab.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("location: ").append(getLocation()).append(", ");        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("hosts: ").append(getHosts() == null ? "null" : getHosts().size()).append(", ");        
        sb.append("groupName: ").append(getGroupName()).append(", ");        
        sb.append("name: ").append(getName()).append(", ");        
        sb.append("domainName: ").append(getDomainName()).append(", ");        
        sb.append("latitude: ").append(getLatitude()).append(", ");        
        sb.append("longitude: ").append(getLongitude());        
        return sb.toString();        
    }    
    
}
