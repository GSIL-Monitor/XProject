package com.xinguang.esearch.algorithm.chain;

import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

import com.xinguang.esearch.algorithm.chain.ChainsPropertise;

/**
 * 
 * 配置文件properties自动加载类
 * 
 * @author hznijiaming 原作者lyh
 * @version 2016-06-07
 * @see PropertiesAutoLoad
 * @since
 */
public class PropertiesAutoLoad {
    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(PropertiesAutoLoad.class);

    /**
     * Singleton
     */
    private static final PropertiesAutoLoad AUTO_LOAD = new PropertiesAutoLoad();

    /**
     * Configuration
     */
    private static PropertiesConfiguration propConfig;

    /**
     * 自动保存
     */
    private static boolean autoSave = true;

    /**
     * properties文件路径
     * 
     * @param propertiesFile
     * @return
     * @see
     */
    public static PropertiesAutoLoad getInstance(String propertiesFile) {
        // 执行初始化
        init(propertiesFile);

        return AUTO_LOAD;
    }

    /**
     * 根据Key获得对应的value
     * 
     * @param key
     * @return
     * @see
     */
    public Object getValueFromPropFile(String key) {
        return propConfig.getProperty(key);
    }

    /**
     * 获得对应的value数组
     * 
     * @param key
     * @return
     * @see
     */
    public String[] getArrayFromPropFile(String key) {
        return propConfig.getStringArray(key);
    }

    /**
     * 设置属性
     * 
     * @param key
     * @param value
     * @see
     */
    public void setProperty(String key, String value) {
        propConfig.setProperty(key, value);
    }

    /**
     * 设置属性
     * 
     * @param map
     * @see
     */
    public void setProperty(Map<String, String> map) {
        for (String key : map.keySet()) {
            propConfig.setProperty(key, map.get(key));
        }
    }

    /**
     * 构造器私有化
     */
    public PropertiesAutoLoad() {

    }

    /**
     * 初始化
     * 
     * @param propertiesFile
     * @see
     */
    private static void init(String propertiesFile) {
        try {
            propConfig = new PropertiesConfiguration(propertiesFile);

            // 自动重新加载
            propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());

            propConfig.addConfigurationListener(new ConfigurationListener() {

                @Override
                public void configurationChanged(ConfigurationEvent e) {
                    // TODO Auto-generated method stub
                    System.out.println("配置文件更新..." + e.getType());
                    if (PropertiesConfiguration.EVENT_RELOAD == e.getType()) {
                        System.out.println("配置文件重载...");
                    }
                }
            });

            // 自动保存
            propConfig.setAutoSave(autoSave);

        } catch (ConfigurationException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Test
     * 
     * @param args
     * @see
     */
    public static void main(String[] args) {
        String fName = "chains.properties";
        String proPath = getPath(ChainsPropertise.class) + fName;
        System.out.println(proPath);
        System.out.println(PropertiesAutoLoad.getInstance(proPath).getValueFromPropFile("offsetNum"));
        try {
            Thread.sleep(20000);
            System.out.println("开始启动");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(PropertiesAutoLoad.getInstance(proPath).getValueFromPropFile("offsetNum"));

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