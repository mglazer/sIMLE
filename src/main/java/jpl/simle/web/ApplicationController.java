package jpl.simle.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.dao.LabManagerDAO;
import jpl.simle.domain.Application;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {
	
	private LabManagerDAO labManager_;
	
	@RequestMapping(value="/application/new")
	public ModelAndView newApplication(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("new", "application", new Application());
	}
	
	@RequestMapping(value="/application/{applicationId}/edit")
	public ModelAndView editApplication(@PathVariable Long applicationId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
		Application app = labManager_.findApplication(applicationId);
		
		if ( app == null )
		{
			return new ModelAndView("redirect:error", "errors", "Could not find application with id " + applicationId);
		}
		
		return new ModelAndView("edit", "application", app);
	}

    @RequestMapping(value="/application/{applicationId}")
    public ModelAndView get(@PathVariable Long applicationId,
    			    ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	return new ModelAndView("show", "application", Application.findApplication(applicationId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/")
    public ModelAndView post(Application application, HttpServletRequest request, HttpServletResponse response) 
    {
    	labManager_.saveApplication(application);
    	
    	if ( application.getId() != null )
    	{
    		return new ModelAndView("redirect:/application/" + application.getId());
    	}
    	else
    	{
    		return new ModelAndView("/application/new", "application", application);
    	}
    }
    
    
    @Autowired
    public void setLabManagerDAO(LabManagerDAO labManagerDAO)
    {
    	labManager_ = labManagerDAO;
    }
}
