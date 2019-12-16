package com.springbootjpa.codeGod.codeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGodException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(CodeGodException.class);

    public CodeGodException(){
        super();
    }

    public CodeGodException(String msg,Class<?> c){
        super(msg);
        logger.error(c.toString()+": "+msg);
    }

    //用指定的详细信息和原因构造一个新的异常
    public CodeGodException(String message, Throwable cause,Class<?> c){
        super(message,cause);
        logger.error(c.toString()+": "+message,cause);

    }

    //用指定原因构造一个新的异常
    public CodeGodException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
