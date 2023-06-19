package ru.sasisa.differentponies.abilities;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveFlySpeedModifier extends PassiveAbility {
    private float modifier = 1;

    public AbilityPassiveFlySpeedModifier(float mod)
    {
        modifier = mod;
    }

    @Override
    public float GetFlySpeedModifier() {
        return modifier;
    }
}
