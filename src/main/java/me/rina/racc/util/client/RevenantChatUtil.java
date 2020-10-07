package me.rina.racc.util.client;

// Minecraft.
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.Minecraft;

// Mojang.
import com.mojang.realmsclient.gui.ChatFormatting;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 26/09/2020.
 *
 **/
public class RevenantChatUtil {
    /**
     * Color static variables:
     * - Make easy color words.
     **/
    public static final ChatFormatting RESET        = ChatFormatting.RESET;
    public static final ChatFormatting BLACK        = ChatFormatting.BLACK;
    public static final ChatFormatting RED          = ChatFormatting.RED;
    public static final ChatFormatting AQUA         = ChatFormatting.AQUA;
    public static final ChatFormatting BLUE         = ChatFormatting.BLUE;
    public static final ChatFormatting GOLD         = ChatFormatting.GOLD;
    public static final ChatFormatting GRAY         = ChatFormatting.GRAY;
    public static final ChatFormatting WHITE        = ChatFormatting.WHITE;
    public static final ChatFormatting GREEN        = ChatFormatting.GREEN;
    public static final ChatFormatting YELLOW       = ChatFormatting.YELLOW;
    public static final ChatFormatting DARK_RED     = ChatFormatting.DARK_RED;
    public static final ChatFormatting DARK_AQUA    = ChatFormatting.DARK_AQUA;
    public static final ChatFormatting DARK_BLUE    = ChatFormatting.DARK_BLUE;
    public static final ChatFormatting DARK_GRAY    = ChatFormatting.DARK_GRAY;
    public static final ChatFormatting DARK_GREEN   = ChatFormatting.DARK_GREEN;
    public static final ChatFormatting DARK_PURPLE  = ChatFormatting.DARK_PURPLE;
    public static final ChatFormatting LIGHT_PURPLE = ChatFormatting.LIGHT_PURPLE;

    public static void sendPlayerMessage(String message) {
        if (Revenant.getMinecraft().player != null) {
            Revenant.getMinecraft().player.connection.sendPacket(new CPacketChatMessage(message));
        }
    }

    public static void sendClientMessage(String message) {
        if (Revenant.getMinecraft().player != null) {
            Revenant.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
        }
    }
}