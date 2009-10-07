package jpl.simle.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect SIMLEUser_Roo_Finder {
    
    public static Query SIMLEUser.findSIMLEUsersByUsernameEquals(String username) {    
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");        
        EntityManager em = SIMLEUser.entityManager();        
        Query q = em.createQuery("SELECT SIMLEUser FROM SIMLEUser AS simleuser WHERE simleuser.username = :username");        
        q.setParameter("username", username);        
        return q;        
    }    
    
}
