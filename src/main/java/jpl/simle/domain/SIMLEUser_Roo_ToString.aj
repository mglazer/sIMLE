package jpl.simle.domain;

privileged aspect SIMLEUser_Roo_ToString {
    
    public java.lang.String SIMLEUser.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("username: ").append(getUsername()).append(", ");        
        sb.append("password: ").append(getPassword()).append(", ");        
        sb.append("enabled: ").append(getEnabled()).append(", ");        
        sb.append("series: ").append(getSeries()).append(", ");        
        sb.append("token: ").append(getToken()).append(", ");        
        sb.append("lastUsed: ").append(getLastUsed());        
        return sb.toString();        
    }    
    
}
