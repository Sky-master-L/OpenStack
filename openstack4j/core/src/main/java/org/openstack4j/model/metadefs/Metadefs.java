package org.openstack4j.model.metadefs;

import org.openstack4j.common.Buildable;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.image.v2.Image;
import org.openstack4j.model.metadefs.builder.MetadefsBuilder;

/**
 * ClassName: Metadefs
 * Description: A Glance v2.0-2.3 Image
 * Date: 2021/6/23 15:19
 *
 * @author luorenjie
 * @version 1.0
 * @see https://docs.openstack.org/api-ref/image/v2/metadefs-index.html?expanded=create-namespace-detail,list-namespaces-detail
 * @since JDK 1.8
 */
public interface Metadefs extends ModelEntity, Buildable<MetadefsBuilder> {
    String getNamespace();

    Image.ImageVisibility getVisibility();

    String getDisplayName();

    String getDescription();

    boolean isProtect();
}
