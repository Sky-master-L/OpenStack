package com.lrj.openstack.service.impl;

import com.lrj.openstack.domain.entity.OpenstackAuth;
import com.lrj.openstack.dto.*;
import com.lrj.openstack.enums.ResponseEnum;
import com.lrj.openstack.response.ManageResponse;
import com.lrj.openstack.service.OpenstackAuthService;
import com.lrj.openstack.service.OpenstackService;
import com.lrj.openstack.service.RegionService;
import com.lrj.openstack.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.api.exceptions.ClientResponseException;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.*;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.builder.ServerCreateBuilder;
import org.openstack4j.model.compute.ext.Hypervisor;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.model.identity.v3.Region;
import org.openstack4j.model.identity.v3.Role;
import org.openstack4j.model.identity.v3.User;
import org.openstack4j.model.metadefs.Metadefs;
import org.openstack4j.model.network.*;
import org.openstack4j.model.network.SecurityGroup;
import org.openstack4j.model.network.builder.SubnetBuilder;
import org.openstack4j.model.network.ext.NetQosPolicy;
import org.openstack4j.model.network.ext.NetQosPolicyBandwidthLimitRule;
import org.openstack4j.model.network.ext.builder.NetQosPolicyBandwidthLimitRuleBuilder;
import org.openstack4j.model.network.ext.builder.NetQosPolicyUpdateBuilder;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.storage.block.domain.VolumeBackendPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: OpenstackServiceImpl
 * Description:
 * Date: 2021/5/27 15:56
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class OpenstackServiceImpl implements OpenstackService {
    @Value("${openstack.keystone.url}")
    private String keystoneUrl;
    @Autowired
    private OpenstackAuthService openstackAuthService;
    @Autowired
    private RegionService regionService;

    private static float DEFAULT_RXTXFACTOR = 1.0f;

    /**
     * <p>
     * 1.无域（unscoped）的权限认证
     * 2.有域（scoped）的权限认证
     * 1).project scoped authentication
     * 2).domain scoped authentication,using the unique userId does not require a domainIdentifier
     * 3).Scoping to a project just by name isn't possible as the project name is only unique within a domain.
     * You can either use this as the id of the project is unique across domains
     * </p>
     * <p>
     * authUserDto.type:
     * 1.domain下的user
     * 2.project下的user
     * 3.project下含有domain的user
     * 4.domain下通过dominId
     * 5.domain下通过dominName
     * </p>
     *
     * @param reqUserDto
     * @return
     */
    @Override
    public ManageResponse getAuthToke(ReqUserDto reqUserDto) {
        try {
            AuthUserDto authUserDto = getAuthUser(reqUserDto.getUserId());
            OSClient.OSClientV3 osClientV3 = null;
            Integer reqType=reqUserDto.getReqType()==null?3:reqUserDto.getReqType();
            switch (reqType) {
                case 1:
                    osClientV3 = OSFactory.builderV3()
                            .endpoint(keystoneUrl)
                            .credentials(authUserDto.getUserName(), authUserDto.getPasswd(), Identifier.byId(authUserDto.getDomainId()))
                            .authenticate();
                    break;
                case 2:
                    osClientV3 = OSFactory.builderV3()
                            .endpoint(keystoneUrl)
                            .credentials(authUserDto.getUserId(), authUserDto.getPasswd(), Identifier.byName(authUserDto.getDomainName()))
                            .scopeToProject(Identifier.byId(authUserDto.getProjectName()))
                            .authenticate();
                    break;
                case 3:
                    osClientV3 = OSFactory.builderV3()
                            .endpoint(keystoneUrl)
                            .credentials(authUserDto.getUserId(), authUserDto.getPasswd())
                            .scopeToProject(Identifier.byName(authUserDto.getProjectName()), Identifier.byId(authUserDto.getDomainId()))
                            .authenticate();
                    break;
                case 4:
                    osClientV3 = OSFactory.builderV3()
                            .endpoint(keystoneUrl)
                            .credentials(authUserDto.getUserId(), authUserDto.getPasswd())
                            .scopeToDomain(Identifier.byId(authUserDto.getDomainId()))
                            .authenticate();
                    break;
                case 5:
                    osClientV3 = OSFactory.builderV3()
                            .endpoint(keystoneUrl)
                            .credentials(authUserDto.getUserId(), authUserDto.getPasswd())
                            .scopeToDomain(Identifier.byName(authUserDto.getDomainName()))
                            .authenticate();
                    break;
                default:
                    log.error("参数类型错误：{}", reqType);
                    break;
            }
            return ManageResponse.returnSuccess(osClientV3);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取token异常：{}", e);
            if (e instanceof AuthenticationException) {
                return ManageResponse.returnFail(((AuthenticationException) e).getStatus(), e.getMessage());
            }
        }
        return null;
    }

    @Override
    public ManageResponse createProject(OSClient.OSClientV3 os, ProjectDto projectDto) {
        try {
            Project project = os.identity().projects().create(Builders.project()
                    .name(projectDto.getProjectName())
                    .description(projectDto.getDescription())
                    .enabled(projectDto.isEnable())
                    .build());
            return ManageResponse.returnSuccess(project);
        } catch (Exception e) {
            log.error("创建租户（project）失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getProjectList(OSClient.OSClientV3 os, ProjectDto projectDto) {
        List<Project> projects = new ArrayList<>();
        if (!ObjectUtils.isEmpty(projectDto.getProjectId())) {
            Project project = os.identity().projects().get(projectDto.getProjectId());
            if (!ObjectUtils.isEmpty(project)) {
                projects.add(project);
            }
            return ManageResponse.returnSuccess(projects);
        }
        if (!ObjectUtils.isEmpty(projectDto.getProjectName())) {
            if (!ObjectUtils.isEmpty(projectDto.getDomainId())) {
                Project project = os.identity().projects().getByName(projectDto.getProjectName(), projectDto.getDomainId());
                if (!ObjectUtils.isEmpty(project)) {
                    projects.add(project);
                }
                return ManageResponse.returnSuccess(projects);
            }
            projects = (List<Project>) os.identity().projects().getByName(projectDto.getProjectName());
            return ManageResponse.returnSuccess(projects);
        }
        if (!ObjectUtils.isEmpty(projectDto.getUserId())) {
            projects = (List<Project>) os.identity().users().listUserProjects(projectDto.getUserId());
            return ManageResponse.returnSuccess(projects);
        }
        projects = (List<Project>) os.identity().projects().list();
        return ManageResponse.returnSuccess(projects);
    }

    @Override
    public ManageResponse updateProject(OSClient.OSClientV3 os, ProjectDto projectDto) {
        try {
            Project project = os.identity().projects().update(Builders.project().enabled(false).build());
            return ManageResponse.returnSuccess(project);
        } catch (Exception e) {
            log.error("更新租户（project）失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse delProject(OSClient.OSClientV3 os, ProjectDto projectDto) {
        ActionResponse actionResponse = os.identity().projects().delete(projectDto.getProjectId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createUser(OSClient.OSClientV3 os, UserDto userDto) {
        try {
            User user = os.identity().users().create(Builders.user()
                    .name(userDto.getUserName())
                    .description(userDto.getDescription())
                    .defaultProjectId(userDto.getProjectId())
                    .password(userDto.getPasswd())
                    .email(userDto.getEmail())
                    .domainId(userDto.getDomainId())
                    .build());
            return ManageResponse.returnSuccess(user);
        } catch (Exception e) {
            log.error("创建用户（user）失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getUserList(OSClient.OSClientV3 os, UserDto userDto) {
        List<User> users = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userDto.getUserId())) {
            User user = os.identity().users().get(userDto.getUserId());
            if (!ObjectUtils.isEmpty(user)) {
                users.add(user);
            }
            return ManageResponse.returnSuccess(users);
        }
        if (!ObjectUtils.isEmpty(userDto.getUserName())) {
            if (!ObjectUtils.isEmpty(userDto.getDomainId())) {
                User user = os.identity().users().getByName(userDto.getUserName(), userDto.getDomainId());
                if (!ObjectUtils.isEmpty(user)) {
                    users.add(user);
                }
                return ManageResponse.returnSuccess(users);
            }
            users = (List<User>) os.identity().users().getByName(userDto.getUserName());
            return ManageResponse.returnSuccess(users);
        }
        users = (List<User>) os.identity().users().list();
        return ManageResponse.returnSuccess(users);
    }

    @Override
    public ManageResponse delUser(OSClient.OSClientV3 os, UserDto userDto) {
        ActionResponse actionResponse = os.identity().users().delete(userDto.getUserId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createRole(OSClient.OSClientV3 os, RoleDto roleDto) {
        try {
            Role role = os.identity().roles().create(Builders.role()
                    .name(roleDto.getRoleName())
                    .domainId(roleDto.getDomainId())
                    .build());
            return ManageResponse.returnSuccess(role);
        } catch (Exception e) {
            log.error("创建角色（role）失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getRoleList(OSClient.OSClientV3 os, RoleDto roleDto) {
        List<Role> roles = new ArrayList<>();
        if (!ObjectUtils.isEmpty(roleDto.getRoleId())) {
            Role role = os.identity().roles().get(roleDto.getRoleId());
            if (!ObjectUtils.isEmpty(role)) {
                roles.add(role);
            }
            return ManageResponse.returnSuccess(roles);
        }
        if (!ObjectUtils.isEmpty(roleDto.getRoleName())) {
            roles = (List<Role>) os.identity().roles().getByName(roleDto.getRoleName());
            return ManageResponse.returnSuccess(roles);
        }
        roles = (List<Role>) os.identity().roles().list();
        return ManageResponse.returnSuccess(roles);
    }

    @Override
    public ManageResponse delRole(OSClient.OSClientV3 os, RoleDto roleDto) {
        ActionResponse actionResponse = os.identity().roles().delete(roleDto.getRoleId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse grantUserRole(OSClient.OSClientV3 os, RoleDto roleDto) {
        ActionResponse actionResponse;
        if (!ObjectUtils.isEmpty(roleDto.getProjectId())) {
            actionResponse = os.identity().roles()
                    .grantProjectUserRole(roleDto.getProjectId(), roleDto.getUserId(), roleDto.getRoleId());
        } else {
            actionResponse = os.identity().roles()
                    .grantDomainUserRole(roleDto.getDomainId(), roleDto.getUserId(), roleDto.getRoleId());
        }

        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse checkUserRole(OSClient.OSClientV3 os, RoleDto roleDto) {
        ActionResponse actionResponse;
        if (!ObjectUtils.isEmpty(roleDto.getProjectId())) {
            actionResponse = os.identity().roles()
                    .checkProjectUserRole(roleDto.getProjectId(), roleDto.getUserId(), roleDto.getRoleId());
        } else {
            actionResponse = os.identity().roles()
                    .checkDomainUserRole(roleDto.getDomainId(), roleDto.getUserId(), roleDto.getRoleId());
        }

        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse revokeUserRole(OSClient.OSClientV3 os, RoleDto roleDto) {
        ActionResponse actionResponse;
        if (!ObjectUtils.isEmpty(roleDto.getProjectId())) {
            actionResponse = os.identity().roles()
                    .revokeProjectUserRole(roleDto.getProjectId(), roleDto.getUserId(), roleDto.getRoleId());
        } else {
            actionResponse = os.identity().roles()
                    .revokeDomainUserRole(roleDto.getDomainId(), roleDto.getUserId(), roleDto.getRoleId());
        }

        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createRegion(OSClient.OSClientV3 os, RegionDto regionDto) {
        try {
            Region region = os.identity().regions().create(Builders.region()
                    .id(regionDto.getRegionId())
                    .description(regionDto.getDescription())
                    .build());
            return ManageResponse.returnSuccess(region);
        } catch (Exception e) {
            log.error("创建区域失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getRegionList(OSClient.OSClientV3 os, RegionDto regionDto) {
        List<Region> regions = new ArrayList<>();
        if (ObjectUtils.isEmpty(regionDto.getRegionId())) {
            regions = (List<Region>) os.identity().regions().list();
            return ManageResponse.returnSuccess(regions);
        }
        Region region = os.identity().regions().get(regionDto.getRegionId());
        regions.add(region);
        return ManageResponse.returnSuccess(regions);
    }

    @Override
    public ManageResponse updateRegion(OSClient.OSClientV3 os, RegionDto regionDto) {
        try {
            Region region = os.identity().regions().update(
                    Builders.region().id(regionDto.getRegionId()).description(regionDto.getDescription()).build());
            return ManageResponse.returnSuccess(region);
        } catch (Exception e) {
            log.error("更新区域失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse delRegion(OSClient.OSClientV3 os, RegionDto regionDto) {
        ActionResponse actionResponse = os.identity().regions().delete(regionDto.getRegionId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createFlavor(OSClient.OSClientV3 os, Integer rid, FlavorDto flavorDto) {
        os = switchRegion(os, rid);
        if (flavorDto.getRxtxFactor() == 0f) {
            flavorDto.setRxtxFactor(DEFAULT_RXTXFACTOR);
        }
        try {
            Flavor flavor = os.compute().flavors().create(
                    flavorDto.getFlavorName(), flavorDto.getRam(), flavorDto.getVcpus(), flavorDto.getDisk(),
                    flavorDto.getEphemeral(), flavorDto.getSwap(), flavorDto.getRxtxFactor(), flavorDto.isPublic());
            return ManageResponse.returnSuccess(flavor);

        } catch (Exception e) {
            log.error("创建配置规格失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }

    }

    @Override
    public ManageResponse getFlavorList(OSClient.OSClientV3 os, Integer rid, String fid) {
        switchRegion(os, rid);
        List<Flavor> flavors = new ArrayList<>();
        if (ObjectUtils.isEmpty(fid)) {
            flavors = (List<Flavor>) os.compute().flavors().list();
            return ManageResponse.returnSuccess(flavors);
        }
        Flavor flavor = os.compute().flavors().get(fid);
        if (ObjectUtils.isEmpty(flavor)) {
            return ManageResponse.returnSuccess(flavors);
        }
        flavors.add(flavor);
        return ManageResponse.returnSuccess(flavors);
    }

    @Override
    public ManageResponse delFlavor(OSClient.OSClientV3 os, Integer rid, String fid) {
        os = switchRegion(os, rid);
        ActionResponse actionResponse = os.compute().flavors().delete(fid);
        if (actionResponse.getCode() == 202) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createFlavorExtra(OSClient.OSClientV3 os, Integer rid, FlavorDto flavorDto) {
        try {
            os = switchRegion(os, rid);
            Map<String, String> map = os.compute().flavors().createAndUpdateExtraSpecs(flavorDto.getFlavorId(), flavorDto.getExtMap());
            return ManageResponse.returnSuccess(map);
        } catch (Exception e) {
            log.error("创建配置规格失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }

    }

    @Override
    public ManageResponse getFlavorExtra(OSClient.OSClientV3 os, Integer rid, FlavorDto flavorDto) {
        os = switchRegion(os, rid);
        if (!ObjectUtils.isEmpty(flavorDto.getExtKey())){
            String s = os.compute().flavors().getSpec(flavorDto.getFlavorId(), flavorDto.getExtKey());
            return ManageResponse.returnSuccess(s);
        }
        Map<String, String> map = os.compute().flavors().listExtraSpecs(flavorDto.getFlavorId());
        return ManageResponse.returnSuccess(map);
    }

    @Override
    public ManageResponse delFlavorExtra(OSClient.OSClientV3 os, Integer rid, FlavorDto flavorDto) {
        os = switchRegion(os, rid);
        os.compute().flavors().deleteExtraSpecs(flavorDto.getFlavorId(), flavorDto.getExtKey());
        return ManageResponse.returnSuccess();
    }

    @Override
    public ManageResponse createMetadataFlavor(OSClient.OSClientV3 os, Integer rid, FlavorDto flavorDto) {
        os = switchRegion(os, rid);
        String flavorId = null;
        if (flavorDto.getRxtxFactor() == 0f) {
            flavorDto.setRxtxFactor(DEFAULT_RXTXFACTOR);
        }
        try {
            Flavor flavor = os.compute().flavors().create(
                    flavorDto.getFlavorName(), flavorDto.getRam(), flavorDto.getVcpus(), flavorDto.getDisk(),
                    flavorDto.getEphemeral(), flavorDto.getSwap(), flavorDto.getRxtxFactor(), flavorDto.isPublic());
            flavorId = flavor.getId();
            Map<String, String> map = os.compute().flavors().createAndUpdateExtraSpecs(flavorId, flavorDto.getExtMap());
            return ManageResponse.returnSuccess(flavor);
        } catch (Exception e) {
            log.error("创建配置规格失败：{}", e.getMessage());
            if (flavorId != null){
                os.compute().flavors().delete(flavorId);
            }
            if (e instanceof ClientResponseException) {
                if (e instanceof ClientResponseException) {
                    return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
                }
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getComputeImageList(OSClient.OSClientV3 os, Integer rid, String imgId) {
        os = switchRegion(os, rid);
        List<Image> images = new ArrayList<>();
        if (ObjectUtils.isEmpty(imgId)) {
            images = (List<Image>) os.compute().images().list();
            return ManageResponse.returnSuccess(images);
        }
        Image image = os.compute().images().get(imgId);
        if (ObjectUtils.isEmpty(image)) {
            return ManageResponse.returnSuccess(images);
        }
        images.add(image);
        return ManageResponse.returnSuccess(images);
    }

    @Override
    public ManageResponse createInstance(OSClient.OSClientV3 os, InstanceDto instanceDto) {
        try {
            List<Server> servers = new ArrayList<>();
            for (int i = 0; i < instanceDto.getNum(); i++) {
                ServerCreateBuilder sb = Builders.server();
                sb.name(instanceDto.getInstanceName())
                        .flavor(instanceDto.getFlavorId())
                        .image(instanceDto.getImageId())
                        .networks(instanceDto.getNetworkIds());
                if (!CollectionUtils.isEmpty(instanceDto.getSecurityGroups())) {
                    for (String securityGroup : instanceDto.getSecurityGroups()) {
                        sb.addSecurityGroup(securityGroup);
                    }
                }
                if (!CollectionUtils.isEmpty(instanceDto.getMetaData())) {
                    sb.addMetadata(instanceDto.getMetaData());
                }
                sb.addPersonality(instanceDto.getPersonalityPath(), instanceDto.getPersonalityValue());
                ServerCreate sc = sb.build();
                Server server = os.compute().servers().boot(sc);
                servers.add(server);
            }
            return ManageResponse.returnSuccess(servers);
        } catch (Exception e) {
            log.error("创建实例失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getInstanceList(OSClient.OSClientV3 os, Integer rid, InstanceDto instanceDto) {
        os = switchRegion(os, rid);
        List<Server> servers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(instanceDto.getFilteringParams())) {
            servers = (List<Server>) os.compute().servers().list(instanceDto.getFilteringParams());
            return ManageResponse.returnSuccess(servers);
        }
        if (ObjectUtils.isEmpty(instanceDto.getInstanceId())) {
            servers = (List<Server>) os.compute().servers().list(instanceDto.isDetails());
            return ManageResponse.returnSuccess(servers);
        }
        Server server = os.compute().servers().get(instanceDto.getInstanceId());
        if (ObjectUtils.isEmpty(server)) {
            return ManageResponse.returnSuccess(servers);
        }
        servers.add(server);
        return ManageResponse.returnSuccess(servers);
    }

    @Override
    public ManageResponse delInstance(OSClient.OSClientV3 os, InstanceDto instanceDto) {
        ActionResponse actionResponse = os.compute().servers().delete(instanceDto.getInstanceId());
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse actionInstance(OSClient.OSClientV3 os, Integer rid, InstanceDto instanceDto) {
        os = switchRegion(os, rid);
        ActionResponse actionResponse = os.compute().servers()
                .action(instanceDto.getInstanceId(), instanceDto.getAction());
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse rebootInstance(OSClient.OSClientV3 os, Integer rid, InstanceDto instanceDto) {
        os = switchRegion(os, rid);
        ActionResponse actionResponse = os.compute().servers()
                .reboot(instanceDto.getInstanceId(), RebootType.SOFT);
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse addVolume(OSClient.OSClientV3 os, InstanceDto instanceDto) {
        try {
            VolumeAttachment volumeAttachment = os.compute().servers()
                    .attachVolume(instanceDto.getInstanceId(), instanceDto.getVolumeId(), null);
            return ManageResponse.returnSuccess(volumeAttachment);
        } catch (Exception e) {
            log.error("给实例添加卷失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse removeVolume(OSClient.OSClientV3 os, InstanceDto instanceDto) {
        ActionResponse actionResponse = os.compute().servers()
                .detachVolume(instanceDto.getInstanceId(), instanceDto.getVolumeId());
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse updateMetadata(OSClient.OSClientV3 os, Integer rid, InstanceDto instanceDto) {
        os = switchRegion(os,rid);
        Map<String, String> map = os.compute().servers().updateMetadata(instanceDto.getInstanceId(), instanceDto.getMetaData());
        if (CollectionUtils.isEmpty(map)){
            return ManageResponse.returnFail(100, "更新元数据失败");
        }
        return ManageResponse.returnSuccess(map);
    }

    @Override
    public ManageResponse createFloatIp(OSClient.OSClientV3 os, FloatIpDto floatIpDto) {
        try {
            FloatingIP ip = os.compute().floatingIps().allocateIP(floatIpDto.getPoolName());
            return ManageResponse.returnSuccess(ip);
        } catch (Exception e) {
            log.error("创建浮动ip失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse delFloatIp(OSClient.OSClientV3 os, FloatIpDto floatIpDto) {
        ActionResponse actionResponse = os.compute().floatingIps().deallocateIP(floatIpDto.getFloatIpId());
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse addFloatIpToInstance(OSClient.OSClientV3 os, FloatIpDto floatIpDto) {
        Server server = os.compute().servers().get(floatIpDto.getInstanceId());
        ActionResponse actionResponse = os.compute().floatingIps()
                .addFloatingIP(server, floatIpDto.getFixedIp(), floatIpDto.getFloatIp());
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse removeFloatIpFromInstance(OSClient.OSClientV3 os, FloatIpDto floatIpDto) {
        Server server = os.compute().servers().get(floatIpDto.getInstanceId());
        ActionResponse actionResponse = os.compute().floatingIps()
                .removeFloatingIP(server, floatIpDto.getFloatIp());
        if (actionResponse.getCode() == 200) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse getHypervisorList(OSClient.OSClientV3 os, Integer rid, InstanceDto instanceDto) {
        os = switchRegion(os, rid);
        List<Hypervisor> hypervisors = new ArrayList<>();
        if (ObjectUtils.isEmpty(instanceDto.getHypervisorId())){
            hypervisors = (List<Hypervisor>) os.compute().hypervisors().list();
            return ManageResponse.returnSuccess(hypervisors);
        }
        Hypervisor hypervisor = os.compute().hypervisors().get(instanceDto.getHypervisorId());
        if (ObjectUtils.isEmpty(hypervisor)){
            return ManageResponse.returnSuccess(hypervisors);
        }
        hypervisors.add(hypervisor);
        return ManageResponse.returnSuccess(hypervisors);
    }

    @Override
    public ManageResponse createNetwork(OSClient.OSClientV3 os, Integer rid, NetworkDto networkDto) {
        try {
            os = switchRegion(os, rid);
            Network network = os.networking().network().create(Builders.network()
                    .name(networkDto.getNetworkName())
                    .adminStateUp(true)
                    .tenantId(networkDto.getProjectId())
                    .build());
            return ManageResponse.returnSuccess(network);
        } catch (Exception e) {
            log.error("创建网络失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getNetworkList(OSClient.OSClientV3 os, NetworkDto networkDto) {
        List<Network> networks = new ArrayList<>();
        if (ObjectUtils.isEmpty(networkDto.getNetworkId())) {
            networks = (List<Network>) os.networking().network().list();
            return ManageResponse.returnSuccess(networks);
        }
        Network network = os.networking().network().get(networkDto.getNetworkId());
        if (ObjectUtils.isEmpty(network)) {
            return ManageResponse.returnSuccess(networks);
        }
        networks.add(network);
        return ManageResponse.returnSuccess(networks);
    }

    @Override
    public ManageResponse delNetwork(OSClient.OSClientV3 os, NetworkDto networkDto) {
        ActionResponse actionResponse = os.networking().network().delete(networkDto.getNetworkId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createSubnet(OSClient.OSClientV3 os, NetworkDto networkDto) {
        try {
            SubnetBuilder sb = Builders.subnet()
                    .name(networkDto.getSubnetName())
                    .networkId(networkDto.getNetworkId())
                    .enableDHCP(true)
                    .tenantId(networkDto.getProjectId())
                    .addPool(networkDto.getIpStart(), networkDto.getIpEnd())
                    .ipVersion(IPVersionType.V4)
                    .cidr(networkDto.getCidr());
            if (!CollectionUtils.isEmpty(networkDto.getNameServers())) {
                for (String nameServer : networkDto.getNameServers()) {
                    sb.addDNSNameServer(nameServer);
                }
            }
            Subnet subnet = os.networking().subnet().create(sb.build());
            return ManageResponse.returnSuccess(subnet);
        } catch (Exception e) {
            log.error("创建子网失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getSubnetList(OSClient.OSClientV3 os, NetworkDto networkDto) {
        List<Subnet> subnets = new ArrayList<>();
        if (ObjectUtils.isEmpty(networkDto.getSubnetId())) {
            subnets = (List<Subnet>) os.networking().subnet().list();
            return ManageResponse.returnSuccess(subnets);
        }
        Subnet subnet = os.networking().subnet().get(networkDto.getSubnetId());
        if (ObjectUtils.isEmpty(subnet)) {
            return ManageResponse.returnSuccess(subnets);
        }
        subnets.add(subnet);
        return ManageResponse.returnSuccess(subnets);
    }

    @Override
    public ManageResponse delSubnet(OSClient.OSClientV3 os, NetworkDto networkDto) {
        ActionResponse actionResponse = os.networking().subnet().delete(networkDto.getSubnetId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createRouter(OSClient.OSClientV3 os, NetworkDto networkDto) {
        try {
            Router router = os.networking().router().create(Builders.router()
                    .name(networkDto.getRouterName())
                    .adminStateUp(true)
                    .externalGateway(networkDto.getNetworkId())
//                    .route(networkDto.getCidr(), "10.20.20.1")
                    .build());
            return ManageResponse.returnSuccess(router);
        } catch (Exception e) {
            log.error("创建路由失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getRouterList(OSClient.OSClientV3 os, NetworkDto networkDto) {
        List<Router> routers = new ArrayList<>();
        if (ObjectUtils.isEmpty(networkDto.getRouterId())) {
            routers = (List<Router>) os.networking().router().list();
            return ManageResponse.returnSuccess(routers);
        }
        Router router = os.networking().router().get(networkDto.getRouterId());
        if (ObjectUtils.isEmpty(router)) {
            return ManageResponse.returnSuccess(routers);
        }
        routers.add(router);
        return ManageResponse.returnSuccess(routers);
    }

    @Override
    public ManageResponse addRouterToSubnet(OSClient.OSClientV3 os, NetworkDto networkDto) {
        try {
            RouterInterface iface = os.networking().router()
                    .attachInterface(networkDto.getRouterId(), AttachInterfaceType.SUBNET, networkDto.getSubnetId());
            if (ObjectUtils.isEmpty(iface)) {
                log.error("子网绑定路由失败，可能子网或者路由不存在");
                return ManageResponse.returnFail(ResponseEnum.FAIL);
            }
            return ManageResponse.returnSuccess(iface);
        } catch (Exception e) {
            log.error("给子网添加路由失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse removeRouterFromSubnet(OSClient.OSClientV3 os, NetworkDto networkDto) {
        try {
            RouterInterface iface = os.networking().router()
                    .detachInterface(networkDto.getRouterId(), networkDto.getSubnetId(), null);
            if (ObjectUtils.isEmpty(iface)) {
                log.error("子网解绑路由失败，可能子网或者路由不存在");
                return ManageResponse.returnFail(ResponseEnum.FAIL);
            }
            return ManageResponse.returnSuccess(iface);
        } catch (Exception e) {
            log.error("给子网解绑路由失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse delRouter(OSClient.OSClientV3 os, NetworkDto networkDto) {
        ActionResponse actionResponse = os.networking().router().delete(networkDto.getRouterId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse getFloatingIpList(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        os = switchRegion(os, rid);
        List<NetFloatingIP> floatingIPS = new ArrayList<>();
        if (ObjectUtils.isEmpty(qosDto.getFid())){
            floatingIPS = (List<NetFloatingIP>) os.networking().floatingip().list();
            return ManageResponse.returnSuccess(floatingIPS);
        }
        NetFloatingIP floatingIP = os.networking().floatingip().get(qosDto.getFid());
        if (ObjectUtils.isEmpty(floatingIP)){
            return ManageResponse.returnSuccess(floatingIPS);
        }
        floatingIPS.add(floatingIP);
        return ManageResponse.returnSuccess(floatingIPS);
    }

    @Override
    public ManageResponse bindQosPolicyToFloatingIp(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        try {
            os = switchRegion(os, rid);
            NetFloatingIP floatingIP = os.networking().floatingip().bindQosPolicy(qosDto.getFid(), Builders.netFloatingIP().qosPolicyId(qosDto.getPolicyId()).build());
            return ManageResponse.returnSuccess(floatingIP);
        } catch (Exception e) {
            log.error("给浮动ip绑定策略失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse createPolicy(OSClient.OSClientV3 os, QosDto qosDto) {
        try {
            NetQosPolicy netQosPolicy = os.networking().netQosPolicy().create(Builders.netQosPolicy()
                    .name(qosDto.getPolicyName())
                    .description(qosDto.getDescription())
                    .tenantId(qosDto.getProjectId())
                    .isDefault(qosDto.getDef())
                    .shared(qosDto.getShare()).build());
            return ManageResponse.returnSuccess(netQosPolicy);
        } catch (Exception e) {
            log.error("创建网络扩展规则失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getPolicyList(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        os = switchRegion(os, rid);
        List<NetQosPolicy> netQosPolicies = new ArrayList<>();
        if (ObjectUtils.isEmpty(qosDto.getPolicyId())) {
            netQosPolicies = (List<NetQosPolicy>) os.networking().netQosPolicy().list();
            ;
            return ManageResponse.returnSuccess(netQosPolicies);
        }
        NetQosPolicy netQosPolicy = os.networking().netQosPolicy().get(qosDto.getPolicyId());
        if (ObjectUtils.isEmpty(netQosPolicy)) {
            return ManageResponse.returnSuccess(netQosPolicies);
        }
        netQosPolicies.add(netQosPolicy);
        return ManageResponse.returnSuccess(netQosPolicies);
    }

    @Override
    public ManageResponse updatePolicy(OSClient.OSClientV3 os, QosDto qosDto) {
        try {
            NetQosPolicyUpdateBuilder builder = Builders.netQosPolicyUpdate();
            if (!ObjectUtils.isEmpty(qosDto.getPolicyName())) {
                builder.name(qosDto.getPolicyName());
            }
            if (!ObjectUtils.isEmpty(qosDto.getDescription())) {
                builder.description(qosDto.getDescription());
            }
            if (!ObjectUtils.isEmpty(qosDto.getDef())) {
                builder.isDefault(qosDto.getDef());
            }
            if (!ObjectUtils.isEmpty(qosDto.getShare())) {
                builder.shared(qosDto.getShare());
            }
            NetQosPolicy netQosPolicy = os.networking().netQosPolicy().update(
                    qosDto.getPolicyId(), builder.build());
            return ManageResponse.returnSuccess(netQosPolicy);
        } catch (Exception e) {
            log.error("更新网络扩展规则失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse delPolicy(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        os = switchRegion(os, rid);
        ActionResponse actionResponse = os.networking().netQosPolicy().delete(qosDto.getPolicyId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createBandwidth(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        try {
            os = switchRegion(os, rid);
            NetQosPolicyBandwidthLimitRule netQosPolicyBandwidthLimitRule = os.networking().netQosPolicyBLRule().create(
                    qosDto.getPolicyId(),
                    Builders.netQosPolicyBandwidthLimitRule()
                            .direction(qosDto.getDirection())
                            .maxKbps(qosDto.getMaxKbps())
                            .maxBurstKbps(qosDto.getMaxBurstKbps()).build());
            return ManageResponse.returnSuccess(netQosPolicyBandwidthLimitRule);
        } catch (Exception e) {
            log.error("创建带宽规则失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getBandwidthList(OSClient.OSClientV3 os, QosDto qosDto) {
        List<NetQosPolicyBandwidthLimitRule> netQosPolicyBandwidthLimitRules = new ArrayList<>();
        if (ObjectUtils.isEmpty(qosDto.getBandwidthId())) {
            netQosPolicyBandwidthLimitRules = (List<NetQosPolicyBandwidthLimitRule>) os.networking().netQosPolicyBLRule().list(qosDto.getPolicyId());
            return ManageResponse.returnSuccess(netQosPolicyBandwidthLimitRules);
        }
        NetQosPolicyBandwidthLimitRule netQosPolicyBandwidthLimitRule = os.networking().netQosPolicyBLRule().get(qosDto.getPolicyId(), qosDto.getBandwidthId());
        if (ObjectUtils.isEmpty(netQosPolicyBandwidthLimitRule)) {
            return ManageResponse.returnSuccess(netQosPolicyBandwidthLimitRules);
        }
        netQosPolicyBandwidthLimitRules.add(netQosPolicyBandwidthLimitRule);
        return ManageResponse.returnSuccess(netQosPolicyBandwidthLimitRules);
    }

    @Override
    public ManageResponse updateBandwidth(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        try {
            os = switchRegion(os, rid);
            NetQosPolicyBandwidthLimitRuleBuilder builder = Builders.netQosPolicyBandwidthLimitRule();
            if (!ObjectUtils.isEmpty(qosDto.getDirection())) {
                builder.direction(qosDto.getDirection());
            }
            if (!ObjectUtils.isEmpty(qosDto.getMaxKbps())) {
                builder.maxKbps(qosDto.getMaxKbps());
            }
            if (!ObjectUtils.isEmpty(qosDto.getMaxBurstKbps())) {
                builder.maxBurstKbps(qosDto.getMaxBurstKbps());
            }
            NetQosPolicyBandwidthLimitRule netQosPolicyBandwidthLimitRule = os.networking().netQosPolicyBLRule().update(
                    qosDto.getPolicyId(), qosDto.getBandwidthId(), builder.build());
            return ManageResponse.returnSuccess(netQosPolicyBandwidthLimitRule);
        } catch (Exception e) {
            log.error("更新带宽规则失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            if (e instanceof NullPointerException) {
                return ManageResponse.returnFail(500, e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse delBandwidth(OSClient.OSClientV3 os, Integer rid, QosDto qosDto) {
        os = switchRegion(os, rid);
        ActionResponse actionResponse = os.networking().netQosPolicyBLRule().delete(qosDto.getPolicyId(), qosDto.getBandwidthId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createSG(OSClient.OSClientV3 os, Integer rid, SGDto sgDto) {
        try {
            os = switchRegion(os, rid);
            SecurityGroup securityGroup = os.networking().securitygroup().create(Builders.securityGroup()
                    .name(sgDto.getSgName())
                    .tenantId(sgDto.getProjectId())
                    .description(sgDto.getDescription()).build());
            return ManageResponse.returnSuccess(securityGroup);
        }catch (Exception e){
            log.error("创建安全组失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            if (e instanceof NullPointerException) {
                return ManageResponse.returnFail(500, e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getSGList(OSClient.OSClientV3 os, Integer rid, SGDto sgDto) {
        os = switchRegion(os, rid);
        List<SecurityGroup> securityGroups = new ArrayList<>();
        if (ObjectUtils.isEmpty(sgDto.getSgId())){
            securityGroups = (List<SecurityGroup>) os.networking().securitygroup().list();
            return ManageResponse.returnSuccess(securityGroups);
        }
        SecurityGroup securityGroup = os.networking().securitygroup().get(sgDto.getSgId());
        if (ObjectUtils.isEmpty(securityGroup)){
            return ManageResponse.returnSuccess(securityGroups);
        }
        securityGroups.add(securityGroup);
        return ManageResponse.returnSuccess(securityGroups);
    }

    @Override
    public ManageResponse delSG(OSClient.OSClientV3 os, Integer rid, SGDto sgDto) {
        switchRegion(os, rid);
        ActionResponse actionResponse = os.networking().securitygroup().delete(sgDto.getSgId());
        if (actionResponse.getCode() == 204) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse createSGRule(OSClient.OSClientV3 os, Integer rid, SGDto sgDto) {
        try {
            os = switchRegion(os, rid);
            SecurityGroupRule securityGroupRule = os.networking().securityrule().create(Builders.securityGroupRule()
                    .securityGroupId(sgDto.getSgId())
                    .direction(sgDto.getDirection())
                    .ethertype(sgDto.getEtherTypeEnum().name())
                    .protocol(sgDto.getProtocol())
                    .remoteIpPrefix(sgDto.getRemoteIpPrefix())
                    .portRangeMax(sgDto.getMaxPort())
                    .portRangeMin(sgDto.getMinPort()).build());
            return ManageResponse.returnSuccess(securityGroupRule);
        }catch (Exception e){
            log.error("创建安全组规则失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            if (e instanceof NullPointerException) {
                return ManageResponse.returnFail(500, e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getSGRuleList(OSClient.OSClientV3 os, Integer rid, SGDto sgDto) {
        os = switchRegion(os, rid);
        List<SecurityGroupRule> securityGroupRules = new ArrayList<>();
        if (ObjectUtils.isEmpty(sgDto.getSgRuleId())){
            securityGroupRules = (List<SecurityGroupRule>) os.networking().securityrule().list();
            return ManageResponse.returnSuccess(securityGroupRules);
        }
        SecurityGroupRule securityGroupRule = os.networking().securityrule().get(sgDto.getSgRuleId());
        if (ObjectUtils.isEmpty(securityGroupRule)){
            return ManageResponse.returnSuccess(securityGroupRules);
        }
        securityGroupRules.add(securityGroupRule);
        return ManageResponse.returnSuccess(securityGroupRules);
    }

    @Override
    public ManageResponse delSGRule(OSClient.OSClientV3 os, Integer rid, SGDto sgDto) {
        os = switchRegion(os, rid);
        os.networking().securityrule().delete(sgDto.getSgRuleId());
        return ManageResponse.returnSuccess();
    }

    @Override
    public ManageResponse createVolume(OSClient.OSClientV3 os, VolumeDto volumeDto) {
        try {
            Volume volume = os.blockStorage().volumes().create(Builders.volume()
                    .name(volumeDto.getVolumeName())
                    .description(volumeDto.getDescription())
                    .size(volumeDto.getSize())
                    .imageRef(volumeDto.getImageId())
                    .bootable(volumeDto.isBootable())
                    .build());
            return ManageResponse.returnSuccess(volume);
        } catch (Exception e) {
            log.error("创建存储卷失败：{}", e.getMessage());
            if (e instanceof ClientResponseException) {
                return ManageResponse.returnFail(((ClientResponseException) e).getStatusCode().getCode(), e.getMessage());
            }
            return ManageResponse.returnFail(ResponseEnum.FAIL);
        }
    }

    @Override
    public ManageResponse getVolumeList(OSClient.OSClientV3 os, VolumeDto volumeDto) {
        List<Volume> volumes = new ArrayList<>();
        if (ObjectUtils.isEmpty(volumeDto.getVolumeId())) {
            volumes = (List<Volume>) os.blockStorage().volumes().list();
            return ManageResponse.returnSuccess(volumes);
        }
        Volume volume = os.blockStorage().volumes().get(volumeDto.getVolumeId());
        if (ObjectUtils.isEmpty(volume)) {
            return ManageResponse.returnSuccess(volumes);
        }
        volumes.add(volume);
        return ManageResponse.returnSuccess(volumes);
    }

    @Override
    public ManageResponse delVolume(OSClient.OSClientV3 os, Integer rid, VolumeDto volumeDto) {
        os = switchRegion(os, rid);
        ActionResponse actionResponse = os.blockStorage().volumes().delete(volumeDto.getVolumeId());
        if (actionResponse.getCode() == 202) {
            return ManageResponse.returnSuccess();
        }
        return ManageResponse.returnFail(actionResponse.getCode(), actionResponse.getFault());
    }

    @Override
    public ManageResponse getVolumeBackendPoolList(OSClient.OSClientV3 os, Integer rid) {
        os = switchRegion(os, rid);
        List<? extends VolumeBackendPool> volumeBackendPools = os.blockStorage().schedulerStatsPools().poolsDetail();
        return ManageResponse.returnSuccess(volumeBackendPools);
    }

    @Override
    public ManageResponse getImageList(OSClient.OSClientV3 os, Integer rid, ImageDto imageDto) {
        os = switchRegion(os, rid);
        List<org.openstack4j.model.image.v2.Image> images = new ArrayList<>();
        if (ObjectUtils.isEmpty(imageDto.getImageId())) {
            images = (List<org.openstack4j.model.image.v2.Image>) os.imagesV2().list();
            return ManageResponse.returnSuccess(images);
        }
        org.openstack4j.model.image.v2.Image image = os.imagesV2().get(imageDto.getImageId());
        if (ObjectUtils.isEmpty(image)) {
            return ManageResponse.returnSuccess(images);
        }
        images.add(image);
        return ManageResponse.returnSuccess(images);
    }

    @Override
    public ManageResponse getMetadefsList(OSClient.OSClientV3 os, Integer rid, String namespace) {
        os = switchRegion(os, rid);
        List<Metadefs> metadefss = new ArrayList<>();
        if (ObjectUtils.isEmpty(namespace)){
            metadefss = (List<Metadefs>) os.metadefs().list();
            return ManageResponse.returnSuccess(metadefss);
        }
        Metadefs metadefs = os.metadefs().get(namespace);
        if (!ObjectUtils.isEmpty(metadefs)){
            metadefss.add(metadefs);
        }
        return ManageResponse.returnSuccess(metadefss);
    }

    /**
     * 获取openstack auth用户信息
     *
     * @param userId
     * @return
     */
    private AuthUserDto getAuthUser(Integer userId) {
        OpenstackAuth openstackAuth = openstackAuthService.getOpenStackAuthInfo(userId);
        if (ObjectUtils.isEmpty(openstackAuth)) {
            return null;
        }
        AuthUserDto authUserDto = new AuthUserDto();
        BeanCopier beanCopier = BeanCopier.create(OpenstackAuth.class, AuthUserDto.class, false);
        beanCopier.copy(openstackAuth, authUserDto, null);
        authUserDto.setPasswd(openstackAuth.getPassword());
        return authUserDto;
    }

    /**
     * 切换region
     *
     * @param os
     * @param regionId
     * @return
     */
    public OSClient.OSClientV3 switchRegion(OSClient.OSClientV3 os, Integer regionId) {
        com.lrj.openstack.domain.entity.Region region = regionService.getRegionInfo(regionId);
        OSClient.OSClientV3 osClientV3 = os.useRegion(region.getRealName());
        return osClientV3;
    }
}
