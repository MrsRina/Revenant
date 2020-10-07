package me.rina.turok.font;

// Minecraft util.
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

// Java.
import java.util.*;
import java.awt.*;

// CFontRenderer.
import me.rina.turok.font.util.CFontRenderer;

// Render.
import me.rina.turok.render.TurokRenderGL;

/**
 *
 * @author Rina.
 * @since 26/09/2020.
 *
 * - Special font manager render to Revenant client.
 *
 **/
public class TurokFontManager {
    /* Types of custom fonts. */
    public static final CFontRenderer CFONT_MODULE  = new CFontRenderer(new Font("Tahoma", 0, 18), true, true);
    public static final CFontRenderer CFONT_SETTING = new CFontRenderer(new Font("Tahoma", 0, 12), true, true);
    public static final CFontRenderer CFONT_HUD     = new CFontRenderer(new Font("Tahoma", 0, 19), true, true);

    /**
     * @param cFontRenderer - Specified font renderer.
     * @param string        - String to render.
     * @param x             - Position x.
     * @param y             - Position y.
     * @param r             - Red value color.
     * @param g             - Green value color.
     * @param b             - Blue value color.
     * @param alpha         - Alpha string.
     * @param shadow        - String shadow boolean.
     **/
    public static void renderString(CFontRenderer cFontRenderer, String string, int x, int y, int r, int g, int b, int alpha, boolean shadow) {
        Color color = new Color(r, g, b, alpha);

        TurokRenderGL.prepareToRenderString();
        TurokRenderGL.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        if (shadow) {
            cFontRenderer.drawStringWithShadow(string, x, y, color.getRGB());
        } else {
            cFontRenderer.drawString(string, x, y, color.getRGB());
        }

        TurokRenderGL.releaseRenderString();
    }

    public static void renderString(CFontRenderer cFontRenderer, String string, int x, int y, int r, int g, int b, boolean shadow) {
        renderString(cFontRenderer, string, x, y, r, g, b, 255, shadow);
    }

    /**
     * @param cFontRenderer - Specified font renderer.
     * @param string        - String to get width.
     **/
    public static int getStringWidth(CFontRenderer cFontRenderer, String string) {
        return (int) cFontRenderer.getStringWidth(string);
    }

    /**
     * @param cFontRenderer - Specified font renderer.
     * @param string        - String to get height.
     **/
    public static int getStringHeight(CFontRenderer cFontRenderer, String string) {
        return (int) cFontRenderer.getStringHeight(string);
    }
}