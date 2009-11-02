package jpl.simle.domain;

privileged aspect Application_Roo_ToString {
    
    public java.lang.String Application.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("protocols: ").append(getProtocols() == null ? "null" : getProtocols().size()).append(", ");        
        sb.append("name: ").append(getName()).append(", ");        
        sb.append("notes: ").append(getNotes()).append(", ");        
        sb.append("addedByUsername: ").append(getAddedByUsername());        
        return sb.toString();        
    }    
    
}
