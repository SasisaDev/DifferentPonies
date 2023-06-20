package ru.sasisa.differentponies.server.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @ModifyVariable(method = "handleFall", at = @At("HEAD"), argsOnly = true)
    public double onFall(double heightDifference)
    {
        boolean isFlying = ((ServerPlayerEntity) (Object) this).getAbilities().flying;

        if(isFlying)
        {
            return 0;
        }

        return heightDifference;
    }
}
