package com.lrj.openstack.controller;

import com.alibaba.fastjson.JSONObject;
import com.lrj.openstack.dto.*;
import com.lrj.openstack.enums.ResponseEnum;
import com.lrj.openstack.exception.ManageException;
import com.lrj.openstack.response.ManageResponse;
import com.lrj.openstack.service.OpenstackService;
import lombok.extern.slf4j.Slf4j;
import org.openstack4j.api.OSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName: OpenstackController
 * Description: 调用openstack接口类
 * Date: 2021/5/27 15:54
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/qjy/manage/openstack")
@Slf4j
public class OpenstackController {

    @Autowired
    private OpenstackService openstackService;

    /*--------------------------------------------------keystone--------------------------------------------------*/


    /**
     * 获取token
     *
     * @param reqUserDto
     * @return
     */
    @PostMapping("/keystone/token")
    public ManageResponse getToken(@RequestBody ReqUserDto reqUserDto) {
        return openstackService.getAuthToke(reqUserDto);
    }

    /**
     * 创建project
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/project/create")
    public ManageResponse createProject(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("projectDto"));
        ProjectDto projectDto = JSONObject.parseObject(s1, ProjectDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createProject(osClient, projectDto);
    }

    /**
     * 查询project
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/project/list")
    public ManageResponse getProjectList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("projectDto"));
        ProjectDto projectDto = JSONObject.parseObject(s1, ProjectDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getProjectList(osClient, projectDto);
    }

    /**
     * 更新project
     *
     * @param params
     * @return
     */
    @PutMapping("/keystone/project/update")
    public ManageResponse updateProject(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("projectDto"));
        ProjectDto projectDto = JSONObject.parseObject(s1, ProjectDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.updateProject(osClient, projectDto);
    }

    /**
     * 删除project
     *
     * @param params
     * @return
     */
    @DeleteMapping("/keystone/project/del")
    public ManageResponse delProject(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("projectDto"));
        ProjectDto projectDto = JSONObject.parseObject(s1, ProjectDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delProject(osClient, projectDto);
    }

    /**
     * 创建user
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/user/create")
    public ManageResponse createUser(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("userDto"));
        UserDto userDto = JSONObject.parseObject(s1, UserDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createUser(osClient, userDto);
    }

    /**
     * 查询user
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/user/list")
    public ManageResponse getUserList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("userDto"));
        UserDto userDto = JSONObject.parseObject(s1, UserDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getUserList(osClient, userDto);
    }

    /**
     * 删除user
     *
     * @param params
     * @return
     */
    @DeleteMapping("/keystone/user/del")
    public ManageResponse delUser(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("userDto"));
        UserDto userDto = JSONObject.parseObject(s1, UserDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delUser(osClient, userDto);
    }

    /**
     * 创建role
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/role/create")
    public ManageResponse createRole(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("roleDto"));
        RoleDto roleDto = JSONObject.parseObject(s1, RoleDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createRole(osClient, roleDto);
    }

    /**
     * 查询role
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/role/list")
    public ManageResponse getRoleList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("roleDto"));
        RoleDto roleDto = JSONObject.parseObject(s1, RoleDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getRoleList(osClient, roleDto);
    }

    /**
     * 删除role
     *
     * @param params
     * @return
     */
    @DeleteMapping("/keystone/role/del")
    public ManageResponse delRole(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("roleDto"));
        RoleDto roleDto = JSONObject.parseObject(s1, RoleDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delRole(osClient, roleDto);
    }

    /**
     * 给用户分配role
     * domain或者project
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/role/grant")
    public ManageResponse grantUserRole(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("roleDto"));
        RoleDto roleDto = JSONObject.parseObject(s1, RoleDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.grantUserRole(osClient, roleDto);
    }

    /**
     * 检查用户是否分配role
     * domain或者project
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/role/check")
    public ManageResponse checkUserRole(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("roleDto"));
        RoleDto roleDto = JSONObject.parseObject(s1, RoleDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.checkUserRole(osClient, roleDto);
    }

    /**
     * 撤销已分配给用户的role
     * domain或者project
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/role/revoke")
    public ManageResponse revokeUserRole(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("roleDto"));
        RoleDto roleDto = JSONObject.parseObject(s1, RoleDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.revokeUserRole(osClient, roleDto);
    }

    /**
     * 创建region
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/region/create")
    public ManageResponse createRegion(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("regionDto"));
        RegionDto regionDto = JSONObject.parseObject(s1, RegionDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createRegion(osClient, regionDto);
    }

    /**
     * 查询region
     *
     * @param params
     * @return
     */
    @PostMapping("/keystone/region/list")
    public ManageResponse getRegionList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("regionDto"));
        RegionDto regionDto = JSONObject.parseObject(s1, RegionDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getRegionList(osClient, regionDto);
    }

    /**
     * 更新region
     *
     * @param params
     * @return
     */
    @PatchMapping("/keystone/region/update")
    public ManageResponse updateRegion(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("regionDto"));
        RegionDto regionDto = JSONObject.parseObject(s1, RegionDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.updateRegion(osClient, regionDto);
    }

    /**
     * 删除region
     *
     * @param params
     * @return
     */
    @DeleteMapping("/keystone/region/del")
    public ManageResponse delRegion(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("regionDto"));
        RegionDto regionDto = JSONObject.parseObject(s1, RegionDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delRegion(osClient, regionDto);
    }

//    /**
//     * 切换region
//     * @param params
//     * @return
//     */
//    @PostMapping("/keystone/region/switch")
//    public ManageResponse switchRegion(@RequestBody Map<String, Object> params){
//        if (ObjectUtils.isEmpty(params.get("reqUserDto"))){
//            log.error("身份验证失败");
//            throw new ManageException(ResponseEnum.FAIL);
//        }
//        String s = JSONObject.toJSONString(params.get("reqUserDto"));
//        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
//        String s1 = JSONObject.toJSONString(params.get("regionDto"));
//        RegionDto regionDto = JSONObject.parseObject(s1, RegionDto.class);
//        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
//        return openstackService.switchRegion(osClient, regionDto);
//    }

    /*--------------------------------------------------nova--------------------------------------------------*/

    /**
     * 创建配置规格
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/flavor/create")
    public ManageResponse createFlavor(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("flavorDto"));
        FlavorDto flavorDto = JSONObject.parseObject(s1, FlavorDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createFlavor(osClient, reqUserDto.getRid(), flavorDto);
    }

    /**
     * 获取单个或者多个配置规格信息
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/flavor/list")
    public ManageResponse getFlavorList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String fid = null;
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        if (!ObjectUtils.isEmpty("fid")) {
            fid = (String) params.get("fid");
        }
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getFlavorList(osClient, reqUserDto.getRid(), fid);
    }

    /**
     * 删除配置规格
     *
     * @param params
     * @return
     */
    @DeleteMapping("/nova/flavor/del")
    public ManageResponse delFlavor(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        if (ObjectUtils.isEmpty("fid")) {
            log.error("缺少参数");
            throw new ManageException(ResponseEnum.PARAMETER_ERROR);
        }
        String fid = (String) params.get("fid");

        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delFlavor(osClient, reqUserDto.getRid(), fid);
    }

    /**
     * 创建配置规格额外规格
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/flavor/createExt")
    public ManageResponse createFlavorExtra(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("flavorDto"));
        FlavorDto flavorDto = JSONObject.parseObject(s1, FlavorDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createFlavorExtra(osClient, reqUserDto.getRid(), flavorDto);
    }

    /**
     * 查询配置规格额外规格
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/flavor/listExt")
    public ManageResponse getFlavorExtra(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("flavorDto"));
        FlavorDto flavorDto = JSONObject.parseObject(s1, FlavorDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getFlavorExtra(osClient, reqUserDto.getRid(), flavorDto);
    }

    /**
     * 删除配置规格额外规格
     *
     * @param params
     * @return
     */
    @DeleteMapping("/nova/flavor/delExt")
    public ManageResponse delFlavorExtra(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("flavorDto"));
        FlavorDto flavorDto = JSONObject.parseObject(s1, FlavorDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delFlavorExtra(osClient, reqUserDto.getRid(), flavorDto);
    }

    /**
     * 创建带有metadata的flavor
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/flavor/createMeta")
    public ManageResponse createMetadataFlavor(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("flavorDto"));
        FlavorDto flavorDto = JSONObject.parseObject(s1, FlavorDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createMetadataFlavor(osClient, reqUserDto.getRid(), flavorDto);
    }

    /**
     * 获取单个或者多个镜像信息
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/image/list")
    public ManageResponse getComputeImageList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String imgId = null;
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        if (!ObjectUtils.isEmpty("imgId")) {
            imgId = (String) params.get("imgId");
        }
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getComputeImageList(osClient, reqUserDto.getRid(), imgId);
    }

    /**
     * 创建实例
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/create")
    public ManageResponse createInstance(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createInstance(osClient, instanceDto);
    }

    /**
     * 查询实例
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/list")
    public ManageResponse getInstanceList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getInstanceList(osClient, reqUserDto.getRid(), instanceDto);
    }

    /**
     * 删除指定实例
     *
     * @param params
     * @return
     */
    @DeleteMapping("/nova/instance/del")
    public ManageResponse delInstance(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delInstance(osClient, instanceDto);
    }

    /**
     * 操作实例，具体操作依据Action.type
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/action")
    public ManageResponse actionInstance(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.actionInstance(osClient, reqUserDto.getRid(), instanceDto);
    }

    /**
     * 重启实例
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/reboot")
    public ManageResponse rebootInstance(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.rebootInstance(osClient, reqUserDto.getRid(), instanceDto);
    }

    /**
     * 给实例添volume
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/addVolume")
    public ManageResponse addVolume(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.addVolume(osClient, instanceDto);
    }

    /**
     * 从实例移除volume
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/removeVolume")
    public ManageResponse removeVolume(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.removeVolume(osClient, instanceDto);
    }

    /**
     * 更新实例的元数据
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/instance/updateMetadata")
    public ManageResponse updateMetadata(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.updateMetadata(osClient, reqUserDto.getRid(), instanceDto);
    }

    /**
     * 创建浮动ip(从网络pool中)
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/floatIp/create")
    public ManageResponse createFloatIp(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("floatIpDto"));
        FloatIpDto floatIpDto = JSONObject.parseObject(s1, FloatIpDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createFloatIp(osClient, floatIpDto);
    }

    /**
     * 删除指定浮动ip
     *
     * @param params
     * @return
     */
    @DeleteMapping("/nova/floatIp/del")
    public ManageResponse delFloatIp(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("floatIpDto"));
        FloatIpDto floatIpDto = JSONObject.parseObject(s1, FloatIpDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delFloatIp(osClient, floatIpDto);
    }

    /**
     * 给实例分配浮动ip
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/floatIp/add")
    public ManageResponse addFloatIpToInstance(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("floatIpDto"));
        FloatIpDto floatIpDto = JSONObject.parseObject(s1, FloatIpDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.addFloatIpToInstance(osClient, floatIpDto);
    }

    /**
     * 从实例移除浮动ip
     *
     * @param params
     * @return
     */
    @PostMapping("/nova/floatIp/remove")
    public ManageResponse removeFloatIpFromInstance(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("floatIpDto"));
        FloatIpDto floatIpDto = JSONObject.parseObject(s1, FloatIpDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.removeFloatIpFromInstance(osClient, floatIpDto);
    }

    @PostMapping("/nova/hypervisor/list")
    public ManageResponse getHypervisorList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("instanceDto"));
        InstanceDto instanceDto = JSONObject.parseObject(s1, InstanceDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getHypervisorList(osClient, reqUserDto.getRid(), instanceDto);
    }


    /*--------------------------------------------------network--------------------------------------------------*/

    /**
     * 创建network
     *
     * @param params
     * @return
     */
    @PostMapping("/network/create")
    public ManageResponse createNetwork(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createNetwork(osClient, reqUserDto.getRid(), networkDto);
    }

    /**
     * 查询network
     *
     * @param params
     * @return
     */
    @PostMapping("/network/list")
    public ManageResponse getNetworkList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getNetworkList(osClient, networkDto);
    }

    /**
     * 删除network
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/del")
    public ManageResponse delNetwork(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delNetwork(osClient, networkDto);
    }

    /**
     * 创建子网subnet
     *
     * @param params
     * @return
     */
    @PostMapping("/network/subnet/create")
    public ManageResponse createSubnet(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createSubnet(osClient, networkDto);
    }

    /**
     * 查询子网subnet
     *
     * @param params
     * @return
     */
    @PostMapping("/network/subnet/list")
    public ManageResponse getSubnetList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getSubnetList(osClient, networkDto);
    }

    /**
     * 删除子网subnet
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/subnet/del")
    public ManageResponse delSubnet(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delSubnet(osClient, networkDto);
    }

    /**
     * 创建router
     *
     * @param params
     * @return
     */
    @PostMapping("/network/router/create")
    public ManageResponse createRouter(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createRouter(osClient, networkDto);
    }

    /**
     * 查询router
     *
     * @param params
     * @return
     */
    @PostMapping("/network/router/list")
    public ManageResponse getRouterList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getRouterList(osClient, networkDto);
    }

    /**
     * 给子网添加router
     *
     * @param params
     * @return
     */
    @PostMapping("/network/router/add")
    public ManageResponse addRouterToSubnet(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.addRouterToSubnet(osClient, networkDto);
    }

    /**
     * 从子网移除router
     *
     * @param params
     * @return
     */
    @PostMapping("/network/router/remove")
    public ManageResponse removeRouterFromSubnet(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.removeRouterFromSubnet(osClient, networkDto);
    }

    /**
     * 删除router
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/router/del")
    public ManageResponse delRouter(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("networkDto"));
        NetworkDto networkDto = JSONObject.parseObject(s1, NetworkDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delRouter(osClient, networkDto);
    }

    /**
     * 查询floating ip
     *
     * @param params
     * @return
     */
    @PostMapping("/network/floatIp/list")
    public ManageResponse getFloatingIpList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getFloatingIpList(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 给floating ip绑定qos policy
     *
     * @param params
     * @return
     */
    @PutMapping("/network/floatIp/bind")
    public ManageResponse bindQosPolicyToFloatingIp(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.bindQosPolicyToFloatingIp(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 创建网络扩展规则Qos-polices
     *
     * @param params
     * @return
     */
    @PostMapping("/network/qos/policy/create")
    public ManageResponse createPolicy(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createPolicy(osClient, qosDto);
    }

    /**
     * 查询网络扩展规则Qos-policy
     *
     * @param params
     * @return
     */
    @PostMapping("/network/qos/policy/list")
    public ManageResponse getPolicyList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getPolicyList(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 更新网络扩展规则Qos-policy
     *
     * @param params
     * @return
     */
    @PutMapping("/network/qos/policy/update")
    public ManageResponse updatePolicy(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.updatePolicy(osClient, qosDto);
    }

    /**
     * 删除网络扩展规则Qos-policy
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/qos/policy/del")
    public ManageResponse delPolicy(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delPolicy(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 创建带宽规则 Qos-policy-bandwidth
     *
     * @param params
     * @return
     */
    @PostMapping("/network/qos/policy/bandwidth/create")
    public ManageResponse createBandwidth(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createBandwidth(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 查询带宽规则 Qos-policy-bandwidth
     *
     * @param params
     * @return
     */
    @PostMapping("/network/qos/policy/bandwidth/list")
    public ManageResponse getBandwidthList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getBandwidthList(osClient, qosDto);
    }

    /**
     * 更新带宽规则 Qos-policy-bandwidth
     *
     * @param params
     * @return
     */
    @PutMapping("/network/qos/policy/bandwidth/update")
    public ManageResponse updateBandwidth(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.updateBandwidth(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 删除带宽规则 Qos-policy-bandwidth
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/qos/policy/bandwidth/del")
    public ManageResponse delBandwidth(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("qosDto"));
        QosDto qosDto = JSONObject.parseObject(s1, QosDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delBandwidth(osClient, reqUserDto.getRid(), qosDto);
    }

    /**
     * 创建安全组
     *
     * @param params
     * @return
     */
    @PostMapping("/network/sg/create")
    public ManageResponse createSG(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("sgDto"));
        SGDto sgDto = JSONObject.parseObject(s1, SGDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createSG(osClient, reqUserDto.getRid(), sgDto);
    }

    /**
     * 查询安全组
     *
     * @param params
     * @return
     */
    @PostMapping("/network/sg/list")
    public ManageResponse getSGList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("sgDto"));
        SGDto sgDto = JSONObject.parseObject(s1, SGDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getSGList(osClient, reqUserDto.getRid(), sgDto);
    }

    /**
     * 删除安全组
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/sg/del")
    public ManageResponse delSG(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("sgDto"));
        SGDto sgDto = JSONObject.parseObject(s1, SGDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delSG(osClient, reqUserDto.getRid(), sgDto);
    }

    /**
     * 创建安全组规则
     *
     * @param params
     * @return
     */
    @PostMapping("/network/sg/rule/create")
    public ManageResponse createSGRule(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("sgDto"));
        SGDto sgDto = JSONObject.parseObject(s1, SGDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createSGRule(osClient, reqUserDto.getRid(), sgDto);
    }

    /**
     * 查询安全组规则
     *
     * @param params
     * @return
     */
    @PostMapping("/network/sg/rule/list")
    public ManageResponse getSGRuleList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("sgDto"));
        SGDto sgDto = JSONObject.parseObject(s1, SGDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getSGRuleList(osClient, reqUserDto.getRid(), sgDto);
    }

    /**
     * 删除安全组规则
     *
     * @param params
     * @return
     */
    @DeleteMapping("/network/sg/rule/del")
    public ManageResponse delSGRule(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("sgDto"));
        SGDto sgDto = JSONObject.parseObject(s1, SGDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delSGRule(osClient, reqUserDto.getRid(), sgDto);
    }

    /*--------------------------------------------------cinder--------------------------------------------------*/

    /**
     * 创建volume
     *
     * @param params
     * @return
     */
    @PostMapping("/cinder/volume/create")
    public ManageResponse createVolume(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("volumeDto"));
        VolumeDto volumeDto = JSONObject.parseObject(s1, VolumeDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.createVolume(osClient, volumeDto);
    }

    /**
     * 获取volume
     *
     * @param params
     * @return
     */
    @PostMapping("/cinder/volume/list")
    public ManageResponse getVolumeList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("volumeDto"));
        VolumeDto volumeDto = JSONObject.parseObject(s1, VolumeDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getVolumeList(osClient, volumeDto);
    }

    /**
     * 删除volume
     *
     * @param params
     * @return
     */
    @DeleteMapping("/cinder/volume/del")
    public ManageResponse delVolume(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("volumeDto"));
        VolumeDto volumeDto = JSONObject.parseObject(s1, VolumeDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.delVolume(osClient, reqUserDto.getRid(), volumeDto);
    }

    @PostMapping("/cinder/volumePool/list")
    public ManageResponse getVolumeBackendPoolList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getVolumeBackendPoolList(osClient, reqUserDto.getRid());
    }


    /*--------------------------------------------------image--------------------------------------------------*/

    @PostMapping("/image/list")
    public ManageResponse getImageList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String s1 = JSONObject.toJSONString(params.get("imageDto"));
        ImageDto imageDto = JSONObject.parseObject(s1, ImageDto.class);
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getImageList(osClient, reqUserDto.getRid(), imageDto);
    }

    /**
     * 获取元数据定义
     *
     * @param params
     * @return
     */
    @PostMapping("/metadefs/list")
    public ManageResponse getMetadefsList(@RequestBody Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("reqUserDto"))) {
            log.error("身份验证失败");
            throw new ManageException(ResponseEnum.FAIL);
        }
        String s = JSONObject.toJSONString(params.get("reqUserDto"));
        ReqUserDto reqUserDto = JSONObject.parseObject(s, ReqUserDto.class);
        String namespace = (String) params.get("namespace");
        OSClient.OSClientV3 osClient = ((OSClient.OSClientV3) openstackService.getAuthToke(reqUserDto).getData());
        return openstackService.getMetadefsList(osClient, reqUserDto.getRid(), namespace);
    }


}
