package ru.sasisa.differentponies.interfaces;

import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;

public interface IPlayerEntityMixin {

    public Race GetRace();
    public void SetRace(Race race);

    public RaceAbilitySet GetAbilitySet();
    public void SetAbilitySet(RaceAbilitySet set);
}
