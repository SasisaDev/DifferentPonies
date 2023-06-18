package ru.sasisa.differentponies.api.ability;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PassiveAbility {

    public void OnGetExperience(PlayerEntity player, int experience)
    {

    }

    public void Tick(PlayerEntity player) {}

    public float GetEnchantmentCostModifier() {
        return 1;
    }

    public float GetHealthModifier() {
        return 1;
    }

    public float GetDropModifier(ItemStack stack, @Nullable BlockState blockState){ return 1; }

    public float GetFoodModifier(ItemStack stack) {
        return 1;
    }

    public float GetDealtDamageModifier(PlayerEntity player, LivingEntity mob, DamageSource source, float damage) {
        return 1;

    }
    public float GetIncomingDamageModifier() {
        return 1;
    }

    public void OnEnchantedItem(PlayerEntity player, ItemStack item, int experienceLevels) {

    }

    public void OnDamageMob(PlayerEntity player, LivingEntity mob, DamageSource source, float damage)
    {

    }
}
