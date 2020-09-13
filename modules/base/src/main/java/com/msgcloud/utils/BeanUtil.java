package com.msgcloud.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class BeanUtil extends BeanUtils {
	public static final String[] ignoreProperties = new String[] { "id",
			"createUserId", "createTime", "lastModifyUserId", "lastModifyTime",
			"deleteFlag" };
	
	public static void copyPropertyNotNull(Object source, Object target){
		copyPropertyNotNull(source, target,null,(String[]) null);
	}
	
	public static void copyPropertyNotNull(Object source, Object target,String... ignoreProperties){
		copyPropertyNotNull(source, target,null,ignoreProperties);
	}
	
	
	
	private static void copyPropertyNotNull(Object source, Object target,Class<?> editable, String... ignoreProperties){
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
						"] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreProperties == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if(value!=null){
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	
	}
	
	
	
	

	static public void copyProperties(Object source, Object target) {
		if(source == null || target == null) {
			return;
		}
		
		try {
			PropertyUtils.copyProperties(target, source);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	static public void copyProperties(Object source, Object target, String... availableProperties) {
		try {
			for (String string : availableProperties) {
				PropertyUtils.setProperty(target, string, getProperty(source, string));
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	static public void setProperty(Object bean, String propertyName, Object value) {
		try {
			PropertyUtils.setProperty(bean, propertyName, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取bean.propertyName的值
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	static public Object getProperty(Object bean, String propertyName) {
		try {
			return PropertyUtils.getProperty(bean, propertyName);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 将javaBean中string类型的数据的null都转成空字符串
	 * @param t 
	 */
	public static void setBeanNull(Object t) throws Exception {
		PropertyDescriptor[] targetPds = getPropertyDescriptors(t.getClass());
		
		for(PropertyDescriptor targetPd : targetPds){
			if(targetPd.getPropertyType().isAssignableFrom(String.class)&& targetPd.getReadMethod().invoke(t)==null){
				try {
				targetPd.getWriteMethod().invoke(t, "");
				}catch (Exception e) {
					System.out.println(String.format("出错的转换字段:%s", targetPd.getName()));
				}
			}
		}
	}
}
