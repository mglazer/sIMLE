package jpl.simle.domain;

import java.lang.String;

privileged aspect PersistentLogin_Roo_ToString {
    
    public String PersistentLogin.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Series: ").append(getSeries()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Username: ").append(getUsername()).append(", ");        
        sb.append("Token: ").append(getToken()).append(", ");        
        sb.append("Timestamp: ").append(getTimestamp());        
        return sb.toString();        
    }    
    
}
