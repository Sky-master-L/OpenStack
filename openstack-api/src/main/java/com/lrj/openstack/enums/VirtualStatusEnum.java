package com.lrj.openstack.enums;

/**
 * ClassName: VirtualStatusEnum
 * Description:
 * Date: 2021/8/18 14:15
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public enum VirtualStatusEnum {
    RUNNING(1,"已开机"),
    STARTING(2,"开机中"),
    OFFING(3,"关机中"),
    STOP(4,"已关机"),
    CREATING(5,"创建中"),
    FAIL(6,"创建失败"),
    CANCEL(7,"取消创建"),
    ;
    private int code;
    private String des;

    VirtualStatusEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
