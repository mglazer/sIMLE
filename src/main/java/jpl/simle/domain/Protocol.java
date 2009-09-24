package jpl.simle.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import jpl.simle.domain.Application;

import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findProtocolsByApplication" })
@JsonAutoDetect(JsonMethod.NONE)
public class Protocol {

    @NotNull
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn
    private Application application;

    private String destinationIP;

    private String direction;

    private String ports;

    private String networkProtocol;

    private String applicationProtocol;

    private String notes;
    
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
