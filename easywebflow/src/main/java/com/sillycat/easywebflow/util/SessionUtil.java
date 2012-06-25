package com.sillycat.easywebflow.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionUtil {

	private final static Log log = LogFactory.getLog(SessionUtil.class);

	static public boolean startNewSessionIfRequired(HttpServletRequest request,
			boolean migrateSessionAttributes) {
		// map to hold all the parameters
		HashMap<String, Object> attributesToMigrate = null;

		// get session
		HttpSession session = request.getSession(false);
		if (session == null) {
			log.debug("how did this happen, there is no session!!!!!!!!!!!!");
			// if no session, there is nothing to deal
			return false;
		}

		String originalSessionId = session.getId();

		// save the attributes in map
		if (migrateSessionAttributes) {
			attributesToMigrate = new HashMap<String, Object>();
			Enumeration<?> enumer = session.getAttributeNames();

			while (enumer.hasMoreElements()) {
				String key = (String) enumer.nextElement();
				attributesToMigrate.put(key, session.getAttribute(key));
			}
		}

		// kill the old session
		if (log.isDebugEnabled()) {
			log.debug("Invalidating session with Id " + originalSessionId + " start!");
		}
		session.invalidate();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error(e);
		}
		if (log.isDebugEnabled()) {
			log.debug("Invalidating session with Id " + originalSessionId + " end!");
		}
		session = request.getSession(true); // we now have a new session
		
		if (log.isDebugEnabled()) {
			log.debug("Started new session: " + session.getId());
		}

		// migrate the attribute to new session
		if (attributesToMigrate != null) {
			Iterator<?> iter = attributesToMigrate.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry<?, ?> entry = (Entry<?, ?>) iter.next();
				session.setAttribute((String) entry.getKey(), entry.getValue());
			}
		}
		return true;
	}

}
