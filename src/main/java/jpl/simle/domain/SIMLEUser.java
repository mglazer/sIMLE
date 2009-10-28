package jpl.simle.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jpl.simle.utils.SIMLEUtils;

@Entity
@RooJavaBean
@RooToString
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@RooEntity(finders = { "findSIMLEUsersByUsernameEquals", "findSIMLEUsersByGroup" })
public class SIMLEUser implements UserDetails, DomainObject {

    /**
     * 
     */
    private static final long serialVersionUID = 5399433905670282881L;

    public enum UserType {

        USER, EVENT_ADMIN, SUPER_ADMIN, GROUP_ADMIN
    }

    @NotNull
    @Column(name = "username")
    @Pattern(regexp = "\\w+", message = "Username cannot have spaces and cannot be empty")
    @Index(name = "IDX_SIMLE_USERNAME")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;

    @NotNull
    @ManyToOne(targetEntity = SIMLEGroup.class)
    @JoinColumn(name = "simle_group")
    private SIMLEGroup group;

    private UserType userType = UserType.USER;

    @Size(max = 32)
    @Column(name = "salt")
    private String salt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate = new Date();
    
    @Transient
    private Collection<GrantedAuthority> authorities = SIMLEUtils.createList();

    public static SIMLEUser findUserByUsername(String username) {
        return (SIMLEUser) SIMLEUser.findSIMLEUsersByUsernameEquals(username).getSingleResult();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType[] getAcceptableCreatableUserTypes() {
        switch(this.userType) {
            case EVENT_ADMIN:
                return new UserType[0];
            case GROUP_ADMIN:
                return new UserType[] { UserType.USER };
            case SUPER_ADMIN:
                return new UserType[] { UserType.EVENT_ADMIN, UserType.GROUP_ADMIN, UserType.SUPER_ADMIN, UserType.USER };
            default:
                return new UserType[0];
        }
    }

    public Collection<GrantedAuthority> getAuthorities() 
    {
        return authorities;
    }
    
    public void setAuthorities(Collection<GrantedAuthority> authorities)
    {
        this.authorities = authorities;
    }

    public boolean isAccountNonExpired() {
        
        if ( expirationDate != null )
        {
            if ( expirationDate.before(new Date()) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled.booleanValue();
    }
}
