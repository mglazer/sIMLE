package jpl.simle.domain;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

@Entity
@RooJavaBean
@RooToString
@Table(name = "groups", uniqueConstraints = @UniqueConstraint(columnNames = { "groupName" }))
@RooEntity(finders = { "findSIMLEGroupsByGroupNameEquals" })
public class SIMLEGroup {

    private String groupName;

    public static final String ADMIN_GROUP = "__admin__";

    public static final String EVENT_ADMIN_GROUP = "__event_admin__";

    public static SIMLEGroup findGroupByGroupName(String groupName) {
        return (SIMLEGroup)SIMLEGroup.findSIMLEGroupsByGroupNameEquals(groupName).getSingleResult();
    }
}
