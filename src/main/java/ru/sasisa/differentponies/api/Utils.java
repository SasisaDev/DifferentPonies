package ru.sasisa.differentponies.api;

import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.sasisa.differentponies.server.luckperms.LuckPermsAPI;

public class Utils {

    public static Race GetRaceFromLuckperms(ServerPlayerEntity player)
    {
        Group group = LuckPermsAPI.API.getGroupManager().getGroup(LuckPermsAPI.getUserByUUID(player.getUuid()).getPrimaryGroup());

        for (PermissionNode node : group.getNodes(NodeType.PERMISSION)) {
            String perm = node.getPermission();

            switch (perm) {
                case "ponyraces.unicorn" -> {
                    return Race.UNICORN;
                }
                case "ponyraces.pegasus" -> {
                    return Race.PEGASUS;
                }
                case "ponyraces.earthpony" -> {
                    return Race.EARTH;
                }
                case "ponyraces.zebra" -> {
                    return Race.ZEBRA;
                }
                case "ponyraces.alicorn" -> {
                    return Race.ALICORN;
                }
                case "ponyraces.kirin" -> {
                    return Race.KIRIN;
                }
                case "ponyraces.sea" -> {
                    return Race.SEA;
                }
                case "ponyraces.changeling" -> {
                    return Race.CHANGLING;
                }
                case "ponyraces.refchangeling" -> {
                    return Race.GOOD_CHANGLING;
                }
                case "ponyraces.griffon" -> {
                    return Race.GRIFFON;
                }
                case "ponyraces.batpony" -> {
                    return Race.BAT;
                }
            }
        }

        return Race.NONE;
    }

    public static Race RaceFromInt(int race)
    {
        switch(race)
        {
            case 0 -> {return Race.UNICORN;}
            case 1 -> {return Race.BAT;}
            case 2 -> {return Race.SEA;}
            case 3 -> {return Race.EARTH;}
            case 4 -> {return Race.KIRIN;}
            case 5 -> {return Race.ZEBRA;}
            case 6 -> {return Race.ALICORN;}
            case 7 -> {return Race.GRIFFON;}
            case 8 -> {return Race.PEGASUS;}
            case 9 -> {return Race.CHANGLING;}
            case 10 -> {return Race.GOOD_CHANGLING;}
        }
        return Race.NONE;
    }

    public static int IntFromRace(Race race)
    {
        switch(race)
        {
            case UNICORN -> {return 0;}
            case BAT -> {return 1;}
            case SEA -> {return 2;}
            case EARTH -> {return 3;}
            case KIRIN -> {return 4;}
            case ZEBRA -> {return 5;}
            case ALICORN -> {return 6;}
            case GRIFFON -> {return 7;}
            case PEGASUS -> {return 8;}
            case CHANGLING -> {return 9;}
            case GOOD_CHANGLING -> {return 10;}
        }
        return -1;
    }
}
