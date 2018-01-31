package com.xinguang.xherald.mq.thread;
import java.util.concurrent.ThreadFactory;

import org.springframework.stereotype.Component;

@Component
public class MyThreadFactory implements ThreadFactory {
    
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
    
    public Thread ctreatThread(Runnable r) throws Exception{
    	return newThread(r);
    }
}