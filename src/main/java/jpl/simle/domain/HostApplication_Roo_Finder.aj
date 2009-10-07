package jpl.simle.domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import jpl.simle.domain.Host;

privileged aspect HostApplication_Roo_Finder {
    
    public static Query HostApplication.findHostApplicationsByHost(Host host) {    
        if (host == null) throw new IllegalArgumentException("The host argument is required");        
        EntityManager em = HostApplication.entityManager();        
        Query q = em.createQuery("SELECT HostApplication FROM HostApplication AS hostapplication WHERE hostapplication.host = :host");        
        q.setParameter("host", host);        
        return q;        
    }    
    
}
