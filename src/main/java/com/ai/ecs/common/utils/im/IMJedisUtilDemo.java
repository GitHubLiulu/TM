package com.ai.ecs.common.utils.im;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.ecs.common.utils.JedisUtils;

import redis.clients.jedis.Jedis;


/**
 * 
 * ClassName: IMJedisUtil <br/>
 * Function: TODO ADD FUNCTION.
 * 扩展现有Redis实现，
 * 添加redis消息缓存的处理 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年6月7日  上午11:22:16 <br/>
 *
 * @author xiyang
 * @version v1.0 
 * @since JDK 1.6
 */
public class IMJedisUtilDemo extends JedisUtils {
	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
	
	/**
	 * 缓存一个数据
	 * 
	 * @param key
	 *            Msg_CustId#ShopId : Msg_CustId#ShopServerId :
	 * @param cacheMsg
	 *            : 缓存的数据
	 * @return true:success false:fail
	 * @throws Exception
	 */
	// MsgInfo cacheMsg --> change to json
	public static boolean setJedisLpush(String key, Object cacheMsg) throws Exception {

		long res = 0L ;
		Jedis jedis = null ;
		try {
			jedis = getResource();
			res = jedis.lpush(key, cacheMsg.toString());
		} catch (Exception e) {
			logger.warn("setJedisLpush->lPush {} = {},[key,cacheMsgList]", key, cacheMsg.toString(), e);
		} finally {
			returnResource(jedis);
		}
		return res > 0 ? true : false;
	}

	/**
	 * 缓存多个数据
	 * 
	 * @param key
	 *            Msg_CustId#ShopId : Msg_CustId#ShopServerId :
	 * @param cacheMsg
	 *            : 缓存的数据
	 * @return true:success false:fail
	 * @throws Exception
	 */
	// MsgInfo cacheMsg --> change to json
	
	public static boolean setJedisLpush(String key, List<Object> cacheMsgList) throws Exception {
		long res = 0L ;
		Jedis jedis = null ;
		try {
			jedis = getResource();
			res = jedis.lpush(key, cacheMsgList.toString());
		} catch (Exception e) {
			logger.warn("setJedisLpush->lPush {} = {},[key,cacheMsgList]", key, cacheMsgList.toString(), e);
		} finally {
			returnResource(jedis);
		}
		return res > 0 ? true : false;
	}

	/**
	 * 获取用户topN的数据
	 * 
	 * @param key
	 *            Msg_CustId#ShopId : Msg_CustId#ShopServerId :
	 * @param cacheMsg
	 *            : 缓存的数据
	 * @return true:success false:fail
	 * @throws Exception···················
	 */
	// MsgInfo cacheMsg --> change to json
	
	public static List<String> getTopNRedis(String key, int len) throws Exception {
		List<String> data = null;
		Jedis jedis = null ;
		try {
			jedis = getResource();
			data = getRedisData(key, 0, len - 1);
		} catch (Exception e) {
			logger.warn("getTopNRedis->lRange {} = {},[key, 0 ,lent]", key,len, e);
		} finally {
			returnResource(jedis);
		}
		return data;
	}

	/**
	 * 获取指定位置的数据：内部调用
	 * 
	 * @param key
	 * @param start
	 * @param len
	 * @return
	 * @throws Exception
	 */
	
	private static List<String> getRedisData(String key, int start, int len) throws Exception {
		List<String> data = null ;
		Jedis jedis = null ;
		try {
			jedis = getResource();
			data = jedis.lrange(key, start, len);
		} catch (Exception e) {
			logger.warn("getRedisData->lRange {} = {},[key,start,lent]", key,start,len, e);
		} finally {
			returnResource(jedis);
		}
		return data;
	}

	/**
	 * 删除缓存数据，用于持久化到数据库操作。
	 * 
	 * @param key
	 *            Msg_CustId#ShopId : Msg_CustId#ShopServerId :
	 * @param cacheMsg
	 *            : 缓存的数据
	 * @return true:success false:fail
	 * @throws Exception
	 */
	// MsgInfo cacheMsg --> change to json
	
	public static String getJedisRpop(String key) throws Exception {
		String data = "";

		Jedis jedis = null;
		try {
			jedis = getResource();
			data = jedis.rpop(key);
		} catch (Exception e) {
			logger.warn("getJedisRpop {} = {}", key, e);
		} finally {
			returnResource(jedis);
		}

		return data;
	}

	/**
	 * 获取key长度 getKeyLen
	 * 
	 * @throws Exception
	 */
	// MsgInfo cacheMsg --> change to json
	
	public static long getKeyLen(String key) throws Exception {
		if (null == key || "".equals(key)) {
			// log.info("key format Error-->["+key+"]->value["+cacheMsg+"]");
			return -1;
		}
		long len = 0L ;
		Jedis jedis = null;
		try {
			jedis = getResource();
			len = jedis.llen(key);
		} catch (Exception e) {
			logger.warn("getKeyLen {} = {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return len;
	}

	/**
	 * 获取keyPrefix前缀的所有keys getKeysByPrefix
	 * 
	 * @throws Exception
	 */
	// MsgInfo cacheMsg --> change to json
	
	public static Set<String> getKeysByPrefix(String keyPrefix) throws Exception {
		
		Jedis jedis = null;
		Set<String> keys = null ;
		try {
			jedis = getResource();
			keys = jedis.keys(keyPrefix + "*");
		} catch (Exception e) {
			logger.warn("KeysByPrefix {} = {}", keyPrefix, e);
		} finally {
			returnResource(jedis);
		}
		
		return keys;
	}
	
	public static void main(String[] args) {
		
		System.out.println("Java");;
	}

}
