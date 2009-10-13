package jpl.simle.web;

import java.io.IOException;
import java.util.HashMap;
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
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.service.LabManagerService;
import jpl.simle.utils.SIMLEUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HostController {

	private LabManagerService labManager_;
	
	private Validator validator_ = Validation.buildDefaultValidatorFactory().getValidator();
	
	private final static Logger logger_ = LoggerFactory.getLogger(HostController.class);
	
	@RequestMapping(value="/lab/{labId}/hosts", method=RequestMethod.GET)
	public String list(@PathVariable Long labId, ModelMap modelMap)
	{
		modelMap.addAttribute("hosts", labManager_.findHosts(labId));
		
		return "/host/list";
	}
	
    @RequestMapping(value="/lab/{labId}/host/{hostId}", method=RequestMethod.GET)
    public String get(@PathVariable Long labId, 
    			    @PathVariable Long hostId, 
    				ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
        modelMap.addAttribute("host", labManager_.findHost(labId, hostId));
        return "/host/show";
    }
    
    @RequestMapping(value="/lab/{labId}/host/new")
    public String createNew(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    {
        modelMap.addAttribute("host", new Host());
    	return "/host/new";
    }
    
    @RequestMapping(method = RequestMethod.POST, value="/lab/{labId}/host", headers={"Content-Type=application/xml"})
    public String createXML(@PathVariable Long labId, @RequestBody Host host, ModelMap modelMap,
    						HttpServletRequest request, HttpServletResponse response,
    						BindingResult result)
    throws IOException
    {
        if ( SIMLEUtils.validateDomainObject(validator_, result, host) )
        {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not validate host");
            return "/host/new";
        }
        
    	host = labManager_.saveHost(labId, host);
    	
    	if ( host.getId() != null )
    	{
    		modelMap.addAttribute("host", host);
    		return "redirect:/lab/" + labId + "/host/" + host.getId();
    	}
    	else
    	{
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not save the host");
    		return "/lab/new";
    	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host")
    public String create(@PathVariable Long labId,
    				 Host host,
    			     ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
    			     BindingResult result) throws IOException 
    {
        Lab lab = labManager_.findLabById(labId);
        if ( lab == null )
        {
            result.reject("Couldn't find lab with ID: " + labId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not find lab with id " + labId);
            return "/host/new";
        }
        
        host.setLab(lab);
        if ( SIMLEUtils.validateDomainObject(validator_, result, host) )
        {
            System.out.println("Validation failed with " + result.getErrorCount());
            System.out.println(result.getAllErrors());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not validate host");
            return "/host/new";
        }
        
        System.out.println("Validation succeeded");
    	host = labManager_.saveHost(labId, host);
    	
    	if ( host.getId() != null )
    	{
    		modelMap.addAttribute("host", host);
    		response.setStatus(HttpServletResponse.SC_CREATED);
    		return "redirect:/lab/" + labId + "/host/" + host.getId();
    	}
    	else
    	{
    	    logger_.info("Failed to save a new host");
    	    result.reject("We didn't get an ID back when we tried to save the host. This is bad");
    	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		return "/host/new";
    	}
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/{hostId}/addApplication")
    public void addApplication(@PathVariable Long labId,
    							   @PathVariable Long hostId,
    							   @RequestParam(required=false) Long appId,
    							   ModelMap modelMap
    								   )
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	if ( host == null )
    	{
    		modelMap.addAttribute("error", "Could not find host with id " + hostId);
    		return;
    	}
    	
    	Application application = null;
    	
    	// if they don't specify an application ID (which is the case when they don't know which
    	// applications exist), then just find the first application that exists
    	if ( appId != null )
    	{
    		application = labManager_.findApplication(appId);
    	}
    	else
    	{
    		List<Application> applications = labManager_.findApplications();
    		if ( applications.size() > 0 )
    		{
    			application = applications.get(0);
    		}
    	}
    	
    	if ( application == null )
    	{
    		modelMap.addAttribute("error", "Could not find application with id " + appId);
    		return;
    	}
    	
    	HostApplication link = labManager_.createHostApplicationLink(application, host);
    	
    	List<Application> applications = labManager_.findApplications();
    	
    	modelMap.addAttribute("applications", applications);
    	modelMap.addAttribute("linkId", link.getId());
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/{hostId}/updateApplicationLink/{linkId}")
    public void updateApplicationConnection(@PathVariable Long labId,
    											@PathVariable Long hostId,
    											@PathVariable Long linkId,
    											@RequestParam Long appId,
    											HttpServletRequest request,
    											ModelMap modelMap)
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	if ( host == null )
    	{
    		modelMap.addAttribute("error", "Could not find host with id " + hostId);
    		return;
    	}
    	
    	Application application = labManager_.findApplication(appId);
    	
    	if ( application == null )
    	{
    		modelMap.addAttribute("error", "Could not find application with id " + appId);
    		return;
    	}
    	
    	HostApplication link = labManager_.findHostApplicationLink(linkId);
    	
    	if ( link == null )
    	{
    		modelMap.addAttribute("error", "Could not find host-application link with id " + linkId);
    		return;
    	}
    	
    	labManager_.updateHostApplicationLink(link, application, host);
    	
    	modelMap.addAttribute("success", "Successfully updated link");
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/lab/{labId}/host/{hostId}/deleteApplicationLink/{linkId}")
    public void deleteApplicationLink(@PathVariable Long labId,
    									  @PathVariable Long hostId,
    									  @PathVariable Long linkId,
    									  ModelMap modelMap)
    {
    	Host host = labManager_.findHost(labId, hostId);
    	
    	if ( host == null )
    	{
    	    modelMap.addAttribute("error", "Could not find host with id " + hostId);
    	}
    	
    	HostApplication link = labManager_.findHostApplicationLink(linkId);
    	
    	if ( link == null )
    	{
    		modelMap.addAttribute("error", "Could not find link with id " + linkId);
    	}
    	
    	labManager_.deleteHostApplicationLink(link);
    	
    	modelMap.addAttribute("success", "Successfully deleted link");
    }
    
    @Autowired
    public void setLabManagerService(LabManagerService labManager)
    {
    	labManager_ = labManager;
    }
}
