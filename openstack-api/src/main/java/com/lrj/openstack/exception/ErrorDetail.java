package com.lrj.openstack.exception;

import lombok.Data;

/**
 * @Description ""
 * @Author lyq
 * @Date 2021/3/2 16:33
 * @Version 1.0
 */
@Data
public class ErrorDetail {
    private String message ;
    private int code;
    public ErrorDetail(){}
    public ErrorDetail(int code, String message){
        this.message=message;
        this.code=code;
    }
}
