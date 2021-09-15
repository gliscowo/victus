package com.glisco.victus.network;

import com.glisco.victus.Victus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class VictusPackets {

    public static final Identifier ASPECT_BROKEN = Victus.id("aspect_broken");

    public static void registerClientListeners() {
        ClientPlayNetworking.registerGlobalReceiver(ASPECT_BROKEN, VictusPackets::onAspectBroken);
    }

    public static void sendAspectBreak(ServerPlayerEntity player, int index) {
        final var buf = PacketByteBufs.create();
        buf.writeVarInt(index);
        ServerPlayNetworking.send(player, ASPECT_BROKEN, buf);
    }

    @Environment(EnvType.CLIENT)
    private static void onAspectBroken(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf byteBuf, PacketSender packetSender) {
        final int index = byteBuf.readVarInt();
        minecraftClient.execute(() -> {
            Victus.ASPECTS.get(minecraftClient.player).getAspect(index).onBrokenClient();
        });
    }

}
