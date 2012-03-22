package com.sillycat.easycastle.scratch;

import junit.framework.Assert;

import org.junit.Test;

import com.sillycat.easycastle.scratch.SHAUtil;

public class SHAUtilTest {

	@Test
	public void encryptSHA() throws Exception {
		String source = "welcome to this world.";
		String return_str = SHAUtil.encryptSHA(source);
		Assert.assertTrue(!source.equals(return_str));

		System.out.println(return_str);
	}

}
