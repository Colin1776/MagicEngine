package github.colin1776.magicengine.item;

import github.colin1776.magicengine.capability.Mana;
import github.colin1776.magicengine.capability.ManaProvider;
import github.colin1776.magicengine.client.ClientManaData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@SuppressWarnings("NullableProblems")
public class MagicItem extends Item implements CastingItem
{
    public MagicItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        /*if (level.isClientSide)
        {
            System.out.println("Client: " + ClientManaData.mana);
            System.out.println("Server: " + ClientManaData.maxMana);
        }
        else
        {
            int mana = player.getCapability(ManaProvider.MANA)
                    .map(Mana::getMana)
                    .orElse(0);

            int maxMana = player.getCapability(ManaProvider.MANA)
                    .map(Mana::getMaxMana)
                    .orElse(0);


            System.out.println("Server: " + mana);
            System.out.println("Server: " +  maxMana);
        }*/

        long tick = level.getGameTime();

        if (!level.isClientSide)
            System.out.println(tick);
        
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int tick)
    {


        //castTick(level, entity, stack, tick);
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_)
    {
        //decrementCooldowns();
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return spellDuration(stack);
    }

    /*@Override
    public UseAnim getUseAnimation(ItemStack p_43105_)
    {
        return UseAnim.BLOCK;
    }*/
}
