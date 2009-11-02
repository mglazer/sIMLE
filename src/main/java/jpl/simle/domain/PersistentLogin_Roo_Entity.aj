package jpl.simle.domain;

privileged aspect PersistentLogin_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager PersistentLogin.entityManager;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer PersistentLogin.version;    
    
    public java.lang.Integer PersistentLogin.getVersion() {    
        return this.version;        
    }    
    
    public void PersistentLogin.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void PersistentLogin.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void PersistentLogin.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            PersistentLogin attached = this.entityManager.find(PersistentLogin.class, this.series);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void PersistentLogin.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void PersistentLogin.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        PersistentLogin merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.series = merged.getSeries();        
    }    
    
    public static javax.persistence.EntityManager PersistentLogin.entityManager() {    
        javax.persistence.EntityManager em = new PersistentLogin().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long PersistentLogin.countPersistentLogins() {    
        return (Long) entityManager().createQuery("select count(o) from PersistentLogin o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.PersistentLogin> PersistentLogin.findAllPersistentLogins() {    
        return entityManager().createQuery("select o from PersistentLogin o").getResultList();        
    }    
    
    public static jpl.simle.domain.PersistentLogin PersistentLogin.findPersistentLogin(java.lang.String id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of PersistentLogin");        
        return entityManager().find(PersistentLogin.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.PersistentLogin> PersistentLogin.findPersistentLoginEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from PersistentLogin o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
