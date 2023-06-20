package ru.sasisa.differentponies.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @ModifyExpressionValue(method = "tickMovement()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z"))
    private boolean customCanFly(boolean canFly)
    {
        boolean customCanFly = false;

        if(((IPlayerEntityMixin)(Object)this).GetAbilitySet() != null) {
            for (PassiveAbility ability : ((IPlayerEntityMixin) (Object) this).GetAbilitySet().Passives) {
                if (ability.CanFly()) {
                    customCanFly = true;
                    break;
                }
            }
        }

        return canFly || customCanFly;
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
