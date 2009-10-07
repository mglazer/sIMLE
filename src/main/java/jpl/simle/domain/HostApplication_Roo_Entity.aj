package jpl.simle.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import jpl.simle.domain.HostApplication;
import org.springframework.transaction.annotation.Transactional;

privileged aspect HostApplication_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager HostApplication.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long HostApplication.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer HostApplication.version;    
    
    public Long HostApplication.getId() {    
        return this.id;        
    }    
    
    public void HostApplication.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer HostApplication.getVersion() {    
        return this.version;        
    }    
    
    public void HostApplication.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void HostApplication.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void HostApplication.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            HostApplication attached = this.entityManager.find(HostApplication.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void HostApplication.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void HostApplication.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        HostApplication merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager HostApplication.entityManager() {    
        EntityManager em = new HostApplication().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long HostApplication.countHostApplications() {    
        return (Long) entityManager().createQuery("select count(o) from HostApplication o").getSingleResult();        
    }    
    
    public static List<HostApplication> HostApplication.findAllHostApplications() {    
        return entityManager().createQuery("select o from HostApplication o").getResultList();        
    }    
    
    public static HostApplication HostApplication.findHostApplication(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of HostApplication");        
        return entityManager().find(HostApplication.class, id);        
    }    
    
    public static List<HostApplication> HostApplication.findHostApplicationEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from HostApplication o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
