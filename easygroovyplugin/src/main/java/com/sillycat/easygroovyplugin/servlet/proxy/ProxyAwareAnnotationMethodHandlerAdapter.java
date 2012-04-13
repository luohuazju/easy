package com.sillycat.easygroovyplugin.servlet.proxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ProxyAwareAnnotationMethodHandlerAdapter extends
		AnnotationMethodHandlerAdapter {

	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter#handle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		handler = unwrapHandler(handler);

		return super.handle(request, response, handler);
	}

	/**
	 * @param handler
	 * @return
	 * @see org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter#supports(java.lang.Object)
	 */
	@Override
	public boolean supports(Object handler) {
		handler = unwrapHandler(handler);

		return super.supports(handler);
	}

	/**
	 * Attempt to unwrap the given handler in case it is an AOP proxy
	 * 
	 * @param handler
	 * @return Object
	 */
	private Object unwrapHandler(Object handler) {
		if (handler instanceof Advised) {
			try {
				TargetSource targetSource = ((Advised) handler)
						.getTargetSource();
				return targetSource.getTarget();

			} catch (Exception x) {
				throw new RuntimeException(x);
			}

		} else {
			return handler;
		}
	}

}
