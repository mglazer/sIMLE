package jpl.simle.domain;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jpl.simle.domain.Host;
import jpl.simle.domain.Protocol;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@RooJavaBean
@RooToString
@RooEntity()
@JsonAutoDetect(JsonMethod.NONE)
@XStreamAlias("application")
public class Application {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    private Set<Protocol> protocols = new HashSet<Protocol>();

    private String name;

    private String notes;

    private String addedByUsername;
    
    public void addProtocol(Protocol protocol)
    {
    	protocols.add(protocol);
    	protocol.setApplication(this);
    }
    
    @JsonValue
    public Map<String,Object> toJSON()
    {
    	Map<String,Object> model = new HashMap<String,Object>();
    	
    	model.put("id", getId());
    	model.put("name", name);
    	model.put("notes", notes);
    	model.put("addedByUsername", addedByUsername);
    	
    	List<Long> protocolIds = new ArrayList<Long>();
    	for ( Protocol protocol : protocols )
    	{
    		protocolIds.add(protocol.getId());
    	}
    	
    	model.put("protocols", protocolIds);
    	
    	return model;
    }
}
