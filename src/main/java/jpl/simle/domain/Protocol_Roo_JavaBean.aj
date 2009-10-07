package jpl.simle.domain;

import java.lang.String;
import jpl.simle.domain.Application;

privileged aspect Protocol_Roo_JavaBean {
    
    public Application Protocol.getApplication() {    
        return this.application;        
    }    
    
    public void Protocol.setApplication(Application application) {    
        this.application = application;        
    }    
    
    public String Protocol.getDestinationIP() {    
        return this.destinationIP;        
    }    
    
    public void Protocol.setDestinationIP(String destinationIP) {    
        this.destinationIP = destinationIP;        
    }    
    
    public String Protocol.getDirection() {    
        return this.direction;        
    }    
    
    public void Protocol.setDirection(String direction) {    
        this.direction = direction;        
    }    
    
    public String Protocol.getPorts() {    
        return this.ports;        
    }    
    
    public void Protocol.setPorts(String ports) {    
        this.ports = ports;        
    }    
    
    public String Protocol.getNetworkProtocol() {    
        return this.networkProtocol;        
    }    
    
    public void Protocol.setNetworkProtocol(String networkProtocol) {    
        this.networkProtocol = networkProtocol;        
    }    
    
    public String Protocol.getApplicationProtocol() {    
        return this.applicationProtocol;        
    }    
    
    public void Protocol.setApplicationProtocol(String applicationProtocol) {    
        this.applicationProtocol = applicationProtocol;        
    }    
    
    public String Protocol.getNotes() {    
        return this.notes;        
    }    
    
    public void Protocol.setNotes(String notes) {    
        this.notes = notes;        
    }    
    
}
