package com.msgcloud.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ContainerUtil {

	final static public boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	final static public boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	@SuppressWarnings("rawtypes")
	final static public boolean isEmpty(Map map) {
		return map == null || map.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	final static public boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}

	/**
	 * 将str转换为list, str中的元素以逗号分隔
	 * 
	 * @param str
	 * @return
	 */
	final static public List<String> string2List(String str) {
		return StringUtil.isEmpty(str) ? null : Arrays.asList(StringUtil.split(
				str, ","));
	}

	final static public String convert2String(Collection<String> collection,
			String symbol) {
		if (isEmpty(collection))
			return "";

		StringBuilder result = new StringBuilder();
		for (String string : collection) {
			result.append(string).append(symbol);
		}

		return StringUtil.removeLast(result.toString());
	}

	/**
	 * 将集合转成字符串，以，分割
	 * 
	 * @param list
	 * @return
	 */
	public static String convertToString(List<String> list) {
		// 转换成sql的空字符串
		if (ContainerUtil.isEmpty(list))
			return "";

		StringBuilder result = new StringBuilder();
		for (String item : list) {
			result.append("'").append(item).append("',");
		}

		return StringUtil.removeLast(result.toString());
	}

	/**
	 * 将collection转换为string. collection中的每个元素都 是自定义类, propertyName object的属性
	 * 
	 * @param collection
	 * @param propertyName
	 * @param symbol
	 * @return
	 */
	final static public String convert2String(Collection<?> collection,
			String propertyName, String symbol) {
		if (isEmpty(collection))
			return "";

		StringBuilder result = new StringBuilder();
		for (Object obj : collection) {
			result.append(BeanUtil.getProperty(obj, propertyName)).append(
					symbol);
		}

		return StringUtil.removeLast(result.toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	final static public List convert2List(Collection collection) {
		List result = new LinkedList();
		if (isEmpty(collection))
			return result;

		Iterator itr = collection.iterator();
		while (itr.hasNext()) {
			result.add(itr.next());
		}

		return result;
	}

	final static public void removeEmpty(Collection<Object> collection) {
		Collection<Object> col = new LinkedList<Object>();
		if (isNotEmpty(collection)) {
			for (Object str : collection) {
				if (str == null)
					col.add(str);
			}
		}
		collection.removeAll(col);
	}

	public static void main(String[] args) {
		System.out.println(ContainerUtil.string2List("1111,2222,3333").size());
	}
}
