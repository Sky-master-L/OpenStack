package com.lrj.openstack.dto;

import lombok.Data;

import java.util.Map;

/**
 * ClassName: FlavorDto
 * Description: 操作flavor传参类
 * Date: 2021/5/31 10:47
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class FlavorDto {
    private String flavorName;
    private String flavorId;
    private int ram;
    private int vcpus;
    private int disk;
    private int ephemeral;
    private int swap;
    private float rxtxFactor;
    private boolean isPublic;
    private Map<String, String> extMap;
    private String extKey;
}
