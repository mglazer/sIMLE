package jpl.simle.domain;

privileged aspect Authority_Roo_ToString {
    
    public java.lang.String Authority.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("username: ").append(getUsername()).append(", ");        
        sb.append("authority: ").append(getAuthority());        
        return sb.toString();        
    }    
    
}
