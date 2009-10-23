package jpl.simle.domain;

import java.lang.String;

privileged aspect SIMLEUser_Roo_ToString {
    
    public String SIMLEUser.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Username: ").append(getUsername()).append(", ");        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("UserType: ").append(getUserType()).append(", ");        
        sb.append("AcceptableCreatableUserTypes: ").append(java.util.Arrays.toString(getAcceptableCreatableUserTypes())).append(", ");        
        sb.append("Authorities: ").append(getAuthorities() == null ? "null" : getAuthorities().size()).append(", ");        
        sb.append("AccountNonExpired: ").append(isAccountNonExpired()).append(", ");        
        sb.append("AccountNonLocked: ").append(isAccountNonLocked()).append(", ");        
        sb.append("CredentialsNonExpired: ").append(isCredentialsNonExpired()).append(", ");        
        sb.append("Enabled: ").append(getEnabled()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Password: ").append(getPassword()).append(", ");        
        sb.append("Enabled: ").append(getEnabled()).append(", ");        
        sb.append("Group: ").append(getGroup());        
        sb.append("Salt: ").append(getSalt());        
        return sb.toString();        
    }    
    
}
