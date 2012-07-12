package com.sillycat.easytalker.plugins.thrift.server;

import java.net.InetSocketAddress;   

import org.apache.thrift.protocol.TBinaryProtocol;   
import org.apache.thrift.server.TServer;   
import org.apache.thrift.server.TThreadPoolServer;   
import org.apache.thrift.server.TThreadPoolServer.Args;   
import org.apache.thrift.transport.TServerSocket;   
import org.apache.thrift.transport.TServerTransport;   
import org.apache.thrift.transport.TTransportFactory;

import com.sillycat.easytalker.plugins.thrift.business.BlogServiceImpl;
import com.sillycat.easytalker.plugins.thrift.gen.code.BlogService;

public class ThriftJavaServer {

	public static void main(String[] args) {

		BlogService.Processor<BlogServiceImpl> processor = new BlogService.Processor<BlogServiceImpl>(
				new BlogServiceImpl());
		try {
			TServerTransport serverTransport = new TServerSocket(
					new InetSocketAddress("0.0.0.0", 981));
			Args trArgs = new Args(serverTransport);
			trArgs.processor(processor);
			trArgs.protocolFactory(new TBinaryProtocol.Factory(true, true));
			trArgs.transportFactory(new TTransportFactory());
			TServer server = new TThreadPoolServer(trArgs);
			System.out.println("server begin ......................");
			server.serve();
			System.out.println("---------------------------------------");
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException("thrift server start failed!!" + "/n"
					+ e.getMessage());
		}

	}

}
