package jpl.simle.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

@Entity
@RooJavaBean
@RooToString
@RooEntity
@Table(name = "groups", uniqueConstraints = @UniqueConstraint(columnNames = { "groupName" }))
public class SIMLEGroup {

    private String groupName;
}
