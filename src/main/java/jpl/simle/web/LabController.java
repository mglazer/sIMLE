package jpl.simle.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import jpl.simle.dao.LabManagerDAO;
import jpl.simle.domain.Application;
import jpl.simle.domain.Host;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Labs;
import jpl.simle.domain.Protocol;
import jpl.simle.domain.validator.LabValidator;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LabController {
	
	private LabManagerDAO labManager_;
	
	private final static Namespace RDF_NAMESPACE = new Namespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	private final static Namespace RDFS_NAMESPACE = new Namespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
	private final static Namespace IML_NAMESPACE = new Namespace("iml", "http://www.jpl.nasa.gov/research/pbm/iml#");
	private final static Namespace GEO_NAMESPACE = new Namespace("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
	
	public final static String IML_CONTENT_TYPE = "application/xml+iml";
	

	@RequestMapping(value="/labs")
	public ModelAndView list()
	{
		List<Lab> labs = labManager_.findLabs();
		
		return new ModelAndView("lab/list", "labs", new Labs(labs));
	}

    @RequestMapping(value="/lab/{labId}")
    public ModelAndView get(@PathVariable Long labId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException 
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

    @RequestMapping(method = RequestMethod.GET, value = "/lab/{labId}.iml", headers="Content-Type=application/xml+iml")
    public void getIMLContentType(@PathVariable Long labId, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
   		getIML(labId, request, response);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/lab/{labId}.iml")
    public void getIML(@PathVariable Long labId, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
    	Lab lab = labManager_.findLabById(labId);
    	
    	if ( lab == null )
    	{
    		response.getWriter().write("Could not find lab with ID: " + labId);
    		response.getWriter().flush();
    		return;
    	}
    	
    	boolean prettyPrint = request.getParameter("pretty") != null && (Boolean.parseBoolean(request.getParameter("pretty")));
    	
    	response.setHeader("Content-Type", "application/xml+iml");
    	
    	Document doc = DocumentHelper.createDocument();
    	Element rdfRoot = doc.addElement(QName.get("rdf", RDF_NAMESPACE));
    	rdfRoot.add(RDF_NAMESPACE);
    	rdfRoot.add(IML_NAMESPACE);
    	rdfRoot.add(RDFS_NAMESPACE);
    	rdfRoot.add(GEO_NAMESPACE);
    	
    	/* add the location of the lab first */
    	addLabInformation(rdfRoot, lab);
    	
    	/* next, add in all of the port information */
    	addPortInformation(rdfRoot, lab);
    	
    	/* next the application definitions */
    	addApplications(rdfRoot, lab);
    	
    	/* finally, define the hosts */
    	addHosts(rdfRoot, lab);
    	
    	
    	if ( prettyPrint )
    	{
    		OutputFormat format = OutputFormat.createPrettyPrint();
    		XMLWriter writer = new XMLWriter(response.getWriter(), format);
    		writer.write(doc);
    	}
    	else
    	{
    		doc.write(response.getWriter());
    	}
    }

    private void addPortInformation(Element rdfRoot, Lab lab) 
    {
    	
		
	}

	private void addHosts(Element rdfRoot, Lab lab) 
    {
    	for ( Host host : lab.getHosts() )
    	{
    		Element nodeDevice = rdfRoot.addElement(imlQname("NodeDevice"));
    		nodeDevice.addAttribute(imlQname("about"), host.getName().replace(' ', '_'));
    		
    		nodeDevice.addElement(imlQname("name")).setText(host.getName());
    		nodeDevice.addElement(imlQname("locatedAt")).addAttribute(rdfQname("about"), resourceLink(lab.getDomainName()));
    		
    		for ( HostApplication ha : labManager_.findHostApplicationsForHost(host) )
    		{
    			nodeDevice.addElement(imlQname("hasApplication")).addAttribute(rdfQname("resource"), resourceLink(ha.getApplication().getName()));
    		}
    	}
	}

	private void addApplications(Element rdfRoot, Lab lab) 
	{
		for ( Application app : labManager_.findApplications() )
		{
			Element appNode = rdfRoot.addElement(imlQname("Application")).addAttribute(rdfQname("about"), resourceLink(app.getName()));
			appNode.addElement(imlQname("name")).setText(app.getName());
			
			for ( Protocol protocol : app.getProtocols() )
			{
				Element useProtocolElement = appNode.addElement(imlQname("UseProtocol")).addAttribute(rdfQname("about"), resourceLink(protocol.getApplicationProtocol()));
				Element linkElement = useProtocolElement.addElement(imlQname("MultiDirectionalLink")).addAttribute(rdfQname("about"), resourceLink(protocol.getNetworkProtocol()));
				
				for ( String port : protocol.getPortsList() )
				{
					linkElement.addElement(imlQname("PortNumber")).setText(port);
				}
			}
		}
	}

	private void addLabInformation(Element rdfRoot, Lab lab) 
	{
		Element location = rdfRoot.addElement(imlQname("Location")).addAttribute(rdfQname("about"), resourceLink(lab.getDomainName()));
		
		location.addElement(imlQname("name")).setText(lab.getName());
		location.addElement(QName.get("lat", GEO_NAMESPACE)).setText(lab.getLatitude().toString());
		location.addElement(QName.get("long", GEO_NAMESPACE)).setText(lab.getLongitude().toString());
		
	}

	@Autowired
	public void setLabManagerDAO(LabManagerDAO labManagerDAO) {
		labManager_ = labManagerDAO;
	}

	public LabManagerDAO getLabManagerDAO() {
		return labManager_;
	}
	
	private QName imlQname(String name)
	{
		return QName.get(name, IML_NAMESPACE);
	}
	
	private QName rdfQname(String name)
	{
		return QName.get(name, RDF_NAMESPACE);
	}
	
	private String resourceLink(String name)
	{
		return "#" + name.replace("\\s", "_");
	}
}
