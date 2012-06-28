package com.sillycat.easywebflow.util;

import junit.framework.Assert;

import org.junit.Test;

public class SecurityTokenUtilTest {
	
	@Test
	public void getRandomIntNum(){
		String num1 = SecurityTokenUtil.getRandomIntNum(10);
		String num2 = SecurityTokenUtil.getRandomIntNum(10);
		Assert.assertNotSame(num1, num2);
	}
	
	@Test
	public void getRandomString(){
		String str1 = SecurityTokenUtil.getRandomString(30);
		String str2 = SecurityTokenUtil.getRandomString(30);
		Assert.assertNotSame(str1,str2);
	}

}
