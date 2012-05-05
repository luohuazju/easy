package org.apache.jackrabbit.j2ee;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DerbyShutdown implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
	}

	public void contextDestroyed(ServletContextEvent event) {
		ClassLoader loader = DerbyShutdown.class.getClassLoader();

		// Deregister all JDBC drivers loaded from this webapp
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			// Check if this driver comes from this webapp
			if (driver.getClass().getClassLoader() == loader) {
				try {
					DriverManager.deregisterDriver(driver);
				} catch (SQLException ignore) {
				}
			}
		}

		// Explicitly tell Derby to release all remaining resources.
		// Use reflection to avoid problems when the Derby is not used.
		try {
			Class<?> monitorClass = loader
					.loadClass("org.apache.derby.iapi.services.monitor.Monitor");
			if (monitorClass.getClassLoader() == loader) {
				Method getMonitorMethod = monitorClass.getMethod("getMonitor",
						new Class<?>[0]);
				Object monitor = getMonitorMethod.invoke(null, new Object[0]);
				if (monitor != null) {
					Method shutdownMethod = monitor.getClass().getMethod(
							"shutdown", new Class<?>[0]);
					shutdownMethod.invoke(monitor, new Object[0]);
				}
			}
		} catch (Exception ignore) {
		}
	}

}
