package jpl.simle.domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import jpl.simle.domain.Lab;

privileged aspect Host_Roo_Finder {
    
    public static Query Host.findHostsByLab(Lab lab) {    
        if (lab == null) throw new IllegalArgumentException("The lab argument is required");        
        EntityManager em = Host.entityManager();        
        Query q = em.createQuery("SELECT Host FROM Host AS host WHERE host.lab = :lab");        
        q.setParameter("lab", lab);        
        return q;        
    }    
    
}
