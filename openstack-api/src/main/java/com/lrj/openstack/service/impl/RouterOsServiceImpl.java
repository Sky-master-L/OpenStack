package com.lrj.openstack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lrj.openstack.domain.entity.RouterOs;
import com.lrj.openstack.domain.entity.RsPort;
import com.lrj.openstack.domain.entity.Virtual;
import com.lrj.openstack.domain.mapper.RouterOsMapper;
import com.lrj.openstack.domain.mapper.RsPortMapper;
import com.lrj.openstack.exception.ManageException;
import com.lrj.openstack.service.RouterOsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lrj
 * @since 2021-08-19
 */
@Service
@Slf4j
public class RouterOsServiceImpl extends ServiceImpl<RouterOsMapper, RouterOs> implements RouterOsService {

    @Autowired
    private RsPortMapper rsPortMapper;

    @Override
    public Integer getUnbindPort(Virtual virtual, RouterOs routerOs, int i) {
        //获取可用的网关端口资源
        //1, 直接获取当前资源表中未绑定的端口资源
        LambdaQueryWrapper<RsPort> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RsPort::getBind, 0)
                .eq(RsPort::getRsId, routerOs.getId())
                .orderByDesc(RsPort::getPort);
        List<RsPort> rsPorts = rsPortMapper.selectList(queryWrapper);
        //2, 如果不够，则新建端口资源返回该资源
        int port;
        if (CollectionUtils.isEmpty(rsPorts)) {
            //不够,新增一个端口
            queryWrapper.clear();
            queryWrapper.eq(RsPort::getRsId, routerOs.getId())
                    .orderByDesc(RsPort::getPort);
            List<RsPort> gps = rsPortMapper.selectList(queryWrapper);

            if (CollectionUtils.isEmpty(gps)) {
                port = 1025;
            } else {
                port = gps.get(0).getPort() + 1;
            }
            //目前先设置最高上限为:65535
            if (port > 65535) {
                throw new ManageException("{" + routerOs.getIp() + "}网关占用端口数量已达最高上限");
            }
            RsPort gp = new RsPort();
            gp.setRsId(routerOs.getId());
            gp.setPort(port);
            gp.setBind(1);
            gp.setVirtualId(virtual.getId());
            rsPortMapper.insert(gp);
        } else {
            //够，更新一个端口
            RsPort gp = rsPorts.get(0);
            port = gp.getPort();
            gp.setBind(1);
            gp.setVirtualId(virtual.getId());
            rsPortMapper.updateById(gp);
        }
        log.info("获取到的空闲端口======>: {}", port);
        return port;
    }

    @Override
    public void disable(RouterOs routerOs, Integer unbindPort) {
        if (routerOs!=null && unbindPort != null) {
            LambdaQueryWrapper<RsPort> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RsPort::getRsId, routerOs.getId()).eq(RsPort::getPort, unbindPort);
            List<RsPort> gatewayPorts = rsPortMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(gatewayPorts)) {
                RsPort rsPort = gatewayPorts.get(0);
                RsPort p = new RsPort();
                p.setId(rsPort.getId());
                p.setBind(1);
                p.setVirtualId(0);
                rsPortMapper.updateById(p);
                log.error("禁用端口:[id:{},gatewayId:{},port:{}]", p.getId(), routerOs.getId(), unbindPort);
            }
        }
    }
}
