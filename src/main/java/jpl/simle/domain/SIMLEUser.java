package jpl.simle.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@RooEntity
@RooJavaBean
@RooToString
@Table(name="users")
public class SIMLEUser {

    @NotNull
    @Column(name="username")
    private String username;

    @NotNull
    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="series")
    private String series;

    @NotNull
    @Column(name="token")
    private String token;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_used")
    private Date lastUsed;
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    //private Set<Authority> authorities = new HashSet<Authority>();
}
