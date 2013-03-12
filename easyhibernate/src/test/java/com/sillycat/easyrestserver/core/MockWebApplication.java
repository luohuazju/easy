package com.sillycat.easyrestserver.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MockWebApplication {
	String webapp() default "src/main/webapp";

	String name();
	
	String[] locations() default "src/test/test-content.xml";
}
