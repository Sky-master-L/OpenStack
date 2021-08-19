package com.lrj.openstack.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * openstack keystone服务验证信息表
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpenstackAuth implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * openstack权限用户id
     */
    private String userId;

    /**
     * openstack权限用户名称
     */
    private String userName;

    /**
     * openstack权限用户密码
     */
    private String password;

    /**
     * openstack权限域id
     */
    private String domainId;

    /**
     * openstack权限域名称
     */
    private String domainName;

    /**
     * openstack项目id
     */
    private String projectId;

    /**
     * openstack项目名称
     */
    private String projectName;

    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 用户类型（1.openstack用户，2.用户）
     */
    private Integer type;


}
