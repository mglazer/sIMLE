package jpl.simle.domain;

privileged aspect Host_Roo_Finder {
    
    public static javax.persistence.Query Host.findHostsByLab(jpl.simle.domain.Lab lab) {    
        if (lab == null) throw new IllegalArgumentException("The lab argument is required");        
        javax.persistence.EntityManager em = new Host().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT Host FROM Host AS host WHERE host.lab = :lab");        
        q.setParameter("lab", lab);        
        return q;        
    }    
    
}
