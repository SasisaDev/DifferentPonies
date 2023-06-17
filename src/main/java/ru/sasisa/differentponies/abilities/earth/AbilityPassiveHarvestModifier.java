package ru.sasisa.differentponies.abilities.earth;

import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveHarvestModifier extends PassiveAbility {
    private float modifier = 1;

    public AbilityPassiveHarvestModifier(float mod)
    {
        modifier = mod;
    }

    // Event Hook
}
