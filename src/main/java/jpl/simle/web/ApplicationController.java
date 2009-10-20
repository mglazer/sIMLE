package jpl.simle.web;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;

import jpl.simle.domain.Application;
import jpl.simle.domain.Applications;
import jpl.simle.service.LabManagerService;
import jpl.simle.utils.SIMLEUtils;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {
	
	private LabManagerService labManager_;
	private final Logger logger_ = LoggerFactory.getLogger(getClass());
	
	private Validator validator_ = Validation.buildDefaultValidatorFactory().getValidator();
	
	@RequestMapping(value="/applications")
	public String list(ModelMap modelMap)
	{
		modelMap.addAttribute("applications", labManager_.findApplications());
		return "/application/list";
	}
	
	@RequestMapping(value="/application/new")
	public String newApplication(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
	    modelMap.addAttribute("application", new Application());
	    return "/application/new";
	}
	
	@RequestMapping(value="/application/{applicationId}/edit")
	public String editApplication(@PathVariable Long applicationId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
		modelMap.addAttribute("application", labManager_.findApplication(applicationId));
		return "/application/edit";
	}

    @RequestMapping(value="/application/{applicationId}")
    public String get(@PathVariable Long applicationId,
    			    ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
        modelMap.put("application", labManager_.findApplication(applicationId));
        return "/application/show";
    }
    
    @RequestMapping(method = RequestMethod.POST, value="/application", headers={"Content-Type=application/xml"})
    public String createXML(@RequestBody Application application, ModelMap modelMap, 
    					  HttpServletRequest request, HttpServletResponse response,
    					  BindingResult result)
    throws IOException
    {
        
        if ( SIMLEUtils.validateDomainObject(validator_, result, application) )
        {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Application validation failed");
            return "/application/new";
        }
        
    	labManager_.saveApplication(application);
    	
    	if ( application.getId() != null )
    	{
    		modelMap.put("application", application);
    		response.setStatus(HttpServletResponse.SC_CREATED);
    		return "redirect:/application/" + application.getId();
    	}
    	else
    	{
    		modelMap.put("errors", "Could not save application");
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Could not save application");
    		return "error";
    	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application")
    public String create(@ModelAttribute("application") Application application, 
                                BindingResult result, ModelMap modelMap, HttpServletRequest request,
                                HttpServletResponse response) throws IOException 
    {
        if ( SIMLEUtils.validateDomainObject(validator_, result, application) )
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "/application/new";
        }
        
    	logger_.info("Request to save application with name " + application.getName());
    	labManager_.saveApplication(application);
    	
    	logger_.info("Saved application, got back id: " + application.getId());
    	
    	if ( application.getId() != null )
    	{
    		modelMap.put("application", application);
    		response.setStatus(HttpServletResponse.SC_CREATED);
    		return "redirect:/application/" + application.getId();
    	}
    	else
    	{
    		return "/application/new";
    	}
    }
    
    
    
    @Autowired
    public void setLabManagerService(LabManagerService labManagerService)
    {
    	labManager_ = labManagerService;
    }
}
