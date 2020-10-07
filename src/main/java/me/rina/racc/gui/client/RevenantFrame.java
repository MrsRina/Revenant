package me.rina.racc.gui.client;

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

// Turok.
import me.rina.turok.math.TurokRect;

// Main GUI.
import me.rina.racc.gui.RevenantMainGUI;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public abstract class RevenantFrame {
    public TurokRect rect;
    public TurokRect rectName;
    public TurokRect rectCheckbox;

    public int moveX, moveY, moveFrameX, moveFrameY;

    /* We use public and not a boolean method */
    public boolean isRendering;
    public boolean isMouseOver;
    public boolean isMouseClicked;
    public boolean isFrameOpen;
    public boolean isFrameDragging;

    public RevenantFrame(String rectTag) {
        this.rect         = new TurokRect(rectTag, 0, 0);
        this.rectName     = new TurokRect(rectTag, 0, 0);
        this.rectCheckbox = new TurokRect(rectTag + "Checkbox", 0, 0);

        this.moveX = 0;
        this.moveY = 0;

        this.moveFrameX = 0;
        this.moveFrameY = 0;

        this.isRendering     = false;
        this.isMouseOver     = false;
        this.isMouseClicked  = false;
        this.isFrameOpen     = false;
        this.isFrameDragging = false;
    }

    public void resetAllEventBase() {
        this.isFrameDragging = false;
        this.isMouseOver     = false;
        this.isMouseClicked  = false;
    }

    public void resetAllCustomEventBase() {}
    public void disableAllCustomEventBase() {}

    public void onUpdateMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                this.isMouseClicked = false;

                if (this.isMouseOver) {
                    Revenant.getGUIClient().refresh();

                    this.isMouseClicked = true;

                    this.moveX = mousePositionX - this.rectName.getX();
                    this.moveY = mousePositionY - this.rectName.getY();

                    this.moveFrameX = mousePositionX - this.rect.getX();
                    this.moveFrameY = mousePositionY - this.rect.getY();
                }

                if (this.rectCheckbox.collide(mousePositionX, mousePositionY)) {
                    Revenant.getGUIClient().refresh();

                    // We dont call dragg.
                    this.isMouseClicked = false;

                    this.isFrameOpen = !this.isFrameOpen;
                }
            }

            if (mouseButtonDown == 1) {
                if (this.rectName.collide(mousePositionX, mousePositionY)) {
                    Revenant.getGUIClient().refresh();

                    this.isFrameOpen = !this.isFrameOpen;
                }
            }
        }
    }

    public void onRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                this.isMouseClicked = false;
            }
        }
    }

    public void onUpdateMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        if (this.isRendering) {
            if (mouseButtonUp == 0) {
                this.isMouseClicked = false;
            }
        }
    }

    public void onRefreshMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        if (this.isRendering) {
            if (mouseButtonUp == 0) {
                this.isMouseClicked = false;
            }
        }
    }

    public void onUpdateEventRender(int mousePositionX, int mousePositionY) {
        if (this.isRendering) {
            this.rect.setX(this.rectName.getX() + 1);
            this.rect.setY(this.rectName.getY() + this.rectName.getHeight());

            if (this.rectName.collide(mousePositionX, mousePositionY)) {
                this.isMouseOver = true;
            } else {
                this.isMouseOver = false;
            }

            if (this.isMouseClicked) {
                this.isFrameDragging = true;
            } else {
                this.isFrameDragging = false;
            }

            if (this.isFrameDragging) {
                this.rectName.setX(mousePositionX - this.moveX);
                this.rectName.setY(mousePositionY - this.moveY);

                this.rect.setX(mousePositionX - this.moveFrameX);
                this.rect.setY(mousePositionY - this.moveFrameY);
            }
        }
    }

    public void onRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {}

    /* Refersh external methods */
    public void onCustomRefreshKeyPressed(char vChar, int keyCode) {}
    public void onCustomRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {}
    public void onCustomRefreshMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {}
    public void onCustomRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {}

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public TurokRect getRect() {
        return rect;
    }

    public TurokRect getRectName() {
        return rectName;
    }

    public TurokRect getRectCheckbox() {
        return rectCheckbox;
    }

    /**
     * Verify the frame on list frame and send a true.
     **/
    public boolean verifyAnyConnectionWithFrame(int mousePositionX, int mousePositionY) {
        boolean returnTrue = false;

        if (this.isMouseOver) {
            returnTrue = true;
        }

        if (this.isMouseClicked) {
            returnTrue = true;
        }

        if (this.isFrameDragging) {
            returnTrue = true;
        }

        if (this.rect.collide(mousePositionX, mousePositionY)) {
            returnTrue = true;
        }

        return returnTrue;
    }

    public void onUpdateRectCheckbox() {
        this.rectCheckbox.setWidth(8);
        this.rectCheckbox.setHeight(8);

        this.rectCheckbox.setX(this.rectName.getX() + this.rectName.getWidth() - this.rectCheckbox.getWidth() - 2);
        this.rectCheckbox.setY(this.rectName.getY() + 2);
    }

    /**
     * @param vChar   - Character typed.
     * @param keyCode - KeyCode listened.
     **/
    public void onKeyPressed(char vChar, int keyCode) {}

    /**
     * @param mousePositionX  - Mouse position x.
     * @param mousePositionY  - Mouse position y.
     * @param mouseButtonDown - Mouse button down key.
     **/
    public void onMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {}

    /**
     * @param mousePositionX - Mouse position x.
     * @param mousePositionY - Mouse position y.
     * @param mouseButtonUp  - Mouse button up key.
     **/
    public void onMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {}

    /**
     * @param mousePositionX - Mouse position x.
     * @param mousePositionY - Mouse position y.
     * @param partialTicks   - Render partial ticks.
     **/
    public void onRender(int mousePositionX, int mousePositionY, float partialTicks) {}

    /**
     * Save methods to client.
     **/
    public void onSave() throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

        String path = "revenant/client/GUI/";

        if (!Files.exists(Paths.get(path))) {
            Files.createDirectories(Paths.get(path));
        }

        if (!Files.exists(Paths.get(path + this.rect.getTag() + ".json"))) {
            Files.createFile(Paths.get(path + this.rect.getTag() + ".json"));
        } else {
            File file = new File(path + this.rect.getTag() + ".json");

            file.delete();

            Files.createFile(Paths.get(path + this.rect.getTag() + ".json"));
        }

        JsonObject mainJson = new JsonObject();

        mainJson.add("x", new JsonPrimitive(this.rectName.getX()));
        mainJson.add("Y", new JsonPrimitive(this.rectName.getY()));
        mainJson.add("isRendering", new JsonPrimitive(this.isRendering));
        mainJson.add("isFrameOpen", new JsonPrimitive(this.isFrameOpen));

        String jsonString = gsonBuilder.toJson(new JsonParser().parse(mainJson.toString()));

        OutputStreamWriter fileOutpotStream = new OutputStreamWriter(new FileOutputStream(path + this.rect.getTag() + ".json"), "UTF-8");

        fileOutpotStream.write(jsonString);
        fileOutpotStream.close();
    }

    public void onLoad() throws IOException {
        String path = "revenant/client/GUI/";

        if (!Files.exists(Paths.get(path + this.rect.getTag() + ".json"))) {
            return;
        }

        InputStream file = Files.newInputStream(Paths.get(path + this.rect.getTag() + ".json"));

        JsonObject mainJson = new JsonParser().parse(new InputStreamReader(file)).getAsJsonObject();

        if (mainJson.get("x") != null) {
            this.rectName.setX(mainJson.get("x").getAsInt());
        }

        if (mainJson.get("y") != null) {
            this.rectName.setY(mainJson.get("y").getAsInt());
        }

        if (mainJson.get("isRendering") != null) {
            this.isRendering = mainJson.get("isRendering").getAsBoolean();
        }

        if (mainJson.get("isFrameOpen") != null) {
            this.isFrameOpen = mainJson.get("isFrameOpen").getAsBoolean();
        }

        file.close();
    }
}