package jpl.simle.domain;

privileged aspect SIMLEGroup_Roo_Finder {
    
    public static javax.persistence.Query SIMLEGroup.findSIMLEGroupsByGroupNameEquals(java.lang.String groupName) {    
        if (groupName == null || groupName.length() == 0) throw new IllegalArgumentException("The groupName argument is required");        
        javax.persistence.EntityManager em = new SIMLEGroup().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT SIMLEGroup FROM SIMLEGroup AS simlegroup WHERE simlegroup.groupName = :groupName");        
        q.setParameter("groupName", groupName);        
        return q;        
    }    
    
}
