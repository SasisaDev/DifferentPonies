package ru.sasisa.differentponies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.sasisa.differentponies.client.networking.ClientChannel;

@Environment(EnvType.CLIENT)
public class DifferentponiesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientChannel.Bootstrap();
    }
}
