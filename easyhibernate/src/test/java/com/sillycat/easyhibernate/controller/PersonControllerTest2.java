package com.sillycat.easyhibernate.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.sillycat.easyhibernate.core.ControllerTestBase;

/**
 * handlerAdapter and handlerMapping to verify our controller
 * can not mock the service or manager in controller
 * @author karl
 *
 */
public class PersonControllerTest2 extends ControllerTestBase{

	@Test  
    public void testAdd() throws Exception {  
		MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        request.setRequestURI("/person/1");  
        request.setMethod("GET");  
        this.excuteAction(request, response);  
    }  

}
