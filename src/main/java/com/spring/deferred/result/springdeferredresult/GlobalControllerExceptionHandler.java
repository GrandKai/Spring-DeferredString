package com.spring.deferred.result.springdeferredresult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
class GlobalControllerExceptionHandler {

  protected static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
  //返回304状态码
  @ResponseStatus(HttpStatus.NOT_MODIFIED)
  @ResponseBody
  //捕获特定异常
  @ExceptionHandler(AsyncRequestTimeoutException.class)
  public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpServletRequest request) {
    System.out.println("handleAsyncRequestTimeoutException");
  }

}
