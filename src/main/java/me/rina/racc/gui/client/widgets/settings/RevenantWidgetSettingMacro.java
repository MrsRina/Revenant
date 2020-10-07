package me.rina.racc.gui.client.widgets.settings;

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

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 02/10/2020.
 *
 **/
public class RevenantWidgetSettingMacro extends RevenantWidget {
    private RevenantFrame master;
    public RevenantSetting setting;
    public RevenantWidgetModuleButton child;

    public int saveY;

    public int colorAlpha;

    /* Public events */
    public boolean isListening;

    public RevenantWidgetSettingMacro(RevenantFrame master, RevenantWidgetModuleButton child, RevenantSetting setting, int nextY) {
        super(setting.getName());

        this.master  = master;
        this.child   = child;
        this.setting = setting;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

        this.isListening = false;

        this.colorAlpha = 255;
    }

    @Override
    public void onKeyPressed(char vChar, int keyCode) {
        if (this.isListening) {
            switch (keyCode) {
                case Keyboard.KEY_ESCAPE : {
                    Revenant.getGUIClient().cancelKeyESCAPE = false;

                    this.isListening = false;

                    break;
                }

                case Keyboard.KEY_DELETE : {
                    this.setting.setInteger(-1);

                    Revenant.getGUIClient().cancelKeyESCAPE = false;

                    this.isListening = false;

                    break;
                }

                default : {
                    this.setting.setInteger(keyCode);

                    Revenant.getGUIClient().cancelKeyESCAPE = false;

                    this.isListening = false;

                    break;
                }
            }
        }
    }

    @Override
    public void resetAllCustomEventBase() {}

    @Override
    public void disableAllCustomEventBase() {
        this.isMouseClicked = false;
        this.isListening    = false;
    }

    @Override
    public void onMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                if (this.isMouseOver) {
                    this.isMouseClicked = true;

                    this.isListening = true;

                    Revenant.getGUIClient().cancelKeyESCAPE = true;
                }
            }
        }
    }

    @Override
    public void onCustomRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            Revenant.getGUIClient().cancelKeyESCAPE = false;

            this.isListening = false;
        }
    }

    @Override
    public void onMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        if (this.isRendering) {
            if (mouseButtonUp == 0) {
                if (this.isMouseClicked) {
                    this.isMouseClicked = false;
                }

                if (this.isMouseOver) {
                    this.isListening = true;

                    Revenant.getGUIClient().cancelKeyESCAPE = true;
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

            if (this.isListening) {
                if (Revenant.getSystemController().getRGBEffect()[0] <= 255) {
                    this.colorAlpha = Revenant.getSystemController().getRGBEffect()[0];
                } else if (Revenant.getSystemController().getRGBEffect()[1] <= 255 && Revenant.getSystemController().getRGBEffect()[0] >= 255) {
                    this.colorAlpha = Revenant.getSystemController().getRGBEffect()[1];
                } else if (Revenant.getSystemController().getRGBEffect()[2] <= 255 && Revenant.getSystemController().getRGBEffect()[0] >= 255 && Revenant.getSystemController().getRGBEffect()[1] >= 255) {
                    this.colorAlpha = Revenant.getSystemController().getRGBEffect()[2];
                }

                if (this.colorAlpha <= 150) {
                    this.colorAlpha = 150;
                }
            } else {
                this.colorAlpha = 255;
            }

            String actualValue = "" + (this.isListening ? "Key Bind Macro | Listening..." : ("Key Bind Macro | " + (this.setting.getInteger() != -1 ? ("" + Keyboard.getKeyName(this.setting.getInteger()).toLowerCase()) : "none")));

            TurokFontManager.renderString(TurokFontManager.CFONT_SETTING, actualValue, this.rect.getX() + 1, this.rect.getY() + 6, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], this.colorAlpha, false);
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