package jpl.simle.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import jpl.simle.domain.SIMLEGroup;

privileged aspect SIMLEUser_Roo_Finder {
    
    public static Query SIMLEUser.findSIMLEUsersByUsernameEquals(String username) {    
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");        
        EntityManager em = SIMLEUser.entityManager();        
        Query q = em.createQuery("SELECT SIMLEUser FROM SIMLEUser AS simleuser WHERE simleuser.username = :username");        
        q.setParameter("username", username);        
        return q;        
    }    
    
    public static Query SIMLEUser.findSIMLEUsersByGroup(SIMLEGroup group) {    
        if (group == null) throw new IllegalArgumentException("The group argument is required");        
        EntityManager em = SIMLEUser.entityManager();        
        Query q = em.createQuery("SELECT SIMLEUser FROM SIMLEUser AS simleuser WHERE simleuser.group = :group");        
        q.setParameter("group", group);        
        return q;        
    }    
    
}
