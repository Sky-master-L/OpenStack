package org.openstack4j.api.networking.ext;

import org.openstack4j.common.RestService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.ext.NetQosPolicyBandwidthLimitRule;

import java.util.List;

/**
 * ClassName: NetQosPolicyBLRuleService
 * Description: Networking (Neutron) Qos Policy Bandwidth Limit Rule Extension API
 * Date: 2021/6/7 16:23
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public interface NetQosPolicyBLRuleService extends RestService {
    /**
     * Lists qos policy bandwidth-limit-rules for tenants
     *
     * @param policyId policy id
     * @return the list of qos policy bandwidth limit rules
     */
    List<? extends NetQosPolicyBandwidthLimitRule> list(String policyId);

    /**
     * Fetches the network qos policy bandwidth-limit-rule for the specified tenant
     *
     * @param policyId policy id
     * @return NetQosPolicyBandwidthLimitRule
     */
    NetQosPolicyBandwidthLimitRule get(String policyId, String ruleId);

    /**
     * Updates the network qos policy bandwidth limit rule for the current tenant
     *
     * @param policyId policy id
     * @param ruleId bandwidth id
     * @param bandwidthLimitRule the net qos policy bandwidth limit rule to update
     * @return NetQosPolicyBandwidthLimitRule
     */
    NetQosPolicyBandwidthLimitRule update(String policyId, String ruleId, NetQosPolicyBandwidthLimitRule bandwidthLimitRule);

    /**
     * Create the current network qos policy bandwidth limit rule for the current tenant back to defaults
     *
     * @param policyId policy id
     * @return NetQosPolicyBandwidthLimitRule the response object
     */
    NetQosPolicyBandwidthLimitRule create(String policyId, NetQosPolicyBandwidthLimitRule bandwidthLimitRule);

    /**
     * Delete the current network qos policy bandwidth limit rule for the current tenant back to defaults
     *
     * @param policyId policy id
     * @param ruleId the net qos policy bandwidth limit rule uuid
     * @return the action response
     */
    ActionResponse delete(String policyId, String ruleId);
}
