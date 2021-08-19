package com.lrj.openstack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lrj.openstack.domain.entity.Virtual;
import com.lrj.openstack.domain.mapper.VirtualMapper;
import com.lrj.openstack.enums.VirtualStatusEnum;
import com.lrj.openstack.service.VirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Service
public class VirtualServiceImpl extends ServiceImpl<VirtualMapper, Virtual> implements VirtualService {
    @Autowired
    private VirtualMapper virtualMapper;

    @Override
    public List<Virtual> getCreatingStateVirtuals() {
        LambdaQueryWrapper<Virtual> gqw = new LambdaQueryWrapper<>();
        gqw.eq(Virtual::getStatus, VirtualStatusEnum.CREATING.getCode());
        List<Virtual> gpCustomerVirtualList = virtualMapper.selectList(gqw);
        if (CollectionUtils.isEmpty(gpCustomerVirtualList)) {
            return null;
        }
        for (Virtual gpCustomerVirtual : gpCustomerVirtualList) {
            gpCustomerVirtual.setStatus(VirtualStatusEnum.FAIL.getCode());
        }
        return gpCustomerVirtualList;
    }
}
