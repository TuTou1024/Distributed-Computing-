import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PostMessage {

    private static String brokerURL = "tcp://115.29.151.24:61616";
    private static ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
	private Topic topic;
    
    public PostMessage(String topicName) throws JMSException {
		
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
    
	public static void main(String[] args) throws JMSException {
    	Scanner s = new Scanner(System.in);
		PostMessage postproxy = new PostMessage("CHAT");
		String nickname = null;
		String message = null;
		System.out.println("Please input your name: ");
		nickname = s.nextLine();
		System.out.println("Hi, " + nickname + "!");
		while(true) {
			System.out.print("Please input your mesage: ");
			message = s.nextLine();
			if (message.equals("exit")) break;
			postproxy.sendMessage(nickname + ": " + message);
			System.out.println(">>>" + message); 
		}
        
        postproxy.close();

	}
	
    public void sendMessage(String msg) throws JMSException {  
        Message message = session.createTextMessage(msg);
		producer.send(message);
    }	

}
