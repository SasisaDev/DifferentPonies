package ru.sasisa.differentponies;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;
import ru.sasisa.differentponies.abilities.unicorn.AbilityPassiveXPBonus;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.AbilityManager;
import ru.sasisa.differentponies.api.ability.ActiveAbility;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.block.CloudBlock;

import java.util.ArrayList;
import java.util.List;

public class Differentponies implements ModInitializer {
    public static AbilityManager manager = new AbilityManager();

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, "cloud_block", new CloudBlock(FabricBlockSettings.of(Material.POWDER_SNOW)));

        manager.BindAbilitySet(Race.UNICORN, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveXPBonus()));
        });
    }
}
