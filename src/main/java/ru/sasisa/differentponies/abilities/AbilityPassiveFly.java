package ru.sasisa.differentponies.abilities;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

// When there are X players of same race, you get additional resources
public class AbilityPassiveFly extends PassiveAbility {

    @Override
    public void OnInitialization(PlayerEntity player)
    {
        player.getAbilities().allowFlying = true;
    }

    @Override
    public void ClientMovementTick(ClientPlayerEntity player)
    {
        /*if(!player.isOnGround() && !player.isSubmergedInWater() && !player.isSubmergedIn(FluidTags.LAVA) && player.input.jumping)
        {
            player.getAbilities().flying = true;
            player.sendAbilitiesUpdate();
        }*/
    }
    @Override
    public void Tick(PlayerEntity player)
    {

    }
}