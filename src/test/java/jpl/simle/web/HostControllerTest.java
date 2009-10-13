package jpl.simle.web;



import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.service.LabManagerService;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;


public class HostControllerTest
{
    private HostController controller = new HostController();
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ModelMap modelMap;
    private LabManagerService labManager;
    Mockery context = new JUnit4Mockery();

    @Before
    public void setUp() throws Exception
    {
        controller = new HostController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        modelMap = new ModelMap();
        
        labManager = context.mock(LabManagerService.class);
        controller.setLabManagerService(labManager);
    }
    
    @Test
    public void shouldListHosts()
    {
        final Long labId = new Long(1);
        final ArrayList<Host> hosts = new ArrayList<Host>();
        context.checking(new Expectations() {{
            oneOf (labManager).findHosts(labId);
                will(returnValue(hosts));
        }});
        
        assertEquals("/host/list", controller.list(labId, modelMap));
        assertNotNull(modelMap.get("hosts"));
    }
    
    @Test
    public void shouldGetHost()
    {
        final Long labId = new Long(1);
        final Long hostId = new Long(1);
        final Host host = new Host();
        host.setName("TEST_HOST");
        
        context.checking(new Expectations() {{
            oneOf (labManager).findHost(labId, hostId);
                will(returnValue(host));
        }});
        
        assertEquals("/host/show", controller.get(labId, hostId, modelMap, request, response));
        assertEquals(host, modelMap.get("host"));
    }
    
    @Test
    public void shouldGetNew()
    {
        assertEquals("/host/new", controller.createNew(modelMap, request, response));
        assertNotNull(modelMap.get("host"));
    }
    
    @Test
    public void shouldCreateHost() throws IOException
    {
        final Long labId = new Long(1);
        final Host host = new Host();
        final Lab lab = new Lab();
        
        host.setName("TEST_HOST");
        host.setAddressIP("24.25.24.24");
        host.setDnsNames("abc.123.com");
        host.setId(new Long(1));
        
        context.checking(new Expectations() {{
            oneOf (labManager).findLabById(labId);
                will(returnValue(lab));
            oneOf (labManager).saveHost(labId, host);
                will(returnValue(host));
        }});
        
        BindingResult result = new BeanPropertyBindingResult(host, "host");
        assertEquals(
                "We should be able to create a host but got some errors: " + result.toString(),
                "redirect:/lab/" + labId + "/host/" + host.getId(), 
                controller.create(labId, host, modelMap, request, response, result));
        assertEquals(host, modelMap.get("host"));
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
    }
    
    @Test
    public void shouldNotCreateHostWithInvalidLab() throws IOException
    {
        final Long labId = new Long(1);

        context.checking(new Expectations() {{
            oneOf (labManager).findLabById(labId);
                will(returnValue(null));
        }});
        
        Host host = new Host();
        BindingResult result = new BeanPropertyBindingResult(host, "host");
        assertEquals("/host/new", 
                controller.create(labId, host, modelMap, request, response, result));
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }
    
    @Test
    public void shouldNotCreateInvalidHost() throws IOException
    {
        final Long labId = new Long(1);
        final Host host = new Host();
        final Lab lab = new Lab();
        // hostname is invalid because it's null
        
        context.checking(new Expectations() {{
            oneOf (labManager).findLabById(labId);
                will(returnValue(lab));
        }});
        
        BindingResult result = new BeanPropertyBindingResult(host, "host");
        assertEquals("/host/new",
                controller.create(labId, host, modelMap, request, response, result));
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }
    
    @Test
    public void shouldAddApplication() 
    {
        final Host host = new Host();
        final Long hostId = new Long(1);
        final Long labId = new Long(1);
        final Long appId = new Long(1);
        final Application application = new Application();
        final HostApplication link = new HostApplication();
        link.setId(new Long(333));
        
        context.checking(new Expectations() {{
            oneOf (labManager).findHost(labId, hostId);
                will(returnValue(host));
            oneOf (labManager).findApplication(appId);
                will(returnValue(application));
            oneOf (labManager).createHostApplicationLink(application, host);
                will(returnValue(link));
            oneOf (labManager).findApplications();
                will(returnValue(new ArrayList<Application>()));
        }});
        
        controller.addApplication(labId, hostId, appId, modelMap);
        assertNotNull(modelMap.get("applications"));
        assertNotNull(modelMap.get("linkId"));
        assertEquals(link.getId(), modelMap.get("linkId"));
    }
    
    @Test
    public void shouldReturnFirstApplicationIfNoAppIdGiven()
    {
        final Host host = new Host();
        final Long hostId = new Long(1);
        final Long labId = new Long(1);
        final Long appId = new Long(1);
        final Application application = new Application();
        final ArrayList<Application> applications = new ArrayList<Application>();
        application.setId(new Long(444));
        applications.add(application);
        final HostApplication link = new HostApplication();
        link.setId(new Long(333));
        
        context.checking(new Expectations() {{
            oneOf (labManager).findHost(labId, hostId);
                will(returnValue(host));
            oneOf (labManager).findApplications();
                will(returnValue(applications));
            oneOf (labManager).createHostApplicationLink(application, host);
                will(returnValue(link));
            oneOf (labManager).findApplications();
                will(returnValue(applications));
        }});
        
        controller.addApplication(labId, hostId, null, modelMap);
        assertNotNull(modelMap.get("applications"));
        assertNotNull(modelMap.get("linkId"));
        assertEquals(link.getId(), modelMap.get("linkId"));  
    }
    
    public void shouldNotUpdateIfNoApplicationFound()
    {
        final Host host = new Host();
        final Long hostId = new Long(1);
        final Long labId = new Long(1);
        final Long appId = new Long(1);
        final Application application = new Application();
        final ArrayList<Application> applications = new ArrayList<Application>();
        application.setId(new Long(444));
        applications.add(application);
        final HostApplication link = new HostApplication();
        link.setId(new Long(333));
        
        context.checking(new Expectations() {{
            oneOf (labManager).findHost(labId, hostId);
                will(returnValue(host));
            oneOf (labManager).findApplication(appId);
                will(returnValue(null));
        }});
        
        controller.addApplication(labId, hostId, appId, modelMap);
        assertNull(modelMap.get("applications"));
        assertNull(modelMap.get("linkId"));
        assertNotNull(modelMap.get("error"));
    }
    
    @Test
    public void shouldUpdateApplicationConnection()
    {
        final Host host = new Host();
        final Application app = new Application();
        final HostApplication link = new HostApplication();
        final Long hostId = new Long(1);
        final Long appId = new Long(1);
        final Long labId = new Long(1);
        final Long linkId = new Long(1);
        
        context.checking(new Expectations() {{
            oneOf (labManager).findHost(labId, hostId);
                will(returnValue(host));
            oneOf (labManager).findApplication(appId);
                will(returnValue(app));
            oneOf (labManager).findHostApplicationLink(linkId);
                will(returnValue(link));
            oneOf (labManager).updateHostApplicationLink(link, app, host);
        }});
        
        controller.updateApplicationConnection(labId, hostId, linkId, appId, request, modelMap);
        assertEquals("Successfully updated link", modelMap.get("success"));
    }
    
    @Test
    public void shouldDeleteApplicationLink()
    {
        final Host host = new Host();
        final Application app = new Application();
        final HostApplication link = new HostApplication();
        final Long hostId = new Long(1);
        final Long appId = new Long(1);
        final Long labId = new Long(1);
        final Long linkId = new Long(1);
        
        context.checking(new Expectations() {{
            oneOf (labManager).findHost(labId, hostId);
                will(returnValue(host));
            oneOf (labManager).findHostApplicationLink(linkId);
                will(returnValue(link));
            oneOf (labManager).deleteHostApplicationLink(link);
        }});
        
        controller.deleteApplicationLink(labId, hostId, linkId, modelMap);
        assertEquals("Successfully deleted link", modelMap.get("success"));
    }

    @After
    public void tearDown() throws Exception
    {
    }

}
