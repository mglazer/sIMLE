package jpl.simle.domain;

privileged aspect HostApplication_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager HostApplication.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long HostApplication.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer HostApplication.version;    
    
    public java.lang.Long HostApplication.getId() {    
        return this.id;        
    }    
    
    public void HostApplication.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer HostApplication.getVersion() {    
        return this.version;        
    }    
    
    public void HostApplication.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void HostApplication.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void HostApplication.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            HostApplication attached = this.entityManager.find(HostApplication.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void HostApplication.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void HostApplication.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        HostApplication merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager HostApplication.entityManager() {    
        javax.persistence.EntityManager em = new HostApplication().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long HostApplication.countHostApplications() {    
        return (Long) entityManager().createQuery("select count(o) from HostApplication o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.HostApplication> HostApplication.findAllHostApplications() {    
        return entityManager().createQuery("select o from HostApplication o").getResultList();        
    }    
    
    public static jpl.simle.domain.HostApplication HostApplication.findHostApplication(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of HostApplication");        
        return entityManager().find(HostApplication.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.HostApplication> HostApplication.findHostApplicationEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from HostApplication o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
