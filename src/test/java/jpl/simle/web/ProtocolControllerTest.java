package jpl.simle.web;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.domain.Application;
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

public class ProtocolControllerTest
{
    private ProtocolController controller = new ProtocolController();
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ModelMap modelMap;
    private LabManagerService labManager;
    Mockery context = new JUnit4Mockery();

    @Before
    public void setUp() throws Exception
    {
        controller = new ProtocolController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        modelMap = new ModelMap();
        
        labManager = context.mock(LabManagerService.class);
        controller.setLabManagerService(labManager);
    }
    
    @Test
    public void shouldListProtocols()
    {
        final Long appId = new Long(333);
        
        context.checking(new Expectations() {{
            oneOf (labManager).findProtocolsByApplicationId(appId);
                will(returnValue(new ArrayList<Application>()));
        }});
        
        assertEquals("/protocol/list", controller.list(appId, modelMap, request, response));
        assertNotNull(modelMap.get("protocols"));
    }
    
    @Test
    public void shouldGetProtocol()
    {
        final Long appId = new Long(1);
        final Long protId = new Long(1);
        final Protocol protocol = new Protocol();
        
        context.checking(new Expectations() {{
            oneOf (labManager).findProtocol(appId, protId);
                will(returnValue(protocol));
        }});
        
        assertEquals("/protocol/show", controller.get(appId, protId, modelMap, request, response));
        assertEquals(protocol, modelMap.get("protocol"));
    }
    
    @Test
    public void shouldCreateProtocol() throws IOException
    {
        final Long appId = new Long(1);
        final Application app = new Application();
        final Protocol prot = new Protocol();
        prot.setNetworkProtocol(Protocol.NetworkProtocol.TCP);
        prot.setApplicationProtocol("TEST_PROTOCOL");
        prot.setNotes("No notes");
        prot.setDestinationIP("24.24.24.24");
        prot.setPorts("80,8080");
        prot.setId(new Long(1));
        
        context.checking(new Expectations() {{
            oneOf (labManager).findApplication(appId);
                will(returnValue(app));
            oneOf (labManager).saveProtocol(appId, prot);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(prot, "protocol");
        assertEquals("redirect:/application/" + appId + "/protocol/" + prot.getId(), 
                controller.create(appId, prot, result, modelMap, request, response));
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        assertEquals(prot, modelMap.get("protocol"));
    }
    
    @Test
    public void shouldNotCreateProtocolWithoutApplication() throws IOException
    {
        final Long appId = new Long(666);
        context.checking(new Expectations() {{
            oneOf (labManager).findApplication(appId);
                will(returnValue(null));
        }});
        
        assertEquals("/protocol/new", 
                controller.create(appId, null, null, modelMap, request, response));
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }
    
    @Test
    public void shouldNotCreateInvalidProtocol() throws IOException
    {
        final Long appId = new Long(1);
        final Application app = new Application();
        final Protocol prot = new Protocol();
        
        context.checking(new Expectations() {{
            oneOf (labManager).findApplication(appId);
                will(returnValue(app));
            never (labManager).saveProtocol(appId, prot);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(prot, "protocol");
        assertEquals("/protocol/new",
                controller.create(appId, prot, result, modelMap, request, response));
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    @After
    public void tearDown() throws Exception
    {
    }

}
