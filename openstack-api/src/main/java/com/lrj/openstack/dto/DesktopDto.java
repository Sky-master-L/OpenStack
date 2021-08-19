package com.lrj.openstack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName DesktopVo
 * @Description ""
 * @Author wuzhou
 * @Date 2020/1/2  14:11
 * @Version 1.0
 */

@Data
public class DesktopDto {

    private Integer userId;

    private String userName;

    private Integer id;

    private String virtualName;

    private String productTypeName;

    private Integer productTypeId;

    private Integer productId;

    private Integer regionId;

    private String regionName;

    private String ip;

    private Integer gatewayPort;

    private String virtualUuid;

    private String password;

    private Integer desktopStatus;

    private Integer virtualStatus;

    private Boolean normal=true;

    private Integer exceptionId;

    private Integer isDelete=0;

    private List<Integer> ports;

    private Boolean hasNetworkTest;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    //@JsonSerialize(using = DatePlugin.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private Integer pricingMode;

    private Integer supportFee;

    /**
     * 1、3、7天到期
     */
    private Integer expireTime;

    /**
     * 桌面类型:1.zstack;2.openstack
     */
    private Integer type;
    /**
     *
     * 实例类型id
     *
     **/
    private Integer instanceTypeId;
}
