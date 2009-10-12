package jpl.simle.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;
import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.LabManagerService;
import jpl.simle.service.UserService;
import jpl.simle.service.impl.AuthenticationServiceImpl;
import jpl.simle.service.impl.UserServiceImpl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"applicationContext.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class LabManagerDAOTest
{
	private LabManagerService labManager_;
	private Mockery context = new JUnit4Mockery();
	private UserService userService;

	@Autowired
	public void setLabManagerService(LabManagerService labManager)
	{
		labManager_ = labManager;
	}
	
	@Before
	public void setUp()
	{
	    userService = context.mock(UserService.class);
	    
	    final SIMLEUser user = new SIMLEUser();
	    user.setUsername("TEST_USER");
	    
	    final SIMLEGroup group = new SIMLEGroup();
	    group.setGroupName("TEST_GROUP");
	    
	    user.setGroup(group);

	    context.checking(new Expectations() {{
	        allowing (userService).getAuthenticatedUserGroup();
	            will(returnValue(group));
	        allowing (userService).getAuthenticatedUsername();
	            will(returnValue(user.getUsername()));
	    }});
	    
	    labManager_.setUserService(userService);
		/*
		labManager_.setAuthenticationDAO(new AuthenticationDAO() {
			public String getAuthenticatedUsername() {
				return "user";
			}
		});
		*/
	}
	
	@Test
	public void shouldSaveLab()
	{
		Lab lab = new Lab();
		lab.setLatitude(new Double(24.54));
		lab.setLongitude(new Double(25.34));
		lab.setName("TEST_LAB");
		
		Lab returnedLab = labManager_.saveLab(lab);
		
		assertNotNull(returnedLab.getId());
		assertNotNull(returnedLab.getGroupName());
	}
	
	@Test
	public void shouldFindLab()
	{
		Lab lab = new Lab();
		lab.setLatitude(new Double(25.54));
		lab.setLongitude(new Double(25.35));
		lab.setName("TEST_LAB");
		
		Lab returnedLab = labManager_.saveLab(lab);
		assertNotNull(returnedLab.getId());
		
		lab = labManager_.findLabById(returnedLab.getId());
		
		assertNotNull(lab);
		assertEquals("TEST_LAB", lab.getName());
	}
	
	@Test(expected=NoResultException.class)
	public void shouldNotFindInvalidLab()
	{
		Lab lab = labManager_.findLabById(new Long(999));
		assertNull(lab);
	}
	
	@Test
	public void shouldNotFindLabWithoutUsername()
	{
		Lab lab = new Lab();
		lab.setLatitude(new Double(25.54));
		lab.setLongitude(new Double(25.34));
		lab.setName("TEST_LAB");
		
		Lab returnedLab = labManager_.saveLab(lab);
		assertNotNull(returnedLab.getId());
		
		// yeah, this is sort of messy, but it's the only way to reset our user
		// service mock so that it returns an invalid username 
		context = new JUnit4Mockery();
		
		final UserService bogusUserService = context.mock(UserService.class);
		
		final SIMLEGroup bogusGroup = new SIMLEGroup();
		bogusGroup.setGroupName("INVALID_GROUP");
		
		context.checking(new Expectations() {{
		    oneOf (bogusUserService).getAuthenticatedUsername();
		        will(returnValue("INVALID_USER"));
		    oneOf (bogusUserService).getAuthenticatedUserGroup();
		        will(returnValue(bogusGroup));
		}});
		labManager_.setUserService(bogusUserService);
		
		try
		{
			Lab nullLab = labManager_.findLabById(returnedLab.getId());
			fail("We should not be capable of finding a lab without an ID and an acceptable username");
		}
		catch (Exception e)
		{
			// passes
		}
	}
	
	@Test
	public void shouldNotFindLabIfUserNotInGroup()
	{
		Lab lab = new Lab();
		lab.setLatitude(new Double(25.54));
		lab.setLongitude(new Double(25.34));
		lab.setName("TEST_LAB");

		// the labManager automatically sets the group name on the lab when we save it,
		// so we have to work around this and persist it manually
		lab.setGroupName("INVALID_GROUP");
		lab.persist();
		
		try
		{
			labManager_.findLabById(lab.getId());
			fail("We should not be capable of finding a lab without an ID and when we're not in its group");
		}
		catch (Exception e)
		{
			// passes
		}
	}
	
	@Test
	public void shouldFindLabs()
	{
		for ( int i = 0; i < 10; ++i )
		{
			Lab lab = new Lab();
			lab.setLatitude(new Double(25.54));
			lab.setLongitude(new Double(25.34));
			lab.setName("TEST_LAB_" + i);
			
			assertNotNull(labManager_.saveLab(lab));
		}
		
		assertEquals(10, labManager_.findLabs().size());
	}
	
	private Lab createTestLab()
	{
		Lab lab = new Lab();
		lab.setLatitude(new Double(25.54));
		lab.setLongitude(new Double(25.34));
		lab.setName("TEST_LAB");
		assertNotNull(labManager_.saveLab(lab));
		return lab;
	}
	
	@Test
	public void shouldSaveHost()
	{
		Lab lab = createTestLab();
		Host host = new Host();
		
		host.setName("TEST_HOST");
		host.setAddressIP("123.234.23.23");
		host.setDnsNames("test.host.com");
		
		Host returnedHost = labManager_.saveHost(lab.getId(), host);
		assertNotNull(returnedHost);
		assertNotNull(returnedHost.getId());
		assertNotNull(returnedHost.getLab());
	}
	
	@Test(expected=NoResultException.class)
	public void shouldNotSaveHostWithInvalidLab()
	{
		Host host = new Host();
		host.setName("TEST_HOST");
		host.setAddressIP("123.234.23.23");
		host.setDnsNames("test.host.com");

		labManager_.saveHost(new Long(999), host);
	}
	
	private Host createTestHost(Lab lab)
	{
		Host host = new Host();
		host.setName("TEST_HOST");
		host.setAddressIP("123.234.23.23");
		host.setDnsNames("test.host.com");
		
		return labManager_.saveHost(lab.getId(), host);
	}
	
	@Test
	public void shouldFindHost()
	{
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		Host foundHost = labManager_.findHost(lab.getId(), host.getId());
		assertNotNull(foundHost);
		assertEquals("TEST_HOST", foundHost.getName());
		assertEquals("test.host.com", foundHost.getDnsNames());
	}
	
	@Test(expected=NoResultException.class)
	public void shouldNotFindHostWithInvalidLabId()
	{
		labManager_.findHost(new Long(999), new Long(999));
	}
	
	@Test
	public void shouldNotFindHostWithInvalidHostId()
	{
		Lab lab = createTestLab();
		
		assertNull(labManager_.findHost(lab.getId(), new Long(999)));
	}
	
	@Test
	public void shouldFindHosts()
	{
		Lab lab = createTestLab();
		
		for ( int i = 0; i < 10; ++i )
		{
			Host host = new Host();
			host.setName("TEST_HOST_" + i);
			host.setDnsNames("test" + i + ".host.com");
			host.setAddressIP("234.23.4." + i);
			
			labManager_.saveHost(lab.getId(),host);
		}
		
		assertEquals(10, labManager_.findHosts(lab.getId()).size());
	}
	
	@Test
	public void shouldSaveApplication()
	{
		Application app = new Application();
		app.setName("TEST_APPLICATION");
		app.setNotes("Some notes");
		
		assertNotNull(labManager_.saveApplication(app));
		
		assertNotNull(app.getId());
		assertNotNull(app.getAddedByUsername());
		assertEquals("TEST_USER", app.getAddedByUsername());
	}
	
	@Test
	public void shouldFindApplication()
	{
		Application app = new Application();
		app.setName("TEST_APPLICATION");
		app.setNotes("Some notes");
		
		assertNotNull(labManager_.saveApplication(app));
		
		Application foundApp = labManager_.findApplication(app.getId());
		
		assertNotNull(foundApp);
		assertEquals(app.getId(), foundApp.getId());
		assertEquals("TEST_APPLICATION", foundApp.getName());
		assertEquals("Some notes", foundApp.getNotes());
	}

	@Test
	public void shouldFindApplications()
	{
		for ( int i = 0; i < 10; ++i )
		{
			Application app = new Application();
			app.setName("TEST_APPLICATION_" + i);
			app.setNotes("some notes " + i);
			
			assertNotNull(labManager_.saveApplication(app));
		}
		
		assertEquals(10, labManager_.findApplications().size());
	}
	
	private Application createTestApplication()
	{
		Application app = new Application();
		app.setName("TEST_APPLICATION");
		app.setNotes("Test notes");
		
		assertNotNull(labManager_.saveApplication(app));
		
		return app;
	}
	
	@Test
	public void shouldCreateHostApplicationLink()
	{
		Application app = createTestApplication();
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		HostApplication ha = labManager_.createHostApplicationLink(app, host);
		assertNotNull(ha);
		assertNotNull(ha.getId());
		assertEquals(app, ha.getApplication());
		assertEquals(host,ha.getHost());
	}
	
	@Test
	public void shouldUpdateHostApplicationLink()
	{
		Application app = createTestApplication();
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		HostApplication ha = labManager_.createHostApplicationLink(app, host);
		assertNotNull(ha);
		assertNotNull(ha.getId());
		assertEquals(app, ha.getApplication());
		assertEquals(host, ha.getHost());
		
		Application newApp = new Application();
		newApp.setName("NEW_APPLICATION");
		newApp.setNotes("New app notes");
		
		assertNotNull(labManager_.saveApplication(newApp));
		
		assertNotNull(labManager_.updateHostApplicationLink(ha, newApp, host));
		assertEquals(newApp, ha.getApplication());
		assertEquals(host, ha.getHost());
	}
	
	@Test(expected=NoResultException.class)
	public void shouldNotUpdateHostApplicationLink()
	{
		Application app = createTestApplication();
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		HostApplication ha = new HostApplication();
		labManager_.updateHostApplicationLink(ha, app, host);
	}
	
	@Test
	public void shouldFindHostApplicationLink()
	{
		Application app = createTestApplication();
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		HostApplication ha = labManager_.createHostApplicationLink(app, host);
		assertNotNull(ha);
		
		HostApplication foundHa = labManager_.findHostApplicationLink(ha.getId());
		assertNotNull(foundHa);
		assertEquals(app, foundHa.getApplication());
		assertEquals(host, foundHa.getHost());
	}
	
	@Test
	public void shouldFindHostApplicationsForHost()
	{
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		for ( int i = 0; i < 10; ++i )
		{
			Application app = new Application();
			app.setName("TEST_APPLICATION_" + i);
			app.setNotes("Test notes " + i);
			
			assertNotNull(labManager_.saveApplication(app));
			assertNotNull(labManager_.createHostApplicationLink(app, host));
		}
		
		assertEquals(10, labManager_.findHostApplicationsForHost(host).size());
	}
	
	@Test
	public void shouldRemoveHostApplicationLink()
	{
		Application app = createTestApplication();
		Lab lab = createTestLab();
		Host host = createTestHost(lab);
		
		HostApplication ha = labManager_.createHostApplicationLink(app, host);
		assertNotNull(ha);
		
		Long id = ha.getId();
		
		labManager_.deleteHostApplicationLink(ha);
		
		assertNull(labManager_.findHostApplicationLink(id));
	}
	
	@Test
	public void shouldSaveProtocol()
	{
		Application app = createTestApplication();
		
		Protocol prot = new Protocol();
		prot.setDirection(Protocol.Direction.IN);
		prot.setDestinationIP("25.34.23.54");
		prot.setNetworkProtocol(Protocol.NetworkProtocol.TCP);
		prot.setNotes("Some notes");
		prot.setPorts("234,25,2345");
		
		assertNotNull(labManager_.saveProtocol(app.getId(), prot).getId());
	}
	
	@Test(expected=NoResultException.class)
	public void shouldNotSaveProtocolWithBadApplicationId()
	{
		
		Protocol prot = new Protocol();
		prot.setDirection(Protocol.Direction.IN);
		prot.setDestinationIP("25.34.23.54");
		prot.setNetworkProtocol(Protocol.NetworkProtocol.TCP);
		prot.setNotes("Some notes");
		prot.setPorts("234,25,2345");
		
		assertNotNull(labManager_.saveProtocol(new Long(999), prot).getId());
	}
	
	@Test
	public void shouldFindProtocolsByApplicationId()
	{
		Application app = createTestApplication();
		
		for ( int i = 0; i < 10; ++i )
		{
			Protocol prot = new Protocol();
			prot.setDirection(Protocol.Direction.IN);
			prot.setDestinationIP("25.34.23.54");
			prot.setNetworkProtocol(Protocol.NetworkProtocol.TCP);
			prot.setNotes("Some notes");
			prot.setPorts("234,25,2345," + i);
			
			assertNotNull(labManager_.saveProtocol(app.getId(), prot).getId());
		}
		
		assertEquals(10, labManager_.findProtocolsByApplicationId(app.getId()).size());
	}
	
	@After
	public void tearDown()
	{
		SecurityContextHolder.clearContext();
	}
	
}
