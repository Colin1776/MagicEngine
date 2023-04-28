package github.colin1776.magicengine.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class SpellCost
{
    // TODO yeah this is being replaced by just a capability for mana implemented to the player


    public abstract void applyCost();

    // this function might need more parameters depending on how many different kinds of implementations i wanna allow for idk,
    // but for the mana implementation I really just need "caster" in order to make sure that the caster has enough mana
    public abstract boolean meetsCost(LivingEntity caster, ItemStack castingItem);
}