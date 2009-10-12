package jpl.simle.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.UserService;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;


public class UsersControllerTest
{
    private UsersController controller = new UsersController();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ModelMap modelMap;
    private UserService userService;
    Mockery context = new JUnit4Mockery();
    
    @Before
    public void setUp()
    {
        controller = new UsersController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        modelMap = new ModelMap();
        
        userService = context.mock(UserService.class);
        controller.setUserService(userService);
    }
    
    @Test
    public void shouldListUsers()
    {
        context.checking(new Expectations() {{
            oneOf (userService).findCurrentGroupMembers();
        }});
        assertEquals("user/list", controller.list(modelMap, request, response));
        assertNotNull(modelMap.get("users"));
    }
    
    @Test
    public void shouldGetUser()
    {
        final SIMLEUser user = new SIMLEUser();
        context.checking(new Expectations() {{
            oneOf (userService).findGroupUser("TEST_USER");
                will(returnValue(user));
        }});
                
        assertEquals("user/show", controller.get("TEST_USER", modelMap, request, response));
        assertEquals(user, modelMap.get("user"));
    }
    
    @Test
    public void shouldGetNewUserForm()
    {
        assertEquals("user/new", controller.form(modelMap));
        assertNotNull(modelMap.get("user"));
    }
    
    @Test
    public void shouldCreateNewUser()
    {
        final SIMLEUser user = new SIMLEUser();
        user.setEnabled(true);
        user.setPassword("password");
        user.setUsername("TEST_USER");
        user.setGroup(new SIMLEGroup());
        
        context.checking(new Expectations() {{
            oneOf (userService).setUsersGroup(user);
            oneOf (userService).saveGroupUser(user);
        }});
        
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        
        String redirect = controller.create(user, bindingResult);
        assertFalse(bindingResult.hasErrors());
        assertEquals("redirect:/user/" + user.getId(), redirect);
    }
    
    @Test
    public void shouldDeleteUser() throws Exception
    {
        final SIMLEUser user = new SIMLEUser();
        
        
        context.checking(new Expectations() {{
            oneOf (userService).findGroupUser("TEST_USER");
                will(returnValue(user));
            oneOf (userService).remove(user);
        }});
        
     
        assertEquals("redirect:/user", controller.delete("TEST_USER"));
    }
    
    @Test
    public void shouldProvideUpdateForm()
    {
        final SIMLEUser user = new SIMLEUser();
        user.setUsername("TEST_USER");
        context.checking(new Expectations() {{
            oneOf (userService).findGroupUser("TEST_USER");
                will(returnValue(user));
        }});
        
        assertEquals("user/edit", controller.updateForm(user.getUsername(), modelMap));
        assertEquals(user, modelMap.get("user"));
    }
    
    @Test
    public void shouldUpdateUser()
    {
        final SIMLEUser user = new SIMLEUser();
        user.setUsername("TEST_USER");
        user.setPassword("PASSWORD");
        user.setGroup(new SIMLEGroup());
        
        context.checking(new Expectations() {{
            oneOf (userService).setUsersGroup(user);
            oneOf (userService).saveGroupUser(user);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        assertEquals("redirect:/user/" + user.getId(), controller.update(user, result));
    }
    
    @Test
    public void shouldNotUpdateInvalidUser()
    {
        final SIMLEUser user = new SIMLEUser();
        
        
        context.checking(new Expectations() {{
            oneOf (userService).setUsersGroup(user);
        }});
        
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        assertEquals("user/edit", controller.update(user, result));
        assertTrue(result.hasErrors());
    }
    
    @After
    public void tearDown()
    {
        
    }

}
