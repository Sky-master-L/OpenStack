package com.lrj.openstack.enums;

/**
 * ClassName: DirectionEnum
 * Description:
 * Date: 2021/7/1 14:48
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public enum DirectionEnum {
    INGRESS("ingress"),
    EGRESS("egress");

    private String val;

    public String getVal() {
        return val;
    }

    DirectionEnum(String val) {
        this.val = val;
    }
}
