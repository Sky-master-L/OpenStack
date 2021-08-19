package com.lrj.openstack.service.impl;

import com.lrj.openstack.domain.entity.TaskFailLog;
import com.lrj.openstack.domain.mapper.TaskFailLogMapper;
import com.lrj.openstack.service.TaskFailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Service
public class TaskFailLogServiceImpl extends ServiceImpl<TaskFailLogMapper, TaskFailLog> implements TaskFailLogService {

}
