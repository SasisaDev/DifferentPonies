package ru.sasisa.differentponies.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
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

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        if(((IPlayerEntityMixin)(Object)this).GetAbilitySet() != null) {
            for (PassiveAbility ability : ((IPlayerEntityMixin) (Object) this).GetAbilitySet().Passives) {
                ability.ClientMovementTick((ClientPlayerEntity) (Object) this);
            }
        }
    }
}
