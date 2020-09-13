package com.msgcloud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.elementspeed.framework.common.util.file.FileUtil;

/**
 * Properties文件相关操作类
 * 
 */
public class PropertiesUtil {
	/**
	 * 
	 * @param prop
	 * @param key
	 * @return 根据key值返回prop中对应的value信息.如果不存在对应的key, 则直接返回key的值.
	 */
	public static String getPropValue(Properties prop, String key) {
		if (prop == null) {
			return null;
		}
		
		return prop.containsKey(key) ? prop.getProperty(key) : key;
	}
	
	/**
	 * 根据key值返回prop中对应的value信息.如果不存在对应的key, 则直接返回defaultV的值.
	 * @param prop
	 * @param key
	 * @param defaultV
	 * @return
	 */
	public static String getPropValue(Properties prop, String key, String defaultV) {
		if (prop == null) {
			return defaultV;
		}
		
		return prop.containsKey(key) ? prop.getProperty(key) : defaultV;
	}
	
	/**
	 * 设置配置文件key  对应的 value
	 * @param prop
	 * @param key
	 * @param value
	 */
	public static void setPropValue(Properties prop, String key, String value) {
		prop.setProperty(key, value);
	}
	
	/**
	 * 根据路径加载.properties文件 
	 * @param path
	 * @return Properties
	 */
	public static Properties getPropertiesFile(File file) {
		Properties prop = new Properties();
		try {
			FileInputStream inputFile = new FileInputStream(file);
			prop.load(inputFile);
			inputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}

	/**
	 * 根据路径加载.properties文件 
	 * @param path		工程中的相对路径, 如 resource/config/elementspeedConf.properties
	 * @return Properties
	 */
	public static Properties getPropertiesFile(String path) {
		Properties prop = new Properties();
		try {
			FileInputStream inputFile = new FileInputStream(getRealPath(path));
			prop.load(inputFile);
			inputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	/**
	 * 根据classPath路径加载.properties文件 
	 * 采用spring的方式
	 * @param path 如 classpath: classpath*:，使用路径数组
	 * @return Properties
	 */
	public static Properties getPropertiesFileByClassPath(String[] paths) {
		Properties prop = new Properties();
		try {
			//使用spring的属性工具处理
			PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
			for(String propPath : paths){
				Resource[] resources = resourceLoader.getResources(propPath);
				for(Resource resource : resources){
					PropertiesLoaderUtils.fillProperties(prop, resource);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return prop;
	}

	/**
	 * 读取jar包中的配置文件
	 * @param clasz
	 * @param filePath
	 * @return
	 */
	public static Properties getPropertiesFromJar(Class<?> clasz, String filePath) {
		Properties prop = new Properties();
		try {
			InputStream is = clasz.getResourceAsStream(filePath);
			if(is != null) {
				prop.load(is);
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	/**
	 *
	 * @param path
	 * @return
	 * @throws URISyntaxException
	 */
	private static String getRealPath(String path) throws URISyntaxException {
		return SpringContextUtil.getApplicationContextPath() + path;
	}
	
	/**
	 * 将prop信息写入path指定的文件.
	 * @param path
	 * @param prop
	 */
	public static void save(String path, Properties prop) {
		try {
			if(!FileUtil.isFile(getRealPath(path))) {
				return;
			}
			
			OutputStream fos = new FileOutputStream(getRealPath(path));
			prop.store(fos, null);
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	


}
