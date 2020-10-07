package me.rina.racc.gui.client.widgets;

// Java.
import java.util.ArrayList;

// Turok.
import me.rina.turok.render.TurokImageManager;
import me.rina.turok.render.image.TurokImage;
import me.rina.turok.font.TurokFontManager;
import me.rina.turok.render.TurokRenderGL;

// GUI.
import me.rina.racc.gui.client.RevenantWidget;
import me.rina.racc.gui.client.RevenantFrame;

// Client.
import me.rina.racc.client.RevenantModule;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 28/09/2020.
 *
 **/
public class RevenantWidgetCategoryButton extends RevenantWidget {
    private RevenantFrame master;

    public RevenantModule.Category category;

    private int saveY;

    /* Events */
    public boolean isActived;

    public RevenantWidgetCategoryButton(RevenantFrame master, RevenantModule.Category category, String categoryName, int nextY) {
        super(categoryName);

        this.master = master;

        this.category = category;

        this.rect.setX(this.master.rect.getX());
        this.rect.setY(nextY);

        this.saveY = nextY;

        this.rect.setWidth(this.master.rect.getWidth());
        this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);
    }

    @Override
    public void resetAllCustomEventBase() {
        this.isMouseOver    = false;
        this.isMouseClicked = false;
    }

    @Override
    public void disableAllCustomEventBase() {
        this.isMouseClicked = false;
    }

    @Override
    public void onMouseClicked(int mousePositionX, int mousePositionY, int mouseButtonDown) {
        if (this.isRendering) {
            if (mouseButtonDown == 0) {
                if (this.isMouseOver) {
                    Revenant.getGUIClient().getFrameByRectTag(this.category.getName()).isRendering = !Revenant.getGUIClient().getFrameByRectTag(this.category.getName()).isRendering;
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
            this.rect.setY(this.master.rect.getY() + saveY);

            this.rect.setWidth(this.master.rect.getWidth());
            this.rect.setHeight(4 + TurokFontManager.getStringHeight(TurokFontManager.CFONT_MODULE, this.rect.getTag()) + 4);

            if (Revenant.getGUIClient().getFrameByRectTag(this.category.getName()).isRendering) {
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
    }
}