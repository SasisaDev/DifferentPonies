package ru.sasisa.differentponies.abilities;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveHarvestModifier extends PassiveAbility {
    private float modifier = 1;
    private int chances = 100;
    protected final Random random;

    public AbilityPassiveHarvestModifier(float mod) {
        this(mod, 100);
    }

    public AbilityPassiveHarvestModifier(float mod, int chance)
    {
        chances = chance;
        modifier = mod;

        random = Random.create();
    }

    @Override
    public float GetDropModifier(ItemStack stack, @Nullable BlockState blockState) {
        if(chances >= random.nextInt(100)) {
            if (blockState != null) {
                if (blockState.getBlock() instanceof CropBlock) {
                    return modifier;
                }
            }
        }

        return 1;
    }
}
