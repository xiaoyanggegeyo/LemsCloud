package com.msgcloud.utils;

import com.elementspeed.framework.common.message.MessageTemp;
import com.elementspeed.framework.common.message.MessageTempSession;

/**
 * 临时存储机制工具类， 存储、获取临时信息的统一接口
 * @author sfg0656
 *
 */
public class MessageUtil {
	
	/**
	 * 默认session实现，可根据具体业务实例化其他对象
	 * @return
	 */
	private static MessageTemp messageTempFactory() {
		return new MessageTempSession();
	}
	
	public static Object get(String key) {
		return messageTempFactory().get(key);
	}
	
	public static void put(String key, Object obj) {
		messageTempFactory().put(key, obj);
	}
	
	public static void remove(String key) {
		messageTempFactory().remove(key);
	}
	
	public static void removeAll() {
		messageTempFactory().removeAll();
	}
}
