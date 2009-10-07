package jpl.simle.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import jpl.simle.domain.Host;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Host_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Host.entityManager;    
    
    @Version    
    @Column(name = "version")    
    private Integer Host.version;    
    
    public Integer Host.getVersion() {    
        return this.version;        
    }    
    
    public void Host.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Host.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Host.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Host attached = this.entityManager.find(Host.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Host.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Host.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Host merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager Host.entityManager() {    
        EntityManager em = new Host().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Host.countHosts() {    
        return (Long) entityManager().createQuery("select count(o) from Host o").getSingleResult();        
    }    
    
    public static List<Host> Host.findAllHosts() {    
        return entityManager().createQuery("select o from Host o").getResultList();        
    }    
    
    public static Host Host.findHost(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Host");        
        return entityManager().find(Host.class, id);        
    }    
    
    public static List<Host> Host.findHostEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Host o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
