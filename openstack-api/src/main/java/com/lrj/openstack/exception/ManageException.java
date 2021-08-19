package com.lrj.openstack.exception;


import com.lrj.openstack.enums.ResponseEnum;

/**
 * ClassName: ManageException
 * Description: 异常类
 * Date: 2021/5/31 14:17
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public class ManageException extends RuntimeException {
    ResponseEnum responseEnum;
    Object errorData;

    public ManageException(ResponseEnum responseEnum) {
        super(responseEnum.getDesZh());
        this.responseEnum = responseEnum;
    }

    public ManageException(RuntimeException e) {
        super(e);
    }

    public ManageException(ResponseEnum responseEnum, Object errorData) {
        super(responseEnum.getDesZh());
        this.responseEnum = responseEnum;
        this.errorData = errorData;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }

    public Object getErrorData() {
        return errorData;
    }

    public ManageException(Throwable cause) {
        super(cause);
    }

    public ManageException(String errMsg) {
        super(errMsg);
    }
}
