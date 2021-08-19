package com.lrj.openstack.dto;

import lombok.Data;

/**
 * ClassName: ProjectDto
 * Description:
 * Date: 2021/6/11 9:51
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class ProjectDto  extends AuthUserDto{
    private boolean enable = true;
}
