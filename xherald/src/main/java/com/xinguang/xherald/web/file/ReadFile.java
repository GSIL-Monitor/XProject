package com.xinguang.xherald.web.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.mq.beans.MQConnection;
import com.xinguang.xherald.mq.beans.MQInitConnection;
import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.mq.thread.ThreadManager;
import com.xinguang.xherald.mq.utils.DecryptMethod;
import com.xinguang.xherald.web.enums.TopologyStatusEnum;

/**
 * 
 * ClassName: ReadFile <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月20日 下午4:52:55 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
@Component
public class ReadFile {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadFile.class);

	@Autowired
	private MQInitConnection mqInitConnection;

	public boolean readListenThreads(String sourcePath, ThreadManager threadManager) {
		LOGGER.info("Initialize exist Topology, read file*******start*******>>> filePath={}", sourcePath);
		File file = new File(sourcePath);
		if (!file.exists()) {
			LOGGER.info("Initialize exist Topology, the path of the specified file does not exist, filePath={}",
					sourcePath);
			return false;
		}

		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(file));

			String line = null;

			while ((line = br.readLine()) != null) {
				try {
					String[] strSplit = line.split(MQES.TAB);
					if (strSplit != null && strSplit.length == 6 && Integer.parseInt(strSplit[4].trim())==TopologyStatusEnum.STATUS_4.getStatusCode()) {
						String[] threadInfo = strSplit[0].split(MQES.CONNECTOR);
						String[] mqInfo = strSplit[2].split(MQES.CONNECTOR);						
						if (threadInfo != null && threadInfo.length == 5 && mqInfo != null && mqInfo.length == 3) {
							Map<String, String> msg = new HashMap<String, String>();
							msg.put(MQES.QUEUENAME, threadInfo[2]);
							msg.put(MQES.INDEXNAME, threadInfo[3]);
							msg.put(MQES.INDEXTYPE, threadInfo[4]);
							
							msg.put(MQES.HOST, mqInitConnection.getHost());
							msg.put(MQES.PORT, String.valueOf(mqInitConnection.getPort()));
							msg.put(MQES.VHOST, mqInfo[0]);
							msg.put(MQES.USERNAME, mqInfo[1]);
							msg.put(MQES.PASSWORD, DecryptMethod.getDecryptValue(mqInfo[2]).trim());

							try {
								threadManager.createThread(msg, MQES.THREADNUM);

							} catch (Exception e) {
								// TODO Auto-generated catch block
								LOGGER.error("create ListenThread error. function={}, input={}, errorMessage={}", "readListenThreads", msg, e);
							}
						}
					}

				} catch (Exception e) {
					// e.printStackTrace();
					LOGGER.error("Read file error>>>. function={}, line={}, errorMessage={}", "readListenThreads", line, e);
					continue;
				}
			}
			br.close();
			LOGGER.info("Initialize exist Topology, read file finish *****end*****");
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("Initialize exist Topology Error>>>. function={}, sourcePath={}, errorMessage={}",
					"readListenThreads", sourcePath, e);
			return false;
		}
	}

	public List<String> getConnections(String sourcePath, String userName) {
		LOGGER.info("Query exist Topology, read file*****start*****>>> filePath={}", sourcePath);
		ArrayList<String> resultList = new ArrayList<String>();
		File file = new File(sourcePath);
		if (!file.exists()) {
			LOGGER.info("Query exist Topology,  the path of the specified file does not exist, filePath={}",
					sourcePath);
			return null;
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				try {
					String[] strSplit = line.split(MQES.TAB);
					if (strSplit != null) {
						if (userName.equals(strSplit[1].trim())) {
							resultList.add(line);
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
					LOGGER.error("Read file error>>>. function={}, line={}, errorMessage={}", "getConnections", line,
							e);
					continue;
				}
			}
			br.close();
			LOGGER.info("Query exist Topology, read file finish*****end*****");
			return resultList;
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("Query exist Topology Error>>>. function={}, sourcePath={}, errorMessage={}", "getConnections",
					sourcePath, e);
			return null;
		}
	}
}
