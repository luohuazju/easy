package com.sillycat.easyopenidgoogle.openid4java;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;

public class ConsumerServlet extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = -5998885243419513055L;

	private final Log log = LogFactory.getLog(this.getClass());

	private ServletContext context;

	private ConsumerManager manager;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		context = config.getServletContext();

		log.debug("context: " + context);

		// --- Forward proxy setup (only if needed) ---
		// ProxyProperties proxyProps = new ProxyProperties();
		// proxyProps.setProxyName("proxy.example.com");
		// proxyProps.setProxyPort(8080);
		// HttpClientFactory.setProxyProperties(proxyProps);
		this.manager = new ConsumerManager();
		manager.setAssociations(new InMemoryConsumerAssociationStore());
		manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if ("true".equals(req.getParameter("is_return"))) {
			processReturn(req, resp);
		} else {
			String identifier = req.getParameter("openid_identifier");
			if (identifier != null) {
				this.authRequest(identifier, req, resp);
			} else {
				this.getServletContext().getRequestDispatcher("/index.jsp")
						.forward(req, resp);
			}
		}
	}

	private void processReturn(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Identifier identifier = this.verifyResponse(req);
		log.debug("identifier: " + identifier);
		if (identifier == null) {
			this.getServletContext().getRequestDispatcher("/index.jsp")
					.forward(req, resp);
		} else {
			req.setAttribute("identifier", identifier.getIdentifier());

			this.getServletContext().getRequestDispatcher("/return.jsp")
					.forward(req, resp);
		}
	}

	// --- placing the authentication request ---
	public String authRequest(String userSuppliedString,
			HttpServletRequest httpReq, HttpServletResponse httpResp)
			throws IOException, ServletException {
		try {
			// configure the return_to URL where your application will receive
			// the authentication responses from the OpenID provider
			// String returnToUrl = "http://example.com/openid";
			String returnToUrl = httpReq.getRequestURL().toString()
					+ "?is_return=true";

			// perform discovery on the user-supplied identifier
			List<?> discoveries = manager.discover(userSuppliedString);

			// attempt to associate with the OpenID provider
			// and retrieve one service endpoint for authentication
			DiscoveryInformation discovered = manager.associate(discoveries);

			// store the discovery information in the user's session
			httpReq.getSession().setAttribute("openid-disc", discovered);

			// obtain a AuthRequest message to be sent to the OpenID provider
			AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

			// Attribute Exchange example: fetching the 'email' attribute
			FetchRequest fetch = FetchRequest.createFetchRequest();
			SRegRequest sregReq = SRegRequest.createFetchRequest();

			if ("1".equals(httpReq.getParameter("nickname"))) {
				fetch.addAttribute("nickname",
						"http://axschema.org/namePerson/last", true);
				sregReq.addAttribute("nickname", true);
			}
			if ("1".equals(httpReq.getParameter("email"))) {
				fetch.addAttribute("email",
						"http://schema.openid.net/contact/email", true);
				sregReq.addAttribute("email", true);
			}
			if ("1".equals(httpReq.getParameter("fullname"))) {
				fetch.addAttribute("fullname",
						"http://axschema.org/namePerson/first", true);
				sregReq.addAttribute("fullname", true);
			}
			if ("1".equals(httpReq.getParameter("country"))) {
				fetch.addAttribute("country",
						"http://axschema.org/contact/country/home", true);
				sregReq.addAttribute("country", true);
			}
			if ("1".equals(httpReq.getParameter("language"))) {
				fetch.addAttribute("language",
						"http://axschema.org/pref/language", true);
				sregReq.addAttribute("language", true);
			}

			// attach the extension to the authentication request
			if (!sregReq.getAttributes().isEmpty()
					|| !fetch.getAttributes().isEmpty()) {
				authReq.addExtension(sregReq);
				authReq.addExtension(fetch);
			}

			if (!discovered.isVersion2()) {
				// Option 1: GET HTTP-redirect to the OpenID Provider endpoint
				// The only method supported in OpenID 1.x
				// redirect-URL usually limited ~2048 bytes
				httpResp.sendRedirect(authReq.getDestinationUrl(true));
				return null;
			} else {
				// Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)

				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/formredirection.jsp");
				httpReq.setAttribute("prameterMap", httpReq.getParameterMap());
				httpReq.setAttribute("message", authReq);
				// httpReq.setAttribute("destinationUrl", httpResp
				// .getDestinationUrl(false));
				dispatcher.forward(httpReq, httpResp);
			}
		} catch (OpenIDException e) {
			// present error to the user
			e.printStackTrace();
		}

		return null;
	}

	// --- processing the authentication response ---
	public Identifier verifyResponse(HttpServletRequest httpReq) {
		try {
			// extract the parameters from the authentication response
			// (which comes in as a HTTP request from the OpenID provider)
			ParameterList response = new ParameterList(
					httpReq.getParameterMap());

			// retrieve the previously stored discovery information
			DiscoveryInformation discovered = (DiscoveryInformation) httpReq
					.getSession().getAttribute("openid-disc");

			// extract the receiving URL from the HTTP request
			StringBuffer receivingURL = httpReq.getRequestURL();
			String queryString = httpReq.getQueryString();
			if (queryString != null && queryString.length() > 0)
				receivingURL.append("?").append(httpReq.getQueryString());

			// verify the response; ConsumerManager needs to be the same
			// (static) instance used to place the authentication request
			VerificationResult verification = manager.verify(
					receivingURL.toString(), response, discovered);

			// examine the verification result and extract the verified
			// identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null) {
				AuthSuccess authSuccess = (AuthSuccess) verification
						.getAuthResponse();

				if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
					MessageExtension ext = authSuccess
							.getExtension(SRegMessage.OPENID_NS_SREG);
					if (ext instanceof SRegResponse) {
						SRegResponse sregResp = (SRegResponse) ext;
						for (Iterator<?> iter = sregResp.getAttributeNames()
								.iterator(); iter.hasNext();) {
							String name = (String) iter.next();
							String value = sregResp.getParameterValue(name);
							httpReq.setAttribute(name, value);
						}
					}
				}
				if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
					FetchResponse fetchResp = (FetchResponse) authSuccess
							.getExtension(AxMessage.OPENID_NS_AX);

					List<?> aliases = fetchResp.getAttributeAliases();
					for (Iterator<?> iter = aliases.iterator(); iter.hasNext();) {
						String alias = (String) iter.next();
						List<?> values = fetchResp.getAttributeValues(alias);
						if (values.size() > 0) {
							log.debug(alias + " : " + values.get(0));
							httpReq.setAttribute(alias, values.get(0));
						}
					}
				}

				return verified; // success
			}
		} catch (OpenIDException e) {
			e.printStackTrace();
			// present error to the user
		}
		return null;
	}
}
