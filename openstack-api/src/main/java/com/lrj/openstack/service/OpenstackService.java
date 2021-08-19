package com.lrj.openstack.service;

import com.lrj.openstack.dto.*;
import com.lrj.openstack.response.ManageResponse;
import org.openstack4j.api.OSClient;

/**
 * ClassName: OpenstackService
 * Description:
 * Date: 2021/5/27 15:55
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public interface OpenstackService {
    ManageResponse getAuthToke(ReqUserDto reqUserDto);

    ManageResponse createProject(OSClient.OSClientV3 osClient, ProjectDto projectDto);

    ManageResponse getProjectList(OSClient.OSClientV3 osClient, ProjectDto projectDto);

    ManageResponse updateProject(OSClient.OSClientV3 osClient, ProjectDto projectDto);

    ManageResponse delProject(OSClient.OSClientV3 osClient, ProjectDto projectDto);

    ManageResponse createUser(OSClient.OSClientV3 osClient, UserDto userDto);

    ManageResponse getUserList(OSClient.OSClientV3 osClient, UserDto userDto);

    ManageResponse delUser(OSClient.OSClientV3 osClient, UserDto userDto);

    ManageResponse createRole(OSClient.OSClientV3 osClient, RoleDto roleDto);

    ManageResponse getRoleList(OSClient.OSClientV3 osClient, RoleDto roleDto);

    ManageResponse delRole(OSClient.OSClientV3 osClient, RoleDto roleDto);

    ManageResponse grantUserRole(OSClient.OSClientV3 osClient, RoleDto roleDto);

    ManageResponse checkUserRole(OSClient.OSClientV3 osClient, RoleDto roleDto);

    ManageResponse revokeUserRole(OSClient.OSClientV3 osClient, RoleDto roleDto);

    ManageResponse createRegion(OSClient.OSClientV3 osClient, RegionDto regionDto);

    ManageResponse getRegionList(OSClient.OSClientV3 osClient, RegionDto regionDto);

    ManageResponse updateRegion(OSClient.OSClientV3 osClient, RegionDto regionDto);

    ManageResponse delRegion(OSClient.OSClientV3 osClient, RegionDto regionDto);

//    ManageResponse switchRegion(OSClient.OSClientV3 osClient, RegionDto regionDto);

    ManageResponse createFlavor(OSClient.OSClientV3 osClient, Integer rid, FlavorDto flavorDto);

    ManageResponse getFlavorList(OSClient.OSClientV3 osClient, Integer rid, String fid);

    ManageResponse delFlavor(OSClient.OSClientV3 osClient, Integer rid, String fid);

    ManageResponse createFlavorExtra(OSClient.OSClientV3 osClient, Integer rid, FlavorDto flavorDto);

    ManageResponse getFlavorExtra(OSClient.OSClientV3 osClient, Integer rid, FlavorDto flavorDto);

    ManageResponse delFlavorExtra(OSClient.OSClientV3 osClient, Integer rid, FlavorDto flavorDto);

    ManageResponse createMetadataFlavor(OSClient.OSClientV3 osClient, Integer rid, FlavorDto flavorDto);

    ManageResponse getComputeImageList(OSClient.OSClientV3 osClient, Integer rid, String imgId);

    ManageResponse createInstance(OSClient.OSClientV3 osClient, InstanceDto instanceDto);

    ManageResponse getInstanceList(OSClient.OSClientV3 osClient, Integer rid, InstanceDto instanceDto);

    ManageResponse delInstance(OSClient.OSClientV3 osClient, InstanceDto instanceDto);

    ManageResponse actionInstance(OSClient.OSClientV3 osClient, Integer rid, InstanceDto instanceDto);

    ManageResponse rebootInstance(OSClient.OSClientV3 osClient, Integer rid, InstanceDto instanceDto);

    ManageResponse addVolume(OSClient.OSClientV3 osClient, InstanceDto instanceDto);

    ManageResponse removeVolume(OSClient.OSClientV3 osClient, InstanceDto instanceDto);

    ManageResponse updateMetadata(OSClient.OSClientV3 osClient, Integer rid, InstanceDto instanceDto);

    ManageResponse createFloatIp(OSClient.OSClientV3 osClient, FloatIpDto floatIpDto);

    ManageResponse delFloatIp(OSClient.OSClientV3 osClient, FloatIpDto floatIpDto);

    ManageResponse addFloatIpToInstance(OSClient.OSClientV3 osClient, FloatIpDto floatIpDto);

    ManageResponse removeFloatIpFromInstance(OSClient.OSClientV3 osClient, FloatIpDto floatIpDto);

    ManageResponse getHypervisorList(OSClient.OSClientV3 osClient, Integer rid, InstanceDto instanceDto);

    ManageResponse createNetwork(OSClient.OSClientV3 osClient, Integer rid, NetworkDto networkDto);

    ManageResponse getNetworkList(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse delNetwork(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse createSubnet(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse getSubnetList(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse delSubnet(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse createRouter(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse getRouterList(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse addRouterToSubnet(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse removeRouterFromSubnet(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse delRouter(OSClient.OSClientV3 osClient, NetworkDto networkDto);

    ManageResponse getFloatingIpList(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse bindQosPolicyToFloatingIp(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse createPolicy(OSClient.OSClientV3 osClient, QosDto qosDto);

    ManageResponse getPolicyList(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse updatePolicy(OSClient.OSClientV3 osClient, QosDto qosDto);

    ManageResponse delPolicy(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse createBandwidth(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse getBandwidthList(OSClient.OSClientV3 osClient, QosDto qosDto);

    ManageResponse updateBandwidth(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse delBandwidth(OSClient.OSClientV3 osClient, Integer rid, QosDto qosDto);

    ManageResponse createSG(OSClient.OSClientV3 osClient, Integer rid, SGDto sgDto);

    ManageResponse getSGList(OSClient.OSClientV3 osClient, Integer rid, SGDto sgDto);

    ManageResponse delSG(OSClient.OSClientV3 osClient, Integer rid, SGDto sgDto);

    ManageResponse createSGRule(OSClient.OSClientV3 osClient, Integer rid, SGDto sgDto);

    ManageResponse getSGRuleList(OSClient.OSClientV3 osClient, Integer rid, SGDto sgDto);

    ManageResponse delSGRule(OSClient.OSClientV3 osClient, Integer rid, SGDto sgDto);

    ManageResponse createVolume(OSClient.OSClientV3 osClient, VolumeDto volumeDto);

    ManageResponse getVolumeList(OSClient.OSClientV3 osClient, VolumeDto volumeDto);

    ManageResponse delVolume(OSClient.OSClientV3 osClient, Integer rid, VolumeDto volumeDto);

    ManageResponse getVolumeBackendPoolList(OSClient.OSClientV3 osClient, Integer rid);

    ManageResponse getImageList(OSClient.OSClientV3 osClient, Integer rid, ImageDto imageDto);

    ManageResponse getMetadefsList(OSClient.OSClientV3 osClient, Integer rid, String namespace);
}
