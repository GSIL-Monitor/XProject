package com.yougou.XRedisCache.enums;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheStatistics {
	
	private final Logger logger = LoggerFactory.getLogger(CacheStatistics.class);
	
	private AtomicLong sum;
	
	private AtomicLong hit;
	
	private AtomicLong maxTime;
	
	private AtomicLong minTime;
	
	private AtomicLong averageTime;
	
	private AtomicLong sumTime;
	
	public CacheStatistics () {
		this.sum = new AtomicLong(0);
		this.hit = new AtomicLong(0);
		this.maxTime = new AtomicLong(0);
		this.minTime = new AtomicLong(Long.MAX_VALUE);
		this.averageTime = new AtomicLong(0);
		this.sumTime = new AtomicLong(0);
	}

	public AtomicLong getSum() {
		return sum;
	}

	public void setSum(AtomicLong sum) {
		this.sum = sum;
	}

	public AtomicLong getHit() {
		return hit;
	}

	public void setHit(AtomicLong hit) {
		this.hit = hit;
	}

	public AtomicLong getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(AtomicLong maxTime) {
		this.maxTime = maxTime;
	}

	public AtomicLong getMinTime() {
		return minTime;
	}

	public void setMinTime(AtomicLong minTime) {
		this.minTime = minTime;
	}

	public AtomicLong getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(AtomicLong averageTime) {
		this.averageTime = averageTime;
	}
	
	public AtomicLong getSumTime() {
		return sumTime;
	}

	public void setSumTime(AtomicLong sumTime) {
		this.sumTime = sumTime;
	}

	public void statisticsInfoPrint() {
		StringBuilder info = new StringBuilder();
		info.append("total number:" + this.sum + ",");
		info.append("hit number:" + this.hit + ",");
		if(0 != this.sum.get()) {
			info.append("shooting:" + (float)this.hit.get() / (float)this.sum.get() * 100 + "%,");
		}
		info.append("maxTime:" + this.maxTime + "ms,");
		if(0 != this.sum.get()) {
			info.append("averageTime:" + this.sumTime.get() / this.sum.get() + "ms,");
		}
		info.append("minTime:" + this.minTime + "ms.");
		logger.info(info.toString());
		clear();
	}
	
	private void clear() {
		logger.info("clear the cache statistics infomation!");
		this.sum = new AtomicLong(0);
		this.hit = new AtomicLong(0);
		this.maxTime = new AtomicLong(0);
		this.minTime = new AtomicLong(Long.MAX_VALUE);
		this.averageTime = new AtomicLong(0);
		this.sumTime = new AtomicLong(0);
	}
}
