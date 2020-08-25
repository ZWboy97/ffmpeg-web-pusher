package com.zwboy.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zwboy.worker.PushWorker;

/**
 * @Title CacheUtil.java
 * @description 推流缓存信息
 * @time 2019年12月17日 下午3:12:45
 * @author wuguodong
 **/
public final class CacheUtil {

	/**
	 * 存储ID与PushWorker之间的映射
	 * ID为PushTask的ID
	 */
	public static Map<String, PushWorker> PUSHWORKERMAP = new ConcurrentHashMap<>();

	/*
	 * 保存服务启动时间
	 */
	public static long STARTTIME;

}
