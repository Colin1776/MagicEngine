package github.colin1776.magicengine.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ManaSpellCost extends SpellCost
{

    // TODO yea mana is just gonna be an int and its gonna be a system enforced by the mod

    @Override
    public void applyCost()
    {
        System.out.println("cost applied XD");
    }

    @Override
    public boolean meetsCost(LivingEntity caster, ItemStack castingItem)
    {
        return true;
    }
}
