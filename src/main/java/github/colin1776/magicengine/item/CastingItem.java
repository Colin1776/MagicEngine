package github.colin1776.magicengine.item;

import github.colin1776.magicengine.MagicEngine;
import github.colin1776.magicengine.spell.Spell;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface CastingItem
{
    // TODO implement spell storage and cooldown system
    // TODO also actually implement the code for the cast functions XD

    default InteractionResultHolder<ItemStack> beginCast(Level level, Player player, InteractionHand hand)
    {
        ItemStack castingItem = player.getItemInHand(hand);

        if (onCooldown(player, castingItem))
            return InteractionResultHolder.fail(castingItem);

        Spell spell = getSelected(castingItem);

        if (spell == null || !spell.canCast(player, castingItem))
            return InteractionResultHolder.fail(castingItem);

        player.startUsingItem(hand);
        return InteractionResultHolder.success(castingItem);
    }

    default void castTick(Level level, LivingEntity entity, ItemStack stack, int ticksRemaining)
    {
        Spell spell = getSelected(stack);

        if (spell.canCast(entity, stack))
        {
            if (spell.getCastType() == Spell.CastType.CONTINUOUS)
                spell.cast(entity, stack);

            if (ticksRemaining <= 1)
            {
                if (spell.getCastType() == Spell.CastType.CHARGED)
                    spell.cast(entity, stack);
                // TODO APPLY COOLDOWN
                entity.stopUsingItem();
            }
        }
        else
        {
            entity.stopUsingItem();
        }
    }

    default void decrementCooldowns()
    {
        // just reduce the cooldowns each tick lol
    }

    default int spellDuration(ItemStack castingItem)
    {
        return getSelected(castingItem).getCastDuration();
    }

    default boolean onCooldown(LivingEntity caster, ItemStack castingItem)
    {
        // check the selected spell with castingItem, see if its on cooldown, return true or false

        return false;
    }

    default Spell getSelected(ItemStack castingItem)
    {
        return MagicEngine.RAY.get();
    }
}
