package com.platform.common.config;

import com.platform.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**注册拦截器
		 * */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AccessInterceptor accessInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessInterceptor)
				.addPathPatterns("/**").
				excludePathPatterns("/admin/login",
						"/admin/tokenVerify",
						"/sys-verify-code/getCode",
						"/sys-verify-code/getVerify/*",
						"/swagger-resources/**",
						"/webjars/**",
						"/v2/**",
//						"/fencePicPath/**",
//						"/fencePicProcessPath/**",
						"/swagger-ui.html/**",
						"/api/fl/sync");

	}


	/**
	 * @Description: 添加静态文件访问地址
	 * @param registry 1 
	 * @return void 
	 * @throws
	 * @author jianghy
	 * @date 2020/5/29 17:22 
	 */
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/fencePicPath/**").addResourceLocations("file:"+fencePicPath);
//		registry.addResourceHandler("/fencePicProcessPath/**").addResourceLocations("file:"+fencePicProcessPath);
//	}
}
