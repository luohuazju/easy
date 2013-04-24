package com.sillycat.easycastle.scratch;

import junit.framework.Assert;

import org.junit.Test;

import com.sillycat.easycastle.scratch.Base64Util;

public class Base64UtilTest {

	@Test
	public void encryptAndDecryptBASE64() throws Exception {
		String source = "<html><body><p>hello</p><a href='http://www.google.com'>google</a></body></html>";
		String return_str = Base64Util.encryptBASE64(source);
		System.out.println(return_str);
		Assert.assertTrue(!source.equals(return_str));
		String source_str = Base64Util.decryptBASE64(return_str);
		Assert.assertEquals(source, source_str);
	}

}
