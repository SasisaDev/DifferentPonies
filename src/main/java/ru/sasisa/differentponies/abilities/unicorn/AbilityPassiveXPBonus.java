package ru.sasisa.differentponies.abilities.unicorn;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import ru.sasisa.differentponies.api.ability.PassiveAbility;

public class AbilityPassiveXPBonus extends PassiveAbility {

    @Override
    public void OnGetExperience(PlayerEntity player, int exp)
    {
        int experience = (int)((float)exp * 0.3F);

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
}
