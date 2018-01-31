/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.job.lite.console.service.impl;

import com.dangdang.ddframe.job.lite.console.common.Constants;
import com.dangdang.ddframe.job.lite.console.domain.Page;
import com.dangdang.ddframe.job.lite.console.domain.RegistryCenterConfiguration;
import com.dangdang.ddframe.job.lite.console.mapper.ExecutionLogMapper;
import com.dangdang.ddframe.job.lite.console.service.JobAPIService;
import com.dangdang.ddframe.job.lite.console.util.DateUtil;
import com.dangdang.ddframe.job.lite.console.util.PageUtil;
import com.dangdang.ddframe.job.lite.console.util.SessionRegistryCenterConfiguration;
import com.dangdang.ddframe.job.lite.lifecycle.api.*;
import com.google.common.base.Optional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class JobAPIServiceImpl implements JobAPIService {
    
	@Resource
	private ExecutionLogMapper executionLogMapper;
	
    @Override
    public JobSettingsAPI getJobSettingsAPI() {
        RegistryCenterConfiguration regCenterConfig = SessionRegistryCenterConfiguration.getRegistryCenterConfiguration();
        return JobAPIFactory.createJobSettingsAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }
    
    @Override
    public JobStatisticsAPI getJobStatisticsAPI(RegistryCenterConfiguration regCenterConfig) {
        return JobAPIFactory.createJobStatisticsAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }
    
    @Override
    public ServerStatisticsAPI getServerStatisticsAPI(RegistryCenterConfiguration regCenterConfig) {
        return JobAPIFactory.createServerStatisticsAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }
    
    @Override
    public JobOperateAPI getJobOperatorAPI(RegistryCenterConfiguration regCenterConfig) {
        return JobAPIFactory.createJobOperateAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }
    
    @Override
	public Map<String, Object> getExecutionLogList(final String jobName, String isSuccess, final Integer pageNo, final Integer pageSize) {
        if ("true".equals(isSuccess)) {
            isSuccess = "1";
        } else if ("false".equals(isSuccess)) {
            isSuccess = "0";
        } else {
            isSuccess = null;
        }

		Map<String, Object> param = new HashMap<>();
		
		param.put("jobName", jobName);
		param.put("isSuccess", isSuccess);

		Page page = getPage(pageNo, pageSize, param);

        param.put("start", (page.getPageNo()-1)*page.getPageSize());
        param.put("size", page.getPageSize());

		List<Map<String, Object>> tmpList = executionLogMapper.getList(param);
		Map<String, Object> m = null;
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < tmpList.size(); i++) {
			m = tmpList.get(i);
			
			m.put("sec", String.valueOf(DateUtil.getSecDuration((String) m.get("startTime"), (String) m.get("completeTime"))));
			
			list.add(m);
		}

		Map<String, Object> map = new HashMap<>();

		map.put("list", list);
		map.put("page", page);

		return map;
	}

    private Page getPage(Integer pageNo, Integer pageSize, Map<String, Object> param) {
        if (pageSize == null) {
            pageSize = Constants.PAGE_SIZE; // 每页显示记录数 (默认)
        }
        Integer totalRecord = executionLogMapper.getTotalRecord(param); // 总记录数
        Integer totalPage = PageUtil.getTotalPage(pageSize, totalRecord); // 总页数
        Boolean hasPre = PageUtil.getHasPre(pageNo); // 是否有上一页
        Boolean hasNext = PageUtil.getHasNext(pageNo, totalPage); // 是否有下一页
        Integer[] pages = PageUtil.getPages(pageNo, totalPage); // 页码序列
        Integer pagePre = pageNo - 1; // 上一页
        Integer pageNext = pageNo + 1; // 下一页

        Page page = new Page();

        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRecord(totalRecord);
        page.setTotalPage(totalPage);
        page.setHasPre(hasPre);
        page.setHasNext(hasNext);
        page.setPages(pages);
        page.setPagePre(pagePre);
        page.setPageNext(pageNext);

        return page;
    }
    
}
