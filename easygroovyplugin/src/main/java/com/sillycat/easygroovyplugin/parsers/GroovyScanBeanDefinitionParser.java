package com.sillycat.easygroovyplugin.parsers;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scripting.config.LangNamespaceUtils;
import org.springframework.scripting.groovy.GroovyScriptFactory;
import org.w3c.dom.Element;

import com.sillycat.easygroovyplugin.utils.SystemConfiguration;

public class GroovyScanBeanDefinitionParser implements BeanDefinitionParser {

	private static final Log logger = LogFactory
			.getLog(GroovyScanBeanDefinitionParser.class);

	/**
	 * 
	 * @param element
	 * @param parserContext
	 * @return
	 */
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String pattern = element.getAttribute("source-pattern");

		String filepath = SystemConfiguration.getString("groovy.file.path");

		pattern = filepath + pattern;

		// Set up infrastructure.
		LangNamespaceUtils
				.registerScriptFactoryPostProcessorIfNecessary(parserContext
						.getRegistry());

		try {
			Resource[] resources = getResources(parserContext
					.getReaderContext().getResourceLoader(), pattern);

			for (Resource resource : resources) {
				// Create script factory bean definition.
				GenericBeanDefinition bd = new GenericBeanDefinition();
				bd.setBeanClass(GroovyScriptFactory.class);
				bd.setSource(resource);

				// Add constructor arguments.
				ConstructorArgumentValues cav = bd
						.getConstructorArgumentValues();
				int constructorArgNum = 0;

				// "file://D:/work/easygroovy/WebContent/WEB-INF/groovy/GroovyController.groovy"
				cav.addIndexedArgumentValue(constructorArgNum++, "file://"
						+ resource.getFile().getAbsolutePath());
				logger.info("file://" + resource.getFile().getAbsolutePath());

				String beanName = parserContext.getReaderContext()
						.generateBeanName(bd);
				parserContext.getRegistry()
						.registerBeanDefinition(beanName, bd);
			}

		} catch (IOException x) {
			throw new RuntimeException(x);
		}

		return null;
	}

	/**
	 * Get <code>Resource</code>s for the given pattern
	 * 
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	private Resource[] getResources(ResourceLoader resourceLoader,
			String pattern) throws IOException {
		if (resourceLoader instanceof ResourcePatternResolver) {
			return ((ResourcePatternResolver) resourceLoader)
					.getResources(pattern);

		} else {
			return new Resource[] { resourceLoader.getResource(pattern) };
		}
	}

}
