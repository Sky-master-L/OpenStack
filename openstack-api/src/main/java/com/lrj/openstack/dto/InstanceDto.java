package com.lrj.openstack.dto;

import lombok.Data;
import org.openstack4j.model.compute.Action;

import java.util.List;
import java.util.Map;

/**
 * ClassName: InstanceDto
 * Description:
 * Date: 2021/5/31 18:20
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class InstanceDto {
    private String instanceName;
    private String instanceId;
    private String flavorId;
    private String hypervisorId;
    private String imageId;
    private List<String> networkIds;
    private String personalityPath;
    private String personalityValue;
    private List<String> securityGroups;
    private Map<String, String> metaData;
    private String volumeId;
    private int num;
    private boolean details;
    private Action action;
    private Map<String, String> filteringParams;
}
