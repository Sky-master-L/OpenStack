package com.lrj.openstack.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lrj.openstack.utils.DatePlugin;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: ChildVirtualDto
 * @Description:
 * @Date: 2020/9/9 17:20
 * @Author luorenjie
 * @Version 1.0
 * @since JDK 1.8
 */
@Data
public class UserVirtualDto {
    private String rayName;
    private String userName;
    private Integer userId;
    private Integer regionId;
    private Integer productTypeId;
    private Integer status;
    private Integer type;
    @JsonSerialize(using = DatePlugin.class)
    private Date endDate;
    @JsonSerialize(using = DatePlugin.class)
    private Date releaseDate;
    private Integer pricingMode;
    //桌面类型:1.zstack;2.openstack
    private Integer stackType;
}
