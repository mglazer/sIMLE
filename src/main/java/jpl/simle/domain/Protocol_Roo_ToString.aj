package jpl.simle.domain;

privileged aspect Protocol_Roo_ToString {
    
    public java.lang.String Protocol.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("application: ").append(getApplication()).append(", ");        
        sb.append("destinationIP: ").append(getDestinationIP()).append(", ");        
        sb.append("direction: ").append(getDirection()).append(", ");        
        sb.append("ports: ").append(getPorts()).append(", ");        
        sb.append("networkProtocol: ").append(getNetworkProtocol()).append(", ");        
        sb.append("applicationProtocol: ").append(getApplicationProtocol()).append(", ");        
        sb.append("notes: ").append(getNotes());        
        return sb.toString();        
    }    
    
}
