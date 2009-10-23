package jpl.simle.service;

import java.util.List;

import jpl.simle.domain.Authority;
import jpl.simle.domain.SIMLEGroup;
import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.exception.OperationNotAllowedException;
import jpl.simle.service.impl.UserServiceImpl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"applicationContext.xml","applicationContext-acl.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class UserServiceTest
{
    private UserService userService_;
    private AuthenticationService authenticationService_;
    private Mockery context = new JUnit4Mockery();

    private SIMLEUser adminUser;
    private SIMLEGroup adminGroup;
    
    @Before
    public void setUp() throws Exception
    {
        authenticationService_ = context.mock(AuthenticationService.class);
        userService_ = new UserServiceImpl();
        userService_.setAuthenticationService(authenticationService_);
    
        adminUser = new SIMLEUser();
        adminUser.setUsername("admin");
        adminUser.setPassword("password");
        adminUser.setEnabled(true);
        
        adminGroup = new SIMLEGroup();
        adminGroup.setGroupName("admin_group");
        adminGroup.persist();
        
        adminUser.setGroup(adminGroup);
        adminUser.persist();
    }

    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void shouldSaveGroupAdminUser()
    {
        context.checking(new Expectations() {{
            oneOf (authenticationService_).getAuthenticatedUserGroup();
                will(returnValue(adminGroup));
            oneOf (authenticationService_).encryptPassword(with(equal("password")), with(any(String.class)));
                will(returnValue("encrypted_password"));
        }});
        
        SIMLEUser user = new SIMLEUser();
        user.setUsername("test_user");
        user.setPassword("password");
        user.setEnabled(true);
        
        assertNotNull(userService_.saveGroupAdminUser(user));
        
        assertNotNull(user.getId());
        assertEquals("encrypted_password", user.getPassword());
        assertNotNull(user.getSalt());
        
        List<Authority> auths = Authority.findAuthoritysByUsernameEquals(user.getUsername()).getResultList();
        Authority groupAdminUser = new Authority();
        groupAdminUser.setUsername(user.getUsername());
        groupAdminUser.setAuthority(Authority.AuthorityTypes.ROLE_GROUP_ADMIN);
        assertTrue(auths.contains(groupAdminUser));
    }
    
    @Test
    public void shouldSaveGroupUser()
    {
        context.checking(new Expectations() {{
            oneOf (authenticationService_).getAuthenticatedUserGroup();
                will(returnValue(adminGroup));
            oneOf (authenticationService_).encryptPassword(with(equal("password")), with(any(String.class)));
                will(returnValue("encrypted_password"));
        }});
        
        SIMLEUser user = new SIMLEUser();
        user.setUsername("test_user");
        user.setPassword("password");
        user.setEnabled(true);
        
        assertNotNull(userService_.saveGroupUser(user));
        
        assertNotNull(user.getId());
        assertEquals("encrypted_password", user.getPassword());
        assertNotNull(user.getSalt());
        
        List<Authority> auths = Authority.findAuthoritysByUsernameEquals(user.getUsername()).getResultList();
        Authority groupUser = new Authority();
        groupUser.setUsername(user.getUsername());
        groupUser.setAuthority(Authority.AuthorityTypes.ROLE_USER);
        assertTrue(auths.contains(groupUser));
    }
    
    @Test
    public void shouldFindGroupMembers()
    {
        List<SIMLEUser> users = userService_.findGroupMembers(adminGroup);
        assertEquals(1, users.size());
        assertTrue(users.contains(adminUser));
    }
    
    @Test
    public void shouldFindCurrentGroupMembers()
    {
        context.checking(new Expectations() {{
            oneOf (authenticationService_).getAuthenticatedUserGroup();
                will(returnValue(adminGroup));
        }});
        
        List<SIMLEUser> users = userService_.findCurrentGroupMembers();
        assertEquals(1, users.size());
        assertTrue(users.contains(adminUser));
    }
    
    @Test
    public void shouldSaveGroup()
    {
        SIMLEGroup group = new SIMLEGroup();
        group.setGroupName("test_group");
        
        assertNotNull(userService_.saveGroup(group));
        assertNotNull(group.getId());
    }
    
    @Test
    public void shouldMergeGroup()
    {
        adminGroup.setGroupName("changed_group_name");
        
        assertNotNull(userService_.saveGroup(adminGroup));
        assertNotNull(adminGroup.getId());
        assertEquals("changed_group_name", adminGroup.getGroupName());
    }
    
    @Test
    public void shouldRemoveUser() throws OperationNotAllowedException
    {
        context.checking(new Expectations() {{
            oneOf (authenticationService_).getAuthenticatedUserGroup();
                will(returnValue(adminGroup));
        }});
        
        userService_.remove(adminUser);
        
        assertNull(userService_.findGroupUser(adminUser.getUsername()));
    }
    
    @Test(expected=OperationNotAllowedException.class)
    public void shouldNotRemoveUserFromOtherGroup() throws OperationNotAllowedException
    {
        context.checking(new Expectations() {{
            oneOf (authenticationService_).getAuthenticatedUserGroup();
                will(returnValue(new SIMLEGroup()));
        }});
        
        userService_.remove(adminUser);
    }
    
    @Test
    public void shouldSaveEventAdminUser()
    {
        context.checking(new Expectations() {{
            oneOf (authenticationService_).getAuthenticatedUserGroup();
                will(returnValue(adminGroup));
            oneOf (authenticationService_).encryptPassword(with(equal("password")), with(any(String.class)));
                will(returnValue("encrypted_password"));
        }});
        
        SIMLEUser user = new SIMLEUser();
        user.setUsername("test_user");
        user.setPassword("password");
        user.setEnabled(true);
        
        assertNotNull(userService_.saveEventAdminUser(user));
        
        assertNotNull(user.getId());
        assertEquals("encrypted_password", user.getPassword());
        assertNotNull(user.getSalt());
        assertNotNull(user.getGroup());
        assertEquals(SIMLEGroup.EVENT_ADMIN_GROUP,user.getGroup().getGroupName());
        
        List<Authority> auths = Authority.findAuthoritysByUsernameEquals(user.getUsername()).getResultList();
        Authority groupUser = new Authority();
        groupUser.setUsername(user.getUsername());
        groupUser.setAuthority(Authority.AuthorityTypes.ROLE_EVENT_ADMIN);
        assertTrue(auths.contains(groupUser));
    }

}
