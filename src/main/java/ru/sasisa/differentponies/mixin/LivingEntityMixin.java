package ru.sasisa.differentponies.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At("TAIL"), cancellable = true)
    public void modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir)
    {
        float mod = 1;

        if(source.getAttacker() != null)
        {
            if(source.getAttacker() instanceof PlayerEntity)
            {
                for (PassiveAbility passive : ((IPlayerEntityMixin) (Object) source.getAttacker()).GetAbilitySet().Passives)
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
                for (PassiveAbility passive : ((IPlayerEntityMixin) (Object) source.getAttacker()).GetAbilitySet().Passives)
                {
                    passive.OnDamageMob((PlayerEntity)source.getAttacker(), (LivingEntity)(Object)this, source, amount);
                }
            }
        }
    }
}
