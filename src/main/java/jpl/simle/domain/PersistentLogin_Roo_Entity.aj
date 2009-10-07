package jpl.simle.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import jpl.simle.domain.PersistentLogin;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PersistentLogin_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager PersistentLogin.entityManager;    
    
    @Version    
    @Column(name = "version")    
    private Integer PersistentLogin.version;    
    
    public Integer PersistentLogin.getVersion() {    
        return this.version;        
    }    
    
    public void PersistentLogin.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void PersistentLogin.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void PersistentLogin.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            PersistentLogin attached = this.entityManager.find(PersistentLogin.class, this.series);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void PersistentLogin.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void PersistentLogin.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        PersistentLogin merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.series = merged.getSeries();        
    }    
    
    public static EntityManager PersistentLogin.entityManager() {    
        EntityManager em = new PersistentLogin().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long PersistentLogin.countPersistentLogins() {    
        return (Long) entityManager().createQuery("select count(o) from PersistentLogin o").getSingleResult();        
    }    
    
    public static List<PersistentLogin> PersistentLogin.findAllPersistentLogins() {    
        return entityManager().createQuery("select o from PersistentLogin o").getResultList();        
    }    
    
    public static PersistentLogin PersistentLogin.findPersistentLogin(String id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of PersistentLogin");        
        return entityManager().find(PersistentLogin.class, id);        
    }    
    
    public static List<PersistentLogin> PersistentLogin.findPersistentLoginEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from PersistentLogin o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
