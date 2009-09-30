package jpl.simle.domain;

import javax.persistence.Entity;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.userdetails.UserDetails;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jpl.simle.domain.Host;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findLabsByNameEquals", "findLabsByUsernameEquals" })
@XStreamAlias("lab")
@JsonAutoDetect(JsonMethod.NONE)
public class Lab {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lab")
    @XStreamAlias("hosts")
    private Set<Host> hosts = new HashSet<Host>();

    @NotNull
    @XStreamAlias("username")
    private String username;

    @NotNull
    @XStreamAlias("name")
    private String name;

    @NotNull
    @XStreamAlias("domainName")
    private String domainName;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
    
    public String getLocation()
    {
    	return latitude.toString() + "," + longitude.toString();
    }

    public void addHost(Host host) {
        host.setLab(this);
        hosts.add(host);
    }

    @JsonValue
    public Map<String, Object> toJSON() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", getId());
        model.put("username", getUsername());
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

}
