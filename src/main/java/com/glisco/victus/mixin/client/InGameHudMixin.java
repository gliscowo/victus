package com.glisco.victus.mixin.client;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.OverlaySpriteProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Unique
    int heartX, heartY, heartIndex;

    @Unique
    HeartAspectComponent aspectComponent = null;

    @Inject(method = "renderHealthBar", at = @At("HEAD"))
    private void storeAspectComponent(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        this.aspectComponent = Victus.ASPECTS.get(player);
    }

    @Inject(method = "renderHealthBar", at = @At("RETURN"))
    private void releaseAspectComponent(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        this.aspectComponent = null;
    }

    @Inject(method = "renderHealthBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void storeLocals(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci, InGameHud.HeartType type, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        this.heartX = p;
        this.heartY = q;
        this.heartIndex = m;
    }

    @Inject(method = "drawHeart", at = @At("TAIL"), cancellable = true)
    private void renderRechargingOutline(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (type != InGameHud.HeartType.CONTAINER) return;
        if (!aspectComponent.recharging() || aspectComponent.getAspect(heartIndex) == null) return;

        RenderSystem.setShaderTexture(0, HeartAspect.HEART_ATLAS_TEXTURE);
        drawTexture(matrices, heartX, heartY, 55, 55, 9, 9, 64, 64);
        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }

    @Inject(method = "renderHealthBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V", ordinal = 3, shift = At.Shift.AFTER))
    private void renderOverlay(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        final var aspect = aspectComponent.getAspect(heartIndex);
        if (aspect == null) return;

        RenderSystem.setShaderTexture(0, aspect.getAtlas());
        renderAspect(matrices, heartX, heartY, aspect.getTextureIndex(), aspect.getRechargeProgress());

        if (aspect instanceof OverlaySpriteProvider spriteProvider && spriteProvider.shouldRenderOverlay()) {
            int color = spriteProvider.getOverlayTint();
            RenderSystem.setShaderColor(getComponent(color, 16), getComponent(color, 8), getComponent(color, 0), 1);
            renderAspect(matrices, heartX, heartY, spriteProvider.getOverlayIndex(), aspect.getRechargeProgress());
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }

        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }

    private static void renderAspect(MatrixStack matrices, int x, int y, int textureIndex, float rechargeProgress) {
        int u = textureIndex % 8 * 8;
        int v = textureIndex / 8 * 8;
        drawTexture(matrices, x + 1, y + 1, u, v, Math.round(rechargeProgress * 7), 7, 64, 64);
    }

    private static float getComponent(int rgb, int shift) {
        return ((rgb >> shift) & 0xFF) / 255f;
    }

}
