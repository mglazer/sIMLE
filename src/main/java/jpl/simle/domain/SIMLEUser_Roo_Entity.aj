package jpl.simle.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import jpl.simle.domain.SIMLEUser;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SIMLEUser_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager SIMLEUser.entityManager;    
    
    @Version    
    @Column(name = "version")    
    private Integer SIMLEUser.version;    
    
    public Integer SIMLEUser.getVersion() {    
        return this.version;        
    }    
    
    public void SIMLEUser.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void SIMLEUser.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void SIMLEUser.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            SIMLEUser attached = this.entityManager.find(SIMLEUser.class, this.username);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void SIMLEUser.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void SIMLEUser.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        SIMLEUser merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.username = merged.getUsername();        
    }    
    
    public static EntityManager SIMLEUser.entityManager() {    
        EntityManager em = new SIMLEUser().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long SIMLEUser.countSIMLEUsers() {    
        return (Long) entityManager().createQuery("select count(o) from SIMLEUser o").getSingleResult();        
    }    
    
    public static List<SIMLEUser> SIMLEUser.findAllSIMLEUsers() {    
        return entityManager().createQuery("select o from SIMLEUser o").getResultList();        
    }    
    
    public static SIMLEUser SIMLEUser.findSIMLEUser(String id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of SIMLEUser");        
        return entityManager().find(SIMLEUser.class, id);        
    }    
    
    public static List<SIMLEUser> SIMLEUser.findSIMLEUserEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from SIMLEUser o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
