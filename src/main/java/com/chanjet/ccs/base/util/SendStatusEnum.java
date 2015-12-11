/**
 * 2015年12月11日上午10:26:01
 * wugqa@chanjet.com
 */
package com.chanjet.ccs.base.util;

/**
 * 2015年12月11日上午10:26:01
 * wugqa@chanjet.com
 */
public enum SendStatusEnum {
	sended(1),unsend(0);
	
	int value;
	
	SendStatusEnum(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}
}
