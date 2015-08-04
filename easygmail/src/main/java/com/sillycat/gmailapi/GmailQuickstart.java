package com.sillycat.gmailapi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;

public class GmailQuickstart {

	/** Application name. */
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"),
			".credentials/gmail-api-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart. */
	// private static final List<String> SCOPES = Arrays
	// .asList(GmailScopes.GMAIL_LABELS);
//	private static final List<String> SCOPES = Arrays.asList(
//			GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_COMPOSE,
//			GmailScopes.GMAIL_INSERT, GmailScopes.GMAIL_MODIFY,
//			GmailScopes.GMAIL_READONLY, GmailScopes.MAIL_GOOGLE_COM);
	
	private static final List<String> SCOPES = Arrays.asList(
			GmailScopes.GMAIL_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = GmailQuickstart.class
				.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to "
				+ DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Gmail client service.
	 * 
	 * @return an authorized Gmail client service
	 * @throws IOException
	 */
	public static Gmail getGmailService() throws IOException {
		Credential credential = authorize();
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public static void main(String[] args) throws IOException {
		// Build a new authorized API client service.
		Gmail service = getGmailService();

		// Print the labels in the user's account.
		String user = "me";
//		ListLabelsResponse listResponse = service.users().labels().list(user)
//				.execute();
//		List<Label> labels = listResponse.getLabels();
//		if (labels.size() == 0) {
//			System.out.println("No labels found.");
//		} else {
//			System.out.println("Labels:");
//			for (Label label : labels) {
//				System.out.printf("- %s\n", label.getName());
//			}
//		}

		// List all the messages related to keywords
		ListMessagesResponse listMessageResponse = service.users().messages()
				.list(user).setQ("job.craigslist.org").execute();
		List<Message> messages = new ArrayList<Message>();
		while (listMessageResponse.getMessages() != null) {
			messages.addAll(listMessageResponse.getMessages());
			if (listMessageResponse.getNextPageToken() != null) {
				String pageToken = listMessageResponse.getNextPageToken();
				listMessageResponse = service.users().messages().list(user).setQ("job.craigslist.org")
						.setPageToken(pageToken).execute();
			} else {
				break;
			}
		}

		for (Message message : messages) {
			System.out.println(message.toPrettyString());
		}

		//Get the Message Attach Info
		Message message = service.users().messages().get(user, "14eda36adbc0789e").execute();
		System.out.println("Message snippet: " + message.getSnippet());
		List<MessagePart> parts = null;
		if(message.getPayload() != null){
			parts = message.getPayload().getParts();
			if(parts != null && parts.size() > 0){
				for(int i = 0; i< parts.size();i++){
					MessagePart part = parts.get(i);
					System.out.println("====== part " + i + "==========");
					System.out.println("filename: " + part.getFilename());
					System.out.println("mimetype: " + part.getMimeType());
					if(part.getMimeType().equalsIgnoreCase("application/pdf")){
						System.out.println("attachmentId : " + part.getBody().getAttachmentId());
					}
				}
			}
		}
		
		//Get the attachment file
		String attachmentId = "ANGjdJ-T4bVPdCT3gQSW-iuq8Txjofxjo3eNjk5qFawdotTon6Rkv2mjH-8EUehiH71OZJsN_mTFA5cHj05JGvuN3_Ev05_26bxUXvO4NaYxw-aExinNWZbUdpgSYqHq5NmS6QVnOKBbsrwdTau1kGzB5JC1qYNuXMKVRuPvrL49RcYEfBR8iIzHDvskqY8bVcyUHPTSCu0mycCf32-eogP7muMu7TO2MjF1HIYv50kCGG2edaSKrBD_miQU6aTqQJVhDJ4BVP7LiEB2bwkZssRuF8esb1Obu7N702e6Gw";
		MessagePartBody attachPart = service.users().messages().attachments().get(user, "14eda36adbc0789e", attachmentId).execute();
		byte[] fileByteArray = Base64.decodeBase64(attachPart.getData());
        FileOutputStream fileOutFile =
            new FileOutputStream("/Users/carl/data/" + "attach1.pdf");
        fileOutFile.write(fileByteArray);
        fileOutFile.close();
	}

}
