package com.lrj.openstack.service;

import com.lrj.openstack.domain.entity.OpenstackAuth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * openstack keystone服务验证信息表 服务类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
public interface OpenstackAuthService extends IService<OpenstackAuth> {

    /**
     * 初始化
     */
    void initAuthInfos();

    /**
     * 查询openstack token请求参数信息
     *
     * @param userId
     * @return
     */
    OpenstackAuth getOpenStackAuthInfo(Integer userId);

    /**
     * 创建openstack token请求参数信息
     *
     * @param openstackAuth
     */
    boolean create(OpenstackAuth openstackAuth);

    /**
     * 更新openstack token请求参数信息
     *
     * @param openstackAuth
     * @return
     */
    void update(OpenstackAuth openstackAuth);
}
