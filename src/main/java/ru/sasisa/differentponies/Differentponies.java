package ru.sasisa.differentponies;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.sasisa.differentponies.abilities.*;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.AbilityManager;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.block.CloudBlock;

import java.util.List;

public class Differentponies implements ModInitializer {
    public static AbilityManager manager = new AbilityManager();

    public static final CloudBlock CLOUD_BLOCK = new CloudBlock(FabricBlockSettings.of(Material.POWDER_SNOW).strength(0.2f).dynamicBounds());

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("differentponies", "cloud"), CLOUD_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("differentponies", "cloud"), new BlockItem(CLOUD_BLOCK, new FabricItemSettings()));

        // To not get crashes
        manager.BindAbilitySet(Race.NONE, () -> {return new RaceAbilitySet(List.of(), List.of());});

        manager.BindAbilitySet(Race.UNICORN, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveXPBonus(0.3F),
                            new AbilityPassiveEnchantmentDiscount(0),
                            new AbilityPassiveHealthModifier(0.8F),
                            new AbilityPassiveUnionPower()));
        });

        manager.BindAbilitySet(Race.EARTH, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(1.5F),
                            new AbilityPassiveHarvestModifier(2F, 15),
                            new AbilityPassiveMobDamage(1.1F, (mob)-> {return mob.getGroup() == EntityGroup.DEFAULT || mob.getGroup() == EntityGroup.AQUATIC;})));
        });

        manager.BindAbilitySet(Race.PEGASUS, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of());
        });

        manager.BindAbilitySet(Race.ALICORN, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(1.25F)));
        });

        manager.BindAbilitySet(Race.ZEBRA, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.9F)));
        });

        manager.BindAbilitySet(Race.SEA, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.9F)));
        });

        manager.BindAbilitySet(Race.KIRIN, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(1.5F)));
        });

        manager.BindAbilitySet(Race.BAT, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveFoodModifier(2.0F, (food)->{return food.isOf(Items.APPLE);})));
        });

        manager.BindAbilitySet(Race.CHANGLING, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.7F)));
        });

        manager.BindAbilitySet(Race.GOOD_CHANGLING, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.75F)));
        });
    }
}
