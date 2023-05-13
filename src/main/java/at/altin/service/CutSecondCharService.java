package at.altin.service;
import at.altin.queue.Action;
import at.altin.queue.QueueService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static at.altin.queue.QueueHelper.receiveAndSendMessage;

public class CutSecondCharService {
    QueueService queueServiceTextReverse = new QueueService("text-reverse");
    QueueService queueServiceCutSecondChar = new QueueService("cut-second-char");

    public static void main(String[] args) {
        CutSecondCharService cutSecondCharService = new CutSecondCharService();
        cutSecondCharService.start();
    }
    public void start() {
        receiveAndSendMessage(queueServiceTextReverse, queueServiceCutSecondChar, Action.CUT_SECOND_CHAR);
    }
    public static String cutSecondChar(String text) {
        return IntStream.range(0, text.length()).filter(i -> i % 2 == 0)
                .mapToObj(i -> String.valueOf(text.charAt(i))).collect(Collectors.joining());
    }

}
