package org.openstack4j.openstack.networking.internal.ext;

import java.util.List;

import org.openstack4j.api.networking.ext.NetQosPolicyBLRuleService;
import org.openstack4j.core.transport.ExecutionOptions;
import org.openstack4j.core.transport.propagation.PropagateOnStatus;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.ext.NetQosPolicyBandwidthLimitRule;
import org.openstack4j.openstack.networking.domain.ext.NeutronNetQosPolicyBandwidthLimitRule;
import org.openstack4j.openstack.networking.internal.BaseNetworkingServices;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ClassName: NetQosPolicyBLRuleServiceImpl
 * Description: Networking (Neutron) Qos Policy Bandwidth Limit Rule Extension API
 * Date: 2021/6/7 17:17
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public class NetQosPolicyBLRuleServiceImpl extends BaseNetworkingServices implements NetQosPolicyBLRuleService {
    @Override
    public List<? extends NetQosPolicyBandwidthLimitRule> list(String policyId) {
        checkNotNull(policyId, "qos policyId must not be null");
        return get(NeutronNetQosPolicyBandwidthLimitRule.NeutronNetQosPolicyBLRules.class, uri("/qos/policies/%s/bandwidth_limit_rules", policyId)).execute().getList();
    }

    @Override
    public NetQosPolicyBandwidthLimitRule get(String policyId, String ruleId) {
        checkNotNull(policyId, "qos policyId must not be null");
        checkNotNull(ruleId, "qos ruleId must not be null");
        return get(NeutronNetQosPolicyBandwidthLimitRule.class, uri("/qos/policies/%s/bandwidth_limit_rules/%s", policyId, ruleId)).execute();
    }

    @Override
    public NetQosPolicyBandwidthLimitRule update(String policyId, String ruleId, NetQosPolicyBandwidthLimitRule bandwidthLimitRule) {
        checkNotNull(policyId, "qos policyId must not be null");
        checkNotNull(ruleId, "netQosPolicy rule id must not be null");
        checkNotNull(bandwidthLimitRule);
        return put(NeutronNetQosPolicyBandwidthLimitRule.class, uri("/qos/policies/%s/bandwidth_limit_rules/%s",
                policyId, ruleId)).entity(bandwidthLimitRule).execute(ExecutionOptions.create(PropagateOnStatus.on(404)));
    }

    @Override
    public NetQosPolicyBandwidthLimitRule create(String policyId, NetQosPolicyBandwidthLimitRule bandwidthLimitRule) {
        checkNotNull(policyId, "qos policyId must not be null");
        checkNotNull(bandwidthLimitRule, "netQosPolicy ruleId must not be null");
        return post(NeutronNetQosPolicyBandwidthLimitRule.class, uri("/qos/policies/%s/bandwidth_limit_rules", policyId)).entity(bandwidthLimitRule).execute(ExecutionOptions.create(PropagateOnStatus.on(404)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String policyId, String ruleId) {
        checkNotNull(policyId, "qos policyId must not be null");
        checkNotNull(ruleId, "netQosPolicy ruleId must not be null");
        return deleteWithResponse(uri("/qos/policies/%s/bandwidth_limit_rules/%s", policyId, ruleId)).execute();
    }

}
