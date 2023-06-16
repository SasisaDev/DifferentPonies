package ru.sasisa.differentponies.api.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityManager {
    public interface AbilitySetCreator
    {
        abstract public RaceAbilitySet Create();
    }
    Map<Race, AbilitySetCreator> REGISTRY = new HashMap<>();

    public void RegisterPlayer(PlayerEntity player)
    {
        AbilitySetCreator creator = REGISTRY.get(((IPlayerEntityMixin)(player)).GetRace());
        if(creator == null) { return; }

        ((IPlayerEntityMixin)(Object)(player)).SetAbilitySet(creator.Create());
    }

    public void UseAbility(ServerPlayerEntity player, int ability)
    {
        // Find player's race

        // Call ability
    }

    public void BindAbilitySet(Race race, AbilitySetCreator set)
    {
        REGISTRY.put(race, set);
    }
}
