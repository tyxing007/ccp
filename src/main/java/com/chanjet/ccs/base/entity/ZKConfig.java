package com.chanjet.ccs.base.entity;

import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.util.PropertyLoader;

public class ZKConfig {

	private String connectionString;
	private String rootNode;
	private String version;
	
	public ZKConfig() {
		this.connectionString = PropertyLoader.getStringProperty(ConstantsCcs.resourceFile, ConstantsCcs.connectionString);
		this.rootNode = PropertyLoader.getStringProperty(ConstantsCcs.resourceFile, ConstantsCcs.rootNode);
		this.version = PropertyLoader.getStringProperty(ConstantsCcs.resourceFile, ConstantsCcs.version);
	}
	
	public String getConnectionString() {
		return connectionString;
	}
	
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	public String getRootNode() {
		return rootNode;
	}
	
	public void setRootNode(String rootNode) {
		this.rootNode = rootNode;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
}
