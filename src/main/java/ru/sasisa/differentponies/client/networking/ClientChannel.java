package ru.sasisa.differentponies.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.Utils;
import ru.sasisa.differentponies.api.ability.AbilityManager;
import ru.sasisa.differentponies.api.interfaces.IPony;
import ru.sasisa.differentponies.api.networking.ChannelConstants;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

public class ClientChannel {

    static public void Bootstrap()
    {
        ClientPlayNetworking.registerGlobalReceiver(ChannelConstants.S2C_SEND_RACE_PACKET_ID, (client, handler, buf, responseSender) -> {
            int race = buf.readInt();

            client.execute(() -> {
                if(client.player == null) { return; }

                ((IPony)client.player).SetRace(Utils.RaceFromInt(race));

                Differentponies.manager.RegisterPlayer(client.player);
            });
        });
    }
}
