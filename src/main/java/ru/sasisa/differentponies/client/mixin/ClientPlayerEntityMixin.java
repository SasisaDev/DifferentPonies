package ru.sasisa.differentponies.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.interfaces.ICloudsWalkable;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @ModifyExpressionValue(method = "tickMovement()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z"))
    private boolean customCanFly(boolean canFly)
    {
        boolean customCanFly = false;
        boolean isInLiquid = ((ClientPlayerEntity)(Object)this).isSubmergedIn(FluidTags.WATER) && ((ClientPlayerEntity)(Object)this).isSubmergedIn(FluidTags.LAVA);

        if(((IPlayerEntityMixin)(Object)this).GetAbilitySet() != null) {
            for (PassiveAbility ability : ((IPlayerEntityMixin) (Object) this).GetAbilitySet().Passives) {
                if (ability.CanFly()) {
                    customCanFly = true;
                    break;
                }
            }
        }

        return (canFly || customCanFly) && !isInLiquid;
    }

    @Inject(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    private void customTickMovement_FallFlying(CallbackInfo ci)
    {
        if(((ICloudsWalkable)(Object)this).CanWalkOnClouds())
        {
            ((ClientPlayerEntity)(Object)this).networkHandler.sendPacket(new ClientCommandC2SPacket((ClientPlayerEntity)(Object)this, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        if(((IPlayerEntityMixin)(Object)this).GetAbilitySet() != null) {
            for (PassiveAbility ability : ((IPlayerEntityMixin) (Object) this).GetAbilitySet().Passives) {
                ability.ClientMovementTick((ClientPlayerEntity) (Object) this);
            }
        }
    }
}
