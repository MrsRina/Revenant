package me.rina.racc.client.commands;

// Client.
import me.rina.racc.client.RevenantCommand;
import me.rina.racc.client.RevenantModule;

// Util.
import me.rina.racc.util.client.RevenantChatUtil;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 26/09/2020.
 *
 **/
public class RevenantCommandToggle extends RevenantCommand {
    public RevenantCommandToggle() {
        super("t", "Toggle modules.");
    }

    @Override
    public boolean onReceive(String[] args) {
        String argumentModuleName = null;

        if (args.length > 1) {
            argumentModuleName = args[1];
        }

        if (argumentModuleName == null) {
            return false;
        }

        RevenantModule moduleRequested = Revenant.getModuleManager().getModuleByTag(argumentModuleName);

        if (moduleRequested == null) {
            RevenantChatUtil.sendClientMessage(Revenant.CHAT + RevenantChatUtil.RED + "There is no a module called " + argumentModuleName);

            return true;
        }

        if (moduleRequested.isNotify()) {
            moduleRequested.toggle();
        } else {
            moduleRequested.toggle();

            RevenantChatUtil.sendClientMessage(Revenant.CHAT + moduleRequested.getTag() + " toggled");
        }

        return true;
    }
}