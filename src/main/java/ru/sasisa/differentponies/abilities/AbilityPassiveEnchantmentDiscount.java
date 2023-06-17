package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.player.PlayerEntity;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

public class AbilityPassiveEnchantmentDiscount extends PassiveAbility {
    private float modifier = 1;

    public AbilityPassiveEnchantmentDiscount(float mod)
    {
        modifier = mod;
    }

    @Override
    public float GetEnchantmentCostModifier() {
        return modifier;
    }
}
