package jpl.simle.web;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Protocol;
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
import org.w3c.dom.Document;

public class LabControllerTest
{
    private LabController controller = new LabController();
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ModelMap modelMap;
    private LabManagerService labManager;
    Mockery context = new JUnit4Mockery();

    @Before
    public void setUp() throws Exception
    {
        controller = new LabController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        modelMap = new ModelMap();
        
        labManager = context.mock(LabManagerService.class);
        controller.setLabManagerService(labManager);
    }
    
    @Test
    public void shouldListLabs()
    {
        context.checking(new Expectations() {{
            oneOf (labManager).findLabs();
                will(returnValue(new ArrayList<Lab>()));
        }});
        
        assertEquals("/lab/list", controller.list(modelMap));
        assertNotNull(modelMap.get("labs"));
    }
    
    @Test 
    public void shouldGetXLS() throws IOException
    {
        final Long labId = new Long(333);
        final Lab lab = new Lab();
        lab.setId(labId);
        
        context.checking(new Expectations() {{
            oneOf (labManager).findLabById(labId);
                will(returnValue(lab));
        }});
        
        assertEquals("labExcelView", controller.getXLS(labId, modelMap, request, response));
        assertEquals(lab, modelMap.get("lab"));
    }
    
    @Test
    public void shouldGetLab() throws IOException
    {
        final Long labId = new Long(333);
        final Lab lab = new Lab();
        lab.setId(labId);
        
        context.checking(new Expectations() {{
            oneOf (labManager).findLabById(labId);
                will(returnValue(lab));
        }});
        
        assertEquals("/lab/show", controller.get(labId, modelMap, request, response));
        assertEquals(lab, modelMap.get("lab"));
    }
    
    @Test
    public void shouldHaveCreateNewPage()
    {
        assertEquals("/lab/new", controller.createNew(request, response, modelMap));
        assertNotNull(modelMap.get("lab"));
    }
    
    @Test
    public void shouldCreateLab() throws IOException
    {
        final Long labId = new Long(333);
        final Lab lab = new Lab();
        lab.setId(labId);
        lab.setName("TEST_LAB");
        lab.setGroupName("TEST_GROUP");
        lab.setDomainName("test.lab.com");
        lab.setLatitude(new Double(55.55));
        lab.setLongitude(new Double(33.33));
        
        context.checking(new Expectations() {{
            oneOf (labManager).saveLab(lab);
                will(returnValue(lab));
        }});
        
        BindingResult result = new BeanPropertyBindingResult(lab, "lab");
        assertEquals(
                "We should save without errors, but there were some: " + result.getAllErrors(),
                "redirect:/lab/" + labId, 
                controller.create(lab, result, modelMap, request, response));
        assertEquals(lab, modelMap.get("lab"));
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
    }
    
    @Test
    public void shouldNotCreateInvalidLab() throws IOException
    {
        final Long labId = new Long(333);
        final Lab lab = new Lab();
        lab.setId(labId);
        // lab has no name
        
        context.checking(new Expectations() {{
            never (labManager).saveLab(lab);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(lab, "lab");
        assertEquals("/lab/new", controller.create(lab, result, modelMap, request, response));
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }
    
    @Test
    public void shouldGetIML() throws IOException
    {
        final Lab lab = new Lab();
        final Long labId = new Long(1);
        lab.setId(labId);
        lab.setName("TEST_LAB");
        lab.setLatitude(new Double(55.55));
        lab.setLongitude(new Double(77.77));
        lab.setDomainName("abc.123.com");
        
        final Host host1 = randomHost();
        final Host host2 = randomHost();
        lab.addHost(host1);
        lab.addHost(host2);
        
        final ArrayList<Application> applications = new ArrayList<Application>();
        applications.add(randomApplication());
        applications.add(randomApplication());
        
        context.checking(new Expectations() {{
            oneOf (labManager).findLabById(labId);
                will(returnValue(lab));
            atLeast(1).of (labManager).findApplications();
                will(returnValue(applications));
            atLeast(1).of (labManager).findHostApplicationsForHost(host1);
                will(returnValue(new ArrayList<HostApplication>()));
            atLeast(1).of (labManager).findHostApplicationsForHost(host2);
                will(returnValue(new ArrayList<HostApplication>()));
        }});
        
        controller.getIML(labId, request, response);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        try
        {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(response.getContentAsByteArray()));
            assertNotNull(doc);
            assertEquals("rdf:rdf", doc.getDocumentElement().getNodeName());
            assertEquals(2, doc.getElementsByTagName("iml:Application").getLength());
            assertEquals(2, doc.getElementsByTagName("iml:NodeDevice").getLength());
        }
        catch (Exception e)
        {
            fail("IML document should have been parseable");
        }
        
    }
    
    private Host randomHost()
    {
        Host host = new Host();
        host.setName("TEST_HOST" + Math.random());
        host.setAddressIP("24.24.24.24");
        host.setDnsNames("host.dns");
        
        return host;
    }
    
    private Application randomApplication()
    {
        Application app = new Application();
        app.setName("TEST_APPLICATION");
        
        Protocol prot = new Protocol();
        prot.setPorts("80,8080");
        app.addProtocol(prot);
        
        return app;
    }

    @After
    public void tearDown() throws Exception
    {
    }

}
