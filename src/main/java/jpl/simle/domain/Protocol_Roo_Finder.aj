package jpl.simle.domain;

privileged aspect Protocol_Roo_Finder {
    
    public static javax.persistence.Query Protocol.findProtocolsByApplication(jpl.simle.domain.Application application) {    
        if (application == null) throw new IllegalArgumentException("The application argument is required");        
        javax.persistence.EntityManager em = new Protocol().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT Protocol FROM Protocol AS protocol WHERE protocol.application = :application");        
        q.setParameter("application", application);        
        return q;        
    }    
    
}
