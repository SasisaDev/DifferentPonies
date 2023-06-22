package ru.sasisa.differentponies.abilities;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.tag.FluidTags;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.interfaces.IHasEnergy;

// When there are X players of same race, you get additional resources
public class AbilityPassiveFly extends PassiveAbility {

    @Override
    public void OnInitialization(PlayerEntity player)
    {
        // player.getAbilities().allowFlying = true;
    }

    @Override
    public void ClientMovementTick(ClientPlayerEntity player)
    {
        if(!player.getAbilities().allowFlying &&
           (player.isSubmergedIn(FluidTags.WATER) || player.isSubmergedIn(FluidTags.LAVA)) &&
           player.getAbilities().flying)
        {
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
        }

        if(player.getAbilities().flying && !player.hasStatusEffect(Differentponies.DRAIN_ENERGY))
        {
            player.sendAbilitiesUpdate();
        }
    }

    @Override
    public boolean CanFly() {return true;}

    @Override
    public void OnEnergyEmptied(PlayerEntity player)
    {
        player.getAbilities().flying = false;
        if(player.world.isClient)
        {
            player.sendAbilitiesUpdate();
        }
        else
        {
            player.addStatusEffect(new StatusEffectInstance(Differentponies.DRAIN_ENERGY, 5 * 20, 3, true, false));
        }
    }

    @Override
    public void Tick(PlayerEntity player)
    {
        if(player.getAbilities().flying && !player.getAbilities().creativeMode)
        {
            if(((IHasEnergy)(Object)player).GetEnergy() < 1)
            {
                OnEnergyEmptied(player);
            }

            if(player.getY() > 259) {
                OnEnergyEmptied(player);
            }

            if(!player.world.isClient) {
                player.addStatusEffect(new StatusEffectInstance(Differentponies.DRAIN_ENERGY, 5, player.isSprinting() ? 2 : 1, true, false));
            }
        }
    }
}
