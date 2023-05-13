package at.altin.queue;

import at.altin.service.CutSecondCharService;
import at.altin.service.RemoveSpacesService;
import at.altin.service.TextReverseService;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class QueueHelper {
    static Scanner sc = new Scanner(System.in);

    /**
     * This method is uswed to publish message to queue
     */

    public static void sendMessage(QueueService sendFrom, Action methodName) {
        try {
            sendFrom.connect();
            System.out.printf("%s started\n", sendFrom.getQueueName());
            while (true) {
                System.out.print("Enter text: (to exit type 0)\n");
                String text = sc.nextLine();
                if (text.equals("0")) {
                    break;
                }

                String newMessage = getNewMessageOnAction(methodName, text);

                sendFrom.sendMessage(newMessage);
                System.out.printf("%s received: %s\n",sendFrom.getQueueName(), text);
                System.out.printf("%s reversed: %s\n",sendFrom.getQueueName(), newMessage);
            }
            sendFrom.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to publish and receive message from queue
     */

    public static void receiveAndSendMessage(QueueService receiveFrom, QueueService actualQueue, Action methodName) {
        try {
            receiveFrom.connect();
            actualQueue.connect();
            System.out.printf("%s started \n", actualQueue.getQueueName());
            while (true) {
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println("Received message:" + message);
                    try {
                        actualQueue.connect();
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }

                    String newMessage = getNewMessageOnAction(methodName, message);

                    actualQueue.sendMessage(newMessage);
                    System.out.println("-------------------------------------------");
                    System.out.println("|"+ String.format("%s",newMessage)+"|");
                    System.out.println("-------------------------------------------");
                };
                receiveFrom.getChannel().basicConsume(receiveFrom.getQueueName(), true, deliverCallback, consumerTag -> { });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getNewMessageOnAction(Action methodName, String message) {
        String newMessage;
        if (methodName.equals(Action.REMOVE_SPACES))
            newMessage = RemoveSpacesService.removeSpaces(message);
        else if (methodName.equals(Action.CUT_SECOND_CHAR))
            newMessage = CutSecondCharService.cutSecondChar(message);
        else if (methodName.equals(Action.REVERSE_TEXT))
            newMessage = TextReverseService.reverse(message);
        else
            newMessage = message;
        return newMessage;
    }
}
