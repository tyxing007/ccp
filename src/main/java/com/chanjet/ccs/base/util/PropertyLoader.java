package com.chanjet.ccs.base.util;

import java.util.ResourceBundle;

public class PropertyLoader {

	private PropertyLoader() {}
	
	public static String getStringProperty(String resourceFile, String property) {
		ResourceBundle bundle = ResourceBundle.getBundle(resourceFile);
		return bundle.getString(property);
	}
}
