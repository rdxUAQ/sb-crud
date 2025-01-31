package com.sb.app.sb_crud.Security;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthoritieJsonCreator {

    public SimpleGrantedAuthoritieJsonCreator(@JsonProperty("authority")String role){

    }

}
