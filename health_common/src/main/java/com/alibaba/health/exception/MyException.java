package com.alibaba.health.exception;

/**
 *自定义异常：
 * 1.友好提示
 * 2.区分系统与自定义的异常
 * 3.终止已经不符合业务逻辑的代码
 */

public class MyException extends RuntimeException{
    public MyException(String message){
        super(message);
    }

}
