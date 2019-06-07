package com.ufrpe.wallot_app.infra;

public class WallotAppException extends Exception {
    public WallotAppException(String msg) {super(msg);}
    public WallotAppException(String msg, Throwable cause) {super(msg, cause);}
}