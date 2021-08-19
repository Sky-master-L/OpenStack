package com.lrj.openstack.service.impl;

import com.lrj.openstack.domain.entity.Task;
import com.lrj.openstack.domain.mapper.TaskMapper;
import com.lrj.openstack.service.TaskService;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}
