package com.glisco.victus.mixin.client;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.OverlaySpriteProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 3), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderVictusOverlay(MatrixStack matrices, CallbackInfo ci, PlayerEntity playerEntity, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s, int t, int u, int v, int w, int heartIndex, int aa, int ab, int ac, int ad, int ae, int af) {
        HeartAspectComponent aspectComponent = Victus.ASPECTS.get(playerEntity);

        final HeartAspect aspect = aspectComponent.getAspect(heartIndex);
        if (aspect == null) return;

        if (aspectComponent.recharging()) {
            client.getTextureManager().bindTexture(HeartAspect.HEART_ATLAS_TEXTURE);
            drawTexture(matrices, ad, ae, 1000, 55, 55, 9, 9, 64, 64);
        }

        client.getTextureManager().bindTexture(aspect.getAtlas());
        renderAspect(matrices, ad, ae, aspect.getTextureIndex(), aspect.getRechargeProgress());

        if (aspect instanceof OverlaySpriteProvider && ((OverlaySpriteProvider) aspect).shouldRenderOverlay()) {
            OverlaySpriteProvider spriteProvider = (OverlaySpriteProvider) aspect;
            int color = spriteProvider.getOverlayTint();
            RenderSystem.color4f(getComponent(color, 16), getComponent(color, 8), getComponent(color, 0), 1);
            renderAspect(matrices, ad, ae, spriteProvider.getOverlayIndex(), aspect.getRechargeProgress());
            RenderSystem.color4f(1, 1, 1, 1);
        }

        client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
    }

    private static void renderAspect(MatrixStack matrices, int x, int y, int textureIndex, float rechargeProgress) {
        int u = textureIndex % 8 * 8;
        int v = textureIndex / 8 * 8;
        drawTexture(matrices, x + 1, y + 1, 1000, u, v, Math.round(rechargeProgress * 7), 7, 64, 64);
    }

    private static float getComponent(int rgb, int shift) {
        return ((rgb >> shift) & 0xFF) / 255f;
    }

}
