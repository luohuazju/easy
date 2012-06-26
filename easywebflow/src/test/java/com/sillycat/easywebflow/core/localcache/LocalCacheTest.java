package com.sillycat.easywebflow.core.localcache;

import org.junit.Test;
import org.springframework.util.Assert;

public class LocalCacheTest {

	@Test
	public void dummy() {
		Assert.isTrue(true);
	}

	@Test
	public void lifetime() throws InterruptedException {
		LocalCache<String, String> localCache = new LocalCache<String, String>(
				"test_name", 1024 * 1024 * 100, 5000);
		for (int i = 0; i < 50; i++) {
			localCache.put("1_" + i, "1");
		}
		//sleep 2
		localCache.get("1_1");
		
		int bytes_of_string = "1".getBytes().length + 4;
		
		Assert.isTrue( 50 * bytes_of_string == localCache.getCacheSize());
		Thread.sleep(2000);
		localCache.get("1_1");
		Assert.isTrue( 50 * bytes_of_string == localCache.getCacheSize());
		for (int i = 0; i < 50; i++) {
			localCache.put("2_" + i, "1");
		}
		localCache.get("1_1");
		Assert.isTrue( 100 * bytes_of_string == localCache.getCacheSize());
		Thread.sleep(4000);
		localCache.get("1_1");
		Assert.isTrue( 50 * bytes_of_string == localCache.getCacheSize());
		Thread.sleep(2000);
		localCache.get("1_1");
		Assert.isTrue( 0 == localCache.getCacheSize());
	}

}
