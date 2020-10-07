package me.rina.racc.gui.client.frames;

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

// Client.
import me.rina.racc.client.RevenantModule;

// GUI.
import me.rina.racc.gui.client.widgets.RevenantWidgetModuleButton;
import me.rina.racc.gui.client.RevenantWidget;
import me.rina.racc.gui.client.RevenantFrame;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 29/09/2020.
 *
 **/
public class RevenantFrameModule extends RevenantFrame {
    private int categoryNameHeight;
    private int categoryFrameHeight;

    private ArrayList<RevenantWidget> widgetList;

    public RevenantModule.Category category;

    /* Two variables to image location, for checkbox */
    private TurokImage checkboxTrue;
    private TurokImage checkboxFalse;

    public RevenantFrameModule(RevenantModule.Category category, int defaultPositionX, int defaultPositionY) {
        super(category.getName());

        this.category = category;

        this.categoryNameHeight  = 2 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.category.getName()) + 2;
        this.categoryFrameHeight = 1;

        this.rectName.setWidth(104);
        this.rectName.setHeight(this.categoryNameHeight);

        this.rect.setWidth(102);
        this.rect.setHeight(this.categoryFrameHeight);

        this.rectName.setX(defaultPositionX);
        this.rectName.setY(defaultPositionY);

        this.rect.setX(this.rectName.getX() + 1);
        this.rect.setY(this.rectName.getY() + this.rectName.getHeight());

        /* We initialize the resource locaiton with TurokImage */
        try {
            this.checkboxTrue  = new TurokImage("/gui/true.png", ImageIO.read(RevenantFrameCategory.class.getResourceAsStream("/gui/true.png")));
            this.checkboxFalse = new TurokImage("/gui/false.png", ImageIO.read(RevenantFrameCategory.class.getResourceAsStream("/gui/false.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.widgetList = new ArrayList<>();

        loadCategoriesButton();
    }

    public void loadCategoriesButton() {
        int cacheCount = 0;
        int cacheSize  = Revenant.getModuleManager().getModuleList().size();

        for (RevenantModule modules : Revenant.getModuleManager().getModuleList()) {
            if (modules.getCategory() != this.category) {
                continue;
            }

            RevenantWidgetModuleButton widgetModuleButton = new RevenantWidgetModuleButton(this, modules, this.categoryFrameHeight);

            widgetModuleButton.rect.setY(this.categoryFrameHeight);

            this.widgetList.add(widgetModuleButton);

            cacheCount++;

            this.categoryFrameHeight = widgetModuleButton.rect.getY() + widgetModuleButton.rect.getHeight() + 1;
        }
    }

    @Override
    public void resetAllCustomEventBase() {
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
        this.onUpdateMouseClicked(mousePositionX, mousePositionY, mouseButtonDown);

        for (RevenantWidget widgets : this.widgetList) {
            widgets.onMouseClicked(mousePositionX, mousePositionY, mouseButtonDown);
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
        this.onUpdateMouseReleased(mousePositionX, mousePositionY, mouseButtonUp);

        for (RevenantWidget widgets : this.widgetList) {
            widgets.onMouseReleased(mousePositionX, mousePositionY, mouseButtonUp);
        }
    }

    @Override
    public void onCustomRefreshMouseReleased(int mousePositionX, int mousePositionY, int mouseButtonUp) {
        for (RevenantWidget widgets : this.widgetList) {
            widgets.onCustomRefreshMouseReleased(mousePositionX, mousePositionY, mouseButtonUp);
        }
    }

    @Override
    public void onRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {
        this.categoryFrameHeight = 1;

        for (RevenantWidget widgets : this.widgetList) {
            if (!(widgets instanceof RevenantWidgetModuleButton)) {
                continue;
            }

            RevenantWidgetModuleButton widgetRequested = (RevenantWidgetModuleButton) widgets;

            widgetRequested.setSaveY(this.categoryFrameHeight);

            if (widgetRequested.isOpened) {
                this.categoryFrameHeight += widgetRequested.getSettingHeight();
            } else {
                this.categoryFrameHeight += widgetRequested.rect.getHeight() + 1; // + 1;
            }
        }
    }

    @Override
    public void onRender(int mousePositionX, int mousePositionY, float partialTicks) {
        this.onUpdateRectCheckbox();
        this.onUpdateEventRender(mousePositionX, mousePositionY);

        TurokRenderGL.color(Revenant.getGUITheme().frameNameBackground[0], Revenant.getGUITheme().frameNameBackground[1], Revenant.getGUITheme().frameNameBackground[2], Revenant.getGUITheme().frameNameBackground[3]);
        TurokRenderGL.drawSolidRect(this.rectName);

        TurokRenderGL.color(Revenant.getGUITheme().frameNameBackground[0], Revenant.getGUITheme().frameNameBackground[1], Revenant.getGUITheme().frameNameBackground[2], Revenant.getGUITheme().frameNameBackground[3]);
        TurokRenderGL.drawUpTriangule(this.rectName.getX() - 2, this.rectName.getY(), 2, this.rectName.getHeight(), 2);

        TurokRenderGL.color(Revenant.getGUITheme().frameNameBackground[0], Revenant.getGUITheme().frameNameBackground[1], Revenant.getGUITheme().frameNameBackground[2], Revenant.getGUITheme().frameNameBackground[3]);
        TurokRenderGL.drawDownTriangule(this.rectName.getX() + this.rectName.getWidth(), this.rectName.getY(), 2, this.rectName.getHeight(), 2);

        if (this.isFrameOpen) {
            TurokRenderGL.color(Revenant.getGUITheme().frameBackground[0], Revenant.getGUITheme().frameBackground[1], Revenant.getGUITheme().frameBackground[2], Revenant.getGUITheme().frameBackground[3]);
            TurokRenderGL.drawSolidRect(this.rect);

            TurokImageManager.renderIMAGE(this.checkboxTrue, this.rectCheckbox.getX(), this.rectCheckbox.getY(), this.rectCheckbox.getWidth(), this.rectCheckbox.getHeight(), Revenant.getGUITheme().frameNameBackground[0], Revenant.getGUITheme().frameNameBackground[1], Revenant.getGUITheme().frameNameBackground[2], Revenant.getGUITheme().frameNameBackground[3]);

            for (RevenantWidget widgets : this.widgetList) {
                widgets.isRendering = true;

                widgets.onRender(mousePositionX, mousePositionY, partialTicks);
            }

            this.rect.setHeight(this.categoryFrameHeight);
        } else {
            TurokImageManager.renderIMAGE(this.checkboxFalse, this.rectCheckbox.getX(), this.rectCheckbox.getY(), this.rectCheckbox.getWidth(), this.rectCheckbox.getHeight(), Revenant.getGUITheme().frameNameBackground[0], Revenant.getGUITheme().frameNameBackground[1], Revenant.getGUITheme().frameNameBackground[2], Revenant.getGUITheme().frameNameBackground[3]);

            for (RevenantWidget widgets : this.widgetList) {
                widgets.isRendering = false;
            }

            this.rect.setHeight(0);
        }

        TurokFontManager.renderString(TurokFontManager.CFONT_MODULE, this.category.getName(), this.rectName.getX() + 2, this.rectName.getY() + 2, Revenant.getGUITheme().frameName[0], Revenant.getGUITheme().frameName[1], Revenant.getGUITheme().frameName[2], Revenant.getGUITheme().shadowFont);
    }

    @Override
    public void onCustomRefreshRender(int mousePositionX, int mousePositionY, float partialTicks) {
        for (RevenantWidget widgets : this.widgetList) {
            widgets.onCustomRefreshRender(mousePositionX, mousePositionY, partialTicks);
        }
    }
}