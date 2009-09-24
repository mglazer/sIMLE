package jpl.simle.domain;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jpl.simle.domain.Application;
import jpl.simle.domain.Lab;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findHostsByLab" })
@JsonAutoDetect(JsonMethod.NONE)
public class Host {

    @NotNull
    @ManyToOne(targetEntity = Lab.class)
    @JoinColumn
    private Lab lab;

    @NotNull
    private String name;

    private String dnsNames;

    private String addressIP;
    
    @JsonValue
    public Map<String,Object> toJSON()
    {
    	Map<String,Object> model = new HashMap<String,Object>();
    	model.put("name", getName());
    	model.put("addressIP", getAddressIP());
    	model.put("dnsNames", getDnsNames());
    	model.put("id", getId());
    	model.put("lab", lab.getId());
    	model.put("applications", HostApplication.findHostApplicationsByHost(this).getResultList());

    	/* TODO: We need to add in the application associations in here */
    	
    	return model; 
    }
    
}
