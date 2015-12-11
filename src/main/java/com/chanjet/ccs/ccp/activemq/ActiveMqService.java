package com.chanjet.ccs.ccp.activemq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActiveMqService {

    static Log log = LogFactory.getLog(ActiveMqService.class);

    private String queueName;
    private int threadNum = 20;
    private final boolean init;
    private static String hosts;
    private static String mqUserName;
    private static String mqPassword;

    public static final ActiveMqService MQ_SERVICE = new ActiveMqService();
    public static final ActiveMQConnectionFactory MQ_FACTORY = new ActiveMQConnectionFactory(hosts);
    public static ExecutorService ES = Executors.newFixedThreadPool(MQ_SERVICE.getThreadNum());
    public static ActiveMQConnection MQ_CONNECTION;
  
    
    private boolean init() {
    	hosts = ActiveMqConf.getString("mq.server.hosts");
    	mqUserName = ActiveMqConf.getString("mq.server.user.name");
    	mqPassword = ActiveMqConf.getString("mq.server.user.pwd");
        String tNum = ActiveMqConf.getString("mq.server.thread.num");
        String qName = ActiveMqConf.getString("mq.queue.name");
        
        if (qName == null || qName.equals("")) {
            log.error("plz check <<mq.queue.name>> in amqconf.properties");
            return false;
        } else {
            queueName = qName;
        }

        try {
            threadNum = Integer.parseInt(tNum);
            if (threadNum <= 0) threadNum = 20;
        } catch (Exception e) {
            log.error("plz check <<mq.server.thread.num>> in amqconf.properties");
        }
        
        return true;
    }

    private ActiveMqService() {
        init = init();
    }

    public synchronized static ActiveMQConnection createMQConnection() throws JMSException {
          
        
        if(!MQ_SERVICE.isInit()) {
            log.error(ActiveMqConf.getString("mq.error.check.properties"));
            return null;
        }
        
        if(MQ_CONNECTION != null) {
            ES.shutdown();
            ES = Executors.newFixedThreadPool(MQ_SERVICE.getThreadNum());
        }
        
        try {
          MQ_CONNECTION = (ActiveMQConnection) MQ_FACTORY.createConnection(mqUserName, mqPassword); 
        } catch (JMSException e) {
            log.error(ActiveMqConf.getString("mq.error.connection.fail"), e);
            MQ_CONNECTION = null;
        }
        
        return MQ_CONNECTION;
    }
    
    public static ActiveMQConnection getMQConnection() throws JMSException {
        if(MQ_CONNECTION == null || !MQ_CONNECTION.isStarted()||MQ_CONNECTION.isClosed()){
            createMQConnection();
        }
        return MQ_CONNECTION;
    }
    
    public static void close() throws Exception {
        if(MQ_CONNECTION != null){
            try {
                MQ_CONNECTION.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if(ES != null){
            try {
                ES.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean sendMessage(String msg) throws JMSException {
        try {
          ActiveMQConnection con = ActiveMqService.getMQConnection();
            if( con != null){
                String qName = MQ_SERVICE.getQueueName();
                
                con.start();
                Session session = con.createSession(false, Session.CLIENT_ACKNOWLEDGE);
               
                Destination queue = session.createQueue(qName);

                MessageProducer producer = session.createProducer(queue);
                producer.setDeliveryMode( DeliveryMode.PERSISTENT); 
                
                producer.send(session.createTextMessage(msg));
                                  
                log.info(" [x] Sent '" + msg + "'");
                session.close();
                con.close();
            } else {
                return false;
            }
        } catch (JMSException e) {
            log.error(ActiveMqConf.getString("mq.error.message.send.fail"), e);
            try {
                ES.shutdown();
                MQ_CONNECTION.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
        return true;
    }
    
    public boolean sendMessages(String[] msgs) throws JMSException{
        for(int i=0; i<msgs.length; i++){
            this.sendMessage(msgs[i]);
        }
        return true;
    }
    
    public String getQueueName() {
        return queueName;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public boolean isInit() {
        return init;
    }
    
}
