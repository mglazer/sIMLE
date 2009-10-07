package jpl.simle.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@RooJavaBean
@RooToString
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@RooEntity(finders = { "findSIMLEUsersByUsernameEquals" })
public class SIMLEUser {

    @NotNull
    @Column(name = "username")
    @Id
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @NotNull
    @ManyToOne(targetEntity = SIMLEGroup.class)
    @JoinColumn(name="simle_group")
    private SIMLEGroup group;
    
    public static SIMLEUser findUserByUsername(String username)
    {
    	return (SIMLEUser)SIMLEUser.findSIMLEUsersByUsernameEquals(username).getSingleResult();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getId() {
		return username;
	}
}
