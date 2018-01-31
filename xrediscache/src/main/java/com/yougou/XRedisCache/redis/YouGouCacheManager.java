package com.yougou.XRedisCache.redis;

import java.util.Collection;

import org.springframework.cache.support.AbstractCacheManager;

public class YouGouCacheManager extends AbstractCacheManager{
	
	private Collection<? extends YouGouRedisCache> caches;
	
	public void setCaches(Collection<? extends YouGouRedisCache> caches) { 
	     this.caches = caches; 
	}	   

	@Override
	protected Collection<? extends YouGouRedisCache> loadCaches() {
		// TODO Auto-generated method stub
		return this.caches;
	}
}
