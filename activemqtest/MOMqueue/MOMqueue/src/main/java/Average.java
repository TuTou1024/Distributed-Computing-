import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.jms.Message;

class MyListener_average implements MessageListener {
	//计算均值
	int num = 0;
	double sum = 0;
	public void onMessage(Message message) {
		try {
			num++;
			String str = ((TextMessage)message).getText();
			sum += Double.valueOf(str);
			System.out.println("当前数字序号为: " + num + "当前均值为: "+sum/num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

public class Average{
	public static void main(String[] args) throws JMSException {
		String brokerURL = "tcp://localhost:61616";
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Topic topic = null;
		MessageConsumer messageConsumer = null;
		MyListener_average listener = null;


		try {
			factory = new ActiveMQConnectionFactory(brokerURL);
			connection = factory.createConnection();
			//创建连接session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//创建topic
			topic = session.createTopic("MOMqueue");
			//创建consumer
			messageConsumer = session.createConsumer(topic);
			//执行计算
			listener = new MyListener_average();
			messageConsumer.setMessageListener(listener);

			connection.start();

			System.out.println("Press any key to exit.");
			System.in.read();   // Pause
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
}