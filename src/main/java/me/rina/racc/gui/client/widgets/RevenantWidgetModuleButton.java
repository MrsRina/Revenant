package me.rina.racc.gui.client.widgets;

// Java.
import java.util.ArrayList;

// Turok.
import me.rina.turok.render.TurokImageManager;
import me.rina.turok.render.image.TurokImage;
import me.rina.turok.font.TurokFontManager;
import me.rina.turok.render.TurokRenderGL;

// GUI.
import me.rina.racc.gui.client.widgets.settings.*;
import me.rina.racc.gui.client.RevenantWidget;
import me.rina.racc.gui.client.RevenantFrame;

// Client.
import me.rina.racc.client.RevenantSetting;
import me.rina.racc.client.RevenantModule;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 30/09/2020.
 *
 **/
public class RevenantWidgetModuleButton extends RevenantWidget {
    private ArrayList<RevenantWidget> widgetList;

    private RevenantFrame master;

    public RevenantModule module;

    private int moduleSettingHeight;

    private int saveY;

    /* Public events */
    public boolean isOpened;

    public RevenantWidgetModuleButton(RevenantFrame master, RevenantModule module, int nextY) {
        super(module.getName());

        this.master = master;

        this.module = module;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

        this.moduleSettingHeight = this.rect.getHeight() + 1;

        this.isRendering = false;

        this.widgetList = new ArrayList<>();

        loadSettingsWidget();
    }

    public void loadSettingsWidget() {
        int cacheCount = 0;
        int cacheSize  = this.module.getSettingList().size();

        for (RevenantSetting settings : this.module.getSettingList()) {
            if (settings.getType() == RevenantSetting.Type.SETTING_BUTTON) {
                RevenantWidgetSettingBoolean widgetButtonBoolean = new RevenantWidgetSettingBoolean(this.master, this, settings, this.moduleSettingHeight);

                cacheCount++;

                this.moduleSettingHeight = widgetButtonBoolean.rect.getY() + widgetButtonBoolean.rect.getHeight() + 1;

                this.widgetList.add(widgetButtonBoolean);
            }

            if (settings.getType() == RevenantSetting.Type.SETTING_SLIDER) {
                RevenantWidgetSettingNumber widgetButtonNumber = new RevenantWidgetSettingNumber(this.master, this, settings, this.moduleSettingHeight);

                cacheCount++;

                this.moduleSettingHeight = widgetButtonNumber.rect.getY() + widgetButtonNumber.rect.getHeight() + 1;

                this.widgetList.add(widgetButtonNumber);
            }

            if (settings.getType() == RevenantSetting.Type.SETTING_ENTRY) {
                RevenantWidgetSettingString widgetButtonString = new RevenantWidgetSettingString(this.master, this, settings, this.moduleSettingHeight);

                cacheCount++;

                this.moduleSettingHeight = widgetButtonString.rect.getY() + widgetButtonString.rect.getHeight() + 1;

                this.widgetList.add(widgetButtonString);
            }

            if (settings.getType() == RevenantSetting.Type.SETTING_SELECT_BOX) {
                RevenantWidgetSettingEnum widgetButtonEnum = new RevenantWidgetSettingEnum(this.master, this, settings, this.moduleSettingHeight);

                cacheCount++;

                this.moduleSettingHeight = widgetButtonEnum.rect.getY() + widgetButtonEnum.rect.getHeight() + 1;

                this.widgetList.add(widgetButtonEnum);
            }
        }

        RevenantWidgetSettingMacro widgetButtonMacro = new RevenantWidgetSettingMacro(this.master, this, this.module.getSettingByTag(this.module.getTag() + "Macro"), this.moduleSettingHeight);

        this.moduleSettingHeight = widgetButtonMacro.rect.getY() + widgetButtonMacro.rect.getHeight() + 1;

        this.widgetList.add(widgetButtonMacro);
    }

    public void setSaveY(int y) {
        this.saveY = y;
    }

    public int getSettingHeight() {
        return moduleSettingHeight;
    }

    @Override
    public void resetAllCustomEventBase() {
        this.isMouseOver = false;

        for (RevenantWidget widgets : this.widgetList) {
            widgets.resetAllCustomEventBase();
        }
    }

    @Override
    public void disableAllCustomEventBase() {
        for (RevenantWidget widgets : this.widgetList) {
            widgets.disableAllCustomEventBase();
        }
    }

    @Override
    public void onKeyPressed(char vChar, int keyCode) {
        if (this.isRendering) {
            for (RevenantWidget widgets : this.widgetList) {
                widgets.onKeyPressed(vChar, keyCode);
            }
        }
    }

    @Override
    public void onMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            for (RevenantWidget widgets : this.widgetList) {
                if (this.isOpened) {
                    widgets.onMouseClicked(mousePositionX, mousePositionY, mouseButtonDown);
                }
            }

            if (mouseButtonDown == 0) {
                if (this.isMouseOver) {
                    this.module.toggle();
                }
            }

            if (mouseButtonDown == 1) {
                if (this.isMouseOver) {
                    this.isOpened = !this.isOpened;

                    this.master.onRefreshRender(mousePositionX, mousePositionY, 0.1f);
                }
            }
        }
    }

    @Override
    public void onCustomRefreshMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        for (RevenantWidget widgets : this.widgetList) {
            widgets.onCustomRefreshMouseClicked(mousePositionX, mousePositionY, mouseButtonDown);
        }
    }

    @Override
    public void onMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        for (RevenantWidget widgets : this.widgetList) {
            if (this.isOpened) {
                widgets.onMouseReleased(mousePositionX, mousePositionY, mouseButtonUp);
            }
        }
    }

    @Override
    public void onCustomRefreshMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        for (RevenantWidget widgets : this.widgetList) {
            widgets.onCustomRefreshMouseClicked(mousePositionX, mousePositionY, mouseButtonUp);
        }
    }

    @Override
    public void onRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.isRendering) {
            this.rect.setX(this.master.rect.getX());
            this.rect.setY(this.master.rect.getY() + saveY);

            this.rect.setWidth(this.master.rect.getWidth());
            this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

            String dick = this.isOpened ? "-" : "+";

            if (this.module.isEnabled()) {
                if (this.isMouseOver) {
                    TurokRenderGL.color(Revenant.getGUITheme().moduleNameHighlight[0], Revenant.getGUITheme().moduleNameHighlight[1], Revenant.getGUITheme().moduleNameHighlight[2], 255);
                    TurokRenderGL.drawSolidRect(this.rect);
                } else {
                    TurokRenderGL.color(Revenant.getGUITheme().moduleNameHighlight[0], Revenant.getGUITheme().moduleNameHighlight[1], Revenant.getGUITheme().moduleNameHighlight[2], 200);
                    TurokRenderGL.drawSolidRect(this.rect);
                }
            } else {
                if (this.isMouseOver) {
                    TurokRenderGL.color(Revenant.getGUITheme().moduleNameHighlight[0], Revenant.getGUITheme().moduleNameHighlight[1], Revenant.getGUITheme().moduleNameHighlight[2], 100);
                    TurokRenderGL.drawSolidRect(this.rect);
                }
            }

            for (RevenantWidget widgets : this.widgetList) {
                if (this.isOpened) {
                    widgets.isRendering = true;

                    widgets.onRender(mousePositionX, mousePositionY, partialTicks);
                } else {
                    widgets.isRendering = false;
                }
            }

            TurokFontManager.renderString(TurokFontManager.CFONT_MODULE, dick, this.rect.getX() + this.rect.getWidth() - TurokFontManager.getStringWidth(TurokFontManager.CFONT_MODULE, dick) - 4, this.rect.getY() + 4, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], Revenant.getGUITheme().shadowFont);
            TurokFontManager.renderString(TurokFontManager.CFONT_MODULE, this.rect.getTag(), this.rect.getX() + ((this.rect.getWidth() / 2) - (TurokFontManager.getStringWidth(TurokFontManager.CFONT_MODULE, this.rect.getTag()) / 2)), this.rect.getY() + 4, Revenant.getGUITheme().moduleName[0], Revenant.getGUITheme().moduleName[1], Revenant.getGUITheme().moduleName[2], Revenant.getGUITheme().shadowFont);
        }
    }

    @Override
    public void onCustomRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {
        if (this.rect.collide(mousePositionX, mousePositionY)) {
            this.isMouseOver = true;
        } else {
            this.isMouseOver = false;
        }

        for (RevenantWidget widgets : this.widgetList) {
            if (this.isOpened) {
                widgets.onCustomRefreshRender(mousePositionX, mousePositionY, partialTicks);
            }
        }
    }
}