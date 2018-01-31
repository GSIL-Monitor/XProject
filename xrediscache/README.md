# XRedisCache
***

## 通过XRedisCache能够完成什么功能？

目前XRedisCache支持的功能有：  
 1. 支持基本的缓存功能 。   
 2. 统计请求发起的总数、缓存命中的总数、调用缓存所花费最长的时间、最短时间以及平均时间等缓存信息。  
 3. 将统计的信息定时以日志形式输出，每次输出后会清空统计信息，重新开始统计。
## 如何使用？
1. 在pom文件中加入maven dependency。  

		<dependency>
	  		<groupId>com.yougou</groupId>
	  		<artifactId>XRedisCache</artifactId>
	 		 <version>0.0.1-SNAPSHOT</version>
		</dependency>

2. 配置配置文件。  
2.1 配置redis.xml。 
 
		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
		xmlns:aop="http://www.springframework.org/schema/aop"
		xmlns:cache="http://www.springframework.org/schema/cache" 
		xmlns:task="http://www.springframework.org/schema/task"
		xsi:schemaLocation="  
		        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  
		        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		        http://www.springframework.org/schema/cache
		       http://www.springframework.org/schema/cache/spring-cache.xsd
		       http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task-3.0.xsd">
		
		
		<cache:annotation-driven />
		
		<bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
			<property name="hostName" value="${redis.host}" />
			<property name="port" value="${redis.port}" />
			<property name="password" value="${redis.pass}" />
			<property name="poolConfig"  ref="jedisPoolConfig" />
		</bean>
		
		<bean id="redisOperations" class="org.springframework.data.redis.core.RedisTemplate" >
			<property name="connectionFactory">
			<ref bean="connectionFactory"/>
			</property>
		</bean>
		
		<bean  id="jedisPoolConfig"  class="redis.clients.jedis.JedisPoolConfig">  
		    <property  
		        name="maxIdle"  
		        value="${redis.pool.maxIdle}" />  
		    <property  
		        name="testOnBorrow"  
		        value="${redis.pool.testOnBorrow}" />  
		</bean>  
		<bean id="redisCache" class="org.springframework.data.redis.cache.RedisCache">
			<constructor-arg name="name" value="${redis.name}" />
			<constructor-arg name="prefix" value="${redis.prefix}" />
			<constructor-arg name="redisOperations" ref="redisOperations" />
			<constructor-arg name="expiration" value="${redis.expiration}" />
		</bean>
		
		<bean id="cacheManager" class="com.yougou.XRedisCache.redis.YouGouCacheManager">
			<property name="caches">
				<set>
					<bean class="com.yougou.XRedisCache.redis.YouGouRedisCache"
						p:redisCache-ref="redisCache">
						<property name="delay" value="${redis.statistics.delay}" />
						<property name="intevalPeriod" value="${redis.statistics.intevalPeriod}" />
						<property name="statisticSwitch" value="${redis.statistics.statisticSwitch}" />
					</bean>
				</set>
			</property>
		</bean>
		</beans>

	2.2 配置redis.properties。

		redis.name=footPrintCache
		redis.prefix=null
		redis.expiration=30
		redis.host=127.0.0.1
		redis.port=6379
		redis.pass=
		  
		redis.pool.maxIdle=300  //最大空闲连接数
		//redis.pool.maxActive=600
		//redis.pool.maxWait=1000
		redis.pool.testOnBorrow=true //在获取链接时检查有效性
		
		redis.statistics.delay=0
		redis.statistics.intevalPeriod=20000
		redis.statistics.statisticSwitch=true

	通过redis.statistics.delay设定统计功能开启的延迟时间。  
	通过redis.statistics.intevalPeriod设定统计功能每一个周期的时间。  
	通过redis.statistics.statisticSwitch设定是否开启统计功能。

	2.3 配置日志文件，根据自己的日志来配置。
3. 在需要缓存的地方加注解。  

		@Cacheable(value="footPrintCache") //存取缓存值
		@CacheaEvict(value="footPrintCache") //清除缓存，适用于更新、重新加载操作

		value、key（可选）、condition（可选）
## 注意事项
1. 在reidscache.xml中不能缺少<cache:annotation-driven />，否则缓存会不起效。
2. XRedisCache需要的spring-data-redis-1.7.2-RELEASE.jar的支持，需要将版本提高到1.7.2。
3. 在redis.properties文件中，redis.expirtation所设置的过期时间是以“秒”为单位的，redis.statistics.intevalperiod所设置的周期时间是以“毫秒”为单位的。

## FAQ

超时过后怎么处理？  
update过程？  
redis挂了之后？  
并发情况？
condition是否支持正则表达式？
默认key的生成方法？
1次更新影响多个数据？

##不足之处