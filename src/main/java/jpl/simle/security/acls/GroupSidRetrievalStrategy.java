package jpl.simle.security.acls;

import java.util.List;

import jpl.simle.domain.SIMLEUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;

public class GroupSidRetrievalStrategy extends SidRetrievalStrategyImpl
{
    
    private final static Logger logger_ = LoggerFactory.getLogger(GroupSidRetrievalStrategy.class);
    
    
    public GroupSidRetrievalStrategy()
    {
        
    }

    public GroupSidRetrievalStrategy(RoleHierarchy roleHierarchy)
    {
        super(roleHierarchy);
    }
    
    /*
    public List<Sid> getSids(Authentication authentication)
    {
        List<Sid> sids = super.getSids(authentication);
        
        // augment it with the group sid information
        logger_.debug("Adding group sid for " + authentication.getPrincipal());
        
        if ( authentication instanceof SIMLEUser )
        {
            SIMLEUser user = (SIMLEUser)authentication;
            sids.add(new GrantedAuthoritySid(new GroupAuthority(user.getGroup())));
        }
        
        logger_.debug("Sids = {}", sids);
            
        return sids;
    }
    */
}
