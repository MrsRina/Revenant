package me.rina.racc.manager;

// Minecraft.
import me.rina.racc.client.modules.combat.RevenantAutoCrystal;
import me.rina.racc.client.modules.player.RevenantAutoReplenish;
import me.rina.racc.client.modules.player.RevenantFastUse;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

// Java.
import java.util.ArrayList;

// Turok.
import me.rina.turok.render.TurokRenderGL;

// Client modules.
import me.rina.racc.client.modules.client.*;

// Components.
import me.rina.racc.client.components.*;

// Event.
import me.rina.racc.event.render.RevenantEventRender3D;

// Client.
import me.rina.racc.client.RevenantComponent;
import me.rina.racc.client.RevenantModule;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantModuleManager {
    private String managerTag;

    private ArrayList<RevenantModule> moduleList;
    private ArrayList<RevenantComponent> overlayList;

    public RevenantModuleManager(String managerTag) {
        this.managerTag = managerTag;

        // Lists to client.
        this.moduleList  = new ArrayList<>();
        this.overlayList = new ArrayList<>();

        // Client category modules.
        addModuleInList(new RevenantModuleGUI());
        addModuleInList(new RevenantModuleHUDEditor());

        // Combat category modules.
        addModuleInList(new RevenantAutoCrystal());

        // Exploit category modules.

        // Movement category modules.

        // Player category modules.
        addModuleInList(new RevenantFastUse());
        addModuleInList(new RevenantAutoReplenish());

        // Render category modules.

        // Overlay components.
        addComponentInList(new RevenantComponentArrayList());
    }

    public void onSaveModuleList() {
        try {
            for (RevenantModule modules : getModuleList()) {
                modules.onSave();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void onLoadModuleList() {
        try {
            for (RevenantModule modules : getModuleList()) {
                modules.onLoad();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void onUpdateModuleList() {
        for (RevenantModule modules : getModuleList()) {
            if (modules.isEnabled()) {
                modules.onUpdate();
            }
        }
    }

    public void onRender2DModuleList() {
        for (RevenantModule modules : getModuleList()) {
            if (modules.isEnabled()) {
                modules.onRender2D();
            }
        }
    }

    public void onRender3DModuleList(RenderWorldLastEvent event) {
        Revenant.getMinecraft().profiler.startSection("revenant");

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1f);

        RevenantEventRender3D eventRender = new RevenantEventRender3D(event.getPartialTicks());

        for (RevenantModule modules : getModuleList()) {
            if (modules.isEnabled()) {
                modules.onRender3D(eventRender);
            }
        }

        GlStateManager.glLineWidth(1f);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();

        TurokRenderGL.release3D();

        Revenant.getMinecraft().profiler.endSection();
    }

    public void onKeyCodeModuleList(int keyCode) {
        if (keyCode != -1) {
            for (RevenantModule modules : getModuleList()) {
                if (modules.getKeyBind() == keyCode) {
                    modules.toggle();
                }
            }
        }
    }

    public void addModuleInList(RevenantModule module) {
        this.moduleList.add(module);
    }

    public void addComponentInList(RevenantComponent component) {
        this.moduleList.add((RevenantModule) component);
        this.overlayList.add(component);
    }

    public void setManagerTag(String managerTag) {
        this.managerTag = managerTag;
    }

    public ArrayList<RevenantModule> getModuleList() {
        return moduleList;
    }

    public ArrayList<RevenantComponent> getOverlayList() {
        return overlayList;
    }

    public RevenantModule getModuleByTag(String tag) {
        for (RevenantModule modules : getModuleList()) {
            if (modules.getTag().equalsIgnoreCase(tag)) {
                return modules;
            }
        }

        return null;
    }

    public String getManagerTag() {
        return managerTag;
    }
}