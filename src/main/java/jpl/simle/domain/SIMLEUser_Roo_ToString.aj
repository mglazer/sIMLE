package jpl.simle.domain;

privileged aspect SIMLEUser_Roo_ToString {
    
    public java.lang.String SIMLEUser.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("username: ").append(getUsername()).append(", ");        
        sb.append("userType: ").append(getUserType()).append(", ");        
        sb.append("acceptableCreatableUserTypes: ").append(java.util.Arrays.toString(getAcceptableCreatableUserTypes())).append(", ");        
        sb.append("authorities: ").append(getAuthorities() == null ? "null" : getAuthorities().size()).append(", ");        
        sb.append("accountNonExpired: ").append(isAccountNonExpired()).append(", ");        
        sb.append("accountNonLocked: ").append(isAccountNonLocked()).append(", ");        
        sb.append("credentialsNonExpired: ").append(isCredentialsNonExpired()).append(", ");        
        sb.append("enabled: ").append(getEnabled()).append(", ");        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("password: ").append(getPassword()).append(", ");        
        sb.append("enabled: ").append(getEnabled()).append(", ");        
        sb.append("group: ").append(getGroup()).append(", ");        
        sb.append("salt: ").append(getSalt());        
        sb.append("expirationDate: ").append(getExpirationDate());        
        return sb.toString();        
    }    
    
}
