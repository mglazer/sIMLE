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
import jpl.simle.domain.SIMLEGroup;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SIMLEGroup_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager SIMLEGroup.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long SIMLEGroup.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer SIMLEGroup.version;    
    
    public Long SIMLEGroup.getId() {    
        return this.id;        
    }    
    
    public void SIMLEGroup.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer SIMLEGroup.getVersion() {    
        return this.version;        
    }    
    
    public void SIMLEGroup.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void SIMLEGroup.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void SIMLEGroup.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            SIMLEGroup attached = this.entityManager.find(SIMLEGroup.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void SIMLEGroup.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void SIMLEGroup.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        SIMLEGroup merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager SIMLEGroup.entityManager() {    
        EntityManager em = new SIMLEGroup().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long SIMLEGroup.countSIMLEGroups() {    
        return (Long) entityManager().createQuery("select count(o) from SIMLEGroup o").getSingleResult();        
    }    
    
    public static List<SIMLEGroup> SIMLEGroup.findAllSIMLEGroups() {    
        return entityManager().createQuery("select o from SIMLEGroup o").getResultList();        
    }    
    
    public static SIMLEGroup SIMLEGroup.findSIMLEGroup(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of SIMLEGroup");        
        return entityManager().find(SIMLEGroup.class, id);        
    }    
    
    public static List<SIMLEGroup> SIMLEGroup.findSIMLEGroupEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from SIMLEGroup o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
