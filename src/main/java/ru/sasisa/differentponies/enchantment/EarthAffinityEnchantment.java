package ru.sasisa.differentponies.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EarthAffinityEnchantment extends Enchantment {
    public EarthAffinityEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD});
    }

    public int getMinPower(int level) {
        return 1;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 40;
    }

    public int getMaxLevel() {
        return 1;
    }
}
