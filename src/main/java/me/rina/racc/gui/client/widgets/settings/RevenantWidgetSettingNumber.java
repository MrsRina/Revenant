package me.rina.racc.gui.client.widgets.settings;

// Java.
import java.math.*;
import java.util.*;

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
 * @since 01/10/2020.
 *
 **/
public class RevenantWidgetSettingNumber extends RevenantWidget {
    private RevenantFrame master;

    public RevenantSetting setting;

    public RevenantWidgetModuleButton child;

    public int saveY;

    public float saveWidth;

    public TurokRect rectSlider;

    public RevenantWidgetSettingNumber(RevenantFrame master, RevenantWidgetModuleButton child, RevenantSetting setting, int nextY) {
        super(setting.getName());

        this.master  = master;
        this.child   = child;
        this.setting = setting;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.saveWidth = 0;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

        this.rectSlider = new TurokRect(setting.getName() + "Slider", 0, 0);
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
                this.isMouseClicked = false;

                if (this.isMouseOver) {
                    this.isMouseClicked = true;
                }
            }
        }
    }

    @Override
    public void onCustomRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                this.isMouseClicked = false;
            }
        }
    }

    @Override
    public void onMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        if (this.isRendering) {
            if (mouseButtonUp == 0) {
                this.isMouseClicked = false;
            }
        }
    }

    @Override
    public void onCustomRefreshMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        if (this.isRendering) {
            if (mouseButtonUp == 0) {
                this.isMouseClicked = false;
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

            this.rectSlider.setX(this.master.rect.getX());
            this.rectSlider.setY(this.rect.getY() + 9);

            this.rectSlider.setWidth(this.master.rect.getWidth());
            this.rectSlider.setHeight(6);

            double mouse = Math.min(this.rectSlider.getWidth(), Math.max(0, mousePositionX - this.rectSlider.getX()));

            double min = this.setting.getMinimum();
            double max = this.setting.getMaximum();

            double value = this.setting.getDouble();

            this.saveWidth = (float) ((this.rectSlider.getWidth()) * (value - min) / (max - min));

            if (this.isMouseClicked) {
                if (mouse == 0) {
                    this.setting.setDouble(min);
                } else {
                    double roundedValue = convertToDecimal(((mouse / this.rectSlider.getWidth()) * ((double) max - (double) min) + (double) min));

                    this.setting.setDouble((double) roundedValue);
                }
            }

            String valueString = this.rect.getTag() + " | " + (this.setting.isSlider("int") ? ("" + this.setting.getInteger()) : ("" + this.setting.getDouble()).replace(".", ","));

            TurokRenderGL.color(Revenant.getGUITheme().moduleNameHighlight[0], Revenant.getGUITheme().moduleNameHighlight[1], Revenant.getGUITheme().moduleNameHighlight[2], 200);
            TurokRenderGL.drawSolidRect((float) this.rectSlider.getX(), (float) this.rectSlider.getY(), this.saveWidth, (float) this.rectSlider.getHeight());

            TurokRenderGL.color(Revenant.getGUITheme().moduleNameHighlight[0], Revenant.getGUITheme().moduleNameHighlight[1], Revenant.getGUITheme().moduleNameHighlight[2], 200);
            TurokRenderGL.drawOutlineRect(this.rectSlider);

            TurokFontManager.renderString(TurokFontManager.CFONT_SETTING, valueString, this.rect.getX() + 1, this.rectSlider.getY() - TurokFontManager.getStringHeight(TurokFontManager.CFONT_SETTING, valueString) - 2, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], false);
        }
    }

    @Override
    public void onCustomRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.rectSlider.collide(mousePositionX, mousePositionY)) {
            this.isMouseOver = true;
        } else {
            this.isMouseOver = false;
        }
    }

    public double convertToDecimal(double vDouble) {
        BigDecimal decimal = new BigDecimal(vDouble);

        decimal = decimal.setScale(2, RoundingMode.HALF_UP);

        return decimal.doubleValue();
    }

}