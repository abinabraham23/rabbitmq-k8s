import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RecvK8s {

    private final static String QUEUE_NAME = "hello";
  
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // factory.setHost("localhost");
        factory.setHost("192.168.49.2");
        factory.setPort(30672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
  }