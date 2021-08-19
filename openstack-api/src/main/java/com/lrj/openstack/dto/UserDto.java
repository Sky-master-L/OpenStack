package com.lrj.openstack.dto;

import lombok.Data;

/**
 * ClassName: UserDto
 * Description:
 * Date: 2021/6/11 11:00
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class UserDto extends AuthUserDto{
    private String email;
}
