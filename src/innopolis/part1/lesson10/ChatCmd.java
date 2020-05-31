package innopolis.part1.lesson10;

public enum ChatCmd {
    UNICAST("-u"),
    QUIT("-quit");

    private final String cmd;

    ChatCmd(String command) {
        this.cmd = command;
    }

    public String getCmd() {
        return cmd;
    }
}