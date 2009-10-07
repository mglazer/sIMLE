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
import jpl.simle.domain.Protocol;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Protocol_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Protocol.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Protocol.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Protocol.version;    
    
    public Long Protocol.getId() {    
        return this.id;        
    }    
    
    public void Protocol.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Protocol.getVersion() {    
        return this.version;        
    }    
    
    public void Protocol.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Protocol.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Protocol.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Protocol attached = this.entityManager.find(Protocol.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Protocol.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Protocol.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Protocol merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager Protocol.entityManager() {    
        EntityManager em = new Protocol().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Protocol.countProtocols() {    
        return (Long) entityManager().createQuery("select count(o) from Protocol o").getSingleResult();        
    }    
    
    public static List<Protocol> Protocol.findAllProtocols() {    
        return entityManager().createQuery("select o from Protocol o").getResultList();        
    }    
    
    public static Protocol Protocol.findProtocol(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Protocol");        
        return entityManager().find(Protocol.class, id);        
    }    
    
    public static List<Protocol> Protocol.findProtocolEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Protocol o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
