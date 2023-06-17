package ru.sasisa.differentponies.api.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class PassiveAbility {

    public void OnGetExperience(PlayerEntity player, int experience)
    {

    }

    public float GetEnchantmentCostModifier() {
        return 1;
    }

    public float GetHealthModifier() {
        return 1;
    }

    public void OnEnchantedItem(PlayerEntity player, ItemStack item, int experienceLevels) {

    }
}
