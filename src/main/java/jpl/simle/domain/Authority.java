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
	
    @Column(name="username")
    private String username;
    
    @NotNull
    @Column(name="authority")
    private String authority;
}
