package jpl.simle.domain;

privileged aspect Lab_Roo_ToString {
    
    public java.lang.String Lab.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("hosts: ").append(getHosts() == null ? "null" : getHosts().size()).append(", ");        
        sb.append("username: ").append(getUsername()).append(", ");        
        sb.append("name: ").append(getName()).append(", ");        
        sb.append("location: ").append(getLocation());        
        return sb.toString();        
    }    
    
}
