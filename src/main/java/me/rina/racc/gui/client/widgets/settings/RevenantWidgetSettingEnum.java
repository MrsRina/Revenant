package me.rina.racc.gui.client.widgets.settings;

// Minecraft.
import net.minecraft.client.gui.GuiTextField;

// Java.
import java.util.ArrayList;

// OpenGL.
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

// Turok.
import me.rina.turok.render.TurokImageManager;
import me.rina.turok.render.image.TurokImage;
import me.rina.turok.font.TurokFontManager;
import me.rina.turok.render.TurokRenderGL;
import me.rina.turok.math.TurokRect;

// GUI.
import me.rina.racc.gui.client.widgets.RevenantWidgetModuleButton;
import me.rina.racc.gui.client.RevenantWidget;
import me.rina.racc.gui.client.RevenantFrame;

// Client.
import me.rina.racc.client.RevenantSetting;

// Util.
import me.rina.racc.util.math.RevenantMathTimerUtil;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 02/10/2020.
 *
 **/
public class RevenantWidgetSettingEnum extends RevenantWidget {
    private RevenantFrame master;
    public RevenantSetting setting;
    public RevenantWidgetModuleButton child;

    public ArrayList<String> values;

    public int saveY;

    public int index;

    /* Events public */
    public boolean isStarted;

    public RevenantWidgetSettingEnum(RevenantFrame master, RevenantWidgetModuleButton child, RevenantSetting setting, int nextY) {
        super(setting.getName());

        this.master  = master;
        this.child   = child;
        this.setting = setting;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

        this.isStarted = true;

        this.values = new ArrayList<>();

        this.index = 0;

        /* We load all values in a ArrayList. */;
        for (Enum values : this.setting.getEnum().getClass().getEnumConstants()) {
            this.values.add(values.name());
        }
    }

    @Override
    public void resetAllCustomEventBase() {}

    @Override
    public void disableAllCustomEventBase() {
        this.isMouseClicked = false;
    }

    @Override
    public void onMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                if (this.isMouseOver) {
                    if (this.index >= (this.values.size() - 1)) {
                        this.index = 0;
                    } else {
                        this.index++;
                    }

                    this.isMouseClicked = true;
                }
            }
        }
    }

    @Override
    public void onCustomRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {}

    @Override
    public void onMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        if (this.isRendering) {
            if (mouseButtonUp == 0) {
                if (this.isMouseClicked) {
                    this.isMouseClicked = false;
                }
            }
        }
    }

    @Override
    public void onRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.isRendering) {
            this.rect.setX(this.master.rect.getX());
            this.rect.setY(this.child.rect.getY() + this.saveY);

            this.rect.setWidth(this.master.rect.getWidth());
            this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

            if (this.isStarted) {
                this.index = this.values.indexOf(this.setting.getEnum().name()) == -1 ? 0 : this.values.indexOf(this.setting.getEnum().name());

                this.setting.setEnum(this.values.get(this.index));

                this.isStarted = false;
            } else {
                this.setting.setEnum(this.values.get(this.index));
            }

            String actualValue = this.rect.getTag() + ": " + this.setting.getEnum().name();

            TurokFontManager.renderString(TurokFontManager.CFONT_SETTING, actualValue, this.rect.getX() + 1, this.rect.getY() + 8, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], false);
        }
    }

    @Override
    public void onCustomRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.rect.collide(mousePositionX, mousePositionY)) {
            this.isMouseOver = true;
        } else {
            this.isMouseOver = false;
        }
    }
}