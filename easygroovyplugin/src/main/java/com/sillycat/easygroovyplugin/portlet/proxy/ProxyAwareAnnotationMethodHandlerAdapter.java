package com.sillycat.easygroovyplugin.portlet.proxy;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ProxyAwareAnnotationMethodHandlerAdapter extends
		AnnotationMethodHandlerAdapter {

	
	public ModelAndView doHandle(PortletRequest request,
			PortletResponse response, Object handler) throws Exception {
		handler = unwrapHandler(handler);
		return super.doHandle(request, response, handler);
	}

	@Override
	public boolean supports(Object handler) {
		handler = unwrapHandler(handler);

		return super.supports(handler);
	}

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
