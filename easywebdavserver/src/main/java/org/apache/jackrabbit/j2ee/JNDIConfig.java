package org.apache.jackrabbit.j2ee;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class JNDIConfig extends AbstractConfig {

	private boolean jndiEnabled;

    private String jndiName;

    private final BootstrapConfig parentConfig;

    private Properties jndiEnv = new Properties();


    public JNDIConfig(BootstrapConfig parentConfig) {
        this.parentConfig = parentConfig;
    }


    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public boolean enabled() {
        return jndiEnabled;
    }

    public String getJndiEnabled() {
        return String.valueOf(jndiEnabled);
    }

    public void setJndiEnabled(String jndiEnabled) {
        this.jndiEnabled = Boolean.valueOf(jndiEnabled).booleanValue();
    }

    public Properties getJndiEnv() {
        return jndiEnv;
    }

    public void init(Properties props) throws ServletException {
        super.init(props);
        // add all props whose name starts with 'java.namming.' to the env
        Iterator iter = props.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            if (name.startsWith("java.naming.")) {
                jndiEnv.put(name, props.getProperty(name));
            }
        }
    }

    public void init(ServletConfig ctx) throws ServletException  {
        super.init(ctx);
        // add all params whose name starts with 'java.namming.' to the env
        Enumeration names = ctx.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.startsWith("java.naming.")) {
                jndiEnv.put(name, ctx.getInitParameter(name));
            }
        }
        // enable jndi if url is specified
        jndiEnabled = jndiEnv.containsKey("java.naming.provider.url");
    }


    public void validate() {
        if (jndiName == null) {
            jndiName = parentConfig.getRepositoryName();
        }
        valid = true;
    }
	
}
