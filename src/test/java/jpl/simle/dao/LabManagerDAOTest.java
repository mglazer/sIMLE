package jpl.simle.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	private LabManagerDAO labManager_;

	@Autowired
	public void setLabManagerDAO(LabManagerDAO labManager)
	{
		labManager_ = labManager;
	}
	
	@Before
	public void setUp()
	{
		labManager_.setAuthenticationDAO(new AuthenticationDAO() {
			public String getAuthenticatedUsername() {
				return "user";
			}
		});
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
		assertNotNull(returnedLab.getUsername());
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
	
	@Test
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
		
		labManager_.setAuthenticationDAO(new AuthenticationDAO() {
			public String getAuthenticatedUsername() {
				return "INVALID_USER";
			}
		});
		
		Lab nullLab = labManager_.findLabById(returnedLab.getId());
		assertNull(nullLab);
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
		assertEquals("user", app.getAddedByUsername());
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
	
}
