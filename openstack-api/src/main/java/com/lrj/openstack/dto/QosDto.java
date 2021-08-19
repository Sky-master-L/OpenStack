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
public class QosDto extends PublicParamsDto {
    private String policyId;
    private String policyName;
    private String projectId;
    private Boolean share;
    private Boolean def;
    private String fid;

    private String bandwidthId;
    private String direction;
    private int maxKbps ;
    private int maxBurstKbps ;
}
