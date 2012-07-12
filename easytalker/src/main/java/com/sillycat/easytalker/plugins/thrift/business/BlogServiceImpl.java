package com.sillycat.easytalker.plugins.thrift.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import com.sillycat.easytalker.plugins.thrift.gen.code.Blog;
import com.sillycat.easytalker.plugins.thrift.gen.code.BlogService;

public class BlogServiceImpl implements BlogService.Iface {

	private Logger logger = Logger.getLogger(this.getClass());

	public String createBlog(Blog blog) throws TException {
		logger.debug("Method createBlog is invoked! Parameters blog = " + blog
				+ " topic = " + blog.getTopic());
		return "1";
	}

	public List<String> batchCreateBlog(List<Blog> blogs) throws TException {
		logger.debug("Method batchCreateBlog is invoked! Parameters blogs = "
				+ blogs);
		List<String> ids = new ArrayList<String>();
		ids.add("1");
		ids.add("2");
		ids.add("3");
		return ids;
	}

	public String deleteBlog(String id) throws TException {
		logger.debug("Method deleteBlog is invoked! Parameters id = " + id);
		return "1";
	}

	public List<Blog> listAll() throws TException {
		logger.debug("Method listAll is invoked!");
		List<Blog> blogs = new ArrayList<Blog>();
		Blog b1 = new Blog();
		Blog b2 = new Blog();
		b1.setContent("test1 content".getBytes());
		b1.setTopic("topic1");
		b1.setCreatedTime(new Date().getTime());
		b1.setIpAddress("127.0.0.1");
		b1.setId("1");

		b2.setContent("test2 content".getBytes());
		b2.setTopic("topic2");
		b2.setCreatedTime(new Date().getTime());
		b2.setIpAddress("127.0.0.1");
		b2.setId("2");

		blogs.add(b1);
		blogs.add(b2);
		return blogs;
	}

	public Blog getOne(String id) throws TException {
		logger.debug("Method getOne is invoked! Parameters id = " + id);
		Blog b1 = new Blog();
		b1.setContent("test1 content".getBytes());
		b1.setTopic("topic1");
		b1.setCreatedTime(new Date().getTime());
		b1.setIpAddress("127.0.0.1");
		b1.setId("1");
		return b1;
	}

	public String updateBlog(Blog blog) throws TException {
		logger.debug("Method updateBlog is invoked! Parameters blog = " + blog
				+ " topic = " + blog.getTopic());
		return "1";
	}

}
