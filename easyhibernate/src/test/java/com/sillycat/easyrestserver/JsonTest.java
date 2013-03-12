//package com.sillycat.easyrestserver;
//
//import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.codehaus.jackson.JsonFactory;
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.map.ObjectMapper;
//
//public class JsonTest {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// User user = new User();
//		// user.setEmail("luohuazju@hotmail.com");
//		// user.setIdxNum(Integer.valueOf(1));
//		// user.setLoginId("luohuazju");
//		// Company u = new Company();
//		// u.setName("handsome");
//		// u.setLocation("四u24029 ");
//		// user.setCompany(u);
//		Map item = new HashMap();
//		// item.put("user", user);
//		item.put("test", "test");
//		JSONObject jo = JSONObject.fromObject(item, configJson());
//		System.out.println("=============json lib====================");
//		System.out.println(jo.toString());
//		System.out.println("=============json lib====================");
//		System.out.println("=============jackson lib====================");
//		System.out.println(getJsonString(item));
//		System.out.println("=============jackson lib====================");
//	}
//
//	private static JsonConfig configJson() {
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//		return jsonConfig;
//	}
//
//	private static String getJsonString(Object item) {
//		JsonFactory jf = new JsonFactory();
//		try {
//			StringWriter sw = new StringWriter();
//			JsonGenerator gen = jf.createJsonGenerator(sw);
//			new ObjectMapper().writeValue(gen, item);
//			gen.flush();
//			return sw.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//}
