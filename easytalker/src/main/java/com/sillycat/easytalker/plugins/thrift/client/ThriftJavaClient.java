//package com.sillycat.easytalker.plugins.thrift.client;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.thrift.TException;
//import org.apache.thrift.protocol.TBinaryProtocol;
//import org.apache.thrift.protocol.TProtocol;
//import org.apache.thrift.transport.TSocket;
//import org.apache.thrift.transport.TTransport;
//
//import com.sillycat.easytalker.plugins.thrift.gen.code.Blog;
//import com.sillycat.easytalker.plugins.thrift.gen.code.BlogService;
//
//public class ThriftJavaClient {
//
//	public static void main(String[] args) throws TException {
//		long start = System.currentTimeMillis();
//		
//		TTransport transport = new TSocket("127.0.0.1", 9813);
//		TProtocol protocol = new TBinaryProtocol(transport);
//		BlogService.Client client = new BlogService.Client(
//				protocol);
//		transport.open();
//
//		//createBlog
//		Blog b1 = new Blog();
//		b1.setContent("test1 content".getBytes());
//		b1.setTopic("topic1");
//		b1.setCreatedTime(new Date().getTime());
//		b1.setIpAddress("127.0.0.1");
//		String result_CreateBlog = client.createBlog(b1);
//		System.out.println("result_CreateBlog = " + result_CreateBlog);
//		
//		//batchCreateBlog
//		List<Blog> blogs = new ArrayList<Blog>();
//		Blog b2 = new Blog();
//		b2.setContent("test2 content".getBytes());
//		b2.setTopic("topic2");
//		b2.setCreatedTime(new Date().getTime());
//		b2.setIpAddress("127.0.0.1");
//		b2.setId("2");
//		
//		blogs.add(b1);
//		blogs.add(b2);
//		
//		List<String> result_ids = client.batchCreateBlog(blogs);
//		System.out.println("result_ids = " + result_ids);
//		
//		//deleteBlog
//		String result_deleteBlog = client.deleteBlog("1");
//		System.out.println("result_deleteBlog = " + result_deleteBlog);
//		
//		//getOne
//		Blog result_GetOne = client.getOne("id");
//		System.out.println("result_GetOne = " + result_GetOne);
//		
//		//listAll
//		List<Blog> result_ListAll = client.listAll();
//		System.out.println("result_ListAll = " + result_ListAll);
//		
//		//updateBlog
//		String result_UpdateBlog = client.updateBlog(b1);
//		System.out.println("result_UpdateBlog = " + result_UpdateBlog);
//		
//		transport.close();
//		System.out.println((System.currentTimeMillis() - start));
//		System.out.println("client sucess!");
//
//	}
//
//}
