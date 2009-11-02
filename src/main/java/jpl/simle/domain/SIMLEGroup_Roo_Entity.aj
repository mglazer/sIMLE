package jpl.simle.domain;

privileged aspect SIMLEGroup_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager SIMLEGroup.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long SIMLEGroup.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer SIMLEGroup.version;    
    
    public java.lang.Long SIMLEGroup.getId() {    
        return this.id;        
    }    
    
    public void SIMLEGroup.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer SIMLEGroup.getVersion() {    
        return this.version;        
    }    
    
    public void SIMLEGroup.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEGroup.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEGroup.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            SIMLEGroup attached = this.entityManager.find(SIMLEGroup.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEGroup.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEGroup.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        SIMLEGroup merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager SIMLEGroup.entityManager() {    
        javax.persistence.EntityManager em = new SIMLEGroup().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long SIMLEGroup.countSIMLEGroups() {    
        return (Long) entityManager().createQuery("select count(o) from SIMLEGroup o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.SIMLEGroup> SIMLEGroup.findAllSIMLEGroups() {    
        return entityManager().createQuery("select o from SIMLEGroup o").getResultList();        
    }    
    
    public static jpl.simle.domain.SIMLEGroup SIMLEGroup.findSIMLEGroup(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of SIMLEGroup");        
        return entityManager().find(SIMLEGroup.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.SIMLEGroup> SIMLEGroup.findSIMLEGroupEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from SIMLEGroup o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
