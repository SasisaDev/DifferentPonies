package ru.sasisa.differentponies.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EarthAffinityEnchantment extends Enchantment {
    public EarthAffinityEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
}
