package ru.sasisa.differentponies.api.networking;

import net.minecraft.util.Identifier;

public class ChannelConstants {
    public static final Identifier C2S_USE_SKILL_PACKET_ID = new Identifier("pony_skill", "use_skill");
    public static final Identifier C2S_REQUEST_RACE_PACKET_ID = new Identifier("pony_skill", "request_race");
    public static final Identifier S2C_SEND_RACE_PACKET_ID = new Identifier("pony_skill", "send_race");
}
