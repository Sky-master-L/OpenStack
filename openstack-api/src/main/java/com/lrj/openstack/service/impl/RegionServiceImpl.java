package com.lrj.openstack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lrj.openstack.domain.entity.Region;
import com.lrj.openstack.domain.mapper.RegionMapper;
import com.lrj.openstack.enums.RedisEnum;
import com.lrj.openstack.service.RegionService;
import com.lrj.openstack.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final int OUT_TIME = 12 * 60 * 60;

    @Override
    public List<Region> getRegions() {
        List<Region> regionList = redisUtils.get(RedisEnum.Redis_for_openstackRegion.name(), List.class);
        if (CollectionUtils.isEmpty(regionList)) {
            regionList = initRegionInfo();
            redisUtils.set(RedisEnum.Redis_for_openstackRegion.name(), regionList);
        }
        return regionList;
    }

    @Override
    public List<Region> initRegionInfo() {
        LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper();
        List<Region> regionList = regionMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(regionList)) {
            return null;
        }
        redisUtils.set(RedisEnum.Redis_for_openstackRegion.name(), regionList, OUT_TIME);
        return regionList;
    }

    @Override
    public Region getRegionInfo(Integer regionId) {
        List<Region> regionList = getRegions();
        List<Region> result;
        result = regionList.stream().filter(x -> x.getId().equals(regionId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }
}
