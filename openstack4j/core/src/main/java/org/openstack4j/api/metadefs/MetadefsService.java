package org.openstack4j.api.metadefs;

import java.util.List;

import org.openstack4j.common.RestService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.metadefs.Metadefs;

/**
 * ClassName: MetadefsService
 * Description: The Metadata Definitions Service
 * Date: 2021/6/23 15:13
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public interface MetadefsService  extends RestService {
    /**
     * create a metadefs
     */
    Metadefs create(Metadefs metadefs);

    /**
     * update a metadefs
     */
    Metadefs update(Metadefs metadefs);

    /**
     * Lists public metadefs by the default page size defined by openstack
     */
    List<? extends Metadefs> list();

    /**
     * Gets an metadefs by namespaceName
     */
    Metadefs get(String namespaceName);

    /**
     * delete a metadefs by namespaceName
     */
    ActionResponse delete(String namespaceName);

}
