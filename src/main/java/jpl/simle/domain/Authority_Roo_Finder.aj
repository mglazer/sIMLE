package jpl.simle.domain;

privileged aspect Authority_Roo_Finder {
    
    public static javax.persistence.Query Authority.findAuthoritysByUsernameEquals(java.lang.String username) {    
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");        
        javax.persistence.EntityManager em = new Authority().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        javax.persistence.Query q = em.createQuery("SELECT Authority FROM Authority AS authority WHERE authority.username = :username");        
        q.setParameter("username", username);        
        return q;        
    }    
    
}
