package me.rina.racc.gui;

// Minecraft Utils.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// OpenGL.
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

// Java.
import java.util.*;
import java.io.*;

// Turok.
import me.rina.turok.font.TurokFontManager;
import me.rina.turok.render.TurokRenderGL;

// Modules specified.
import me.rina.racc.client.modules.client.RevenantModuleHUDEditor;

// Frame.
import me.rina.racc.gui.client.RevenantFrame;

// Client.
import me.rina.racc.client.RevenantModule;

// GUI.
import me.rina.racc.gui.client.frames.RevenantFrameCategory;
import me.rina.racc.gui.client.frames.RevenantFrameModule;
import me.rina.racc.gui.client.RevenantFrame;

// Revanant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantMainGUI extends GuiScreen {
    public ArrayList<RevenantFrame> frameList;

    private RevenantFrame overlayFrame;
    private RevenantFrame focusedFrame;

    private RevenantModuleHUDEditor moduleHUDEditor;

    public int screenWidth;
    public int screenHeight;

    public boolean cancelKeyESCAPE;

    public RevenantMainGUI() {
        this.frameList = new ArrayList<>();

        // for (RevenantModule.Category categories : RevenantModule.Category.values()) {}

        this.overlayFrame = new RevenantFrameModule(RevenantModule.Category.OVERLAY, 10, 10);

        this.frameList.add(new RevenantFrameCategory(1, 1));

        int cachePosX = getFrameByRectTag(Revenant.NAME).getRect().getWidth() + 4;
        int cachePosY = 1;

        for (RevenantModule.Category categories : RevenantModule.Category.values()) {
            if (categories == RevenantModule.Category.OVERLAY) {
                continue;
            }

            RevenantFrameModule categoriesModuleFrame = new RevenantFrameModule(categories, cachePosX, cachePosY);

            cachePosY = categoriesModuleFrame.rect.getY() + categoriesModuleFrame.rect.getHeight() + 1;

            this.frameList.add(categoriesModuleFrame);
        }

        this.focusedFrame = this.frameList.get(0);

        this.screenWidth  = 0;
        this.screenHeight = 0;

        this.cancelKeyESCAPE = false;

        this.moduleHUDEditor = (RevenantModuleHUDEditor) Revenant.getModuleManager().getModuleByTag("HUDEditor");
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        if (Revenant.getModuleManager().getModuleByTag("HUDEditor").isEnabled()) {
            Revenant.getModuleManager().getModuleByTag("HUDEditor").setDisabled();
            Revenant.getModuleManager().getModuleByTag("GUI").setDisabled();
        } else {
            Revenant.getModuleManager().getModuleByTag("GUI").setDisabled();
        }

        for (RevenantFrame frames : this.frameList) {
            if (frames.isRendering) {
                frames.resetAllEventBase();
                frames.disableAllCustomEventBase();
            }
        }

        if (this.overlayFrame.isRendering) {
            this.overlayFrame.resetAllEventBase();
            this.overlayFrame.disableAllCustomEventBase();
        }

        onSaveFrameList();
    }

    public void handleMouseInput() throws IOException {
        int speedScroll = Revenant.getModuleManager().getModuleByTag("GUI").getSettingByTag("GUIScrollSpeed").getInteger();

        if (Revenant.getModuleManager().getModuleByTag("HUDEditor").isEnabled()) {
            if (Mouse.getEventDWheel() > 0) {
                this.overlayFrame.rectName.y += speedScroll;
            }

            if (Mouse.getEventDWheel() < 0) {
                this.overlayFrame.rectName.y -= speedScroll;
            }
        } else {
            if (Mouse.getEventDWheel() > 0) {
                for (RevenantFrame frames : this.frameList) {
                    frames.rectName.y += speedScroll;
                }
            }

            if (Mouse.getEventDWheel() < 0) {
                for (RevenantFrame frames : this.frameList) {
                    frames.rectName.y -= speedScroll;
                }
            }
        }

        super.handleMouseInput();
    }


    @Override
    public void keyTyped(char vChar, int keyCode) {
        if (this.cancelKeyESCAPE) {
            if (Revenant.getModuleManager().getModuleByTag("HUDEditor").isEnabled()) {
                if (this.overlayFrame.isRendering) {
                    this.overlayFrame.onKeyPressed(vChar, keyCode);
                }
            } else {
                if (this.focusedFrame.isRendering) {
                    this.focusedFrame.onKeyPressed(vChar, keyCode);
                }
            }
        } else {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                this.onGuiClosed();

                Revenant.getMinecraft().displayGuiScreen(null);
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y, int mouse) {
        if (Revenant.getModuleManager().getModuleByTag("HUDEditor").isEnabled()) {
            this.overlayFrame.onMouseClicked(x, y, mouse);

            this.moduleHUDEditor.onMouseClickedHUD(x, y, mouse);
        } else {
            for (RevenantFrame frames : this.frameList) {
                if (frames.isRendering) {
                    frames.onCustomRefreshMouseClicked(x, y, mouse);
                }

                if (frames.verifyAnyConnectionWithFrame(x, y)) {
                    this.focusedFrame = frames;
                }
            }

            this.focusedFrame.onMouseClicked(x, y, mouse);
        }
    }

    @Override
    public void mouseReleased(int x, int y, int mouse) {
        if (Revenant.getModuleManager().getModuleByTag("HUDEditor").isEnabled()) {
            this.overlayFrame.onMouseReleased(x, y, mouse);

            this.moduleHUDEditor.onMouseReleasedHUD(x, y, mouse);
        } else {
            for (RevenantFrame frames : this.frameList) {
                if (frames.isRendering) {
                    frames.onCustomRefreshMouseReleased(x, y, mouse);
                }

                if (frames.verifyAnyConnectionWithFrame(x, y)) {
                    this.focusedFrame = frames;
                }
            }

            this.focusedFrame.onMouseReleased(x, y, mouse);
        }
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        this.drawDefaultBackground();

        ScaledResolution scaled_resolution = new ScaledResolution(Revenant.getMinecraft());

        TurokRenderGL.fixScreen(scaled_resolution.getScaledWidth(), scaled_resolution.getScaledHeight());

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        refreshGUI(x, y, scaled_resolution);

        if (Revenant.getModuleManager().getModuleByTag("HUDEditor").isEnabled()) {
            this.moduleHUDEditor.onRenderHUD(x, y, partialTicks);

            this.overlayFrame.isRendering = true;

            if (this.overlayFrame.isRendering) {
                this.overlayFrame.onRender(x, y, partialTicks);
            }

            this.overlayFrame.onCustomRefreshRender(x, y, partialTicks);
        } else {
            this.overlayFrame.isRendering = false;

            for (RevenantFrame frames : this.frameList) {
                if (frames.isRendering) {
                    frames.onRender(x, y, partialTicks);
                }

                if (frames.verifyAnyConnectionWithFrame(x, y)) {
                    this.focusedFrame = frames;
                }

                frames.resetAllCustomEventBase();
            }

            this.focusedFrame.onCustomRefreshRender(x, y, partialTicks);
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1);
    }

    public void refreshGUI(int x, int y, ScaledResolution scaledResolution) {
        this.screenWidth  = scaledResolution.getScaledWidth();
        this.screenHeight = scaledResolution.getScaledHeight();
    }

    public void refresh() {
        this.frameList.remove(this.focusedFrame);
        this.frameList.add(this.focusedFrame);
    }

    public void onSaveFrameList() {
        try {
            for (RevenantFrame frames : this.frameList) {
                frames.onSave();
            }

            this.overlayFrame.onSave();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void onLoadFrameList() {
        try {
            for (RevenantFrame frames : this.frameList) {
                frames.onLoad();
            }

            this.overlayFrame.onLoad();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public RevenantFrame getFrameByRectTag(String rect) {
        for (RevenantFrame frames : this.frameList) {
            if (frames.getRect().getTag().equalsIgnoreCase(rect)) {
                return frames;
            }
        }

        return null;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}