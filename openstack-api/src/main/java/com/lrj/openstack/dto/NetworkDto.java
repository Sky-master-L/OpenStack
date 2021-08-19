package com.lrj.openstack.dto;

import lombok.Data;

import java.util.List;

/**
 * ClassName: NetworkDto
 * Description:
 * Date: 2021/6/1 20:04
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class NetworkDto {
    private String networkId;
    private String networkName;
    private String subnetId;
    private String subnetName;
    private String routerId;
    private String routerName;
    private String projectId;
    private String ipStart;
    private String ipEnd;
    private String cidr;
    private List<String> nameServers;
}
