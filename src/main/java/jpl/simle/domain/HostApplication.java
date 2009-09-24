package jpl.simle.domain;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import jpl.simle.domain.Host;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import jpl.simle.domain.Application;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findHostApplicationsByHost" })
@JsonAutoDetect(JsonMethod.NONE)
public class HostApplication {

    @NotNull
    @ManyToOne(targetEntity = Host.class)
    @JoinColumn
    private Host host;

    @NotNull
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn
    private Application application;

    private String comment;

    private Date dateAdded = new Date();

    @JsonValue
    public Map<String,Object> toJSON()
    {
    	Map<String,Object> model = new HashMap<String,Object>();
    	
    	model.put("host", host.getId());
    	model.put("application", application.getId());
    	model.put("comment", comment);
    	model.put("dateAdded", dateAdded);
    	
    	model.put("id", getId());
    	
    	return model;
    }
    
}
