package at.altin.queue;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

/**
 * This class holds base queue service methods.
 */
public class QueueService {
    protected static final String RABBITMQ_HOST = "localhost";
    protected final ConnectionFactory factory;
    protected Connection connection;
    protected Channel channel;
    protected String queueName;

    public QueueService(String queueName) {
        this.queueName = queueName;

        factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
    }

    /**
     * Connect to RabbitMQ and create a queue with the specified name
     * @throws IOException if there's an issue creating the queue
     * @throws TimeoutException if there's a timeout connecting to RabbitMQ
     */
    public void connect() throws IOException, TimeoutException {
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
    }

    /**
     * Close the connection to RabbitMQ
     * @throws IOException if there's an issue closing the connection
     * @throws TimeoutException if there's a timeout closing the connection
     */
    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public void sendMessage(String message) {
        try {
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("Sent message: " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
