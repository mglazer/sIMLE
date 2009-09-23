package jpl.simle.domain;

privileged aspect SIMLEUser_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager SIMLEUser.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long SIMLEUser.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer SIMLEUser.version;    
    
    public java.lang.Long SIMLEUser.getId() {    
        return this.id;        
    }    
    
    public void SIMLEUser.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer SIMLEUser.getVersion() {    
        return this.version;        
    }    
    
    public void SIMLEUser.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEUser.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEUser.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            SIMLEUser attached = this.entityManager.find(SIMLEUser.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEUser.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void SIMLEUser.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        SIMLEUser merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager SIMLEUser.entityManager() {    
        javax.persistence.EntityManager em = new SIMLEUser().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long SIMLEUser.countSIMLEUsers() {    
        return (Long) entityManager().createQuery("select count(o) from SIMLEUser o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.SIMLEUser> SIMLEUser.findAllSIMLEUsers() {    
        return entityManager().createQuery("select o from SIMLEUser o").getResultList();        
    }    
    
    public static jpl.simle.domain.SIMLEUser SIMLEUser.findSIMLEUser(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of SIMLEUser");        
        return entityManager().find(SIMLEUser.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.SIMLEUser> SIMLEUser.findSIMLEUserEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from SIMLEUser o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
