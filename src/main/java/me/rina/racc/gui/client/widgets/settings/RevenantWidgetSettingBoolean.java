package me.rina.racc.gui.client.widgets.settings;

// Javax.
import javax.imageio.ImageIO;

// Java.
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

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

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 30/09/2020.
 *
 **/
public class RevenantWidgetSettingBoolean extends RevenantWidget {
    private RevenantFrame master;

    public RevenantSetting setting;

    public RevenantWidgetModuleButton child;

    public int saveY;

    private TurokRect rectCheckbox;

    /* Two variables to image location, for checkbox */
    private TurokImage checkboxTrue;
    private TurokImage checkboxFalse;

    public RevenantWidgetSettingBoolean(RevenantFrame master, RevenantWidgetModuleButton child, RevenantSetting setting, int nextY) {
        super(setting.getName());

        this.master  = master;
        this.child   = child;
        this.setting = setting;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

        this.rectCheckbox = new TurokRect(setting.getName() + "Checkbox", 0, 0);

        this.rectCheckbox.setX(this.master.rect.getX() + 1);
        this.rectCheckbox.setY(this.rect.getY() + 9);

        this.rectCheckbox.setWidth(6);
        this.rectCheckbox.setHeight(6);

        /* We initialize the resource locaiton with TurokImage */
        try {
            this.checkboxTrue  = new TurokImage("/gui/true.png", ImageIO.read(RevenantWidgetSettingBoolean.class.getResourceAsStream("/gui/true.png")));
            this.checkboxFalse = new TurokImage("/gui/false.png", ImageIO.read(RevenantWidgetSettingBoolean.class.getResourceAsStream("/gui/false.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
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
                    this.setting.setBoolean(!this.setting.getBoolean());
                }
            }
        }
    }

    @Override
    public void onMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {}

    @Override
    public void onRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.isRendering) {
            this.rect.setX(this.master.rect.getX());
            this.rect.setY(this.child.rect.getY() + this.saveY);

            this.rect.setWidth(this.master.rect.getWidth());
            this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

            this.rectCheckbox.setX(this.master.rect.getX() + 1);
            this.rectCheckbox.setY(this.rect.getY() + 6);

            this.rectCheckbox.setWidth(6);
            this.rectCheckbox.setHeight(6);

            if (this.setting.getBoolean()) {
                TurokImageManager.renderIMAGE(this.checkboxTrue, this.rectCheckbox.getX(), this.rectCheckbox.getY(), this.rectCheckbox.getWidth(), this.rectCheckbox.getHeight(), Revenant.getGUITheme().moduleNameHighlight[0], Revenant.getGUITheme().moduleNameHighlight[1], Revenant.getGUITheme().moduleNameHighlight[2], 255);
            } else {
                TurokImageManager.renderIMAGE(this.checkboxFalse, this.rectCheckbox.getX(), this.rectCheckbox.getY(), this.rectCheckbox.getWidth(), this.rectCheckbox.getHeight(), 0, 0, 0, 255);
            }

            TurokFontManager.renderString(TurokFontManager.CFONT_SETTING, this.rect.getTag(), this.rectCheckbox.getX() + this.rectCheckbox.getWidth() + 1, this.rectCheckbox.getY() + this.rectCheckbox.getHeight() - TurokFontManager.getStringHeight(TurokFontManager.CFONT_SETTING, this.rect.getTag()) - 1, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], false);
        }
    }

    @Override
    public void onCustomRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.rectCheckbox.collide(mousePositionX, mousePositionY)) {
            this.isMouseOver = true;
        } else {
            this.isMouseOver = false;
        }
    }
}