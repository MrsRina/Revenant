package me.rina.racc.client.social;

/**
 *
 * @author Rina.
 * @since 05/10/2020.
 *
 **/
public class RevenantSocialUser {
    private String name;

    private Context context;

    public RevenantSocialUser(String name, Context context) {
        this.name    = name;
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public Context getContext() {
        return context;
    }

    public enum Context {
        FRIEND, ENEMY;
    }
}