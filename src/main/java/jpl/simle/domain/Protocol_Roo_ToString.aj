package jpl.simle.domain;

import java.lang.String;

privileged aspect Protocol_Roo_ToString {
    
    public String Protocol.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("PortsList: ").append(getPortsList() == null ? "null" : getPortsList().size()).append(", ");        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Application: ").append(getApplication()).append(", ");        
        sb.append("DestinationIP: ").append(getDestinationIP()).append(", ");        
        sb.append("Direction: ").append(getDirection()).append(", ");        
        sb.append("Ports: ").append(getPorts()).append(", ");        
        sb.append("NetworkProtocol: ").append(getNetworkProtocol()).append(", ");        
        sb.append("ApplicationProtocol: ").append(getApplicationProtocol()).append(", ");        
        sb.append("Notes: ").append(getNotes());        
        return sb.toString();        
    }    
    
}
