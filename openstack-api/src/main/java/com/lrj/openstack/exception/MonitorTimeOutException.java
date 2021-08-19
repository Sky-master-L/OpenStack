package com.lrj.openstack.exception;

/**
 * @ClassName MonitorTimeOutException
 * @Description ""
 * @Author wuzhou
 * @Date 2019/12/4  19:32
 * @Version 1.0
 */

public class MonitorTimeOutException extends ManageException {

    public MonitorTimeOutException(RuntimeException e) {
        super(e);
    }


    public MonitorTimeOutException(String errMsg) {
        super(errMsg);
    }

    public MonitorTimeOutException(Throwable cause) {
        super(cause);
    }
}
