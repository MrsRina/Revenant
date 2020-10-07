package me.rina.racc.util.render;

// Java.
import java.awt.Color;

// ORG.
import org.lwjgl.opengl.GL11;

public class RVColor extends Color
{
    public RVColor (int rgb) {
        super(rgb);
    }

    public RVColor (int rgba, boolean hasalpha) {
        super(rgba,hasalpha);
    }

    public RVColor (int r, int g, int b) {
        super(r,g,b);
    }

    public RVColor (int r, int g, int b, int a) {
        super(r,g,b,a);
    }

    public RVColor (Color color) {
        super(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }

    public RVColor (RVColor color, int a) {
        super(color.getRed(),color.getGreen(),color.getBlue(),a);
    }

    public static RVColor fromHSB (float hue, float saturation, float brightness) {
        return new RVColor(Color.getHSBColor(hue,saturation,brightness));
    }

    public float getHue() {
        return RGBtoHSB(getRed(),getGreen(),getBlue(),null)[0];
    }

    public float getSaturation() {
        return RGBtoHSB(getRed(),getGreen(),getBlue(),null)[1];
    }

    public float getBrightness() {
        return RGBtoHSB(getRed(),getGreen(),getBlue(),null)[2];
    }

    public void glColor() {
        GL11.glColor4f(getRed()/255.0f,getGreen()/255.0f,getBlue()/255.0f,getAlpha()/255.0f);
    }
}
