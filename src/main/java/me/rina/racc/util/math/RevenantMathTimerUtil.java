package me.rina.racc.util.math;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 26/09/2020.
 *
 **/
public class RevenantMathTimerUtil {
    private long brute;
    private long time;

    public RevenantMathTimerUtil() {
        this.brute = -1;
    }

    public RevenantMathTimerUtil(long vLong) {
        this.brute = vLong;
    }

    public void setBruteTimer(long vLong) {
        this.brute = vLong;
    }

    public void resetTimer() {
        this.brute = System.currentTimeMillis();
    }

    public long getBruteTimer() {
        return brute;
    }

    /**
     * @param ms Mile seconds!!
     **/
    public boolean isPassedMS(double ms) {
        return System.currentTimeMillis() - this.brute >= ms;
    }

    /**
     * @param si Seconds.
     **/
    public boolean isPassedSI(double si) {
        return System.currentTimeMillis() - this.brute >= (si * 1000);
    }
}