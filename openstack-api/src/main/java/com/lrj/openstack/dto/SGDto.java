package com.lrj.openstack.dto;

import com.lrj.openstack.enums.EtherTypeEnum;
import lombok.Data;

/**
 * ClassName: SecurityGroupsDto
 * Description:
 * Date: 2021/6/26 12:12
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class SGDto extends PublicParamsDto {
    private String sgId;
    private String sgName;
    private String projectId;

    private String sgRuleId;
    private String direction;
    private String protocol;
    private String remoteIpPrefix;
    private Integer maxPort;
    private Integer minPort;
    private EtherTypeEnum etherTypeEnum;
}
