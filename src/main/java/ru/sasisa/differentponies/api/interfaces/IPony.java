package ru.sasisa.differentponies.api.interfaces;

import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;

public interface IPony {
    public Race GetRace();
    public void SetRace(Race race);

    public RaceAbilitySet GetAbilitySet();
    public void SetAbilitySet(RaceAbilitySet set);

    public boolean HasWings();
}
