package jpl.simle.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@RooEntity
@RooJavaBean
@RooToString
@Table(name="authorities")
public class Authority {
	
	public enum AuthorityTypes 
	{
		ROLE_USER,
		ROLE_GROUP_ADMIN,
		ROLE_ADMIN
	}
	
    @Column(name="username")
    private String username;
    
    @NotNull
    @Column(name="authority")
    private String authority;
    
    
    public void setAuthority(AuthorityTypes authority)
    {
    	this.authority = authority.toString();
    }
}
