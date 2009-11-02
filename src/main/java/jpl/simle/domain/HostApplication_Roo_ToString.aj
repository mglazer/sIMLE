package jpl.simle.domain;

privileged aspect HostApplication_Roo_ToString {
    
    public java.lang.String HostApplication.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("host: ").append(getHost()).append(", ");        
        sb.append("application: ").append(getApplication()).append(", ");        
        sb.append("comment: ").append(getComment()).append(", ");        
        sb.append("dateAdded: ").append(getDateAdded());        
        return sb.toString();        
    }    
    
}
