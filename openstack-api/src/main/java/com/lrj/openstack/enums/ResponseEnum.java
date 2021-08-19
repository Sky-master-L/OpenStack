package com.lrj.openstack.enums;

/**
 * ClassName: ResponseEnum
 * Description:
 * Date: 2021/8/13 15:04
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public enum ResponseEnum {
    SUCCESS(200, "success", "成功"),
    FAIL(100, "fail", "失败"),
    FORBIDDEN(403, "forbidden", "没有权限"),
    PARAMETER_ERROR(600, "parameter error", "参数错误"),
    PARAMETER_ILLEGAL(601, "parameter illegal", "参数非法");

    private int code;
    private String desEn;
    private String desZh;

    ResponseEnum(int code, String desEn, String desZh) {
        this.code = code;
        this.desEn = desEn;
        this.desZh = desZh;
    }

    public int getCode() {
        return code;
    }

    public String getDesEn() {
        return desEn;
    }

    public String getDesZh() {
        return desZh;
    }
}
