package org.apache.jackrabbit.j2ee;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class BootstrapConfig extends AbstractConfig {

	 private String repositoryHome;

	    private String repositoryConfig;

	    private String repositoryName;

	    private JNDIConfig jndiConfig = new JNDIConfig(this);

	    private RMIConfig rmiConfig = new RMIConfig(this);

	    public void init(Properties props) throws ServletException {
	        super.init(props);
	        jndiConfig.init(props);
	        rmiConfig.init(props);
	    }

	    public void init(ServletConfig ctx) throws ServletException {
	        super.init(ctx);
	        jndiConfig.init(ctx);
	        rmiConfig.init(ctx);
	    }

	    public String getRepositoryHome() {
	        return repositoryHome;
	    }

	    public void setRepositoryHome(String repositoryHome) {
	        this.repositoryHome = repositoryHome;
	    }

	    public String getRepositoryConfig() {
	        return repositoryConfig;
	    }

	    public void setRepositoryConfig(String repositoryConfig) {
	        this.repositoryConfig = repositoryConfig;
	    }

	    public String getRepositoryName() {
	        return repositoryName;
	    }

	    public void setRepositoryName(String repositoryName) {
	        this.repositoryName = repositoryName;
	    }

	    public JNDIConfig getJndiConfig() {
	        return jndiConfig;
	    }

	    public RMIConfig getRmiConfig() {
	        return rmiConfig;
	    }

	    public void validate() {
	        valid = repositoryName != null;
	        jndiConfig.validate();
	        rmiConfig.validate();
	    }


	    public void logInfos() {
	        super.logInfos();
	        if (jndiConfig.isValid()) {
	            jndiConfig.logInfos();
	        }
	        if (rmiConfig.isValid()) {
	            rmiConfig.logInfos();
	        }
	    }
	
}
