package jpl.simle.domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import jpl.simle.domain.Application;

privileged aspect Protocol_Roo_Finder {
    
    public static Query Protocol.findProtocolsByApplication(Application application) {    
        if (application == null) throw new IllegalArgumentException("The application argument is required");        
        EntityManager em = Protocol.entityManager();        
        Query q = em.createQuery("SELECT Protocol FROM Protocol AS protocol WHERE protocol.application = :application");        
        q.setParameter("application", application);        
        return q;        
    }    
    
}
