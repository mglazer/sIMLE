package jpl.simle.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import jpl.simle.domain.Application;
import jpl.simle.domain.validator.IPAddress;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findProtocolsByApplication" })
@JsonAutoDetect(JsonMethod.NONE)
@XStreamAlias("protocol")
public class Protocol {

	public enum NetworkProtocol {
		UDP,
		TCP;
	}
	
	public enum Direction {
		IN,
		OUT,
		BOTH;
	}

	
    @NotNull
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn
    private Application application;

    @IPAddress
    private String destinationIP;

    private String direction;

    private String ports;

    @Pattern(regexp="\\w+", message="cannot contain special characters or spaces")
    private String networkProtocol;

    @Pattern(regexp="\\w+", message="cannot contain special characters or spaces")
    private String applicationProtocol;

    private String notes;
    
    public void setNetworkProtocol(NetworkProtocol protocol)
    {
    	networkProtocol = protocol.toString();
    }
    
    public void setDirection(Direction direction)
    {
    	this.direction = direction.toString();
    }
    
    public List<Integer> getPortsList()
    {
    	if ( ports == null )
    	{
    		return new ArrayList<Integer>();
    	}
    	// first split up the ports list based on the commas
    	List<Integer> portsList = new ArrayList<Integer>();
    	String[] csvPorts = ports.split(",");
    	
    	for ( String ps : csvPorts )
    	{
    		portsList.addAll(parsePortRange(ps));
    	}
    	
    	return portsList;
    }
    
    private List<Integer> parsePortRange(String portRange)
    {
    	List<Integer> rval = new ArrayList<Integer>();
    	if ( portRange.contains("-") )
    	{
    		// it's a range, check if there's a range mask
    		int increment = 1;
    		if ( portRange.contains("/") )
    		{
    			String[] rangeIncrement = portRange.split("/");
    			
    			portRange = rangeIncrement[0];
    			increment = Integer.parseInt(rangeIncrement[1]);
    			if ( increment <= 0 )
    			{
    				increment = 1;
    			}
    		}
    		
    		String[] startEnd = portRange.split("-");
    		
    		int start = Integer.parseInt(startEnd[0]);
    		int end = Integer.parseInt(startEnd[1]);
    		
    		// if start is less than end, then we can perform the range
    		// addition, otherwise we'll just add nothing because it's indeterminate
    		// what the right move is
    		if ( start < end )
    		{
    			while ( start <= end )
    			{
    				rval.add(new Integer(start));
    				start += increment;
    			}
    		}
    	}
    	else
    	{
    		rval.add(new Integer(portRange));
    	}
    	
    	return rval;
    }
    
    @JsonValue
    public Map<String,Object> toJSON()
    {
    	Map<String,Object> model = new HashMap<String,Object>();
    	
    	model.put("id", getId());
    	model.put("destinationIP", destinationIP);
    	model.put("direction", direction);
    	model.put("ports", ports);
    	model.put("networkProtocol", networkProtocol);
    	model.put("applicationProtocol", applicationProtocol);
    	model.put("notes", notes);

    	model.put("application", application.getId());
    	
    	return model;
    }
}
