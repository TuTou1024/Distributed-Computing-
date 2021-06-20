import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
class MyListener_Error implements MessageListener{

    int err_num = 0;
    ArrayList arrayList = new ArrayList();
    BigDecimal variance = new BigDecimal("0");
    BigDecimal avg = new BigDecimal("0");
    //开根号
    public BigDecimal sqrt (BigDecimal decimal){
        MathContext mc = new MathContext(100, RoundingMode.HALF_UP);//精度为100
        if(decimal.compareTo(BigDecimal.ZERO)==0) {
            return new BigDecimal("0");
        }else {
            BigDecimal x=decimal;
            int cnt=0;
            while(cnt<100){
                x=(x.add(decimal.divide(x,mc))).divide(BigDecimal.valueOf(2.0),mc);
                cnt++;
            }
            return x;
        }
    }
    @Override
    public void onMessage(Message message) {
        BigDecimal str = null;
        try {
            BigDecimal sum = new BigDecimal("0");
            BigDecimal sum_var = new BigDecimal("0");
            str = new BigDecimal(((TextMessage)message).getText());
            arrayList.add(str);
            for (int i = 0; i < arrayList.size(); i++ ){
                BigDecimal d1 = (BigDecimal)arrayList.get(i);
                sum = sum.add(d1);
            }
            avg = sum.divide(BigDecimal.valueOf(arrayList.size()),20,BigDecimal.ROUND_UP);
            for (int i = 0; i < arrayList.size(); i++ ){
                BigDecimal d2 = (BigDecimal)arrayList.get(i);
                sum_var = sum_var.add((d2.subtract(avg)).multiply(d2.subtract(avg)));
            }

            variance = sum_var.divide(BigDecimal.valueOf(arrayList.size()),20,BigDecimal.ROUND_UP);

            BigDecimal sq = sqrt(variance);
            //μ-3σ
            BigDecimal xx = avg.subtract(sq.multiply(new BigDecimal("3")));
            //μ+3σ
            BigDecimal yy = avg.add(sq.multiply(new BigDecimal("3")));
            if (str.compareTo(xx)<0||str.compareTo(yy)>0){
                err_num++;
            }
            System.out.println("异常点数为: "+err_num);
        } catch (JMSException e) {
            e.printStackTrace();
        }



    }
}
public class Error {
    public static void main(String[] args) throws JMSException {
        String brokerURL = "tcp://localhost:61616";
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Topic topic = null;
        MessageConsumer messageConsumer = null;
        MyListener_Error listener = null;


        try {
            factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();
            //创建连接session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic("MOMqueue");
            //创建consumer
            messageConsumer = session.createConsumer(topic);
            //执行计算
            listener = new MyListener_Error();

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
