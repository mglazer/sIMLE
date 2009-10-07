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
import jpl.simle.domain.GroupMember;
import org.springframework.transaction.annotation.Transactional;

privileged aspect GroupMember_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager GroupMember.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long GroupMember.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer GroupMember.version;    
    
    public Long GroupMember.getId() {    
        return this.id;        
    }    
    
    public void GroupMember.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer GroupMember.getVersion() {    
        return this.version;        
    }    
    
    public void GroupMember.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void GroupMember.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void GroupMember.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            GroupMember attached = this.entityManager.find(GroupMember.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void GroupMember.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void GroupMember.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        GroupMember merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static EntityManager GroupMember.entityManager() {    
        EntityManager em = new GroupMember().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long GroupMember.countGroupMembers() {    
        return (Long) entityManager().createQuery("select count(o) from GroupMember o").getSingleResult();        
    }    
    
    public static List<GroupMember> GroupMember.findAllGroupMembers() {    
        return entityManager().createQuery("select o from GroupMember o").getResultList();        
    }    
    
    public static GroupMember GroupMember.findGroupMember(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of GroupMember");        
        return entityManager().find(GroupMember.class, id);        
    }    
    
    public static List<GroupMember> GroupMember.findGroupMemberEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from GroupMember o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
