package jpl.simle.domain;

privileged aspect SIMLEUser_Roo_Finder {
    
    public static javax.persistence.Query SIMLEUser.findSIMLEUsersByUsernameEquals(java.lang.String username) {    
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");        
        javax.persistence.EntityManager em = new SIMLEUser().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT SIMLEUser FROM SIMLEUser AS simleuser WHERE simleuser.username = :username");        
        q.setParameter("username", username);        
        return q;        
    }    
    
    public static javax.persistence.Query SIMLEUser.findSIMLEUsersByGroup(jpl.simle.domain.SIMLEGroup group) {    
        if (group == null) throw new IllegalArgumentException("The group argument is required");        
        javax.persistence.EntityManager em = new SIMLEUser().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT SIMLEUser FROM SIMLEUser AS simleuser WHERE simleuser.group = :group");        
        q.setParameter("group", group);        
        return q;        
    }    
    
}
