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
import jpl.simle.domain.Lab;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Lab_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Lab.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Lab.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Lab.version;    
    
    public Long Lab.getId() {    
        return this.id;        
    }    
    
    public void Lab.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Lab.getVersion() {    
        return this.version;        
    }    
    
    public void Lab.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Lab.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Lab.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Lab attached = this.entityManager.find(Lab.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Lab.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Lab.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Lab merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager Lab.entityManager() {    
        EntityManager em = new Lab().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Lab.countLabs() {    
        return (Long) entityManager().createQuery("select count(o) from Lab o").getSingleResult();        
    }    
    
    public static List<Lab> Lab.findAllLabs() {    
        return entityManager().createQuery("select o from Lab o").getResultList();        
    }    
    
    public static Lab Lab.findLab(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Lab");        
        return entityManager().find(Lab.class, id);        
    }    
    
    public static List<Lab> Lab.findLabEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Lab o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
