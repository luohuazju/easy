package com.sillycat.easynio.plugins.mina.businesshandlers;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaculateHandler extends IoHandlerAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ScriptEngine jsEngine = null;

	public CaculateHandler() {
		ScriptEngineManager sfm = new ScriptEngineManager();
		jsEngine = sfm.getEngineByName("JavaScript");
		if (jsEngine == null) {
			throw new RuntimeException("No Java Script Engine.");
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.warn(cause.getMessage(), cause);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String expression = message.toString();
		if ("quit".equalsIgnoreCase(expression.trim())) {
			session.close(true);
			return;
		}
		try {
			Object result = jsEngine.eval(expression);
			session.write(result.toString());
		} catch (ScriptException e) {
			logger.warn(e.getMessage(), e);
			session.write("Wrong expression, try again.");
		}
	}

}
