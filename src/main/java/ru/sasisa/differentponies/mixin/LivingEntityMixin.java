package ru.sasisa.differentponies.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.interfaces.ICloudsWalkable;
import ru.sasisa.differentponies.api.interfaces.IPony;
import ru.sasisa.differentponies.block.CloudBlock;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements ICloudsWalkable {

    @ModifyExpressionValue(method = "tickFallFlying()V", at = @At(value = "INVOKE", target="Lnet/minecraft/item/ElytraItem;isUsable(Lnet/minecraft/item/ItemStack;)Z"))
    public boolean customTickFallFlying_isUsable(boolean canFallFly)
    {
        boolean customCanFallFly = false;

        if((Object)this instanceof ICloudsWalkable walker)
        {
            customCanFallFly = walker.CanWalkOnClouds();
        }
        return canFallFly || customCanFallFly;
    }

    @ModifyExpressionValue(method = "tickFallFlying()V", at = @At(value = "INVOKE", target="Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean customTickFallFlying_isOf(boolean canFallFly)
    {
        boolean customCanFallFly = false;

        if((Object)this instanceof ICloudsWalkable walker)
        {
            customCanFallFly = walker.CanWalkOnClouds();
        }
        return canFallFly || customCanFallFly;
    }

    @ModifyVariable(method = "applyMovementInput(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;",
                    at = @At(value="TAIL"), ordinal = 1)
    public Vec3d modifyAppliedMovementInput(Vec3d vec3d)
    {
        if (((LivingEntity)(Object)this).getBlockStateAtPos().isOf(Differentponies.CLOUD_BLOCK) && CloudBlock.canWalkOnCloud((LivingEntity)(Object)this)) {
            return new Vec3d(vec3d.x, 0.2, vec3d.z);
        }

        return vec3d;
    }

    @Inject(method = "modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At("TAIL"), cancellable = true)
    public void modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir)
    {
        float mod = 1;

        if(source.getAttacker() != null)
        {
            if(source.getAttacker() instanceof PlayerEntity)
            {
                for (PassiveAbility passive : ((IPony) (Object) source.getAttacker()).GetAbilitySet().Passives)
                {
                    mod *= passive.GetDealtDamageModifier((PlayerEntity)source.getAttacker(), (LivingEntity)(Object)this, source, amount);
                }
            }
        }

        cir.setReturnValue(amount * mod);
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("TAIL"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir)
    {
        if(source.getAttacker() != null)
        {
            if(source.getAttacker() instanceof PlayerEntity)
            {
                for (PassiveAbility passive : ((IPony) (Object) source.getAttacker()).GetAbilitySet().Passives)
                {
                    passive.OnDamageMob((PlayerEntity)source.getAttacker(), (LivingEntity)(Object)this, source, amount);
                }
            }
        }

        if((LivingEntity)(Object)this instanceof PlayerEntity player)
        {
            for (PassiveAbility passive : ((IPony)player).GetAbilitySet().Passives)
            {
                passive.OnDamaged(player, source.getAttacker(), source, amount);
            }
        }
    }

    @Override
    public boolean CanWalkOnClouds() {
        return false;
    }
}
