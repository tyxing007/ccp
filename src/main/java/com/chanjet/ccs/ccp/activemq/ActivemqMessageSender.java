package com.chanjet.ccs.ccp.activemq;

import org.springframework.jms.core.JmsTemplate;

public class ActivemqMessageSender implements MessageSender {

	private JmsTemplate jmsTemplate;
	
	public ActivemqMessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	@Override
	public void sendMessage(String message) {
		jmsTemplate.convertAndSend(message);
	}

}
