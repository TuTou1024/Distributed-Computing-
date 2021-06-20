import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import com.sun.glass.ui.Robot;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.math.*;
import java.util.*;
import java.awt.*;

public class Publisher {

    private static String brokerURL = "tcp://localhost:61616";
    private static ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
	private Topic topic;
    
    public Publisher(String topicName) throws JMSException {
		
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		topic = session.createTopic(topicName);
        producer = session.createProducer(topic);
		
		connection.start();
    }    
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
	public static void main(String[] args) throws JMSException, InterruptedException {
    	Publisher publisher = new Publisher("MOMqueue");
    	System.out.println("-------------------开始产生随机数--------------------");
        int i=0;
    	while(i<100000){

    	    publisher.sendMessage();
            Thread.currentThread().sleep(10000);
            i++;
        }

        publisher.close();

	}
	
    public void sendMessage() throws JMSException {
        //产生随机数
        Random r =new Random();
        double num = r.nextGaussian();
        BigDecimal bigDecimal = BigDecimal.valueOf(num);
        Message message = session.createTextMessage(bigDecimal.toString());
        System.out.println(bigDecimal);
        producer.send(message);
    }	

}
