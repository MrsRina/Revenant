package me.rina.racc.manager;

// Gson.
import com.google.gson.*;

// Java.
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import java.awt.*;
import java.io.*;

// Client.
import me.rina.racc.client.social.RevenantSocialUser;

/**
 *
 * @author Rina.
 * @since 05/10/2020.
 *
 **/
public class RevenantSocialManager {
    private ArrayList<RevenantSocialUser> socialList;

    public RevenantSocialManager() {
        this.socialList = new ArrayList<>();
    }

    public void addSocialUserFriend(String nameOfSocialUser) {
        this.socialList.add(new RevenantSocialUser(nameOfSocialUser, RevenantSocialUser.Context.FRIEND));
    }

    public void removeSocialUser(RevenantSocialUser socialUser) {
        this.socialList.remove(socialUser);
    }

    public void setNewContextSocialUser(String name, RevenantSocialUser.Context newContext) {
        if (getSocialUserByName(name) != null) {
            RevenantSocialUser socialUser = getSocialUserByName(name);

            socialUser.setContext(newContext);
        }
    }

    public void setNewContextSocialUserByString(String name, String newContext) {
        if (getSocialUserByName(name) != null) {
            RevenantSocialUser socialUser = getSocialUserByName(name);

            for (RevenantSocialUser.Context contexts : RevenantSocialUser.Context.values()) {
                if (contexts.name().equals(newContext)) {
                    socialUser.setContext(contexts);

                    break;
                }
            }
        }
    }


    public ArrayList<RevenantSocialUser> getSocialList() {
        return socialList;
    }

    public RevenantSocialUser getSocialUserByName(String name) {
        for (RevenantSocialUser socialUsers : getSocialList()) {
            if (socialUsers.getName().equals(name)) {
                return socialUsers;
            }
        }

        return null;
    }

    public boolean isFriend(String name) {
        if (getSocialUserByName(name) == null) {
            return false;
        }

        RevenantSocialUser socialUser = getSocialUserByName(name);

        if (socialUser.getContext() == RevenantSocialUser.Context.FRIEND) {
            return true;
        }

        return false;
    }

    public boolean isEnemy(String name) {
        if (getSocialUserByName(name) == null) {
            return false;
        }

        RevenantSocialUser socialUser = getSocialUserByName(name);

        if (socialUser.getContext() == RevenantSocialUser.Context.ENEMY) {
            return true;
        }

        return false;
    }

    public boolean isPlayerContext(String name, RevenantSocialUser.Context context) {
        if (getSocialUserByName(name) == null) {
            return false;
        }

        RevenantSocialUser socialUser = getSocialUserByName(name);

        if (socialUser.getContext() == context) {
            return true;
        }

        return false;
    }

    /**
     * System savement.
     **/
    public void onLoadSocialUserList() {
        try {
            String path = "revenant/client/";

            if (!Files.exists(Paths.get(path + "Social.json"))) {
                return;
            }

            InputStream file = Files.newInputStream(Paths.get(path + "Social.json"));

            JsonObject mainJson = new JsonParser().parse(new InputStreamReader(file)).getAsJsonObject();

            Set<Map.Entry<String, JsonElement>> entrySet = mainJson.entrySet();

            for (Map.Entry<String, JsonElement> entry : entrySet) {
                JsonObject jsonSocialUser = mainJson.get((String) entry.getKey()).getAsJsonObject();

                addSocialUserFriend(jsonSocialUser.get("name").getAsString());

                setNewContextSocialUserByString(jsonSocialUser.get("name").getAsString(), jsonSocialUser.get("context").getAsString());
            }

            file.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void onSaveSocialUserList() {
        try {
            Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

            String path = "revenant/client/";

            if (!Files.exists(Paths.get(path))) {
                Files.createDirectories(Paths.get(path));
            }

            if (!Files.exists(Paths.get(path + "Social.json"))) {
                Files.createFile(Paths.get(path + "Social.json"));
            } else {
                File file = new File(path + "Social.json");

                file.delete();

                Files.createFile(Paths.get(path + "Social.json"));
            }

            JsonObject mainJson = new JsonObject();

            for (RevenantSocialUser socialUsers : getSocialList()) {
                JsonObject jsonSocialUser = new JsonObject();

                jsonSocialUser.add("name", new JsonPrimitive(socialUsers.getName()));
                jsonSocialUser.add("context", new JsonPrimitive(socialUsers.getContext().name()));

                mainJson.add(socialUsers.getName(), jsonSocialUser);
            }

            String jsonString = gsonBuilder.toJson(new JsonParser().parse(mainJson.toString()));

            OutputStreamWriter fileOutpotStream = new OutputStreamWriter(new FileOutputStream(path + "Social.json"), "UTF-8");

            fileOutpotStream.write(jsonString);
            fileOutpotStream.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}