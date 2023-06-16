package ru.sasisa.differentponies.api.ability;

public class ActiveAbility {
    public boolean IsPreparing = false;

    /*
     * Returns true if preparation mode needed
     * In which case OnUse() event would be call upon next usage.
     */
    public boolean OnPrepare()
    {
        IsPreparing = true;
        return false;
    }

    public void OnUse()
    {

    }
}
