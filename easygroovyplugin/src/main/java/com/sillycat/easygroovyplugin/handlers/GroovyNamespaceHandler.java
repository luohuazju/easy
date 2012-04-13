package com.sillycat.easygroovyplugin.handlers;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.sillycat.easygroovyplugin.parsers.GroovyScanBeanDefinitionParser;

/**
 * 
 * @author luohua
 *
 */
public class GroovyNamespaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("scan",
				new GroovyScanBeanDefinitionParser());
	}

}
