package ru.sasisa.differentponies.abilities;

import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveHealthModifier extends PassiveAbility {
    private float modifier = 1;

    public AbilityPassiveHealthModifier(float mod)
    {
        modifier = mod;
    }

    @Override
    public float GetHealthModifier() {
        return modifier;
    }
}
