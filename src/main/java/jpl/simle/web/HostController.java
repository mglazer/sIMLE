package jpl.simle.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.dao.LabManagerDAO;
import jpl.simle.domain.Host;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    public void setLabManagerDAO(LabManagerDAO labManager)
    {
    	labManager_ = labManager;
    }
}
