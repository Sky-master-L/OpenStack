package com.lrj.openstack.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lrj.openstack.enums.ResponseEnum;
import com.lrj.openstack.exception.ErrorDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ClassName: ManageResponse
 * Description:
 * Date: 2021/5/31 15:07
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManageResponse<T> implements Serializable {
    private T data;
    private Error error;
    private int okCode;
    private long serverTime;

    public ManageResponse() {
        this.setData((T) new ArrayList());
        this.setOkCode(1);
        this.serverTime = System.currentTimeMillis();
    }

    public ManageResponse(T data, int okCode) {
        this.data = data;
        this.okCode = okCode;
        this.serverTime = System.currentTimeMillis();
    }

    public ManageResponse(Error error, int okCode) {
        this.error = error;
        this.okCode = okCode;
        this.serverTime = System.currentTimeMillis();
    }

    public static <T> ManageResponse<T> returnSuccess(T data) {
        return new ManageResponse(data, 1);
    }

    public static <T> ManageResponse<T> returnSuccess() {
        return new ManageResponse();
    }

    public static <T> ManageResponse<T> returnFail(ResponseEnum responseEnum) {
        ErrorDetail errorDetail = new ErrorDetail(responseEnum.getCode(), responseEnum.getDesZh());
        return new ManageResponse(errorDetail, 0);
    }

    public static <T> ManageResponse<T> returnFail(int id, String msg) {
        ErrorDetail errorDetail = new ErrorDetail(id, msg);
        return new ManageResponse(errorDetail, 0);
    }

}
