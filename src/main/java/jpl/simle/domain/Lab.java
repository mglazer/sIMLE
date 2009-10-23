package jpl.simle.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@RooJavaBean
@RooToString
@XStreamAlias("lab")
@JsonAutoDetect(JsonMethod.NONE)
@RooEntity(finders = { "findLabsByNameEquals", "findLabsByGroupNameEquals" })
public class Lab implements DomainObject
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XStreamAlias("id")
	private Long id;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lab")
    @XStreamAlias("hosts")
    private Set<Host> hosts = new HashSet<Host>();

    // this should be not null, but we are going to enforce this at the service layer
    //@NotNull
    @XStreamAlias("groupName")
    private String groupName;

    @NotNull
    @Pattern(regexp="[\\w\\s]+", message="cannot be empty or contain special characters")
    @XStreamAlias("name")
    private String name;

    @NotNull
    @XStreamAlias("domainName")
    private String domainName;

    @NotNull
    @XStreamAlias("latitude")
    private Double latitude;

    @NotNull
    @XStreamAlias("longitude")
    private Double longitude;

    public String getLocation() {
        return latitude.toString() + "," + longitude.toString();
    }

    public void addHost(Host host) {
        host.setLab(this);
        hosts.add(host);
    }
    
    public static Lab findLabByIdAndGroupname(Long id, String groupName)
    {
    	Query query = entityManager().createQuery("SELECT Lab from Lab AS lab WHERE lab.id = :id AND lab.groupName = :groupName");
    	query.setParameter("id", id);
    	query.setParameter("groupName", groupName);
    	
    	return (Lab)query.getSingleResult();
    }

    @JsonValue
    public Map<String, Object> toJSON() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", getId());
        model.put("groupName", getGroupName());
        model.put("name", getName());
        model.put("latitude", getLatitude());
        model.put("longitude", getLongitude());
        model.put("domainName", getDomainName());
        List<Long> hostIds = new ArrayList<Long>();
        for (Host host : hosts) {
            hostIds.add(host.getId());
        }
        model.put("hosts", hostIds);
        return model;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
