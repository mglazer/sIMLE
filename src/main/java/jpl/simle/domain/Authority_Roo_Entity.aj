package jpl.simle.domain;

privileged aspect Authority_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager Authority.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long Authority.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer Authority.version;    
    
    public java.lang.Long Authority.getId() {    
        return this.id;        
    }    
    
    public void Authority.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer Authority.getVersion() {    
        return this.version;        
    }    
    
    public void Authority.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Authority.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Authority.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Authority attached = this.entityManager.find(Authority.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Authority.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Authority.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Authority merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager Authority.entityManager() {    
        javax.persistence.EntityManager em = new Authority().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Authority.countAuthoritys() {    
        return (Long) entityManager().createQuery("select count(o) from Authority o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.Authority> Authority.findAllAuthoritys() {    
        return entityManager().createQuery("select o from Authority o").getResultList();        
    }    
    
    public static jpl.simle.domain.Authority Authority.findAuthority(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Authority");        
        return entityManager().find(Authority.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.Authority> Authority.findAuthorityEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Authority o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
