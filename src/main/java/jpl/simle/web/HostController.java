package jpl.simle.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.dao.LabManagerDAO;
import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HostController {

	private LabManagerDAO labManager_;
	
	@RequestMapping(value="/lab/{labId}/hosts", method=RequestMethod.GET)
	public ModelAndView list(@PathVariable Long labId)
	{
		List<Host> hosts = labManager_.findHosts(labId);
		
		return new ModelAndView("host/list", "hosts", hosts);
	}
	
    @RequestMapping(value="/lab/{labId}/host/{hostId}", method=RequestMethod.GET)
    public ModelAndView get(@PathVariable Long labId, 
    			    @PathVariable Long hostId, 
    				ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	return new ModelAndView("host/show", "host", host);
    }
    
    @RequestMapping(value="/lab/{labId}/host/new")
    public ModelAndView createNew(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    {
    	Host host = new Host();
    	
    	return new ModelAndView("/host/new", "host", host);
    }
    

    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/")
    public ModelAndView post(@PathVariable Long labId,
    				 Host host,
    			     ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	host = labManager_.saveHost(labId, host);
    	
    	if ( host.getId() != null )
    	{
    		return new ModelAndView("redirect:/lab/" + labId + "/host/" + host.getId());
    	}
    	else
    	{
    		return new ModelAndView("/lab/" + labId + "/host/new", "host", host);
    	}
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/{hostId}/addApplication")
    public ModelMap addApplication(@PathVariable Long labId,
    							   @PathVariable Long hostId,
    							   @RequestParam(required=false) Long appId
    								   )
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	if ( host == null )
    	{
    		return new ModelMap("error", "Could not find host with id " + hostId);
    	}
    	
    	Application application;
    	
    	if ( appId != null )
    	{
    		application = labManager_.findApplication(appId);
    	}
    	else
    	{
    		application = Application.findApplicationEntries(0, 1).get(0);
    	}
    	
    	if ( application == null )
    	{
    		return new ModelMap("error", "Could not find application with id " + appId);
    	}
    	
    	HostApplication link = labManager_.createHostApplicationLink(application, host);
    	
    	List<Application> applications = labManager_.findApplications();
    	
    	ModelMap modelMap = new ModelMap();
    	modelMap.addAttribute("applications", applications);
    	modelMap.addAttribute("linkId", link.getId());
    	
    	return modelMap;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/{hostId}/updateApplicationLink/{linkId}")
    public ModelMap updateApplicationConnection(@PathVariable Long labId,
    											@PathVariable Long hostId,
    											@PathVariable Long linkId,
    											@RequestParam Long appId,
    											HttpServletRequest request)
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	if ( host == null )
    	{
    		return new ModelMap("error", "Could not find host with id " + hostId);
    	}
    	
    	Application application = labManager_.findApplication(appId);
    	
    	if ( application == null )
    	{
    		return new ModelMap("error", "Could not find application with id " + appId);
    	}
    	
    	HostApplication link = labManager_.findHostApplicationLink(linkId);
    	
    	if ( link == null )
    	{
    		return new ModelMap("error", "Could not find host-application link with id " + linkId);
    	}
    	
    	labManager_.updateHostApplicationLink(link, application, host);
    	
    	return new ModelMap("success", "Successfully updated link");
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/{hostId}/deleteApplicationLink/{linkId}")
    public ModelMap deleteApplicationLink(@PathVariable Long labId,
    									  @PathVariable Long hostId,
    									  @PathVariable Long linkId)
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	if ( host == null )
    	{
    		return new ModelMap("error", "Could not find host with id " + hostId);
    	}
    	
    	HostApplication link = labManager_.findHostApplicationLink(linkId);
    	
    	if ( link == null )
    	{
    		return new ModelMap("error", "Could not find link with id " + linkId);
    	}
    	
    	labManager_.deleteHostApplicationLink(link);
    	
    	return new ModelMap("success", "Successfully deleted link");
    }
    
    @Autowired
    public void setLabManagerDAO(LabManagerDAO labManager)
    {
    	labManager_ = labManager;
    }
}
