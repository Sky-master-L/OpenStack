package com.lrj.openstack.service.impl;

import com.lrj.openstack.domain.entity.Customer;
import com.lrj.openstack.domain.mapper.CustomerMapper;
import com.lrj.openstack.service.CustomerService;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
