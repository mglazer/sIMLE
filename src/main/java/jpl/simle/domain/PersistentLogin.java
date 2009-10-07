package jpl.simle.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@RooJavaBean
@RooToString
@RooEntity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @NotNull
    private String username;

    @Id
    @NotNull
    private String series;

    @NotNull
    private String token;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    public String getSeries()
    {
    	return series;
    }
    
    public void setSeries(String series)
    {
    	this.series = series;
    }
}
