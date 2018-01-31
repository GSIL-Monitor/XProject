package com.yougou.XRedisCache.redis;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;

import com.yougou.XRedisCache.enums.CacheStatistics;


public class YouGouRedisCache implements Cache,InitializingBean{
	
	private final Logger logger = LoggerFactory.getLogger(YouGouRedisCache.class);
	
	private RedisCache redisCache;
	
	private static CacheStatistics cacheStatistics = new CacheStatistics();
	
	private AtomicLong sumTime = new AtomicLong(0);
	
	long delay;
	
	long intevalPeriod;
	
	boolean statisticSwitch;
	
	public void afterPropertiesSet() throws Exception {
		logger.info("initialize a cache named {}", this.redisCache.getName());
		if(statisticSwitch) {
			timeTask();
		}
	}
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getIntevalPeriod() {
		return intevalPeriod;
	}

	public void setIntevalPeriod(long intevalPeriod) {
		this.intevalPeriod = intevalPeriod;
	}
	

	public boolean getStatisticSwitch() {
		return statisticSwitch;
	}

	public void setStatisticSwitch(boolean statisticSwitch) {
		this.statisticSwitch = statisticSwitch;
	}

	public String getName() {
		return redisCache.getName();
	}

	public Object getNativeCache() {
		return redisCache.getNativeCache();
	}

	public ValueWrapper get(Object key) {
		ValueWrapper result;
		if(statisticSwitch) {
			cacheStatistics.setSum(new AtomicLong(cacheStatistics.getSum().incrementAndGet()));
			StopWatch click = new StopWatch();
			click.start();
			result = redisCache.get(key);
			click.stop();
			if(click.getTime() > cacheStatistics.getMaxTime().longValue()) {
				cacheStatistics.setMaxTime(new AtomicLong(click.getTime()));
			} else if (click.getTime() < cacheStatistics.getMinTime().longValue()) {
				cacheStatistics.setMinTime(new AtomicLong(click.getTime()));
			}
			sumTime = new AtomicLong(sumTime.addAndGet(click.getTime()));
			cacheStatistics.setSumTime(sumTime);
			if(null != result) {
				cacheStatistics.setHit(new AtomicLong(cacheStatistics.getHit().incrementAndGet()));
			}
		} else {
			result = redisCache.get(key);
		}
		logger.info("get from cache, the key is {}, the value is {}", key, result);
		return result;
	}

	public void put(Object key, Object value) {	
		logger.info("put value into cache, the key is {}, the value is {}", key, value);
		redisCache.put(key, value);
	}

	public void evict(Object key) {
		redisCache.evict(key);
	}

	public void clear() {
		redisCache.clear();		
	}

	public RedisCache getRedisCache() {
		return redisCache;
	}

	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}

	public <T> T get(Object arg0, Class<T> arg1) {
		return redisCache.get(arg0, arg1);
	}

	public <T> T get(Object arg0, Callable<T> arg1) {
		return redisCache.get(arg0, arg1);
	}

	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		return redisCache.putIfAbsent(arg0, arg1);
	}
	
	public void printStatisticsInfo() {
		cacheStatistics.statisticsInfoPrint();
	}
	
	public void timeTask() {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				printStatisticsInfo();
			}
			
		};		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);
	}

}
