package com.sb.app.sb_crud.consts;

public class ErrorMessages {

    public static final ErrorBase EntityNotFound = new ErrorBase("ENT001", "Entity with id provide have not been found");
    public static final ErrorBase EntityUsernameRegistered = new ErrorBase("ENT002", "The username provided is already taken by another entity");

}
