package jpl.simle.web;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import jpl.simle.domain.Application;
import jpl.simle.service.LabManagerService;
import jpl.simle.service.UserService;

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

public class ApplicationControllerTest
{
    private ApplicationController controller = new ApplicationController();
    private HttpServletRequest request;
    private MockHttpServletResponse response;
    private ModelMap modelMap;
    private UserService userService;
    private LabManagerService labManager;
    Mockery context = new JUnit4Mockery();

    @Before
    public void setUp() throws Exception
    {
        controller = new ApplicationController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        modelMap = new ModelMap();
        
        userService = context.mock(UserService.class);
        labManager = context.mock(LabManagerService.class);
        controller.setLabManagerService(labManager);
    }
    
    @Test
    public void shouldListApplications()
    {
        context.checking(new Expectations() {{
            oneOf (labManager).findApplications();
                will(returnValue(new ArrayList<Application>()));
        }});
        
        assertEquals("/application/list", controller.list(modelMap));
        assertNotNull(modelMap.get("applications"));
    }
    
    @Test
    public void shouldGetNewApplication()
    {
        assertEquals("/application/new", controller.newApplication(modelMap, request, response));
        assertNotNull(modelMap.get("application"));
    }
    
    @Test
    public void shouldEditApplication()
    {
        final Long applicationId = new Long(5);
        final Application app = new Application();
        app.setName("APPLICATION");
        context.checking(new Expectations() {{
            oneOf (labManager).findApplication(applicationId);
                will(returnValue(app));
        }});
        
        assertEquals("/application/edit", controller.editApplication(applicationId, modelMap, request, response));
        assertEquals(app, modelMap.get("application"));
    }
    
    @Test
    public void shouldGetApplication()
    {
        final Long applicationId = new Long(5);
        final Application app = new Application();
        app.setName("APPLICATION");
        
        context.checking(new Expectations() {{
            oneOf (labManager).findApplication(applicationId);
                will(returnValue(app));
        }});
        
        assertEquals("/application/show", controller.get(applicationId, modelMap, request, response));
        assertEquals(app, modelMap.get("application"));
    }
    
    @Test
    public void shouldCreateXML() throws IOException
    {
        // note that this test is pretty wonky because of the @RequestBody annotation,
        // I'm not exactly sure how to run tests to ensure that the XML marshalling and
        // unmarshalling work correctly, I guess I just have to assume that everyone else
        // has done the correct thing
        final Application app = new Application();
        app.setName("APPLICATION");
        app.setAddedByUsername("TEST_USER");
        app.setNotes("There are no notes");
        app.setId(new Long(1));
        
        context.checking(new Expectations() {{
            oneOf (labManager).saveApplication(app);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(app, "application");
        assertEquals("redirect:/application/" + app.getId(), controller.createXML(app, modelMap, request, response, result));
        assertEquals(app, modelMap.get("application"));
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
    }
    
    @Test
    public void shouldCreateApplication() throws IOException
    {
        final Application app = new Application();
        app.setName("APPLICATION");
        app.setAddedByUsername("TEST_USER");
        app.setNotes("There are no notes");
        app.setId(new Long(1));
        
        context.checking(new Expectations() {{
            oneOf (labManager).saveApplication(app);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(app, "application");
        assertEquals("redirect:/application/" + app.getId(), controller.create(app, modelMap, request, response, result));
        assertEquals(app, modelMap.get("application"));
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        
    }
    
    @Test
    public void shouldNotCreateInvalidApplication() throws IOException
    {
        final Application app = new Application();
        
        BindingResult result = new BeanPropertyBindingResult(app, "application");
        assertEquals("/application/new", controller.create(app, modelMap, request, response, result));
        assertEquals("Will fail because application name is empty", HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }
    

    @After
    public void tearDown() throws Exception
    {
    }

}
