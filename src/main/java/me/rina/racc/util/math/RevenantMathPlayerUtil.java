package me.rina.racc.util.math;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 26/09/2020.
 *
 **/
public class RevenantMathPlayerUtil {
    public static double[] getPosition() {
        return new double[] {
                Revenant.getMinecraft().player.posX,
                Revenant.getMinecraft().player.posY,
                Revenant.getMinecraft().player.posZ
        };
    }
}