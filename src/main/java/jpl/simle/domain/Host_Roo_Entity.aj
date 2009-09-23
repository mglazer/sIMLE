package jpl.simle.domain;

privileged aspect Host_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager Host.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long Host.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer Host.version;    
    
    public java.lang.Long Host.getId() {    
        return this.id;        
    }    
    
    public void Host.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer Host.getVersion() {    
        return this.version;        
    }    
    
    public void Host.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Host.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Host.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Host attached = this.entityManager.find(Host.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Host.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Host.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Host merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager Host.entityManager() {    
        javax.persistence.EntityManager em = new Host().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Host.countHosts() {    
        return (Long) entityManager().createQuery("select count(o) from Host o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.Host> Host.findAllHosts() {    
        return entityManager().createQuery("select o from Host o").getResultList();        
    }    
    
    public static jpl.simle.domain.Host Host.findHost(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Host");        
        return entityManager().find(Host.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.Host> Host.findHostEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Host o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
