package com.sb.app.sb_crud.consts;

public class ErrorBase {
    private final String code;
    private final String message;

    public ErrorBase(String code, String message){

        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    

}
