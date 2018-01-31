package com.xinguang.esearchcenter.inter;

import java.util.List;

import com.xinguang.esearchcenter.request.PageRequest;

public interface UserService {
	
	String userSearch(PageRequest request);
	
	String userSearchByIds(List<Long> idList);

}
