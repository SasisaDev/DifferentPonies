package ru.sasisa.differentponies.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.sasisa.differentponies.api.Race;
import ru.sasisa.differentponies.api.ability.RaceAbilitySet;
import ru.sasisa.differentponies.interfaces.IPlayerEntityMixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerEntityMixin {

    private Race race = Race.NONE;
    private RaceAbilitySet abilities = null;

    @Inject(method = "addExperience(I)V", at = @At("HEAD"))
    public void customAddExperience(int experience, CallbackInfo ci)
    {
        abilities.Passives.forEach((el)->{ el.OnGetExperience(((PlayerEntity)(Object)this), experience);});
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
    }
}
