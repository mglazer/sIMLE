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

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jpl.simle.domain.Application;
import jpl.simle.domain.Lab;
import jpl.simle.domain.validator.IPAddress;

import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findHostsByLab" })
@XStreamAlias("host")
@JsonAutoDetect(JsonMethod.NONE)
public class Host {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @NotNull
    @ManyToOne(targetEntity = Lab.class)
    @JoinColumn
    private Lab lab;

    @NotNull
    @Pattern(regexp = "\\w+", message="should not contain special characters")
    @XStreamAlias("name")
    private String name;

    @XStreamAlias("dnsNames")
    private String dnsNames;

    //@IPAddress
    @XStreamAlias("addressIP")
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
    
}
