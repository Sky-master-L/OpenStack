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
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RsPort implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * routerOS的id
     */
    private Integer rsId;

    /**
     * 分配给机器的端口号
     */
    private Integer port;

    /**
     * 机器id
     */
    private Integer virtualId;

    /**
     * 是否绑定（0否，1是）
     */
    private Integer bind;

    /**
     * 创建时间
     */
    private Date createTime;


}
