package com.lrj.openstack.dto;

import lombok.Data;

/**
 * ClassName: RoleDto
 * Description:
 * Date: 2021/6/11 15:31
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class RoleDto extends AuthUserDto{
    private String roleId;
    private String roleName;
}
