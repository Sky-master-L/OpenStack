package com.lrj.openstack.controller;

import com.lrj.openstack.dto.InvokeInstanceDto;
import com.lrj.openstack.dto.InvokeUserDto;
import com.lrj.openstack.response.ManageResponse;
import com.lrj.openstack.service.OpenstackCombineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: OpenstackCombineController
 * Description: openstack聚合调用接口类
 * Date: 2021/6/26 15:53
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/qjy/manage/openstack/combine")
@Slf4j
public class OpenstackCombineController {

    @Autowired
    private OpenstackCombineService openstackCombineService;
//    @Autowired
//    private MsgPublisher publisher;
//    @Value("${mq.routing-key}")
//    private String key;
//    @Value("${mq.routing-key1}")
//    private String key1;

    /**
     * 初始化openstack平台用户相关资源
     * 使用admin账号给用户创建project、user、network、qos、security groups等资源
     * @param invokeUserDto
     * @return
     */
    @PostMapping("/invoke/user")
    public ManageResponse invokeUser(@RequestBody InvokeUserDto invokeUserDto){
        return openstackCombineService.invokeUser(invokeUserDto);
    }

    /**
     * 根据用户id创建实例，初始化实例资源
     * @param invokeInstanceDto
     * @return
     */
    @PostMapping("/invoke/instance")
    public ManageResponse createInstance(@RequestBody InvokeInstanceDto invokeInstanceDto){
        return openstackCombineService.createInstance(invokeInstanceDto);
    }

    /**
     * 创建实例前检测资源总量
     * @param invokeInstanceDto
     * @return
     */
    @PostMapping("/invoke/checkResource")
    public String checkResource(@RequestBody InvokeInstanceDto invokeInstanceDto){
        return openstackCombineService.checkResource(invokeInstanceDto);
    }
//
//    @GetMapping("/invoke/sent")
//    public void sentMsg(){
//        publisher.send(key, new ArrayList<>(Arrays.asList(1, 2, 3)));
//        publisher.send(key1, new ArrayList<>(Arrays.asList("hello world")));
//    }
}
