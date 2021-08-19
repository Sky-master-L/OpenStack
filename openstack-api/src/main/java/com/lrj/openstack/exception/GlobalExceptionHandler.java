package com.lrj.openstack.exception;//package com.ray.qjy.exception;
//
//import com.ray.qjy.api.*;
//import lombok.extern.slf4j.Slf4j;
//import org.openstack4j.api.exceptions.ConnectionException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * ClassName: GlobalExceptionHandler
// * Description: manage全局异常
// * Date: 2021/5/31 14:54
// *
// * @author luorenjie
// * @version 1.0
// * @since JDK 1.8
// */
//@Slf4j
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = {ManageException.class, RuntimeException.class, Exception.class})
//    @ResponseBody
//    public ResponseEntity<?> internalExceptionHandler(Exception e) {
//        log.error("internalExceptionHandler: stacktrace={}", e);
//        BaseEnum baseEnum = null;
//        if(e instanceof ManageException){
//            baseEnum = ((ManageException) e).getBaseEnum();
//            ManageResponse manageResponse=ManageResponse.returnFail(baseEnum);
//            return new ResponseEntity(manageResponse, HttpStatus.OK);
//        }else if (e instanceof ConnectionException){
//            ManageResponse manageResponse=ManageResponse.returnFail(401, e.getMessage());
//            return new ResponseEntity(manageResponse, HttpStatus.OK);
//        }else{
//            //不是业务异常，返回500，代表服务器处理错误
//            baseEnum = baseEnum==null? ApiResponseEnum.INTERNAL_ERROR:baseEnum;
//            APIResponse apiResponse = APIResponse.returnFail(baseEnum);
//            return new ResponseEntity(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
