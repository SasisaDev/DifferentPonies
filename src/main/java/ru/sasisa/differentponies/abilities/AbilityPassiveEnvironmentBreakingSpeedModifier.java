package ru.sasisa.differentponies.abilities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveEnvironmentBreakingSpeedModifier extends PassiveAbility {

    public enum Environment{
        SUBMERGED_IN_WATER,
        SUBMERGED_IN_LAVA,
        IN_AIR,
        ON_GROUND,
        BASE
    }

    Environment env = Environment.ON_GROUND;
    float mod = 1;

    public AbilityPassiveEnvironmentBreakingSpeedModifier(float modifier, Environment environment)
    {
        mod = modifier;
        env = environment;
    }

    public AbilityPassiveEnvironmentBreakingSpeedModifier(float modifier)
    {
        mod = modifier;
    }

    public float GetBlockBreakModifier(BlockState blockState, PlayerEntity player)
    {
        float f = 1;

        switch(env)
        {
            case ON_GROUND -> {
                if(!player.isSubmergedIn(FluidTags.WATER) && !player.isSubmergedIn(FluidTags.WATER) && player.isOnGround())
                {
                    f *= mod;
                }
            }
            case BASE -> {
                f *= mod;
            }
            case IN_AIR -> {
                if(!player.isSubmergedIn(FluidTags.WATER) && !player.isSubmergedIn(FluidTags.WATER) && !player.isOnGround())
                {
                    f *= mod;
                }
            }
            case SUBMERGED_IN_LAVA -> {
                if(player.isSubmergedIn(FluidTags.LAVA))
                {
                    f *= mod;
                }
            }
            case SUBMERGED_IN_WATER -> {
                if(player.isSubmergedIn(FluidTags.WATER))
                {
                    f *= mod;
                }
            }
        }

        return f;
    }
}
