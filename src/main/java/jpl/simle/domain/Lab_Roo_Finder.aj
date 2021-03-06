package jpl.simle.domain;

privileged aspect Lab_Roo_Finder {
    
    public static javax.persistence.Query Lab.findLabsByNameEquals(java.lang.String name) {    
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");        
        javax.persistence.EntityManager em = new Lab().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT Lab FROM Lab AS lab WHERE lab.name = :name");        
        q.setParameter("name", name);        
        return q;        
    }    
    
    public static javax.persistence.Query Lab.findLabsByGroupNameEquals(java.lang.String groupName) {    
        if (groupName == null || groupName.length() == 0) throw new IllegalArgumentException("The groupName argument is required");        
        javax.persistence.EntityManager em = new Lab().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT Lab FROM Lab AS lab WHERE lab.groupName = :groupName");        
        q.setParameter("groupName", groupName);        
        return q;        
    }    
    
}
