package me.rina.racc.event;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantEvent extends RevenantEventStage {
    private boolean cancelled;

    public RevenantEvent() {
        super(Stage.PRE);

        this.cancelled = false;
    }

    public RevenantEvent(Stage defaultStage) {
        super(defaultStage);

        this.cancelled = false;
    }

    public RevenantEvent(Stage defaultStage, boolean defaultCancelament) {
        super(defaultStage);

        this.cancelled = defaultCancelament;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}