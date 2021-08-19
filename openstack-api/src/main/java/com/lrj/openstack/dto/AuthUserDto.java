package com.lrj.openstack.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * ClassName: AuthUserDto
 * Description:
 * Date: 2021/5/27 14:13
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUserDto extends PublicParamsDto {
    private String userId;
    private String userName;
    private String passwd;
    private String projectId;
    private String projectName;
    private String domainId;
    private String domainName;
    private Integer type;
}
