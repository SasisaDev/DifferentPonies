package ru.sasisa.differentponies.server.luckperms;

import java.util.UUID;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;

@Environment(EnvType.SERVER)
public class LuckPermsAPI {
    public static LuckPerms API = LuckPermsProvider.get();

    public static User getUserByUUID(UUID uuid) {
        return API.getUserManager().getUser(uuid);
    }
}
