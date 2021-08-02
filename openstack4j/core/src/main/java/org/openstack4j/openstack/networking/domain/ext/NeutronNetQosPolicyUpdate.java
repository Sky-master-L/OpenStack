package org.openstack4j.openstack.networking.domain.ext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.model.network.ext.NetQosPolicyUpdate;
import org.openstack4j.model.network.ext.builder.NetQosPolicyUpdateBuilder;

/**
 * ClassName: NeutronNetQosPolicyUpdate
 * Description: Network Updates a QoS policy
 * Date: 2021/6/9 10:31
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@JsonRootName("policy")
public class NeutronNetQosPolicyUpdate implements NetQosPolicyUpdate {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String name;
    @JsonProperty("shared")
    private Boolean shared;
    @JsonProperty("is_default")
    private Boolean isDefault;
    @JsonProperty
    private String description;

    public static NetQosPolicyUpdateBuilder builder() {
        return new NetQosPolicyUpdateConcreteBuilder();
    }

    @Override
    public NetQosPolicyUpdateBuilder toBuilder() {
        return new NetQosPolicyUpdateConcreteBuilder(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isShared() {
        return shared == null ? false: shared;
    }

    @JsonIgnore
    @Override
    public boolean isDefault() {
        return isDefault == null ? false: isDefault;
    }

    private static class NetQosPolicyUpdateConcreteBuilder implements NetQosPolicyUpdateBuilder {

        private NeutronNetQosPolicyUpdate model;

        public NetQosPolicyUpdateConcreteBuilder(NeutronNetQosPolicyUpdate model) {
            this.model = model;
        }

        public NetQosPolicyUpdateConcreteBuilder() {
            this.model = new NeutronNetQosPolicyUpdate();
        }

        @Override
        public NetQosPolicyUpdate build() {
            return model;
        }

        @Override
        public NetQosPolicyUpdateBuilder from(NetQosPolicyUpdate in) {
            model = (NeutronNetQosPolicyUpdate) in;
            return this;
        }

        @Override
        public NetQosPolicyUpdateBuilder description(String description) {
            model.description = description;
            return this;
        }

        @Override
        public NetQosPolicyUpdateBuilder shared(boolean shared) {
            model.shared = shared;
            return this;
        }

        @Override
        public NetQosPolicyUpdateBuilder isDefault(boolean isDefault) {
            model.isDefault = isDefault;
            return this;
        }

        @Override
        public NetQosPolicyUpdateBuilder name(String name) {
            model.name = name;
            return this;
        }
    }
}
