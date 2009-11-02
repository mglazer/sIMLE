package jpl.simle.domain;

privileged aspect HostApplication_Roo_Finder {
    
    public static javax.persistence.Query HostApplication.findHostApplicationsByHost(jpl.simle.domain.Host host) {    
        if (host == null) throw new IllegalArgumentException("The host argument is required");        
        javax.persistence.EntityManager em = new HostApplication().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT HostApplication FROM HostApplication AS hostapplication WHERE hostapplication.host = :host");        
        q.setParameter("host", host);        
        return q;        
    }    
    
}
