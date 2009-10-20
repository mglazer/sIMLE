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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Pattern;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@RooJavaBean
@RooToString
@RooEntity()
@JsonAutoDetect(JsonMethod.NONE)
@XStreamAlias("application")
public class Application {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    private Set<Protocol> protocols = new HashSet<Protocol>();

    @NotNull
    @Pattern(regexp = "[\\w\\s]+", message = "cannot be blank or contain special characters")
    private String name;

    private String notes;

    // this should be not null, but we enforce this at the service layer
    //@NotNull
    private String addedByUsername;
    
    public int hashCode()
    {
        return new HashCodeBuilder()
            .append(name)
            .toHashCode();
    }
    
    public boolean equals(Object obj)
    {
        if ( obj == null )  
        {
            return false;
        }
        if ( obj == this )
        {
            return true;
        }
        if ( obj.getClass() != getClass() )
        {
            return false;
        }
        
        Application rhs = (Application)obj;
        return new EqualsBuilder()
            .append(name, rhs.name)
            .isEquals();
    }
    
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
