package com.lrj.openstack.service;

import com.lrj.openstack.domain.entity.RouterOs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lrj.openstack.domain.entity.Virtual;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lrj
 * @since 2021-08-19
 */
public interface RouterOsService extends IService<RouterOs> {

    Integer getUnbindPort(Virtual virtual, RouterOs routerOs, int i);

    void disable(RouterOs routerOs, Integer unbindPort);
}
