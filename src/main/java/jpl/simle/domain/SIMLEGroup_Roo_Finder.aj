package jpl.simle.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect SIMLEGroup_Roo_Finder {
    
    public static Query SIMLEGroup.findSIMLEGroupsByGroupNameEquals(String groupName) {    
        if (groupName == null || groupName.length() == 0) throw new IllegalArgumentException("The groupName argument is required");        
        EntityManager em = SIMLEGroup.entityManager();        
        Query q = em.createQuery("SELECT SIMLEGroup FROM SIMLEGroup AS simlegroup WHERE simlegroup.groupName = :groupName");        
        q.setParameter("groupName", groupName);        
        return q;        
    }    
    
}
