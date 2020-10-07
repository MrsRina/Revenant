package me.rina.racc.manager;

// Java.
import java.util.ArrayList;

// Commands.
import me.rina.racc.client.commands.*;

// Client.
import me.rina.racc.client.RevenantCommand;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantCommandManager {
    private String managerTag;

    private ArrayList<RevenantCommand> commandList;

    private String prefix;

    public RevenantCommandManager(String managerTag) {
        this.managerTag  = managerTag;
        this.commandList = new ArrayList<>();

        this.prefix = ".";

        addCommandInList(new RevenantCommandToggle());
    }

    public void addCommandInList(RevenantCommand command) {
        this.commandList.add(command);
    }

    public String[] verifyCommandAndGetArguments(String message) {
        String[] args = {};

        if (message.startsWith(getPrefix())) {
            args = message.replaceFirst(getPrefix(), "").split(" ");
        }

        return args;
    }

    public boolean hasPrefix(String message) {
        return message.startsWith(getPrefix());
    }

    public ArrayList<RevenantCommand> getCommandList() {
        return commandList;
    }

    public RevenantCommand getCommandByCommand(String command) {
        for (RevenantCommand commands : getCommandList()) {
            if (commands.getCommand().equals(command)) {
                return commands;
            }
        }

        return null;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setManagerTag(String managerTag) {
        this.managerTag = managerTag;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getManagerTag() {
        return managerTag;
    }
}