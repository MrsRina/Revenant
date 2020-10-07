package me.rina.racc.client.mixins;

// Minecraft.
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

public interface RevenantIMinecraft
{
    Timer getTimer();

    void setSession(Session session);

    Session getSession();

    void setRightClickDelayTimer(int delay);

    void clickMouse();

    ServerData getCurrentServerData();
}
