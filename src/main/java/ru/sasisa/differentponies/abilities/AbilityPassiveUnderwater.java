package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

// When there are X players of same race, you get additional resources
public class AbilityPassiveUnderwater extends PassiveAbility {

    @Override
    public void Tick(PlayerEntity player)
    {
        if (!player.world.isClient) {
            if (player.isSubmergedIn(FluidTags.WATER)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2*20, 6, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 2*20, 6, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2*20, 6, true, false));
            }
        }
    }
}
