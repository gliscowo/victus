package com.glisco.victus.client;

import com.glisco.victus.network.VictusPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VictusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VictusPackets.registerClientListeners();
    }
}
