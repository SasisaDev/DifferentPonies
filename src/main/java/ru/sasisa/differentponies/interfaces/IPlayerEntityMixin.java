package ru.sasisa.differentponies.interfaces;

import ru.sasisa.differentponies.api.Race;

public interface IPlayerEntityMixin {

    public Race GetRace();
    public void SetRace(Race race);
}
