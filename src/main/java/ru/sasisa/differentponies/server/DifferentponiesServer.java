package ru.sasisa.differentponies.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.Utils;
import ru.sasisa.differentponies.api.interfaces.IPony;
import ru.sasisa.differentponies.api.networking.ChannelConstants;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

import java.util.logging.Logger;

@Environment(EnvType.SERVER)
public class DifferentponiesServer implements DedicatedServerModInitializer {
    Logger LOGGER = Logger.getLogger("Differentponies");

    @Override
    public void onInitializeServer() {
        ServerPlayerEvents.AFTER_RESPAWN.register((ServerPlayerEntity oldPlayer, ServerPlayerEntity player, boolean alive) -> {
            Race race = Utils.GetRaceFromLuckperms(player);
            ((IPony)(Object)player).SetRace(race);

            Differentponies.manager.RegisterPlayer(player);

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(Utils.IntFromRace(race));

            ServerPlayNetworking.send(player, ChannelConstants.S2C_SEND_RACE_PACKET_ID, buf);
        });

        ServerPlayConnectionEvents.JOIN.register(
                (ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) -> {
                    Race race = Utils.GetRaceFromLuckperms(handler.player);
                    ((IPony)(Object)handler.player).SetRace(race);

                    Differentponies.manager.RegisterPlayer(handler.player);

                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(Utils.IntFromRace(race));

                    LOGGER.info("Joined player race is: " + String.valueOf(Utils.IntFromRace(race)));

                    ServerPlayNetworking.send(handler.player, ChannelConstants.S2C_SEND_RACE_PACKET_ID, buf);
                });
    }
}
