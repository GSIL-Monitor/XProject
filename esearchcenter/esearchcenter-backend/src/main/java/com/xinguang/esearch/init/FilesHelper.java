package com.xinguang.esearch.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinguang.esearch.constant.CommonConstant;

/**
 * 
 * ClassName: FilesHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:55:39 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class FilesHelper {
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FilesHelper.class);

    /**
     * INDEX:索引(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static String INDEX = "testindex";
    
    /**
     * categorys:类目(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static HashSet<String> CATEGORYS = new HashSet<String>();
    
    /**
     * brands:品牌(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static HashSet<String> BRANDS = new HashSet<String>();
    
    /**
     * cATEGORYS.
     *
     * @return  the cATEGORYS
     * @since   JDK 1.7
     */
    public static HashSet<String> getCATEGORYS() {
        return CATEGORYS;
    }

    /**
     * bRANDS.
     *
     * @return  the bRANDS
     * @since   JDK 1.7
     */
    public static HashSet<String> getBRANDS() {
        return BRANDS;
    }

    /**
     * cATEGORYS.
     *
     * @param   cATEGORYS    the cATEGORYS to set
     * @since   JDK 1.7
     */
    public static void setCATEGORYS(HashSet<String> cATEGORYS) {
        CATEGORYS = cATEGORYS;
    }

    /**
     * bRANDS.
     *
     * @param   bRANDS    the bRANDS to set
     * @since   JDK 1.7
     */
    public static void setBRANDS(HashSet<String> bRANDS) {
        BRANDS = bRANDS;
    }

    /**
     * map:TODO(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static Map<String, HashSet<String>> MAPS = new HashMap<String, HashSet<String>>();
    
    static {
        MAPS.put("categorys", CATEGORYS);
        MAPS.put("brands", BRANDS);
    
    	for (Entry<String, HashSet<String>> entry : MAPS.entrySet()) {
            String key = entry.getKey();
            HashSet<String> value = entry.getValue();
            synchronized (value) {
            	if (value.size() == 0){
            	    search(key, value);
            	}
            }
    
    	}
    
    }
    
    /**
     * search:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param key key
     * @param set set
     * @return 
     * @since JDK 1.7
     */
    private static void search(String key, HashSet<String> set) {
    	StringBuffer sb = new StringBuffer();
    	String rootPath = sb.append(CommonConstant.SOURCEROOT).append(File.separator).append(CommonConstant.DISTRIBUTION).append(File.separator).append(INDEX).append(File.separator).append(key).toString();
    	
    	// 遍历该目录下的所有含关键字的文件
    	File path = new File(rootPath);
        readDirectory(path,set);  
    
    	System.out.println(key + " size:" + set.size());
    	System.out.println(key + " content:" + set.toString());
    }

    /**
     * readDirectory://遍历目录. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param path 路径
     * @param set 返回值
     * @since JDK 1.7
     */
    private static void readDirectory(File path,HashSet<String> set){       
        if (!path.exists()){//文件根路径  
            System.out.println("文件路径不存在!");  
        }else{  
            if (path.isFile()){//具体文件 
                readFiles(path,set); 
            }else{//文件夹  
                File[] files = path.listFiles();  
                for (int i = 0; i < files.length; i++  ){  
                    readDirectory(files[i],set);  
                }  
            }  
        }  
    }  

    /**
     * readFiles:读取具体的文件. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param file 文件名
     * @param set 返回值
     * @since JDK 1.7
     */
    private static void readFiles(File file,HashSet<String> set) {
    	try {
    	    String encoding = "UTF-8";
            if (file.isFile() && file.exists()) { // 判断文件是否存在
            
            	InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
            
            	BufferedReader bufferedReader = new BufferedReader(read);
            
            	String lineTxt = null;
            
            	while ((lineTxt = bufferedReader.readLine()) != null) {
            	    set.add(lineTxt);
            	}
            
            	read.close();
            }
    	} catch (Exception ex) {
    	    System.out.println("read "+file.getAbsolutePath()+"error ===>" + ex.getMessage());
    	}
    }
}
