package com.lrj.openstack.service;

import com.lrj.openstack.domain.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
public interface RegionService extends IService<Region> {
    List<Region> getRegions();

    List<Region> initRegionInfo();

    Region getRegionInfo(Integer rid);
}
