package jpl.simle.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import jpl.simle.domain.Host;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import jpl.simle.domain.Application;
import java.util.Date;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findHostApplicationsByHost" })
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
}
