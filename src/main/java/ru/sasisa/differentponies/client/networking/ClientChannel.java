package ru.sasisa.differentponies.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.Utils;
import ru.sasisa.differentponies.api.ability.AbilityManager;
import ru.sasisa.differentponies.api.networking.ChannelConstants;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

public class ClientChannel {

    static public void Bootstrap()
    {
        ClientPlayNetworking.registerGlobalReceiver(ChannelConstants.S2C_SEND_RACE_PACKET_ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                if(client.player == null) { return; }

                ((IPlayerEntityMixin)client.player).SetRace(Utils.RaceFromInt(buf.getInt(0)));

                Differentponies.manager.RegisterPlayer(client.player);
            });
        });
    }
}
