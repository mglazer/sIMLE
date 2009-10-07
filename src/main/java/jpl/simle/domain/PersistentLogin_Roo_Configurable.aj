package jpl.simle.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect PersistentLogin_Roo_Configurable {
    
    declare @type: PersistentLogin: @Configurable;    
    
}
