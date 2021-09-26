package com.lrj.openstack.service;

import com.lrj.openstack.domain.entity.Virtual;
import com.lrj.openstack.dto.InvokeInstanceDto;
import com.lrj.openstack.dto.InvokeUserDto;
import com.lrj.openstack.response.ManageResponse;

/**
 * ClassName: OpenstackCombineService
 * Description:
 * Date: 2021/6/26 16:07
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public interface OpenstackCombineService {
    ManageResponse invokeUser(InvokeUserDto invokeUserDto);

    ManageResponse createInstance(InvokeInstanceDto invokeInstanceDto);

    String checkResource(InvokeInstanceDto invokeInstanceDto);

    ManageResponse destroyInstance(InvokeInstanceDto invokeInstanceDto);

    ManageResponse actionInstance(InvokeInstanceDto invokeInstanceDto, boolean start);

}
