package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.interfaces.IHasEnergy;

// When there are X players of same race, you get additional resources
public class AbilityPassiveSetEnergy extends PassiveAbility {

    int energy = 0;

    public AbilityPassiveSetEnergy(int newEnergy)
    {
        energy = newEnergy;
    }
    @Override
    public void OnInitialization(PlayerEntity player)
    {
        if(player instanceof IHasEnergy energyContainer)
        {
            energyContainer.SetMaxEnergy(energy);
            energyContainer.SetEnergy(energy);
        }
    }
}
