package me.rina.racc.manager;

// Minecraft.
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

// Mixin.
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

// Javax.
import javax.annotation.Nullable;

// Java.
import java.util.Map;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantMixinManager implements IFMLLoadingPlugin {
    private String managerTag;

    public RevenantMixinManager() {
        this.managerTag = "mixins";

        MixinBootstrap.init();

        Mixins.addConfiguration("mixins.revenant.json");

        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public String getManagerTag() {
        return this.managerTag;
    }
}