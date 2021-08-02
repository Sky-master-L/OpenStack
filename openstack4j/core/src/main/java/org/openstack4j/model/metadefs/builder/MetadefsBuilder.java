package org.openstack4j.model.metadefs.builder;

import org.openstack4j.common.Buildable;
import org.openstack4j.model.image.v2.Image;
import org.openstack4j.model.metadefs.Metadefs;

/**
 * ClassName: MetadefsBuilder
 * Description: Builder which creates a v2 Metadefs
 * Date: 2021/6/23 16:56
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public interface MetadefsBuilder extends Buildable.Builder<MetadefsBuilder, Metadefs> {

    /**
     * @see Metadefs#getNamespace()
     */
    MetadefsBuilder namespace(String namespace);

    /**
     * @see Metadefs#getDisplayName()()
     */
    MetadefsBuilder displayName(String displayName);

    /**
     * @see Metadefs#getDescription()
     */
    MetadefsBuilder description(String description);

    /**
     * @see Metadefs#getVisibility()
     */
    MetadefsBuilder visibility(Image.ImageVisibility visibility);

    /**
     * @see Metadefs#isProtect()
     */
    MetadefsBuilder protect(boolean protect);
}
