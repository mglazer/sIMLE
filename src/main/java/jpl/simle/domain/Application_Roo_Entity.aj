package jpl.simle.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import jpl.simle.domain.Application;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Application_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Application.entityManager;    
    
    @Version    
    @Column(name = "version")    
    private Integer Application.version;    
    
    public Integer Application.getVersion() {    
        return this.version;        
    }    
    
    public void Application.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Application.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Application.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Application attached = this.entityManager.find(Application.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Application.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Application.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Application merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager Application.entityManager() {    
        EntityManager em = new Application().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Application.countApplications() {    
        return (Long) entityManager().createQuery("select count(o) from Application o").getSingleResult();        
    }    
    
    public static List<Application> Application.findAllApplications() {    
        return entityManager().createQuery("select o from Application o").getResultList();        
    }    
    
    public static Application Application.findApplication(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Application");        
        return entityManager().find(Application.class, id);        
    }    
    
    public static List<Application> Application.findApplicationEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Application o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
