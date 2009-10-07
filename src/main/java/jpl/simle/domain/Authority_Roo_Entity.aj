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
import jpl.simle.domain.Authority;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Authority_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Authority.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Authority.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Authority.version;    
    
    public Long Authority.getId() {    
        return this.id;        
    }    
    
    public void Authority.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Authority.getVersion() {    
        return this.version;        
    }    
    
    public void Authority.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Authority.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Authority.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Authority attached = this.entityManager.find(Authority.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Authority.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Authority.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Authority merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager Authority.entityManager() {    
        EntityManager em = new Authority().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Authority.countAuthoritys() {    
        return (Long) entityManager().createQuery("select count(o) from Authority o").getSingleResult();        
    }    
    
    public static List<Authority> Authority.findAllAuthoritys() {    
        return entityManager().createQuery("select o from Authority o").getResultList();        
    }    
    
    public static Authority Authority.findAuthority(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Authority");        
        return entityManager().find(Authority.class, id);        
    }    
    
    public static List<Authority> Authority.findAuthorityEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Authority o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
