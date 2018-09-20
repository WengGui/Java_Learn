package com.weng.concurrency;

import com.weng.concurrency.threadLocal.HttpFilter;
import com.weng.concurrency.threadLocal.HttpInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ConcurrencyApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean httpFilter(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		// 指定对应的filter类
		registrationBean.setFilter(new HttpFilter());
		// 要拦截的url
		registrationBean.addUrlPatterns("/threadLocal/*");
		return registrationBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");

	}
}
