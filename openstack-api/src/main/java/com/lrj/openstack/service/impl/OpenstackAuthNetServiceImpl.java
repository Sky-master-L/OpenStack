package com.lrj.openstack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lrj.openstack.domain.entity.OpenstackAuthNet;
import com.lrj.openstack.domain.mapper.OpenstackAuthNetMapper;
import com.lrj.openstack.service.OpenstackAuthNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户在openstack不同region关联的网络信息表 服务实现类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Service
public class OpenstackAuthNetServiceImpl extends ServiceImpl<OpenstackAuthNetMapper, OpenstackAuthNet> implements OpenstackAuthNetService {
    @Autowired
    private OpenstackAuthNetMapper openstackAuthNetMapper;

    @Override
    public OpenstackAuthNet getOpenstackAuthNet(Integer rid, Integer uid) {
        List<OpenstackAuthNet> openstackAuthNets = getOpenstackAuthNets();
        if (CollectionUtils.isEmpty(openstackAuthNets)) {
            return null;
        }
        List<OpenstackAuthNet> result = openstackAuthNets.stream().filter(x -> x.getCustomerId().equals(uid) && x.getRegionId().equals(rid)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<OpenstackAuthNet> getOpenstackAuthNets() {
        List<OpenstackAuthNet> openstackAuthNets = openstackAuthNetMapper.selectList(new QueryWrapper<>());
        return openstackAuthNets;
    }
}
