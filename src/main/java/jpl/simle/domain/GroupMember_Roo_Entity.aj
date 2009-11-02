package jpl.simle.domain;

privileged aspect GroupMember_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager GroupMember.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long GroupMember.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer GroupMember.version;    
    
    public java.lang.Long GroupMember.getId() {    
        return this.id;        
    }    
    
    public void GroupMember.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer GroupMember.getVersion() {    
        return this.version;        
    }    
    
    public void GroupMember.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void GroupMember.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void GroupMember.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            GroupMember attached = this.entityManager.find(GroupMember.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void GroupMember.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void GroupMember.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        GroupMember merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager GroupMember.entityManager() {    
        javax.persistence.EntityManager em = new GroupMember().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long GroupMember.countGroupMembers() {    
        return (Long) entityManager().createQuery("select count(o) from GroupMember o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.GroupMember> GroupMember.findAllGroupMembers() {    
        return entityManager().createQuery("select o from GroupMember o").getResultList();        
    }    
    
    public static jpl.simle.domain.GroupMember GroupMember.findGroupMember(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of GroupMember");        
        return entityManager().find(GroupMember.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.GroupMember> GroupMember.findGroupMemberEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from GroupMember o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
