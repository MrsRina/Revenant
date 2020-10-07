package me.rina.racc.client;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantCommand {
    private String command;
    private String description;

    public RevenantCommand(String command, String description) {
        this.command     = command;
        this.description = description;
    }

    /**
     * @param args - Arguments message.
     **/
    public boolean onReceive(String[] args) {
        return true;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}