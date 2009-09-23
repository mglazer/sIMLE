package jpl.simle.domain;

privileged aspect Lab_Roo_Entity {
    
    @javax.persistence.PersistenceContext    
    transient javax.persistence.EntityManager Lab.entityManager;    
    
    @javax.persistence.Id    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)    
    @javax.persistence.Column(name = "id")    
    private java.lang.Long Lab.id;    
    
    @javax.persistence.Version    
    @javax.persistence.Column(name = "version")    
    private java.lang.Integer Lab.version;    
    
    public java.lang.Long Lab.getId() {    
        return this.id;        
    }    
    
    public void Lab.setId(java.lang.Long id) {    
        this.id = id;        
    }    
    
    public java.lang.Integer Lab.getVersion() {    
        return this.version;        
    }    
    
    public void Lab.setVersion(java.lang.Integer version) {    
        this.version = version;        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Lab.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Lab.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Lab attached = this.entityManager.find(Lab.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Lab.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @org.springframework.transaction.annotation.Transactional    
    public void Lab.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Lab merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static javax.persistence.EntityManager Lab.entityManager() {    
        javax.persistence.EntityManager em = new Lab().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Lab.countLabs() {    
        return (Long) entityManager().createQuery("select count(o) from Lab o").getSingleResult();        
    }    
    
    public static java.util.List<jpl.simle.domain.Lab> Lab.findAllLabs() {    
        return entityManager().createQuery("select o from Lab o").getResultList();        
    }    
    
    public static jpl.simle.domain.Lab Lab.findLab(java.lang.Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Lab");        
        return entityManager().find(Lab.class, id);        
    }    
    
    public static java.util.List<jpl.simle.domain.Lab> Lab.findLabEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Lab o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
