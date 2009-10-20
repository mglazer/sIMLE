package jpl.simle.web;

import java.io.IOException;
import java.util.List;

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
import jpl.simle.domain.Protocol;
import jpl.simle.service.LabManagerService;
import jpl.simle.utils.SIMLEUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProtocolController {

	private LabManagerService labManager_;
	private Validator validator_ = Validation.buildDefaultValidatorFactory().getValidator();
	
	@RequestMapping(value="/application/{applicationId}/protocols", method=RequestMethod.GET)
	public String list(@PathVariable Long applicationId, 
							 ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	{
	    modelMap.put("protocols", labManager_.findProtocolsByApplicationId(applicationId));
	    return "/protocol/list";
	}
	
    @RequestMapping(value="/application/{applicationId}/protocol/{protocolId}", method=RequestMethod.GET)
    public String get(@PathVariable Long applicationId, @PathVariable Long protocolId,
    						ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	modelMap.put("protocol", labManager_.findProtocol(applicationId, protocolId));
    	
    	return "/protocol/show";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/application/{applicationId}/protocol", headers={"Content-Type:application/xml"})
    public String createXML(@PathVariable Long applicationId, @RequestBody Protocol protocol, 
    					 ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
    					 BindingResult result) 
    throws IOException
    {
        return create(applicationId, protocol, result, modelMap, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/{applicationId}/protocol")
    public String create(@PathVariable Long applicationId, Protocol protocol,
    						 BindingResult result, ModelMap modelMap, HttpServletRequest request,
    						 HttpServletResponse response) throws IOException 
    {
        Application application = labManager_.findApplication(applicationId);
        if ( application == null )
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "/protocol/new";
        }
        
        protocol.setApplication(application);
        if ( SIMLEUtils.validateDomainObject(validator_, result, protocol) )
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "/protocol/new";
        }
        
    	labManager_.saveProtocol(applicationId, protocol);
    	
    	if ( protocol.getId() != null )
    	{
    		modelMap.put("protocol", protocol);
    		response.setStatus(HttpServletResponse.SC_CREATED);
    		return "redirect:/application/" + applicationId + "/protocol/" + protocol.getId();
    	}
    	else
    	{
    	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		return "/protocol/new";
    	}
    }
    
    @Autowired
    public void setLabManagerService(LabManagerService labManagerService)
    {
    	labManager_ = labManagerService;
    }
}
