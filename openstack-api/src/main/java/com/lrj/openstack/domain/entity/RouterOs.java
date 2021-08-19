package com.lrj.openstack.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouterOs implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 网关ip
     */
    private String ip;

    /**
     * 连接端口号
     */
    private Integer port;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登录密码
     */
    private String pasword;

    /**
     * 区域
     */
    private Integer regionId;


}
