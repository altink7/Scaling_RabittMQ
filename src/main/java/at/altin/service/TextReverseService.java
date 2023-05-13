package at.altin.service;
import at.altin.queue.Action;
import at.altin.queue.QueueService;
import static at.altin.queue.QueueHelper.sendMessage;

public class TextReverseService {
    QueueService queueServiceTextReverse = new QueueService("text-reverse");

    public static void main(String[] args) {
        TextReverseService textReverseService = new TextReverseService();
        textReverseService.start();
    }
    public void start() {
        sendMessage(queueServiceTextReverse, Action.REVERSE_TEXT);
    }
    public static String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}
