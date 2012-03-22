package com.sillycat.easycastle.scratch;

import junit.framework.Assert;

import org.junit.Test;

import com.sillycat.easycastle.scratch.MD5Util;

public class MD5UtilTest {
	
	@Test
	public void encryptMD5() throws Exception{
		String source = "welcome to this world.";
		String return_str = MD5Util.encryptMD5(source);
		Assert.assertTrue(!source.equals(return_str));
		
		System.out.println(return_str);
	}

}
