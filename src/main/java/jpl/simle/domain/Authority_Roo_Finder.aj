package jpl.simle.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect Authority_Roo_Finder {
    
    public static Query Authority.findAuthoritysByUsernameEquals(String username) {    
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");        
        EntityManager em = Authority.entityManager();        
        Query q = em.createQuery("SELECT Authority FROM Authority AS authority WHERE authority.username = :username");        
        q.setParameter("username", username);        
        return q;        
    }    
    
}
