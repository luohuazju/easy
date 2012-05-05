package org.apache.jackrabbit.j2ee;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.collections.BeanMap;
import org.apache.jackrabbit.util.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfig {

	/**
     * default logger
     */
    private static final Logger log = LoggerFactory.getLogger(AbstractConfig.class);

    protected boolean valid;

    private BeanMap map = new BeanMap(this);

    /**
     * Initializes the configuration with values from the given properties
     * @param props the configuration properties
     */
    public void init(Properties props) throws ServletException {
        Iterator iter = props.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            String mapName = toMapName(name, '.');
            try {
                if (map.containsKey(mapName)) {
                    map.put(mapName, props.getProperty(name));
                }
            } catch (Exception e) {
                throw new ServletExceptionWithCause(
                        "Invalid configuration property: " + name, e);
            }
        }
    }

    public void init(ServletConfig ctx) throws ServletException {
        Enumeration names = ctx.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String mapName = toMapName(name, '-');
            try {
                if (map.containsKey(mapName)) {
                    map.put(mapName, ctx.getInitParameter(name));
                }
            } catch (Exception e) {
                throw new ServletExceptionWithCause(
                        "Invalid servlet configuration option: " + name, e);
            }
        }
    }

    public String toMapName(String name, char delim) {
        StringBuffer ret = new StringBuffer();
        String[] elems = Text.explode(name, delim);
        ret.append(elems[0]);
        for (int i=1; i<elems.length; i++) {
            ret.append(elems[i].substring(0, 1).toUpperCase());
            ret.append(elems[i].substring(1));
        }
        return ret.toString();
    }

    public void validate() {
        valid = true;
    }

    public boolean isValid() {
        return valid;
    }

    public void logInfos() {
        log.info("Configuration of {}", Text.getName(this.getClass().getName(), '.'));
        log.info("----------------------------------------------");
        Iterator iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            log.info("  {}: {}", name, map.get(name));
        }
        log.info("----------------------------------------------");
    }
	
}
