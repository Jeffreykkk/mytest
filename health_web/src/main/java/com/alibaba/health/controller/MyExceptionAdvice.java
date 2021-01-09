package com.alibaba.health.controller;


import com.alibaba.health.entity.Result;
import com.alibaba.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class MyExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(MyException.class);

    /**
     * 业务异常的处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e) {
        // 我们自己抛出的异常，把异常信息包装下返回即可
        return new Result(false, e.getMessage()) ;
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        //记录异常信息
        log.error("发生未知异常",e);
        return new Result(false, "发生未知异常，请联系管理员");
    }
}


