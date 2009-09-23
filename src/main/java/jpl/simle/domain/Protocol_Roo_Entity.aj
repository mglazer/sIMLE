package jpl.simle.domain;

privileged aspect Protocol_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager Protocol.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long Protocol.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer Protocol.version;    
    
    public java.lang.Long Protocol.getId() {    
        return this.id;        
    }    
    
    public void Protocol.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer Protocol.getVersion() {    
        return this.version;        
    }    
    
    public void Protocol.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Protocol.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Protocol.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Protocol attached = this.entityManager.find(Protocol.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Protocol.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Protocol.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Protocol merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager Protocol.entityManager() {    
        javax.persistence.EntityManager em = new Protocol().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Protocol.countProtocols() {    
        return (Long) entityManager().createQuery("select count(o) from Protocol o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.Protocol> Protocol.findAllProtocols() {    
        return entityManager().createQuery("select o from Protocol o").getResultList();        
    }    
    
    public static jpl.simle.domain.Protocol Protocol.findProtocol(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Protocol");        
        return entityManager().find(Protocol.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.Protocol> Protocol.findProtocolEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Protocol o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
