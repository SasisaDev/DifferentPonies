package ru.sasisa.differentponies.server.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Environment(EnvType.SERVER)
@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @ModifyExpressionValue(method = "onUpdatePlayerAbilities(Lnet/minecraft/network/packet/c2s/play/UpdatePlayerAbilitiesC2SPacket;)V", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z"))
    private boolean onUpdatePlayerAbilities_CanFly(boolean canFly)
    {
        boolean customCanFly = false;

        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler)(Object)this;
        ServerPlayerEntity player = handler.player;

        if(((IPlayerEntityMixin)player).GetAbilitySet() != null) {
            for (PassiveAbility ability : ((IPlayerEntityMixin)player).GetAbilitySet().Passives) {
                if (ability.CanFly()) {
                    customCanFly = true;
                    break;
                }
            }
        }

        return canFly || customCanFly;
    }
}
