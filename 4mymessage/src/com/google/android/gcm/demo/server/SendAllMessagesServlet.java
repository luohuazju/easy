package com.google.android.gcm.demo.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class SendAllMessagesServlet extends BaseServlet {

	private static final long serialVersionUID = -5280431666956740669L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<String> devices = Datastore.getDevices();
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			Queue queue = QueueFactory.getQueue("gcm");
			if (devices.size() == 1) {
				String device = devices.get(0);
				queue.add(withUrl("/send").param(
						SendMessageServlet.PARAMETER_DEVICE, device));
				//send the message via queue
				status = "Single message queued for registration id " + device;
			} else {
				// send a multicast message using JSON
				// must split in chunks of 1000 devices (GCM limit)
				int total = devices.size();
				List<String> partialDevices = new ArrayList<String>(total);
				int counter = 0;
				int tasks = 0;
				for (String device : devices) {
					counter++;
					partialDevices.add(device);
					int partialSize = partialDevices.size();
					if (partialSize == Datastore.MULTICAST_SIZE
							|| counter == total) {
						String multicastKey = Datastore
								.createMulticast(partialDevices);
						logger.fine("Queuing " + partialSize
								+ " devices on multicast " + multicastKey);
						TaskOptions taskOptions = TaskOptions.Builder
								.withUrl("/send")
								.param(SendMessageServlet.PARAMETER_MULTICAST,
										multicastKey).method(Method.POST);
						queue.add(taskOptions);
						partialDevices.clear();
						tasks++;
					}
				}
				status = "Queued tasks to send " + tasks
						+ " multicast messages to " + total + " devices";
			}
		}
		req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
		getServletContext().getRequestDispatcher("/home").forward(req, resp);
	}

}
