package jpl.simle.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpl.simle.dao.LabManagerDAO;
import jpl.simle.domain.Lab;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LabController {
	
	private LabManagerDAO labManager_;
	
	@RequestMapping(value="/labs")
	public ModelAndView list()
	{
		List<Lab> labs = labManager_.findLabs();
		
		return new ModelAndView("lab/list", "labs", labs);
	}

    @RequestMapping(value="/lab/{labId}")
    public ModelAndView get(@PathVariable Long labId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) 
    {
    	Lab lab = labManager_.findLabById(labId);
    	
    	return new ModelAndView("lab/show", "lab", lab);
    }
    
    @RequestMapping(value="/lab/new")
    public ModelAndView createNew(HttpServletRequest request, HttpServletResponse response)
    {
    	Lab lab = new Lab();
    	
    	return new ModelAndView("lab/new", "lab", lab);
    }
    
    @RequestMapping(value="/lab", method = RequestMethod.POST)
    public ModelAndView create(Lab lab, HttpServletRequest request, HttpServletResponse response)
    {
    	lab = getLabManagerDAO().saveLab(lab);
    	
    	if ( lab.getId() != null )
    	{
    		return new ModelAndView("redirect:/lab/" + lab.getId());
    	}
    	else
    	{
    		return new ModelAndView("/lab/new", "lab", lab);
    	}
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/lab/{labId}")
    public void update(@PathVariable Long labId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @Autowired
	public void setLabManagerDAO(LabManagerDAO labManagerDAO) {
		labManager_ = labManagerDAO;
	}

	public LabManagerDAO getLabManagerDAO() {
		return labManager_;
	}
}
