package jpl.simle.domain;

privileged aspect ApplicationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ApplicationDataOnDemand: @org.springframework.stereotype.Component;    
    
    private java.util.Random ApplicationDataOnDemand.rnd = new java.security.SecureRandom();    
    
    private java.util.List<jpl.simle.domain.Application> ApplicationDataOnDemand.data;    
    
    public jpl.simle.domain.Application ApplicationDataOnDemand.getNewTransientApplication(int index) {    
        jpl.simle.domain.Application obj = new jpl.simle.domain.Application();        
        obj.setAddedByUsername("addedByUsername_" + index);        
        obj.setName("name_" + index);        
        obj.setNotes("notes_" + index);        
        return obj;        
    }    
    
    public jpl.simle.domain.Application ApplicationDataOnDemand.getRandomApplication() {    
        init();        
        return data.get(rnd.nextInt(data.size()));        
    }    
    
    public boolean ApplicationDataOnDemand.modifyApplication(jpl.simle.domain.Application obj) {    
        return false;        
    }    
    
    @org.springframework.transaction.annotation.Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)    
    public void ApplicationDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = jpl.simle.domain.Application.findApplicationEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Application' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        for (int i = 0; i < 10; i++) {        
            jpl.simle.domain.Application obj = getNewTransientApplication(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
