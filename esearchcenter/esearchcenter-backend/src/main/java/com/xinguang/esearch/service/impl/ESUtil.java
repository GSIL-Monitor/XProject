/**
 * 
 */
package com.xinguang.esearch.service.impl;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.xinguang.esearch.algorithm.chain.ChainsPropertise;
import com.xinguang.esearch.algorithm.chain.CommonChain;
import com.xinguang.esearch.algorithm.chain.PropertiesAutoLoad;
import com.xinguang.esearch.constant.CommonConstant;

/**
 * 
 * ClassName: ESUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午3:30:00 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */

public class ESUtil {
    /**
     * LOGGER:logger对象.
     * 
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ESUtil.class);

    /**
     * esClusterName:es集群名称.
     * 
     * @since JDK 1.7
     */
    @Value("${elasticsearch.clustername}")
    private String clustername;

    /**
     * ESTCPPORT:esTcpPort.
     * 
     * @since JDK 1.7
     */
    @Value("${elasticsearch.estcpport}")
    private Integer estcpport;

    /**
     * ESDISCOVERYHOSTS:esDiscoveyHosts.
     * 
     * @since JDK 1.7
     */
    @Value("${elasticsearch.esdiscoveryhosts}")
    private String esdiscoveryhosts;

    /**
     * client:TODO(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.7
     */
    private static Client CLIENT = null;

    /**
     * cLIENT.
     *
     * @return the cLIENT
     * @since JDK 1.7
     */
    public static Client getCLIENT() {
        return CLIENT;
    }

    /**
     * cLIENT.
     *
     * @param cLIENT
     *            the cLIENT to set
     * @since JDK 1.7
     */
    public static void setCLIENT(Client cLIENT) {
        CLIENT = cLIENT;
    }

    /**
     * ES 客户端类型：node--对应es node客户端；tcpclient--对应ES transport client。 详细信息参考官网 {@link https
     * ://www.elastic.co/guide/en/elasticsearch/client/java-api/2.1/client.html}
     * 
     * @author hzlengxing
     */
    protected enum ClientType {
        /**
         * Node:TODO(用一句话描述这个变量表示什么).
         * 
         * @since JDK 1.7
         */
        Node("node"),

        /**
         * TcpClient:TODO(用一句话描述这个变量表示什么).
         * 
         * @since JDK 1.7
         */
        TcpClient("tcpclient");

        /**
         * type:TODO(用一句话描述这个变量表示什么).
         * 
         * @since JDK 1.7
         */
        private String type;

        ClientType(String value) {
            this.type = value;
        }
    }

    /**
     * 创建client
     */
    protected void init() {
        if (CLIENT == null) {
            client(ClientType.TcpClient);
        }
    }

    /**
     * 根据类型生成两种客户端：1.Node；2.TcpClient
     * 
     * @data 2016年2月22日; @author ruibo
     * @param type
     *            es客户端类型
     * @return 生成客户端
     */
    protected Client client(ClientType type) {
        if (type.equals(ClientType.Node)) {
            CLIENT = nodeBuilder()
                    .settings(
                            Settings.settingsBuilder().put("http.enabled", false)
                                    .put("cluster.name", clustername)
                                    .put("transport.tcp.port", estcpport)
                                    .put("network.host", "0.0.0.0")
                                    .put("discovery.zen.ping.unicast.hosts", esdiscoveryhosts)
                                    .put("path.home", "."))
                    .clusterName(clustername).client(true).node().client();
        } else if (type.equals(ClientType.TcpClient)) {
            Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
            TransportClient tcpClient = TransportClient.builder().settings(settings).build();
            String[] hosts = esdiscoveryhosts.split(",|，");
            for (String host : hosts) {
                String[] hostparts = host.split(":");
                String hostname = hostparts[0];
                int hostport = estcpport;
                if (hostparts.length == 2) {
                    hostport = Integer.parseInt(hostparts[1]);
                }
                try {
                    tcpClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname),
                            hostport));
                } catch (UnknownHostException e) {
                    LOGGER.error("build TcpClient error for invalide host. host:port={}:{}", hostname, hostport);
                }
            }
            CLIENT = tcpClient;
        }
        return CLIENT;
    }

    /**
     * 关闭连接
     */
    protected void close() {
        if (CLIENT != null) {
            CLIENT.close();
        }
    }

    /**
     * 设置责任链
     * 
     * @param chainName
     *            过滤链名称
     * @return AbstractHandler 返回类型
     */
    protected static CommonChain setChains(String chainName) {

        // 读取配置文件
        String fName = CommonConstant.CHAINSPROPERTIES;// "chains.properties";
        String proPath = getPath(ChainsPropertise.class) + fName;
        System.out.println(proPath);
        // 动态读取配置文件
        Object chainObject = PropertiesAutoLoad.getInstance(proPath).getValueFromPropFile(chainName);

        ArrayList<String> chains = null;//存放属性文件配置的过滤节点
        if (chainObject == null) {//没有设置过滤链，就走默认的过滤链
            chains = (ArrayList<String>) PropertiesAutoLoad.getInstance(proPath).getValueFromPropFile(CommonConstant.DEFAULTCHAIN);
        } else if (chainObject instanceof ArrayList<?>) {//过滤链节点有多个
            chains = (ArrayList<String>) chainObject;
        } else {//过滤链节点只有一个
            chains = new ArrayList<String>();
            chains.add(chainObject.toString());
        }

        // 定义具体的责任对象：
        ArrayList<CommonChain> chainList = new ArrayList<CommonChain>();//存放经过辨识有效的过滤节点
        for (String name : chains) {
            if (!StringUtils.isBlank(name)) {
                chainList.add(new CommonChain(name));
            }
        }

        int flag = 0;
        int chainSize = chainList.size();
        for (int i = 0; i < chainSize; i++) {
            CommonChain chain = chainList.get(i);
            flag++;//标示当前节点的next
            if (flag < chainSize) {
                chain.setNextHandlerOfChain(chainList.get(flag));
            } else {
                chain.setNextHandlerOfChain(null);
            }
        }

        if (chainSize > 0) {
            return chainList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 得到某一个类的路径
     * 
     * @param name
     * @return
     */
    private static String getPath(Class name) {
        String strResult = null;
        if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
            strResult = name.getResource("/").toString().replace("file:/", "").replace("%20", " ");
        } else {
            strResult = name.getResource("/").toString().replace("file:", "").replace("%20", " ");
        }
        return strResult;
    }
}
