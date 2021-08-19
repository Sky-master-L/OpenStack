package com.lrj.openstack.dto;

import lombok.Data;

/**
 * ClassName: FloatIpDto
 * Description:
 * Date: 2021/6/1 14:21
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class FloatIpDto {
    private String floatIpId;
    private String floatIp;
    private String fixedIp;
    private String poolName;
    private String instanceId;
}
