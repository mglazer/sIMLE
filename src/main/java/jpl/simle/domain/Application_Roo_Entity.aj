package jpl.simle.domain;

privileged aspect Application_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager Application.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long Application.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer Application.version;    
    
    public java.lang.Long Application.getId() {    
        return this.id;        
    }    
    
    public void Application.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer Application.getVersion() {    
        return this.version;        
    }    
    
    public void Application.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Application.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Application.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Application attached = this.entityManager.find(Application.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Application.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Application.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Application merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager Application.entityManager() {    
        javax.persistence.EntityManager em = new Application().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Application.countApplications() {    
        return (Long) entityManager().createQuery("select count(o) from Application o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.Application> Application.findAllApplications() {    
        return entityManager().createQuery("select o from Application o").getResultList();        
    }    
    
    public static jpl.simle.domain.Application Application.findApplication(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Application");        
        return entityManager().find(Application.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.Application> Application.findApplicationEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Application o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
