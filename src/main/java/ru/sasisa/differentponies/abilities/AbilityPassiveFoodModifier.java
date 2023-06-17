package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveFoodModifier extends PassiveAbility {
    public interface FoodSelector {
        public boolean DoesFit(ItemStack stack);
    }

    private float modifier = 1;
    private FoodSelector selector;

    public AbilityPassiveFoodModifier(float mod, FoodSelector sel)
    {
        modifier = mod;
        selector = sel;
    }

    @Override
    public float GetFoodModifier(ItemStack stack) {
        if(selector.DoesFit(stack))
        {
            return modifier;
        }
        return 1;
    }
}
