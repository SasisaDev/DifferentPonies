package ru.sasisa.differentponies.abilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

public class AbilityPassiveXPBonus extends PassiveAbility {
    private float modifier = 0;

    public AbilityPassiveXPBonus(float mod)
    {
        modifier = mod;
    }

    @Override
    public void OnGetExperience(PlayerEntity player, int exp)
    {
        ((IPlayerEntityMixin)(Object)player).AddExperienceUnhooked((int)((float)exp * modifier));
    }
}
