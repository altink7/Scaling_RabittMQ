package at.altin.queue;

public enum Action {
    REMOVE_SPACES("removeSpaces"),
    CUT_SECOND_CHAR("cutSecondChar"),
    REVERSE_TEXT("reverseText");
    private final String action;
    Action(String action) {
        this.action = action;
    }
    public String getAction() {
        return action;
    }
}
