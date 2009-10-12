package jpl.simle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@RooEntity(finders = { "findSIMLEUsersByUsernameEquals", "findSIMLEUsersByGroup" })
public class SIMLEUser {

    @NotNull
    @Column(name = "username")
    @Pattern(regexp = "\\w+", message = "Username cannot have spaces and cannot be empty")
    @Id
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @NotNull
    @ManyToOne(targetEntity = SIMLEGroup.class)
    @JoinColumn(name = "simle_group")
    private SIMLEGroup group;

    public static SIMLEUser findUserByUsername(String username) {
        return (SIMLEUser) SIMLEUser.findSIMLEUsersByUsernameEquals(username).getSingleResult();
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
