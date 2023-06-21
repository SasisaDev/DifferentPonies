package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

// When there are X players of same race, you get additional resources
public class AbilityPassiveBatVision extends PassiveAbility {

    @Override
    public void Tick(PlayerEntity player)
    {
        if (!player.world.isClient) {
            if(!player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 999 * 20, 6, true, false));
            }
        }
    }
}
