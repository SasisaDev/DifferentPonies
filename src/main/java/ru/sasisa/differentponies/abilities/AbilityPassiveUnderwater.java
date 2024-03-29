package ru.sasisa.differentponies.abilities;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveUnderwater extends PassiveAbility {

    @Override
    public void Tick(PlayerEntity player)
    {
        if (!player.world.isClient) {
            if (player.isSubmergedIn(FluidTags.WATER)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2*20, 6, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 2*20, 6, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 11*20, 1, true, false));
            }
        }
    }

    @Override
    public float GetBlockBreakModifier(BlockState blockState, PlayerEntity player)
    {
        float f = 1;

        if (player.isSubmergedIn(FluidTags.WATER)) {
            f *= 5.0F;

            if (!player.isOnGround()) {
                f *= 5.0F;
            }
        } else if (EnchantmentHelper.getEquipmentLevel(Differentponies.EARTH_AFFINITY, player) <= 0){
            f /= 2.0F;
        }

        return f;
    }
}
