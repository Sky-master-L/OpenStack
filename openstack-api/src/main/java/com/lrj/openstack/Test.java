package com.lrj.openstack;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;

/**
 * ClassName: Test <br/>
 * Description: <br/>
 *
 * @author luorenjie<br />
 * @version 1.0
 * @date: 2021/5/29 11:07<br/>
 * @since JDK 1.8
 */
public class Test {
    public static void main(String[] args) {
        OSClient.OSClientV3 osClientV3 = OSFactory.builderV3()
                .endpoint("http://10.121.80.55:5000/v3/")
                .credentials("93a1794826d24bf4bf167757e1470bdf", "QJcloud#123")
                .scopeToProject(Identifier.byName("admin"), Identifier.byName("default"))
                .authenticate();
        System.out.printf(osClientV3.getToken().toString());
    }
}
