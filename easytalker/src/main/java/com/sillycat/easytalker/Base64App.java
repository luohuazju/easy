package com.sillycat.easytalker;

import org.apache.commons.codec.binary.Base64;

public class Base64App {

	public static void main(String[] args) {
		String data1 = "11227d82433354b9bbe7223e10efaa5f4710864ced18198615ce2044620db79e9c2a7a2bbcc66fe138ebc745c5cd16503ba946d965218241078a35bba12e58d5";
		String data2 = "MTAwMDozY2IwODgxMDM2ZTQ2ODMzNWQ4YTljMDEyNDE4YjI3MzpkYTk2ZjUyZmZiYzk1NjYyMDc3Y2FhMTVlMDA2MDE1ZGQ5N2EzZjUwZDc4OWJmNWQ5NmMyM2U4M2E5NmMzYjJmMGJlMGRiN2E1YmU3ZDk1NmI3ODkzYjA4YjA2NWQyNjE4MDM0ZDU4ZjdlMjY2ZjljN2MwNzNkM2JmMWUyZmMzMw==";
		
		Boolean result1 = Base64.isArrayByteBase64(data1.getBytes());
		Boolean result2 = Base64.isArrayByteBase64(data2.getBytes());
		System.out.println(result1 + ":" + result2);
		
		String result3 = new String(Base64.decodeBase64(data1));
		String result4 = new String(Base64.decodeBase64(data2));
		System.out.println("result3:" + result3);
		System.out.println("result4:" + result4);
		
		String[] result5 = result3.split(":");
		String[] result6 = result4.split(":");
		System.out.println(result5[0] + ":" + result6[0]);
		
	}

}
