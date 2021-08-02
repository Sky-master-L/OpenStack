package org.openstack4j.openstack.metadefs.internal;

import org.openstack4j.api.metadefs.MetadefsService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.metadefs.Metadefs;
import org.openstack4j.openstack.image.v2.internal.BaseImageServices;
import org.openstack4j.openstack.metadefs.domain.GlanceMetadefs;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ClassName: MetadefsServiceImpl
 * Description:
 * Date: 2021/6/23 16:26
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public class MetadefsServiceImpl extends BaseImageServices implements MetadefsService {
    @Override
    public Metadefs create(Metadefs metadefs) {
        checkNotNull(metadefs, "metadefs must not be null");
        return post(GlanceMetadefs.class, uri("/metadefs/namespaces")).entity(metadefs).execute();
    }

    @Override
    public Metadefs update(Metadefs metadefs) {
        checkNotNull(metadefs.getNamespace(), "namespace name must not be null");
        return put(GlanceMetadefs.class, uri("/metadefs/namespaces/%s", metadefs.getNamespace())).entity(metadefs).execute();
    }

    @Override
    public List<? extends Metadefs> list() {
        return get(GlanceMetadefs.GlanceMetadefss.class, uri("/metadefs/namespaces")).execute().getList();
    }

    @Override
    public Metadefs get(String namespaceName) {
        checkNotNull(namespaceName, "namespace name must not be null");
        return get(GlanceMetadefs.class, uri("/metadefs/namespaces/%s", namespaceName)).execute();
    }

    @Override
    public ActionResponse delete(String namespaceName) {
        checkNotNull(namespaceName, "namespace name must not be null");
        return deleteWithResponse(uri("/metadefs/namespaces/%s", namespaceName)).execute();
    }
}
