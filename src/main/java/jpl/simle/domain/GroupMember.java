package jpl.simle.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import jpl.simle.domain.SIMLEUser;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import jpl.simle.domain.SIMLEGroup;

@Entity
@RooJavaBean
@RooToString
@RooEntity
public class GroupMember {

    @ManyToOne(targetEntity = SIMLEGroup.class)
    @JoinColumn(name="simle_group")
    @NotNull
    private SIMLEGroup group;

    @ManyToOne(targetEntity = SIMLEUser.class)
    @JoinColumn
    @NotNull
    private SIMLEUser user;
}
