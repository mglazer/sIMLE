package jpl.simle.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect Lab_Roo_Finder {
    
    public static Query Lab.findLabsByNameEquals(String name) {    
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");        
        EntityManager em = Lab.entityManager();        
        Query q = em.createQuery("SELECT Lab FROM Lab AS lab WHERE lab.name = :name");        
        q.setParameter("name", name);        
        return q;        
    }    
    
    public static Query Lab.findLabsByGroupNameEquals(String groupName) {    
        if (groupName == null || groupName.length() == 0) throw new IllegalArgumentException("The groupName argument is required");        
        EntityManager em = Lab.entityManager();        
        Query q = em.createQuery("SELECT Lab FROM Lab AS lab WHERE lab.groupName = :groupName");        
        q.setParameter("groupName", groupName);        
        return q;        
    }    
    
}
