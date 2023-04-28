package github.colin1776.magicengine;

import github.colin1776.magicengine.capability.Mana;
import github.colin1776.magicengine.capability.ManaProvider;
import github.colin1776.magicengine.network.ManaSyncPacket;
import github.colin1776.magicengine.network.MaxManaSyncPacket;
import github.colin1776.magicengine.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagicEngine.MOD_ID)
public class EventHandler
{
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            if (!event.getObject().getCapability(ManaProvider.MANA).isPresent())
            {
                event.addCapability(new ResourceLocation(MagicEngine.MOD_ID, "mana"), new ManaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            event.getOriginal().getCapability(ManaProvider.MANA).ifPresent(oldStore ->
                    event.getEntity().getCapability(ManaProvider.MANA).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.register(Mana.class);
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event)
    {
        if (event.getLevel().isClientSide)
            return;

        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        player.getCapability(ManaProvider.MANA).ifPresent(mana ->
        {
            mana.setMaxMana(100);
            NetworkHandler.sendToPlayer(new MaxManaSyncPacket(mana.getMaxMana()), player);
        });
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event)
    {
        if (event.level.isClientSide)
            return;

        if (event.phase == TickEvent.Phase.START)
            return;

        event.level.players().forEach(player ->
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                serverPlayer.getCapability(ManaProvider.MANA).ifPresent(mana ->
                {
                    mana.setMana(mana.getMana() + 1);
                    NetworkHandler.sendToPlayer(new ManaSyncPacket(mana.getMana()), serverPlayer);
                });
            }
        });
    }
}
