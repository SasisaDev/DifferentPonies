package ru.sasisa.differentponies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import ru.sasisa.differentponies.client.networking.ClientChannel;
import ru.sasisa.differentponies.client.ui.EnergyHUD;

@Environment(EnvType.CLIENT)
public class DifferentponiesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientChannel.Bootstrap();

        HudRenderCallback.EVENT.register(new EnergyHUD());
    }
}
