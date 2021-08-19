package com.lrj.openstack.service;

import com.lrj.openstack.domain.entity.Virtual;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
public interface VirtualService extends IService<Virtual> {
    List<Virtual> getCreatingStateVirtuals();
}
