package ru.sasisa.differentponies.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.sasisa.differentponies.Differentponies;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.api.interfaces.ICloudsWalkable;
import ru.sasisa.differentponies.api.interfaces.IHasEnergy;
import ru.sasisa.differentponies.api.interfaces.IPony;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerEntityMixin, ICloudsWalkable, IHasEnergy, IPony {

    private Race race = Race.NONE;
    private RaceAbilitySet pony_abilities = null;

    private int maxEnergy = 0;
    private int energy = 0;

    // 0 for unnoticed, 1 for drained, 2 for filled
    private int energyStateLastTick = 0;

    /*@Inject(method = "checkFallFlying()Z", at = @At(value = "INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    public void customFallFlyingCheck(CallbackInfoReturnable<Boolean> cir)
    {
        if(HasWings())
        {
            ((PlayerEntity)(Object)this).startFallFlying();
            cir.setReturnValue(true);
        }
    }*/

    @ModifyVariable(method= "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    public float customBlockBreakingSpeed(float f)
    {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if(race == Race.SEA) {
            if (player.isSubmergedIn(FluidTags.WATER)) {
                f *= 5.0F;

                if (!player.isOnGround()) {
                    f *= 5.0F;
                }
            } else if (EnchantmentHelper.getEquipmentLevel(Differentponies.EARTH_AFFINITY, player) <= 0){
                f /= 2.0F;
            }
        }
        else if(race == Race.ALICORN)
        {
            if (!player.isSubmergedIn(FluidTags.WATER) && !player.isOnGround()) {
                f *= 5.0F;
            }
        }
        else if(race == Race.PEGASUS)
        {
            if(!player.isSubmergedIn(FluidTags.WATER))
            {
                f /= 1.5F;
            }
        }

        return f;
    }

    @Inject(method = "addExperience(I)V", at = @At("HEAD"))
    public void customAddExperience(int experience, CallbackInfo ci)
    {
        pony_abilities.Passives.forEach((el) -> {el.OnGetExperience(((PlayerEntity) (Object) this), experience);});
    }

    @ModifyVariable(method= "applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V", at = @At("HEAD"), argsOnly = true)
    public int modifyEnchantmentCost(int experienceLevels)
    {
        float modifier = 1;

        for(PassiveAbility ability : pony_abilities.Passives)
        {
            modifier *= ability.GetEnchantmentCostModifier();
        }

        return (int)((float)experienceLevels * modifier);
    }

    @Inject(method= "applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V", at = @At("TAIL"))
    public void onEnchantedItem(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci)
    {
        for(PassiveAbility ability : pony_abilities.Passives)
        {
            ability.OnEnchantedItem((PlayerEntity)(Object)this, enchantedItem, experienceLevels);
        }
    }

    @Inject(method="eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    public void onEatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir)
    {
        PlayerEntity player = (PlayerEntity)(Object)this;

        float mod = 1;

        for(PassiveAbility ability : GetAbilitySet().Passives)
        {
            mod *= ability.GetFoodModifier(stack);

            ability.OnEatFood(player, stack);
        }

        mod -= 1;

        FoodComponent food = stack.getItem().getFoodComponent();
        player.getHungerManager().add((int)((float)food.getHunger() * mod), food.getSaturationModifier());
    }

    @Inject(method="tick()V", at = @At("HEAD"))
    public void onTick(CallbackInfo ci)
    {
        if(pony_abilities != null) {
            for (PassiveAbility ability : pony_abilities.Passives) {
                ability.Tick((PlayerEntity) (Object) this);
            }

            if(energy == 0 && energyStateLastTick != 1) {
                for (PassiveAbility ability : pony_abilities.Passives) {
                    ability.OnEnergyEmptied((PlayerEntity) (Object) this);
                }
                energyStateLastTick = 1;
            } else if(energy == maxEnergy && energyStateLastTick != 2) {
                for (PassiveAbility ability : pony_abilities.Passives) {
                    ability.OnEnergyFilled((PlayerEntity) (Object) this);
                }
                energyStateLastTick = 2;
            }

            if(!((PlayerEntity)(Object)this).hasStatusEffect(Differentponies.DRAIN_ENERGY))
            {
                IncrementEnergy(1);
            }
        }
    }

    @Override
    public Race GetRace() {
        return race;
    }

    @Override
    public void SetRace(Race _race) {
        this.race = _race;
    }

    @Override
    public RaceAbilitySet GetAbilitySet() {
        return pony_abilities;
    }

    @Override
    public void SetAbilitySet(RaceAbilitySet set) {
        pony_abilities = set;

        float healthModifier = 1;
        float flySpeedModifier = 1;

        for(PassiveAbility ability : pony_abilities.Passives)
        {
            healthModifier *= ability.GetHealthModifier();
            flySpeedModifier *= ability.GetFlySpeedModifier();

            ability.OnInitialization((PlayerEntity)(Object)this);
        }

        ((PlayerEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20 * healthModifier);
        ((PlayerEntity)(Object)this).getAbilities().setFlySpeed(0.05F * flySpeedModifier);
    }

    @Override
    public boolean HasWings() {
        if(race == Race.BAT || race == Race.ALICORN || race == Race.PEGASUS || race == Race.GRIFFON) {
            return true;
        } else if(race == Race.CHANGLING || race == Race.GOOD_CHANGLING)
        {
            // TODO morphing
            return true;
        }
        return false;
    }

    @Override
    public void AddExperienceUnhooked(int experience) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        player.addScore(experience);
        player.experienceProgress += (float)experience / (float)player.getNextLevelExperience();
        player.totalExperience = MathHelper.clamp((int)(player.totalExperience + experience), (int)0, (int)Integer.MAX_VALUE);

        while(player.experienceProgress < 0.0F) {
            float f = player.experienceProgress * (float)player.getNextLevelExperience();
            if (player.experienceLevel > 0) {
                player.addExperienceLevels(-1);
                player.experienceProgress = 1.0F + f / (float)player.getNextLevelExperience();
            } else {
                player.addExperienceLevels(-1);
                player.experienceProgress = 0.0F;
            }
        }

        while(player.experienceProgress >= 1.0F) {
            player.experienceProgress = (player.experienceProgress - 1.0F) * (float)player.getNextLevelExperience();
            player.addExperienceLevels(1);
            player.experienceProgress /= (float)player.getNextLevelExperience();
        }
    }

    @Override
    public boolean CanWalkOnClouds() {
        return HasWings();
    }

    @Override
    public int GetMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void SetMaxEnergy(int newMaxEnergy) {
        maxEnergy = newMaxEnergy;
        if(energy > maxEnergy)
        {
            energy = maxEnergy;
            UpdateEnergyLastTickState();
        }
    }

    @Override
    public int GetEnergy() {
        return energy;
    }

    @Override
    public void SetEnergy(int newEnergy) {
        energy = newEnergy;
        if(energy > maxEnergy)
        {
            energy = maxEnergy;
        }
        UpdateEnergyLastTickState();
    }

    @Override
    public void IncrementEnergy(int inc) {
        energy += inc;
        if(energy > maxEnergy)
        {
            energy = maxEnergy;
        }

        UpdateEnergyLastTickState();
    }

    @Override
    public void DecrementEnergy(int dec) {
        energy -= dec;
        if(energy < 0)
        {
            energy = 0;
        }

        UpdateEnergyLastTickState();
    }

    private void UpdateEnergyLastTickState()
    {
        if(energy != maxEnergy && energy != 0)
        {
            energyStateLastTick = 0;
        }
    }
}
