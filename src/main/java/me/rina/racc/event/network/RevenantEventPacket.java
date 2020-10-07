package me.rina.racc.event.network;

// Minecraft.
import net.minecraft.network.Packet;

// Event.
import me.rina.racc.event.RevenantEventStage;
import me.rina.racc.event.RevenantEvent;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantEventPacket extends RevenantEvent {
    private Packet packet;

    public RevenantEventPacket(RevenantEventStage.Stage stage, Packet packet) {
        super(stage);

        this.packet = packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public static class Send extends RevenantEventPacket {
        public Send(RevenantEventStage.Stage stage, Packet packet) {
            super(stage, packet);
        }
    }

    public static class Receive extends RevenantEventPacket {
        public Receive(RevenantEventStage.Stage stage, Packet packet) {
            super(stage, packet);
        }
    }
}