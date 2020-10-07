package me.rina.racc.event;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantEventStage {
    private Stage stage;

    public RevenantEventStage() {
        this.stage = Stage.PRE;
    }

    public RevenantEventStage(Stage defaultStage) {
        this.stage = defaultStage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public enum Stage {
        PRE, POST;
    }
}