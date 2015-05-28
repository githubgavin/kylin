package com.kylin.common.cache;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisCache implements ICache {
	private ShardedJedisPool pool;
	private List<JedisShardInfo> shardInfoList;

	public RedisCache() {
	}

	public RedisCache(List<JedisShardInfo> shardInfoList) {
		this.shardInfoList = shardInfoList;
		initShardPool();
	}

	/**
	 * 通过配置字符串创建RedisCache
	 * 
	 * @param hostPosts
	 *            参数格式为: "host1:port1,host2:port2"
	 */
	public RedisCache(String hostPosts) {
		this.shardInfoList = convertHostPorts(hostPosts);
		initShardPool();
	}

	// 兼容单台redis
	public RedisCache(String host, int port) {
		this.shardInfoList = new ArrayList<JedisShardInfo>();
		this.shardInfoList.add(new JedisShardInfo(host, port));
		initShardPool();
	}

	/**
	 * 将配置字符串转换Redis主机列表
	 * 
	 * @param hostPosts
	 *            参数格式为: "host1:port1,host2:port2"
	 * @return
	 */
	private List<JedisShardInfo> convertHostPorts(String hostPosts) {
		List<JedisShardInfo> result = new ArrayList<JedisShardInfo>();
		for (String hostPostString : hostPosts.split(",")) {
			String[] hostAndPost = hostPostString.split(":");
			if (hostAndPost.length == 0) {
				continue;
			}
			String host = hostAndPost[0].trim();
			Integer port = 6379;
			if (hostAndPost.length > 1) {
				port = Integer.valueOf(hostAndPost[1].trim());
			}
			result.add(new JedisShardInfo(host, port));
		}
		return result;
	}

	private void initShardPool() {
		if (this.shardInfoList == null) {
			return;
		}
		// TODO Jedis连接池可以在这里自定义配置，目前采用默认配置
		JedisPoolConfig config = new JedisPoolConfig();
		this.pool = new ShardedJedisPool(config, this.shardInfoList);
	}

	public ShardedJedisPool getPool() {
		return pool;
	}

	public void setJedis(ShardedJedisPool pool) {
		this.pool = pool;
	}

	public String get(String key) {
		try (ShardedJedis jedis = pool.getResource()) {
			return jedis.get(key);
		} catch (Exception ex) {
			return null;
		}
	}

	public String set(String key, String value) {
		try (ShardedJedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		} catch (Exception ex) {
			return null;
		}
	}

	public String setex(String key, int seconds, String value) {
		try (ShardedJedis jedis = pool.getResource()) {
			return jedis.setex(key, seconds, value);
		} catch (Exception ex) {
			return null;
		}
	}

	public Boolean exists(String key) {
		try (ShardedJedis jedis = pool.getResource()) {
			return jedis.exists(key);
		}
	}

	public Long del(String key) {
		try (ShardedJedis jedis = pool.getResource()) {
			return jedis.del(key);
		}
	}

}
