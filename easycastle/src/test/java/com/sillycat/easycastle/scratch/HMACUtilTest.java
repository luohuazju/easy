package com.sillycat.easycastle.scratch;

import org.junit.Test;

import com.sillycat.easycastle.scratch.HMACUtil;

public class HMACUtilTest {
	
	@Test
	public void encryptHMAC() throws Exception{
		String key = HMACUtil.initMacKey();
		System.out.println("key will be: " + key);
		
		String return_str = HMACUtil.encryptHMAC("hello world", key);
		System.out.println("return string will be: " + return_str);
	}

}
