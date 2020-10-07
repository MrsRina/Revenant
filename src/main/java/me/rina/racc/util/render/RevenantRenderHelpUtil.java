package me.rina.racc.util.render;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

// OpenGL.
import static org.lwjgl.opengl.GL11.*;

// Java.
import java.awt.Color;
import java.util.*;

// Turok.
import me.rina.turok.render.TurokRenderGL;

// Util.
import me.rina.racc.util.entity.RevenantEntityInterpolizerUtil;

// Revenant.
import me.rina.racc.Revenant;

/**
 * @author Rina
 *
 * Created by Rina.
 * 08/09/2020.
 *
 **/
public class RevenantRenderHelpUtil {
    private static final Minecraft mc = Revenant.getMinecraft();

    public static void prepare(float line) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glLineWidth(line);
    }

    public static void release() {
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void render3DSolid(ICamera camera, BlockPos blockpos, int r, int g, int b, int a) {
        render3DSolid(camera, blockpos.x, blockpos.y, blockpos.z, 1, 1, 1, r, g, b, a, 1.0f);
    }

    public static void render3DSolid(ICamera camera, BlockPos blockpos, int r, int g, int b, int a, float line) {
        render3DSolid(camera, blockpos.x, blockpos.y, blockpos.z, 1, 1, 1, r, g, b, a, line);
    }

    public static void render3DSolid(ICamera camera, int x, int y, int z, int r, int g, int b, int a) {
        render3DSolid(camera, x, y, z, 1, 1, 1, r, g, b, a, 1.0f);
    }

    public static void render3DSolid(ICamera camera, int x, int y, int z, int r, int g, int b, int a, float line) {
        render3DSolid(camera, x, y, z, 1, 1, 1, r, g, b, a, line);
    }

    public static void render3DSolid(ICamera camera, int x, int y, int z, double offsetX, double offsetY, double offsetZ, int r, int g, int b, int a) {
        render3DSolid(camera, x, y, z, offsetX, offsetY, offsetZ, r, g, b, a, 1.0f);
    }

    public static void render3DSolid(ICamera camera, int x, int y, int z, double offsetX, double offsetY, double offsetZ, int r, int g, int b, int a, float line) {
        Color color = new Color(r, g, b, a);

        final AxisAlignedBB bb = new AxisAlignedBB(
                // The start render position.
                x - mc.getRenderManager().viewerPosX,
                y - mc.getRenderManager().viewerPosY,
                z - mc.getRenderManager().viewerPosZ,

                // We have offset for render.
                x + offsetX - mc.getRenderManager().viewerPosX,
                y + offsetY - mc.getRenderManager().viewerPosY,
                z + offsetZ - mc.getRenderManager().viewerPosZ
        );

        camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(
                // Start position.
                bb.minX + mc.getRenderManager().viewerPosX,
                bb.minY + mc.getRenderManager().viewerPosY,
                bb.minZ + mc.getRenderManager().viewerPosZ,

                // offset.
                bb.maxX + mc.getRenderManager().viewerPosX,
                bb.maxY + mc.getRenderManager().viewerPosY,
                bb.maxZ + mc.getRenderManager().viewerPosZ))) {
            // Render.
            // Prepare.
            prepare(line);

            // Render global.
            RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

            // Release.
            release();
        }
    }

    public static void render3DOutline(ICamera camera, BlockPos blockpos, int r, int g, int b, int a) {
        render3DOutline(camera, blockpos.x, blockpos.y, blockpos.z, 1, 1, 1, r, g, b, a, 1.0f);
    }

    public static void render3DOutline(ICamera camera, BlockPos blockpos, int r, int g, int b, int a, float line) {
        render3DOutline(camera, blockpos.x, blockpos.y, blockpos.z, 1, 1, 1, r, g, b, a, line);
    }

    public static void render3DOutline(ICamera camera, int x, int y, int z, int r, int g, int b, int a) {
        render3DOutline(camera, x, y, z, 1, 1, 1, r, g, b, a, 1.0f);
    }

    public static void render3DOutline(ICamera camera, int x, int y, int z, int r, int g, int b, int a, float line) {
        render3DOutline(camera, x, y, z, 1, 1, 1, r, g, b, a, line);
    }

    public static void render3DOutline(ICamera camera, int x, int y, int z, double offsetX, double offsetY, double offsetZ, int r, int g, int b, int a) {
        render3DOutline(camera, x, y, z, offsetX, offsetY, offsetZ, r, g, b, a, 1.0f);
    }

    public static void render3DOutline(ICamera camera, int x, int y, int z, double offsetX, double offsetY, double offsetZ, int r, int g, int b, int a, float line) {
        Color color = new Color(r, g, b, a);

        final AxisAlignedBB bb = new AxisAlignedBB(
                // The start render position.
                x - mc.getRenderManager().viewerPosX,
                y - mc.getRenderManager().viewerPosY,
                z - mc.getRenderManager().viewerPosZ,

                // We have offset for render.
                x + offsetX - mc.getRenderManager().viewerPosX,
                y + offsetY - mc.getRenderManager().viewerPosY,
                z + offsetZ - mc.getRenderManager().viewerPosZ
        );

        camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(
                // Start position.
                bb.minX + mc.getRenderManager().viewerPosX,
                bb.minY + mc.getRenderManager().viewerPosY,
                bb.minZ + mc.getRenderManager().viewerPosZ,

                // offset.
                bb.maxX + mc.getRenderManager().viewerPosX,
                bb.maxY + mc.getRenderManager().viewerPosY,
                bb.maxZ + mc.getRenderManager().viewerPosZ))) {
            // Render.
            // Prepare.
            prepare(line);

            // Render global.
            RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

            // Release.
            release();
        }
    }

    public static void renderTracer(float partialTicks, Entity entity, int r, int g, int b, int a, float line) {
        final Vec3d pos = RevenantEntityInterpolizerUtil.getInterpolatedPos(entity, partialTicks).subtract(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY, mc.getRenderManager().renderPosZ);

        if (pos == null) {
            return;
        }

        final boolean bobbing = mc.gameSettings.viewBobbing;

        mc.gameSettings.viewBobbing = false;
        mc.entityRenderer.setupCameraTransform(partialTicks, 0);

        final Vec3d forward = new Vec3d(0, 0, 1).rotatePitch(-(float) Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(mc.player.rotationYaw));

        TurokRenderGL.drawLine3D((double) forward.x, (double) forward.y + mc.player.getEyeHeight(), (float) forward.z, (double) pos.x, (double) pos.y, (double) pos.z, r, g, b, a, line);

        mc.gameSettings.viewBobbing = bobbing;
        mc.entityRenderer.setupCameraTransform(partialTicks, 0);
    }
}