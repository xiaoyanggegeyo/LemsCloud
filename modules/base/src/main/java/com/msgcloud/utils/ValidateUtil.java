package com.msgcloud.utils;

import java.util.Collection;
import java.util.Date;

import com.elementspeed.framework.base.exception.BOException;

/**
 * 基础数据校验Util
 *
 */
public class ValidateUtil {

	/**
	 * 非空校验.
	 * 如果collection为空, 抛出 "请至少选择一条记录"
	 * @param collection
	 * @throws BOException
	 */
	static public void isEmpty(Collection<?> collection) throws BOException {
		if(ContainerUtil.isEmpty(collection))
			throw new BOException("请至少选择一条记录");
	}
	
	/**
	 * 非空校验.
	 * 如果value为空, 抛出 "name不能为空"
	 * @param name		属性名
	 * @param value		属性值
	 * @throws BOException
	 */
	static public void isEmpty(String name, Object value) throws BOException {
		if(value == null || StringUtil.isEmpty(value + ""))
			throw new BOException(String.format("%s不能为空", name));
	}
	
	/**
	 * 非空校验.
	 * 如果collection元素的name属性为空, 抛出 "name不能为空"
	 * @param name				属性名
	 * @param collection		属性集合
	 * @throws BOException
	 */
	static public void isEmpty(String name, Collection<?> collection) throws BOException {
		if(ContainerUtil.isEmpty(collection))
			throw new BOException(String.format("%s不能为空", name));
	}
	
	/**
	 * 最大长度校验.
	 * 如果value.length > maxLength, 抛出 "name不能超过maxLength个字符" 
	 * @param name		属性名
	 * @param value		属性值
	 * @param maxLength 最大长度
	 * @throws BOException
	 */
	static public void gtMaxLength(String name, String value, int maxLength) throws BOException {
		if(StringUtil.isNotEmpty(value) && maxLength < value.length())
			throw new BOException(String.format("%s不能超过%d个字符", name, maxLength));
	}
	
	/**
	 * 最小长度校验.
	 * 如果value.length < minLength, 抛出 "name不能小于minLength个字符" 
	 * @param name		属性名
	 * @param value		属性值
	 * @param minLength 最小长度
	 * @throws BOException
	 */
	static public void ltMaxLength(String name, String value, int minLength) throws BOException {
		if(StringUtil.isNotEmpty(value) && minLength > value.length())
			throw new BOException(String.format("%s不能小于%d个字符", name, minLength));
	}
	
	/**
	 * 字符串长度闭区间校验.
	 * 如果value.length in (minLength, maxLength)
	 * @param name		属性名
	 * @param value		属性值
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @throws BOException
	 */
	static public void inLengthClosedInterval(String name, String value, int minLength, int maxLength) throws BOException {
		if(StringUtil.isNotEmpty(value) && (minLength > value.length() || maxLength < value.length()))
			throw new BOException(String.format("%s必须在%d个字符~%d个字符之间", name, minLength, maxLength));
	}
	
	/**
	 * 数值闭区间校验.  value in (start, end)
	 * @param name
	 * @param value
	 * @param start
	 * @param end
	 * @throws BOException 
	 */
	static public void inDoubleClosedInterval(String name, Double value, double start, double end) throws BOException {
		if(value == null)
			return;
		else if(MathUtil.compareTo(value, start) != 1 || MathUtil.compareTo(value, end) != -1)
			throw new BOException(String.format("%s必须在%s~%s之间", name, start + "", end + ""));
	}
	
	/**
	 * value是否大于当前日期
	 * @param name
	 * @param value
	 * @throws BOException 
	 */
	static public void gtCurrentDate(String name, Date value) throws BOException {
		if(DateUtil.compareWithToday(value) > 0)
			throw new BOException(String.format("%s不能大于当前日期", name));

	}
	
	/**
	 * value是否小于当前日期
	 * @param name
	 * @param value
	 * @throws BOException 
	 */
	static public void ltCurrentDate(String name, Date value) throws BOException {
		if(DateUtil.compareWithToday(value) < 0)
			throw new BOException(String.format("%s不能小于当前日期", name));
	}
}
