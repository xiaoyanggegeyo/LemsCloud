package com.msgcloud;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import java.util.Properties;

/**
 * base基础服务：
 * 1.配置zuul路由网关 将此模块作为统一访问的路由网关
 * 2.配置jwt 用作token  后端拦截器验证request head 前端拦截器验证  response head
 */
@SpringBootApplication
@ImportResource("classpath*:config/i18nMessages.xml")
@MapperScan({"com.msgcloud.mapper","com.msgcloud.**.mapper"})
@Aspect
@EnableFeignClients
@EnableHystrix
@EnableApolloConfig
@EnableHystrixDashboard
@EnableCircuitBreaker
public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	//因为继承了SpringBootServletInitializer 所以可以自己配置项目名称  配置port
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

}