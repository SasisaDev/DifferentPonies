package ru.sasisa.differentponies.api.interfaces;

public interface IHasEnergy {

    public int GetMaxEnergy();
    public void SetMaxEnergy(int newMaxEnergy);

    public int GetEnergy();
    public void SetEnergy(int newEnergy);
    public void IncrementEnergy(int inc);
    public void DecrementEnergy(int dec);
}
