package com.lrj.openstack.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户在openstack不同region关联的网络信息表
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpenstackAuthNet implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * openstack网络id
     */
    private String networkId;

    /**
     * openstack路由id
     */
    private String routerId;

    /**
     * openstack安全组id
     */
    private String sgId;

    /**
     * openstack子网id
     */
    private String subnetId;

    /**
     * openstack区域id
     */
    private Integer regionId;

    /**
     * 用户id
     */
    private Integer customerId;


}
