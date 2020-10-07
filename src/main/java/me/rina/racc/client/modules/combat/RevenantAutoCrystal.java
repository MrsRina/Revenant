package me.rina.racc.client.modules.combat;

// Revenant Module.
import me.rina.racc.client.RevenantModule;

// Revenant Setting.
import me.rina.racc.client.RevenantSetting;

// Revenant Events.
import me.rina.racc.event.network.RevenantEventPacket;
import me.rina.racc.event.render.RevenantRenderEvent;

// Revenant Friends.
import me.rina.racc.manager.RevenantSocialManager;

// Revenant Font Utils.
import me.rina.turok.font.TurokFontManager;

// Revenant Utils.
import me.rina.racc.util.client.RevenantChatUtil;
import me.rina.racc.util.render.RevenantTessellator;
import me.rina.racc.util.render.RVColor;

// Com.
import com.mojang.realmsclient.gui.ChatFormatting;

// Alpine.
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

// Minecraft.
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

// Java.
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class RevenantAutoCrystal extends RevenantModule
{
    public RevenantAutoCrystal() {
        super("AutoCrystal", "AutoCrystal", "AutoCrystal", Category.COMBAT);
    }

    private BlockPos render;
    private Entity renderEnt;
    // we need this cooldown to not place from old hotbar slot, before we have switched to crystals
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private boolean isPlacing = false;
    private boolean isBreaking = false;
    private int oldSlot = -1;
    private int newSlot;
    private int waitCounter;
    EnumFacing f;
    private static boolean togglePitch = false;


}
