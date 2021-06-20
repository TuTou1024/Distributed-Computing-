import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

class MyListener_Variance implements MessageListener {

    int N = 0;

    ArrayList arrayList = new ArrayList();

    //初始化浮点数用BigDecimal（“0”）
    BigDecimal variance = new BigDecimal("0");
    BigDecimal avg = new BigDecimal("0");
    MyListener_Variance(int n){
        this.N = n;
    }

    @Override
    public void onMessage(Message message) {
        try {
            //存放传进来的数据
            BigDecimal str = new BigDecimal(((TextMessage)message).getText());
            arrayList.add(str);
            BigDecimal sum =new BigDecimal("0");
            BigDecimal variances =new BigDecimal("0");
            //计算均值
            if (N<arrayList.size()){
                for(int i = arrayList.size()-1; i >= arrayList.size()-N; i--){
                    sum = sum.add((BigDecimal)arrayList.get(i));
                }
                avg = sum.divide(BigDecimal.valueOf(N), 20, BigDecimal.ROUND_UP);

                //计算方差
                for(int i = arrayList.size()-1; i >= arrayList.size()-N; i--){
                    BigDecimal t = (BigDecimal)arrayList.get(i);
                    variances = variances.add((t.subtract(avg)).multiply(t.subtract(avg)));
                }
                variance = variances.divide(BigDecimal.valueOf(N), 20, BigDecimal.ROUND_UP);
                System.out.println("方差为: "+variance);
            }
            else{
                System.out.println("数字还未传输到");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
public class Variance {
    public static void main(String[] args) throws JMSException {
        String brokerURL = "tcp://localhost:61616";
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Topic topic = null;
        MessageConsumer messageConsumer = null;
        MyListener_Variance listener = null;


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
            System.out.println("请输入N:");
            Scanner scanner = new Scanner(System.in);
            int N = scanner.nextInt();
            listener = new MyListener_Variance(N);
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
