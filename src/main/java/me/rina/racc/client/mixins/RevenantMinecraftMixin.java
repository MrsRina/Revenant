package me.rina.racc.client.mixins;

// Minecraft.
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

// OpenGL.
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// Revenant.
import me.rina.racc.Revenant;

/**
 *
 * @author Rina.
 * @since 02/10/2020.
 *
 **/
@Mixin(value = Minecraft.class, priority = 9998)
public class RevenantMinecraftMixin {
    @Inject(method = "shutdown", at = @At("HEAD"))
    private void shutdown(CallbackInfo info) {
        Revenant.getModuleManager().onSaveModuleList();
        Revenant.getSocialUserManager().onSaveSocialUserList();

        try {
            Revenant.INSTANCE.onSave();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    private void crash(Minecraft minecraft, CrashReport crash) {
        Revenant.getModuleManager().onSaveModuleList();
        Revenant.getSocialUserManager().onSaveSocialUserList();

        try {
            Revenant.INSTANCE.onSave();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}