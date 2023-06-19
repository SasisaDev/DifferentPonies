package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

import java.util.List;

// When there are X players of same race, you get additional resources
public class AbilityPassiveFoodEffect extends PassiveAbility {

    List<Item> target;
    StatusEffectInstance effect;

    public AbilityPassiveFoodEffect(StatusEffectInstance eff, List<Item> targets)
    {
        target = targets;
        eff = effect;
    }

    @Override
    public void OnEatFood(PlayerEntity player, ItemStack food)
    {
        for(Item item : target)
        {
            if(food.isOf(item))
            {
                player.addStatusEffect(effect);
                return;
            }
        }
    }
}
