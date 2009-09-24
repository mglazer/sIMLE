package jpl.simle.domain;

privileged aspect ApplicationIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ApplicationIntegrationTest: @org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class);    
    
    declare @type: ApplicationIntegrationTest: @org.springframework.test.context.ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");    
    
    @org.springframework.beans.factory.annotation.Autowired    
    private jpl.simle.domain.ApplicationDataOnDemand ApplicationIntegrationTest.dod;    
    
    @org.junit.Test    
    public void ApplicationIntegrationTest.testCountApplications() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        long count = jpl.simle.domain.Application.countApplications();        
        junit.framework.Assert.assertTrue("Counter for 'Application' incorrectly reported there were no entries", count > 0);        
    }    
    
    @org.junit.Test    
    public void ApplicationIntegrationTest.testFindApplication() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        java.lang.Long id = dod.getRandomApplication().getId();        
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);        
        jpl.simle.domain.Application obj = jpl.simle.domain.Application.findApplication(id);        
        junit.framework.Assert.assertNotNull("Find method for 'Application' illegally returned null for id '" + id + "'", obj);        
        junit.framework.Assert.assertEquals("Find method for 'Application' returned the incorrect identifier", id, obj.getId());        
    }    
    
    @org.junit.Test    
    public void ApplicationIntegrationTest.testFindAllApplications() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        long count = jpl.simle.domain.Application.countApplications();        
        junit.framework.Assert.assertTrue("Too expensive to perform a find all test for 'Application', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);        
        java.util.List<jpl.simle.domain.Application> result = jpl.simle.domain.Application.findAllApplications();        
        junit.framework.Assert.assertNotNull("Find all method for 'Application' illegally returned null", result);        
        junit.framework.Assert.assertTrue("Find all method for 'Application' failed to return any data", result.size() > 0);        
    }    
    
    @org.junit.Test    
    public void ApplicationIntegrationTest.testFindApplicationEntries() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        long count = jpl.simle.domain.Application.countApplications();        
        if (count > 20) count = 20;        
        java.util.List<jpl.simle.domain.Application> result = jpl.simle.domain.Application.findApplicationEntries(0, (int)count);        
        junit.framework.Assert.assertNotNull("Find entries method for 'Application' illegally returned null", result);        
        junit.framework.Assert.assertEquals("Find entries method for 'Application' returned an incorrect number of entries", count, result.size());        
    }    
    
    @org.junit.Test    
    @org.springframework.transaction.annotation.Transactional    
    public void ApplicationIntegrationTest.testFlush() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        java.lang.Long id = dod.getRandomApplication().getId();        
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);        
        jpl.simle.domain.Application obj = jpl.simle.domain.Application.findApplication(id);        
        junit.framework.Assert.assertNotNull("Find method for 'Application' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyApplication(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.flush();        
        junit.framework.Assert.assertTrue("Version for 'Application' failed to increment on flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @org.junit.Test    
    @org.springframework.transaction.annotation.Transactional    
    public void ApplicationIntegrationTest.testMerge() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        java.lang.Long id = dod.getRandomApplication().getId();        
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);        
        jpl.simle.domain.Application obj = jpl.simle.domain.Application.findApplication(id);        
        junit.framework.Assert.assertNotNull("Find method for 'Application' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyApplication(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.merge();        
        obj.flush();        
        junit.framework.Assert.assertTrue("Version for 'Application' failed to increment on merge and flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @org.junit.Test    
    @org.springframework.transaction.annotation.Transactional    
    public void ApplicationIntegrationTest.testPersist() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        jpl.simle.domain.Application obj = dod.getNewTransientApplication(Integer.MAX_VALUE);        
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to provide a new transient entity", obj);        
        junit.framework.Assert.assertNull("Expected 'Application' identifier to be null", obj.getId());        
        obj.persist();        
        junit.framework.Assert.assertNotNull("Expected 'Application' identifier to no longer be null", obj.getId());        
    }    
    
    @org.junit.Test    
    @org.springframework.transaction.annotation.Transactional    
    public void ApplicationIntegrationTest.testRemove() {    
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());        
        java.lang.Long id = dod.getRandomApplication().getId();        
        junit.framework.Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);        
        jpl.simle.domain.Application obj = jpl.simle.domain.Application.findApplication(id);        
        junit.framework.Assert.assertNotNull("Find method for 'Application' illegally returned null for id '" + id + "'", obj);        
        obj.remove();        
        junit.framework.Assert.assertNull("Failed to remove 'Application' with identifier '" + id + "'", jpl.simle.domain.Application.findApplication(id));        
    }    
    
}
