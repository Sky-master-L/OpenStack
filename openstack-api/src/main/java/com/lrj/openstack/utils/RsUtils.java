package com.lrj.openstack.utils;

import com.lrj.openstack.domain.entity.RouterOs;
import com.lrj.openstack.exception.ManageException;
import com.lrj.openstack.vo.NatVo;
import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.net.SocketFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: RsUtils
 * Description: routerOS映射工具类
 * Date: 2021/8/19 14:22
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Slf4j
public class RsUtils {
    //    private static String MappingCMD = "/ip/firewall/nat/add action=dst-nat chain=dstnat comment=%s dst-address=0.0.0.0/0 dst-port=%s protocol=%s to-addresses=%s to-ports=%s";
    private static String MappingCMD = "/ip/firewall/nat/add action=dst-nat chain=dstnat comment=%s dst-address=%s dst-port=%s protocol=%s to-addresses=%s to-ports=%s";
    private static String MappingListCMD = "/ip/firewall/nat/print";
    private static String DisMappingCMD = "/ip/firewall/nat/remove .id=%s";
    private static String XsCMD = "/queue/simple/add max-limit=%sM/%sM name=%s target=%s/32";
    private static String DisXsCMD = "/queue/simple/remove .id=%s";
    private static String XsListCMD = "/queue/simple/print";

    /**
     * 为目标ip分配端口号
     *
     * @param routerOs
     * @param toIp
     * @param toPort
     * @param portType
     */
    public static void attachPort(RouterOs routerOs, Integer destPort, String toIp, Integer toPort, String portType) {
        synchronized (routerOs.getIp().intern()) {
            log.info("开始将端口映射到目标ip，toPort：{}==>toIp：{}", toPort, toIp);
            ApiConnection apiConnection = null;
            try {
                apiConnection = getConnection(routerOs.getIp(), routerOs.getPort());
                apiConnection.login(routerOs.getUserName(), routerOs.getPasword());
                //查重，如果端口已映射则返回成功;如果端口被其他toIp占用则报错。
                List<Map<String, String>> result = apiConnection.execute(MappingListCMD);
                List<NatVo> nats = convert(result);
                if (!exist(nats, destPort.toString(), toIp, toPort.toString(), portType)) {
                    apiConnection.execute(String.format(MappingCMD, "openstack_" + System.currentTimeMillis(), routerOs.getIp(), destPort, portType, toIp, toPort));
                }
                log.info("端口映射成功[{}]--->[{}]", destPort, toIp);
            } catch (MikrotikApiException e) {
                throw new ManageException(e);
            } finally {
                if (apiConnection != null) {
                    try {
                        apiConnection.close();
                    } catch (ApiConnectionException e) {
                        log.error("router api 关闭连接失败", e);
                    }
                }
            }
        }
    }

    public static void detachPort(RouterOs routerOs, Integer destPort, String toIp, Integer toPort) {
        ApiConnection apiConnection = null;
        try {
            apiConnection = getConnection(routerOs.getIp(), routerOs.getPort());
            apiConnection.login(routerOs.getUserName(), routerOs.getPasword());
            List<Map<String, String>> result = apiConnection.execute(MappingListCMD);
            List<NatVo> natVos = convert(result);
            List<String> ids = natVos.stream().filter(x -> !StringUtils.isEmpty(x.getId()) && !StringUtils.isEmpty(x.getDstPort()))
                    .filter(x -> x.getDstPort().equals(destPort) && x.getToAddresses().equals(toIp))
                    .map(NatVo::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(ids)) {
                //未解绑
                for (String id : ids) {
                    apiConnection.execute(String.format(DisMappingCMD, id));
                    log.info("端口解绑成功id:[{}]===[{}]--->[{}]", id, destPort, toIp);
                }
            } else {
                //已解绑
                log.info("端口解绑成功[{}]--->[{}],未找到映射记录", destPort, toIp);
            }
        } catch (MikrotikApiException e) {
            log.error("端口解绑失败:" + toIp, e);
            throw new ManageException(e);
        } finally {
            if (apiConnection != null) {
                try {
                    apiConnection.close();
                } catch (ApiConnectionException e) {
                    log.error("router api 关闭连接失败", e);
                }
            }
        }
    }

    private static ApiConnection getConnection(String ip, Integer port) throws MikrotikApiException {
        if (port == null) {
            return ApiConnection.connect(ip);
        } else {
            return ApiConnection.connect(SocketFactory.getDefault(), ip, port, ApiConnection.DEFAULT_CONNECTION_TIMEOUT);
        }
    }

    private static List<NatVo> convert(List<Map<String, String>> result) {
        List<NatVo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Map<String, String> map : result) {
                NatVo natVo = new NatVo();
                natVo.setId(map.get(".id"));
                natVo.setDstAddress(map.get("dst-address"));
                natVo.setDstPort(map.get("dst-port"));
                natVo.setToAddresses(map.get("to-addresses"));
                natVo.setToPorts(map.get("to-ports"));
                natVo.setId(map.get("comment"));
                natVo.setProtocol(map.get("protocol"));
                list.add(natVo);
            }
        }
        return list;
    }

    private static boolean exist(List<NatVo> natVos, String destPort, String toIp, String toPort, String portType) {

        if (CollectionUtils.isEmpty(natVos)) {
            return false;
        }
        //判断此端口否已有映射
        //有且toIp=当前要映射的toIp则返回true；有且如果被其他toIp占用就抛出异常
        List<NatVo> filterList = natVos.stream().filter(x -> !StringUtils.isEmpty(x.getDstPort()) && !StringUtils.isEmpty(x.getId()))
                .filter(x -> x.getDstPort().equals(destPort)).collect(Collectors.toList());
        log.info("[{}]端口映射到-->[{}]，此端口已被{}映射", destPort, toIp, filterList.stream().map(NatVo::getToAddresses).toArray());
        if (!CollectionUtils.isEmpty(filterList)) {
            if (filterList.size() == 1) {
                NatVo natVo = filterList.get(0);
                if (natVo.getToAddresses().equals(toIp) && natVo.getProtocol().equals(portType) && natVo.getToPorts().equals(toPort)) {
                    return true;
                } else {
                    throw new ManageException(String.format("端口[%s],已被[%s]映射,protocol:[%s],桌面端口protocol:[%s],桌面端口：[%s]", destPort, natVo.getToAddresses(), natVo.getProtocol(), portType, toPort));
                }
            } else {
                //mapping端口，被多个映射
                throw new ManageException(String.format("端口[%s],存在[%s]个映射", destPort, filterList.size()));
            }
        } else {
            return false;
        }
    }
}
