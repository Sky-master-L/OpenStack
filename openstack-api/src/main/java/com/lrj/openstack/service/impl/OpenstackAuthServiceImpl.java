package com.lrj.openstack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lrj.openstack.domain.entity.OpenstackAuth;
import com.lrj.openstack.domain.mapper.OpenstackAuthMapper;
import com.lrj.openstack.enums.RedisEnum;
import com.lrj.openstack.service.OpenstackAuthService;
import com.lrj.openstack.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * openstack keystone服务验证信息表 服务实现类
 * </p>
 *
 * @author lrj
 * @since 2021-08-18
 */
@Service
@Slf4j
public class OpenstackAuthServiceImpl extends ServiceImpl<OpenstackAuthMapper, OpenstackAuth> implements OpenstackAuthService {
    @Autowired
    private OpenstackAuthMapper openstackAuthMapper;
    @Resource
    private RedisUtils redisUtils;

    private static final int OUT_TIME = 12 * 60 * 60;

    @Override
    public void initAuthInfos() {
        List<OpenstackAuth> openstackAuthList = openstackAuthMapper.selectList(new QueryWrapper());
        if (!CollectionUtils.isEmpty(openstackAuthList)) {
            for (OpenstackAuth openstackAuth : openstackAuthList) {
                String redisKey = RedisEnum.Redis_for_openstackAuth.name() + openstackAuth.getCustomerId();
                redisUtils.set(redisKey, openstackAuth, OUT_TIME);
            }
        }
    }

    @Override
    public OpenstackAuth getOpenStackAuthInfo(Integer userId) {
        String redisKey = RedisEnum.Redis_for_openstackAuth.name() + userId;
        OpenstackAuth openstackAuth = redisUtils.get(redisKey, OpenstackAuth.class);
        if (ObjectUtils.isEmpty(openstackAuth)){
            LambdaQueryWrapper<OpenstackAuth> oqw = new LambdaQueryWrapper<>();
            oqw.eq(OpenstackAuth::getCustomerId, userId);
            openstackAuth = openstackAuthMapper.selectOne(oqw);
            if (!ObjectUtils.isEmpty(openstackAuth)){
                //更新redis
                redisUtils.set(redisKey, openstackAuth, OUT_TIME);
            }
        }
        return openstackAuth;
    }

    @Override
    public boolean create(OpenstackAuth openstackAuth) {
        QueryWrapper<OpenstackAuth> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(OpenstackAuth::getCustomerId, openstackAuth.getCustomerId());
        int num = openstackAuthMapper.selectCount(queryWrapper);
        if (num > 0) {
            log.error("用户：{}认证信息已存在", openstackAuth.getCustomerId());
            return false;
        }
        openstackAuthMapper.insert(openstackAuth);
        //更新redis
        String redisKey = RedisEnum.Redis_for_openstackAuth.name() + + openstackAuth.getCustomerId();
        redisUtils.set(redisKey, openstackAuth, OUT_TIME);
        return true;
    }

    @Override
    public void update(OpenstackAuth openstackAuth) {
        openstackAuthMapper.updateById(openstackAuth);
        //更新redis
        String redisKey = RedisEnum.Redis_for_openstackAuth.name() + openstackAuth.getCustomerId();
        OpenstackAuth openstackAuth1 = redisUtils.get(redisKey, OpenstackAuth.class);
        if (ObjectUtils.isEmpty(openstackAuth1)){
            redisUtils.set(redisKey, openstackAuth, OUT_TIME);
            return;
        }
        Long time = redisUtils.getKeyTime(redisKey);
        redisUtils.set(redisKey, openstackAuth, time);
    }

}
