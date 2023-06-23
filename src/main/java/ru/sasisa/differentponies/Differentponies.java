package ru.sasisa.differentponies;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.sasisa.differentponies.abilities.*;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.AbilityManager;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.api.interfaces.IPony;
import ru.sasisa.differentponies.block.CloudBlock;
import ru.sasisa.differentponies.effect.DrainEnergyEffect;
import ru.sasisa.differentponies.enchantment.EarthAffinityEnchantment;

import java.util.List;

public class Differentponies implements ModInitializer {
    public static AbilityManager manager = new AbilityManager();

    public static final CloudBlock CLOUD_BLOCK = new CloudBlock(FabricBlockSettings.of(Material.POWDER_SNOW).strength(0.2f).dynamicBounds());

    public static final Enchantment EARTH_AFFINITY = new EarthAffinityEnchantment();

    public static final StatusEffect DRAIN_ENERGY = new DrainEnergyEffect();

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("differentponies", "drain_energy"), DRAIN_ENERGY);

        Registry.register(Registry.ENCHANTMENT, new Identifier("differentponies", "earth_affinity"), EARTH_AFFINITY);

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
                            new AbilityPassiveEnchantmentCostModifier(0.7F),
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
                            new AbilityPassiveMobDamage(1.25F, (mob)-> {return true /*mob.getGroup() == EntityGroup.DEFAULT || mob.getGroup() == EntityGroup.AQUATIC*/;})));
        });

        manager.BindAbilitySet(Race.PEGASUS, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveFlySpeedModifier(1.5F),
                            new AbilityPassiveFly(),
                            new AbilityPassiveSetEnergy(45 * 20),
                            new AbilityPassiveEnvironmentBreakingSpeedModifier(0.5F, AbilityPassiveEnvironmentBreakingSpeedModifier.Environment.ON_GROUND)));
        });

        manager.BindAbilitySet(Race.GRIFFON, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveFlySpeedModifier(1.35F),
                            new AbilityPassiveFly(),
                            new AbilityPassiveSetEnergy(60 * 20),
                            new AbilityPassiveFoodEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10*20, 1), List.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, Items.MUTTON, Items.RABBIT, Items.SALMON, Items.COD))));
        });

        manager.BindAbilitySet(Race.ALICORN, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(1.25F),
                            new AbilityPassiveFly(),
                            new AbilityPassiveSetEnergy(45 * 20),
                            new AbilityPassiveEnvironmentBreakingSpeedModifier(1.3F, AbilityPassiveEnvironmentBreakingSpeedModifier.Environment.IN_AIR)));
        });

        manager.BindAbilitySet(Race.ZEBRA, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.9F),
                            new AbilityPassiveXPBonus(1.5F)));
        });

        manager.BindAbilitySet(Race.SEA, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.9F),
                            new AbilityPassiveUnderwater()));
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
                    List.of(new AbilityPassiveFoodModifier(2.0F, (food)->{return food.isOf(Items.APPLE);}),
                            new AbilityPassiveFly(),
                            new AbilityPassiveSetEnergy(60 * 20),
                            new AbilityPassiveBatVision(),
                            new AbilityPassiveMobDamage(1.1F, (mob) -> {return !(mob instanceof PlayerEntity);})));
        });

        manager.BindAbilitySet(Race.CHANGLING, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.7F),
                            new AbilityPassiveFly(),
                            new AbilityPassiveSetEnergy(60 * 20),
                            new AbilityPassiveFlySpeedModifier(0.45F)));
        });

        manager.BindAbilitySet(Race.GOOD_CHANGLING, () -> {
            return new RaceAbilitySet(
                    // Actives
                    List.of(),
                    // Passives
                    List.of(new AbilityPassiveHealthModifier(0.75F),
                            new AbilityPassiveFly(),
                            new AbilityPassiveSetEnergy(60 * 20),
                            new AbilityPassiveFlySpeedModifier(0.5F)));
        });

        // Events

        EntityElytraEvents.CUSTOM.register((LivingEntity entity, boolean tickElytra) -> {
            if(entity instanceof IPony pony)
            {
                return pony.HasWings();
            }
            return false;
        });
    }
}
