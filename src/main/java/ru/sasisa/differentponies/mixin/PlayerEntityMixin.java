package ru.sasisa.differentponies.mixin;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.PassiveAbility;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerEntityMixin {

    private Race race = Race.NONE;
    private RaceAbilitySet abilities = null;

    @Inject(method = "addExperience(I)V", at = @At("HEAD"))
    public void customAddExperience(int experience, CallbackInfo ci)
    {
        abilities.Passives.forEach((el)->{ el.OnGetExperience(((PlayerEntity)(Object)this), experience);});
    }

    @Inject(method="applyEnchantmentCosts", at = @At("HEAD"))
    public void modifyEnchantmentCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci)
    {
        // TODO Doesn't seem to work
        float modifier = 1;

        for(PassiveAbility ability : abilities.Passives)
        {
            modifier *= ability.GetEnchantmentCostModifier();
        }

        experienceLevels = (int)((float)experienceLevels * modifier);

        for(PassiveAbility ability : abilities.Passives)
        {
            ability.OnEnchantedItem((PlayerEntity)(Object)this, enchantedItem, experienceLevels);
        }
    }

    @Inject(method="tick()V", at = @At("HEAD"))
    public void onTick(CallbackInfo ci)
    {
        if(abilities != null) {
            for (PassiveAbility ability : abilities.Passives) {
                ability.Tick((PlayerEntity) (Object) this);
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
        return abilities;
    }

    @Override
    public void SetAbilitySet(RaceAbilitySet set) {
        abilities = set;

        float healthModifier = 1;

        for(PassiveAbility ability : abilities.Passives)
        {
            healthModifier *= ability.GetHealthModifier();
        }

        ((PlayerEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
                .setBaseValue(((PlayerEntity)(Object)this).getMaxHealth() * healthModifier);
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
    public boolean HasWings() {
        if(race == Race.BAT || race == Race.ALICORN || race == Race.PEGASUS) {
            return true;
        } else if(race == Race.CHANGLING || race == Race.GOOD_CHANGLING)
        {
            // TODO morphing
            return true;
        }
        return false;
    }
}
