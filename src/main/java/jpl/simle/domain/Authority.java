package jpl.simle.domain;

import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@RooJavaBean
@RooToString
@Table(name = "authorities")
@RooEntity(finders = { "findAuthoritysByUsernameEquals" })
public class Authority {

    public enum AuthorityTypes {

        ROLE_USER, ROLE_GROUP_ADMIN, ROLE_ADMIN, ROLE_EVENT_ADMIN
    }

    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "authority")
    private String authority;

    public void setAuthority(AuthorityTypes authority) {
        this.authority = authority.toString();
    }
    
    @SuppressWarnings("unchecked")
    public static List<Authority> findAuthoritiesByUsername(String username)
    {
        return (List<Authority>) findAuthoritysByUsernameEquals(username).getResultList();
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
        
        Authority auth = (Authority)obj;
        
        return new EqualsBuilder()
            .append(username, auth.username)
            .append(authority, auth.authority)
            .isEquals();
    }
}
