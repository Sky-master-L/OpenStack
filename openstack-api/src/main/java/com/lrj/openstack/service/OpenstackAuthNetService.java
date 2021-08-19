package com.lrj.openstack.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lrj.openstack.domain.entity.OpenstackAuthNet;

import java.util.List;

/**
 * <p>
 * 用户在openstack不同region关联的网络信息表 服务类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
public interface OpenstackAuthNetService extends IService<OpenstackAuthNet> {
    OpenstackAuthNet getOpenstackAuthNet(Integer rid, Integer uid);

    List<OpenstackAuthNet> getOpenstackAuthNets();
}
