package com.sillycat.easycometd.comed.chat.filter;

import org.cometd.server.filter.DataFilter;
import org.cometd.server.filter.JSONDataFilter;

public class BadWordFilter extends JSONDataFilter {

	protected Object filterString(String string) {
		if (string.indexOf("dang") >= 0){
			throw new DataFilter.Abort();
		}
		return string;
	}
	
}
