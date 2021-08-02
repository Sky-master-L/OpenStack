package org.openstack4j.openstack.metadefs.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.openstack4j.model.image.v2.Image;
import org.openstack4j.model.metadefs.Metadefs;
import org.openstack4j.model.metadefs.builder.MetadefsBuilder;
import org.openstack4j.openstack.common.ListResult;
import org.openstack4j.openstack.networking.domain.ext.NeutronNetQosPolicy;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: GlanceMetadefs
 * Description: A glance v2.0-2.3 metadefs model implementation
 * Date: 2021/6/23 16:36
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlanceMetadefs implements Metadefs {
    @JsonProperty("created_at")
    private Date createdAt;

    private String description;

    @JsonProperty("display_name")
    private String displayName;

    private String namespace;

    @JsonProperty("resource_type_associations")
    private List<Map<String, Object>> resourceTypeAssociations;

    private Map<String, Object> properties;

    private String owner;

    @JsonProperty("protected")
    private boolean protect;

    private String schema;

    private String self;

    @JsonProperty("updated_at")
    private Date updatedAt;

    private Image.ImageVisibility visibility;

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<Map<String, Object>> getResourceTypeAssociations() {
        return resourceTypeAssociations;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public String getOwner() {
        return owner;
    }

    public String getSchema() {
        return schema;
    }

    public String getSelf() {
        return self;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public Image.ImageVisibility getVisibility() {
        return visibility;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isProtect() {
        return protect;
    }

    public static MetadefsBuilder builder() {
        return new MetadefsConcreteBuilder();
    }

    @Override
    public MetadefsBuilder toBuilder() {
        return new GlanceMetadefs.MetadefsConcreteBuilder(this);
    }


    @Override public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("createdAt", createdAt)
                .add("description", description)
                .add("displayName", displayName)
                .add("namespace", namespace)
                .add("resourceTypeAssociations", resourceTypeAssociations)
                .add("properties", properties)
                .add("owner", owner)
                .add("protect", protect)
                .add("schema", schema)
                .add("self", self)
                .add("updatedAt", updatedAt)
                .add("visibility", visibility)
                .toString();
    }

    public static class MetadefsConcreteBuilder implements MetadefsBuilder {
        private GlanceMetadefs model;
        public MetadefsConcreteBuilder() {
            model = new GlanceMetadefs();
        }
        public MetadefsConcreteBuilder(GlanceMetadefs model) {
            this.model = model;
        }

        @Override
        public Metadefs build() {
            return model;
        }

        @Override
        public MetadefsBuilder from(Metadefs in) {
            model = (GlanceMetadefs) in;
            return this;
        }

        @Override
        public MetadefsBuilder namespace(String namespace) {
            model.namespace = namespace;
            return this;
        }

        @Override
        public MetadefsBuilder displayName(String displayName) {
            model.displayName = displayName;
            return this;
        }

        @Override
        public MetadefsBuilder description(String description) {
            model.description = description;
            return this;
        }

        @Override
        public MetadefsBuilder visibility(Image.ImageVisibility visibility) {
            model.visibility = visibility;
            return this;
        }

        @Override
        public MetadefsBuilder protect(boolean protect) {
            model.protect = protect;
            return this;
        }
    }

    public static class GlanceMetadefss extends ListResult<GlanceMetadefs> {

        private static final long serialVersionUID = 1L;

        @JsonProperty("namespaces")
        private List<GlanceMetadefs> metadefss;

        @Override
        protected List<GlanceMetadefs> value() {
            return metadefss;
        }
    }
}
