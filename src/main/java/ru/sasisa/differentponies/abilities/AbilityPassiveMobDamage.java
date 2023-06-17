package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveMobDamage extends PassiveAbility {

    public interface MobSelector {
        public boolean DoesFit(LivingEntity mob);
    }

    private float modifier = 1;
    private MobSelector selector;

    public AbilityPassiveMobDamage(float mod, MobSelector sel)
    {
        modifier = mod;
        selector = sel;
    }

    @Override
    public float GetDealtDamageModifier(PlayerEntity player, LivingEntity mob, DamageSource source, float damage)
    {
        if(selector.DoesFit(mob))
        {
            return modifier;
        }
        return 1;
    }
}
