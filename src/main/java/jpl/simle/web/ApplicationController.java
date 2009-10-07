package jpl.simle.web;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.domain.Application;
import jpl.simle.domain.Applications;
import jpl.simle.service.LabManagerService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {
	
	private LabManagerService labManager_;
	private final Logger logger_ = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/applications")
	public ModelAndView list()
	{
		List<Application> applications = Application.findAllApplications();
		
		return new ModelAndView("/application/list", "applications", new Applications(applications));
	}
	
	@RequestMapping(value="/application/new")
	public ModelAndView newApplication(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/application/new", "application", new Application());
	}
	
	@RequestMapping(value="/application/{applicationId}/edit")
	public ModelAndView editApplication(@PathVariable Long applicationId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
		Application app = labManager_.findApplication(applicationId);
		
		if ( app == null )
		{
			return new ModelAndView("redirect:error", "errors", "Could not find application with id " + applicationId);
		}
		
		return new ModelAndView("/application/edit", "application", app);
	}

    @RequestMapping(value="/application/{applicationId}")
    public ModelAndView get(@PathVariable Long applicationId,
    			    ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	return new ModelAndView("/application/show", "application", Application.findApplication(applicationId));
    }
    
    @RequestMapping(method = RequestMethod.POST, value="/application", headers={"Content-Type=application/xml"})
    public String createXML(@RequestBody Application application, ModelMap modelMap, 
    					  HttpServletRequest request, HttpServletResponse response)
    throws IOException
    {
    	labManager_.saveApplication(application);
    	
    	if ( application.getId() != null )
    	{
    		modelMap.put("application", application);
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
    public ModelAndView create(Application application, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	logger_.info("Request to save application with name " + application.getName());
    	labManager_.saveApplication(application);
    	
    	logger_.info("Saved application, got back id: " + application.getId());
    	
    	if ( application.getId() != null )
    	{
    		modelMap.put("application", application);
    		return new ModelAndView("redirect:/application/" + application.getId());
    	}
    	else
    	{
    		return new ModelAndView("/application/new", "application", application);
    	}
    }
    
    
    
    @Autowired
    public void setLabManagerDAO(LabManagerService labManagerDAO)
    {
    	labManager_ = labManagerDAO;
    }
}
