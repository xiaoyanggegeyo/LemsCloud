package com.msgcloud.utils;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

/**
 * spring 容器重写，不能提前读取
 * @author pengj
 * @date 2016年12月13日
 * @path com.elementspeed.framework.common.util.SpringContextUtil.java
 */
public class SpringContextUtil implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
	
	private static AbstractApplicationContext ctx;
/*	private static String SPRING_PROFILES = "spring.profiles.active";
	private static String SPRING_PROFILES_DEFAULT = "production";*/
	
	/**
	 * 获取spring上下文环境
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.ctx = (AbstractApplicationContext) applicationContext;
	}
	
	private static ApplicationContext getAppCtx() {
//		if(ctx == null) {
//			String profile = System.getProperty(SPRING_PROFILES);
//			if(StringUtil.isEmpty(profile)){
//				profile = SPRING_PROFILES_DEFAULT;
//			}
//			ctx = new GenericXmlApplicationContext();
//			ctx.getEnvironment().setActiveProfiles(profile);
//			ctx.load("classpath*:config/applicationContext-spring*.xml",
//					"classpath*:config/applicationContext-mybatis.xml");
//			ctx.refresh();
//			ctx = new ClassPathXmlApplicationContext(new String[]{
//				"classpath*:config/applicationContext-spring*.xml",
//				"classpath*:config/applicationContext-mybatis.xml"});
//		}
		return ctx;
	}

	/**
	 * 获取WEB-INF\classes的真是路径
	 * <p> 例:
	 * F:\SpeedElement\eclipse\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\Madoll\WEB-INF\classes\
	 * </p>
	 * @return
	 */
	public static String getApplicationContextPath() {
		try {
			return getAppCtx().getResource("").getFile().getAbsolutePath() + "/";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	/**
	 * 获取国际化信息
	 * @param key	国际化信息的key
	 * @return		转换后的的国际化信息
	 */
	public static String getMessage(String key, String... args) {
//		Locale locale = LocaleContextHolder.getLocale();
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			RequestContext requestContext = new RequestContext(request);
			return requestContext.getMessage(key, args);
		} catch(Exception e) {
			logger.warn("no key = " + key);
			return key;
		}
	}
	
	public static String getLocaleLanguage() {
		Locale locale = LocaleContextHolder.getLocale();
		return locale.getLanguage();
	}
	
	public static Object getBean(String beanId) throws BeansException {
		return getAppCtx().getBean(beanId);
	}

	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name
	 *            bean注册名
	 * @param requiredType
	 *            返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getAppCtx().getBean(name, requiredType);
	}
	
	/**
	 * 获取session
	 * @return
	 * @throws BeansException
	 */
	public static HttpSession getHttpSession() throws BeansException{
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
	}
}
