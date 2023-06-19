package ru.sasisa.differentponies.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Mixin(LootTable.class)
public class LootTableMixin {

    @ModifyVariable(method = "generateLoot(Lnet/minecraft/loot/context/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", at = @At(value = "TAIL"))
    private ObjectArrayList<ItemStack> modifyDroppedStack(ObjectArrayList<ItemStack> stacks, LootContext ctx)
    {
        if(ctx.hasParameter(LootContextParameters.BLOCK_STATE) && ctx.hasParameter(LootContextParameters.THIS_ENTITY)) {
            Entity entity = ctx.get(LootContextParameters.THIS_ENTITY);
            if(entity instanceof PlayerEntity player) {
                if(((IPlayerEntityMixin)player).GetAbilitySet() == null)
                {
                    return stacks;
                }

                BlockState blockState = ctx.get(LootContextParameters.BLOCK_STATE);

                for (ItemStack stack : stacks) {
                    float modifier = 1;

                    for(PassiveAbility ability : ((IPlayerEntityMixin)player).GetAbilitySet().Passives)
                    {
                        modifier *= ability.GetDropModifier(stack, blockState);
                    }

                    stack.setCount((int) Math.ceil((float) stack.getCount() * modifier));
                }
            }
        }
        return stacks;
    }
}
