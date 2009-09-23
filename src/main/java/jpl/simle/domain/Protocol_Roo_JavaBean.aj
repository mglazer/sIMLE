package jpl.simle.domain;

privileged aspect Protocol_Roo_JavaBean {
    
    public jpl.simle.domain.Application Protocol.getApplication() {    
        return this.application;        
    }    
    
    public void Protocol.setApplication(jpl.simle.domain.Application application) {    
        this.application = application;        
    }    
    
    public java.lang.String Protocol.getDestinationIP() {    
        return this.destinationIP;        
    }    
    
    public void Protocol.setDestinationIP(java.lang.String destinationIP) {    
        this.destinationIP = destinationIP;        
    }    
    
    public java.lang.String Protocol.getDirection() {    
        return this.direction;        
    }    
    
    public void Protocol.setDirection(java.lang.String direction) {    
        this.direction = direction;        
    }    
    
    public java.lang.String Protocol.getPorts() {    
        return this.ports;        
    }    
    
    public void Protocol.setPorts(java.lang.String ports) {    
        this.ports = ports;        
    }    
    
    public java.lang.String Protocol.getNetworkProtocol() {    
        return this.networkProtocol;        
    }    
    
    public void Protocol.setNetworkProtocol(java.lang.String networkProtocol) {    
        this.networkProtocol = networkProtocol;        
    }    
    
    public java.lang.String Protocol.getApplicationProtocol() {    
        return this.applicationProtocol;        
    }    
    
    public void Protocol.setApplicationProtocol(java.lang.String applicationProtocol) {    
        this.applicationProtocol = applicationProtocol;        
    }    
    
    public java.lang.String Protocol.getNotes() {    
        return this.notes;        
    }    
    
    public void Protocol.setNotes(java.lang.String notes) {    
        this.notes = notes;        
    }    
    
}
