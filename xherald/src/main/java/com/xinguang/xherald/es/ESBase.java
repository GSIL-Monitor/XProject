/**
 * 
 */
package com.xinguang.xherald.es;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 
 * ClassName: ESBase <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月10日 下午7:28:04 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
public class ESBase {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	
    /**
     * LOGGER:logger对象.
     * 
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ESBase.class);

    /**
     * esClusterName:es集群名称.
     * 
     * @since JDK 1.7
     */
    //private static String ESCLUSTERNAME = "ecommerce-es";
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
    //private static String ESDISCOVERYHOSTS = "10.165.124.192:9600,10.165.124.193:9600,10.165.124.197:9600,10.165.124.198:9600";
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
                    .clusterName("ecommerce-es").client(true).node().client();
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
