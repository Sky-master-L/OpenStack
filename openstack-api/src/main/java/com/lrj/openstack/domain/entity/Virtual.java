package com.lrj.openstack.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Virtual implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机器名称
     */
    private String name;

    /**
     * 机器密码
     */
    private String password;

    /**
     * 机器状态（1已开机，2开机中，3关机中，4已关机，5创建中，6创建失败，7取消创建）
     */
    private Integer status;

    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * openstack实例id
     */
    private String instanceId;

    /**
     * openstack实例创建进度
     */
    private String progress;

    /**
     * openstack浮动ip
     */
    private String floatingIp;

    /**
     * openstack浮动ip id
     */
    private String floatingIpId;

    /**
     * openstack策略id
     */
    private String policyId;

    /**
     * openstack数据盘id
     */
    private String volumeId;

    /**
     * openstack区域id
     */
    private Integer regionId;


}
