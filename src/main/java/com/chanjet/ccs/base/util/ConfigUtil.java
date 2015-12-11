package com.chanjet.ccs.base.util;

import java.util.ResourceBundle;

/**
 * properties资源获取类
 * @author huangx
 *
 */
public class ConfigUtil {
	
	public static String  getConfigContent(String configName,String key){
	
		ResourceBundle resource = ResourceBundle.getBundle(configName);
		
		return resource.getString(key)==null?"":resource.getString(key);
	}

}
