package ru.sasisa.differentponies.abilities;

import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveEnchantmentCostModifier extends PassiveAbility {
    private float modifier = 1;

    public AbilityPassiveEnchantmentCostModifier(float mod)
    {
        modifier = mod;
    }

    @Override
    public float GetEnchantmentCostModifier() {
        return modifier;
    }
}
