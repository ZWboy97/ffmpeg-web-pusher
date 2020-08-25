package com.zwboy;

import com.zwboy.cache.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

/**
 * spring boot 启动
 *
 * @author ZWBoy
 */
@SpringBootApplication
public class Application {

    private final static Logger logger =
            LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // 将服务启动时间存入缓存
        CacheUtil.STARTTIME = System.currentTimeMillis();
        SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    public void destory() {
        logger.info("服务结束，开始释放空间...");
        // 关闭线程池
    }
}
