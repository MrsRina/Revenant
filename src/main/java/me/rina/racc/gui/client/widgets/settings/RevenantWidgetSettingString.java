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
 * @since 30/09/2020.
 *
 **/
public class RevenantWidgetSettingString extends RevenantWidget {
    private RevenantFrame master;
    public RevenantSetting setting;
    public RevenantWidgetModuleButton child;

    public int saveY;

    private String textSplitAnimation;

    private RevenantMathTimerUtil tick;

    private GuiTextField textField;

    /* Events public */
    public boolean isFocused;
    public boolean isStarted;

    public RevenantWidgetSettingString(RevenantFrame master, RevenantWidgetModuleButton child, RevenantSetting setting, int nextY) {
        super(setting.getName());

        this.master  = master;
        this.child   = child;
        this.setting = setting;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

        this.textField = new GuiTextField(0, Revenant.getMinecraft().fontRenderer, 0, 0, 0, 0);

        this.isFocused = false;
        this.isStarted = true;

        this.tick = new RevenantMathTimerUtil();
    }

    @Override
    public void resetAllCustomEventBase() {}

    @Override
    public void disableAllCustomEventBase() {
        this.isMouseClicked = false;
    }

    @Override
    public void onKeyPressed(char vChar, int keyCode) {
        if (this.textField.isFocused()) {
            switch (keyCode) {
                case Keyboard.KEY_ESCAPE : {
                    this.textField.setFocused(false);

                    this.isFocused = false;

                    Revenant.getGUIClient().cancelKeyESCAPE = true;

                    break;
                }

                case Keyboard.KEY_RETURN : {
                    this.setting.setString(this.textField.getText());

                    this.textField.setFocused(false);

                    this.isFocused = false;

                    Revenant.getGUIClient().cancelKeyESCAPE = false;

                    break;
                }

                default : {
                    this.textField.textboxKeyTyped(vChar, keyCode);

                    break;
                }
            }
        }
    }

    @Override
    public void onMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                this.textField.setFocused(false);

                if (this.isMouseOver) {
                    this.textField.setFocused(true);

                    this.isMouseClicked = true;

                    Revenant.getGUIClient().cancelKeyESCAPE = true;
                }
            }
        }
    }

    @Override
    public void onCustomRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            Revenant.getGUIClient().cancelKeyESCAPE = false;

            this.textField.setFocused(false);

            this.isMouseClicked = false;
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
                    this.textField.setFocused(true);

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

            if (this.isFocused) {
                this.setting.setString(this.textField.getText());

                Revenant.getGUIClient().cancelKeyESCAPE = true;

                this.textSplitAnimation = "";

                if (this.tick.isPassedMS(500)) {
                    this.textSplitAnimation = "";
                } else {
                    this.textSplitAnimation = "_";
                }

                if (this.tick.isPassedMS(1000)) {
                    this.tick.resetTimer();
                }
            } else {
                if (this.isStarted) {
                    this.textField.setText(this.setting.getString());

                    this.isStarted = false;
                } else {
                    this.setting.setString(this.textField.getText());
                }

                this.tick.resetTimer();
            }

            this.isFocused = this.textField.isFocused();

            TurokFontManager.renderString(TurokFontManager.CFONT_SETTING, this.rect.getTag() + ":", this.rect.getX() + 1, this.rect.getY() + 2, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], false);
            TurokFontManager.renderString(TurokFontManager.CFONT_SETTING, this.setting.getString() + (this.textField.isFocused() ? this.textSplitAnimation : ""), this.rect.getX() + 1, this.rect.getY() + 3 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_SETTING, this.setting.getString() + (this.textField.isFocused() ? this.textSplitAnimation : "")) + 2, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], false);
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