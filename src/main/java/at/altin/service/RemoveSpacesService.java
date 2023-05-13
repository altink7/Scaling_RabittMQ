package at.altin.service;
import at.altin.queue.Action;
import at.altin.queue.QueueService;
import static at.altin.queue.QueueHelper.receiveAndSendMessage;

public class RemoveSpacesService {
    QueueService queueServiceCutSecondChar = new QueueService("cut-second-char");
    QueueService queueServiceRemoveSpaces = new QueueService("remove-spaces");

    public static void main(String[] args) {
        RemoveSpacesService removeSpacesService = new RemoveSpacesService();
        removeSpacesService.start();
    }
    public void start(){
       receiveAndSendMessage(queueServiceCutSecondChar, queueServiceRemoveSpaces, Action.REMOVE_SPACES);
    }
    public static String removeSpaces(String text) {
        return text.replaceAll("\\s", "");
    }
}
