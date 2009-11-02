package jpl.simle.domain;

privileged aspect PersistentLogin_Roo_ToString {
    
    public java.lang.String PersistentLogin.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("series: ").append(getSeries()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("username: ").append(getUsername()).append(", ");        
        sb.append("token: ").append(getToken()).append(", ");        
        sb.append("timestamp: ").append(getTimestamp());        
        return sb.toString();        
    }    
    
}
