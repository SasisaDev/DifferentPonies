package ru.sasisa.differentponies.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import ru.sasisa.differentponies.api.interfaces.IHasEnergy;

public class DrainEnergyEffect extends StatusEffect {
    public DrainEnergyEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xffc14f);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof IHasEnergy energy) {
            energy.DecrementEnergy(amplifier);
        }
    }
}