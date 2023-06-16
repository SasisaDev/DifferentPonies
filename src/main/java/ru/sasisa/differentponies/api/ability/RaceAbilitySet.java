package ru.sasisa.differentponies.api.ability;

import java.util.List;

public class RaceAbilitySet {
    public List<ActiveAbility> Actives;
    public List<PassiveAbility> Passives;

    public RaceAbilitySet(List<ActiveAbility> actives, List<PassiveAbility> passives)
    {
        Actives = actives;
        Passives = passives;
    }
}
