package com.lrj.openstack.vo;

import lombok.Data;

/**
 * @ClassName Nat
 * @Description ""
 * @Author wuzhou
 * @Date 2019/11/26  18:36
 * @Version 1.0
 */
@Data
public class NatVo {

    private String id;

    private String dstAddress;

    private String dstPort;

    private String toAddresses;

    private String toPorts;

    private String comment;

    private String protocol;
}
