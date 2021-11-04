package com.glisco.victus.network;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class VictusPackets {

    public static final Identifier ASPECT_BROKEN = Victus.id("aspect_broken");
    public static final Identifier REMOVE_FIRST_ASPECT = Victus.id("remove_first_aspect");

    public static void registerClientListeners() {
        ClientPlayNetworking.registerGlobalReceiver(ASPECT_BROKEN, VictusPackets::onAspectBroken);
    }

    public static void registerServerListeners(){
        ServerPlayNetworking.registerGlobalReceiver(REMOVE_FIRST_ASPECT, VictusPackets::onRemovalRequested);
    }

    public static void sendAspectBreak(ServerPlayerEntity player, int index, boolean callHandler) {
        final PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(index);
        buf.writeBoolean(callHandler);
        ServerPlayNetworking.send(player, ASPECT_BROKEN, buf);
    }

    public static void requestAspectRemoval() {
        ClientPlayNetworking.send(REMOVE_FIRST_ASPECT, PacketByteBufs.create());
    }

    @Environment(EnvType.CLIENT)
    private static void onAspectBroken(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf byteBuf, PacketSender packetSender) {
        final int index = byteBuf.readVarInt();
        final boolean callHandler = byteBuf.readBoolean();
        minecraftClient.execute(HeartAspect.createBreakEvent(minecraftClient, index, callHandler));
    }

    private static void onRemovalRequested(MinecraftServer minecraftServer, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf byteBuf, PacketSender packetSender) {
        if (!player.isCreative()) return;
        player.dropItem(Victus.ASPECTS.get(player).removeAspect());
    }
}
