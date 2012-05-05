package org.apache.jackrabbit.j2ee;

import javax.jcr.Repository;

public class JcrRemotingServlet extends
		org.apache.jackrabbit.server.remoting.davex.JcrRemotingServlet {

	private static final long serialVersionUID = -4150840313818462613L;

	protected Repository getRepository() {
		return RepositoryAccessServlet.getRepository(getServletContext());
	}

}
