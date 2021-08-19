package com.lrj.openstack.config;

import com.lrj.openstack.domain.entity.Virtual;
import com.lrj.openstack.service.OpenstackAuthService;
import com.lrj.openstack.service.RegionService;
import com.lrj.openstack.service.VirtualService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * ClassName: InitInfos
 * Description: 启动初始化openstack用户认证信息
 * Date: 2021/6/18 15:12
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Slf4j
@Configuration
public class InvokeInfos implements ApplicationContextAware {

    @Autowired
    private OpenstackAuthService openstackAuthService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private VirtualService virtualService;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        openstackAuthService.initAuthInfos();
        log.info("初始化openstackAuth完成");

        regionService.initRegionInfo();
        log.info("初始化openstackRegion完成");

        List<Virtual> gpCustomerVirtualList = virtualService.getCreatingStateVirtuals();
        if (!CollectionUtils.isEmpty(gpCustomerVirtualList)){
            virtualService.updateBatchById(gpCustomerVirtualList);
            log.info("重置创建中机器状态为创建失败状态完成");
        }
    }
}
