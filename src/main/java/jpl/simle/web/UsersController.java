package jpl.simle.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import jpl.simle.domain.SIMLEUser;
import jpl.simle.service.UserService;
import jpl.simle.service.exception.OperationNotAllowedException;
import jpl.simle.utils.SIMLEUtils;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UsersController
{

    private UserService userService_;
    
    private Validator validator_ = Validation.buildDefaultValidatorFactory().getValidator();
    
    private final static Logger logger_ = LoggerFactory.getLogger(UsersController.class);

    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    {
        modelMap.addAttribute("users", userService_.findAuthorizedUsers());
        logger_.debug("Returning users");
        return "user/list";
    }

    @RequestMapping(value = "/user/{username}", method=RequestMethod.GET)
    public String get(@PathVariable String username, ModelMap modelMap, 
            HttpServletRequest request,
            HttpServletResponse response)
    {
        modelMap.addAttribute("user", userService_.findGroupUser(username));
        
        return "user/show";
    }
    
    @RequestMapping(value = "/user/new", method=RequestMethod.GET)
    public String form(ModelMap modelMap)
    {
        modelMap.addAttribute("user", new SIMLEUser());
        return "user/new";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") SIMLEUser user, BindingResult result)
    {
        Assert.notNull(user, "User must be provided");
        
        userService_.setUsersGroup(user);

        if ( SIMLEUtils.validateDomainObject(validator_, result, user) )
        {
            return "user/new";
        }
        
        userService_.saveGroupUser(user);
        
        logger_.info("Redirecting to /user/" + user.getId());
        return "redirect:/user/" + user.getId();
    }
    
    @RequestMapping(value = "/user/{username}/edit", method = RequestMethod.GET)
    public String updateForm(@PathVariable("username") String username, ModelMap modelMap)
    {
        modelMap.addAttribute("user", userService_.findGroupUser(username));
        return "user/edit";
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String update(@ModelAttribute("user") SIMLEUser user, BindingResult result) 
    {
        Assert.notNull(user, "User must be provided.");
        
        userService_.setUsersGroup(user);
        
        if ( SIMLEUtils.validateDomainObject(validator_, result, user) )
        {
            return "user/edit";
        }
        
        userService_.saveGroupUser(user);
        return "redirect:/user/" + user.getId();
    }
    
    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("username") String username) throws OperationNotAllowedException
    {
        userService_.remove(userService_.findGroupUser(username));
        return "redirect:/user";
    }
    
    

    @Autowired
    public void setUserService(UserService userService)
    {
        userService_ = userService;
    }

    public UserService getUserService()
    {
        return userService_;
    }

}
