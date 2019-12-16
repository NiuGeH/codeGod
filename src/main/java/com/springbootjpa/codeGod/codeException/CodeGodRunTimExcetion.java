package com.springbootjpa.codeGod.codeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGodRunTimExcetion extends RuntimeException {
    private static Logger logger = LoggerFactory.getLogger(CodeGodRunTimExcetion.class);

    public CodeGodRunTimExcetion(){
        super();
    }

    public CodeGodRunTimExcetion(String msg,Class<?> c){
        super(msg);
        logger.error(c.toString()+": "+msg);
    }

    //用指定的详细信息和原因构造一个新的异常
    public CodeGodRunTimExcetion(String message, Throwable cause,Class<?> c){
        super(message,cause);
        logger.error(c.toString()+": "+message,cause);

    }
    //用指定原因构造一个新的异常
    public CodeGodRunTimExcetion(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
