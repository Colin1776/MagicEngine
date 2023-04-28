package github.colin1776.magicengine;

import com.mojang.logging.LogUtils;
import github.colin1776.magicengine.item.MagicItem;
import github.colin1776.magicengine.network.NetworkHandler;
import github.colin1776.magicengine.spell.Lore;
import github.colin1776.magicengine.spell.ManaSpellCost;
import github.colin1776.magicengine.spell.RaySpell;
import github.colin1776.magicengine.spell.Spell;
import github.colin1776.magicengine.spell.SpellEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(MagicEngine.MOD_ID)
public class MagicEngine
{
    public static final String MOD_ID = "magicengine";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Lore> LORES = DeferredRegister.create(new ResourceLocation(MOD_ID, "lores"), MOD_ID);
    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(new ResourceLocation(MOD_ID, "spells"), MOD_ID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Lore> ARCANE = LORES.register("arcane", () -> new Lore("arcane"));

    public static final RegistryObject<Spell> RAY = SPELLS.register("ray", () -> new RaySpell("ray", ARCANE.get(), 15, 10, 32,
            false, RaySpell.ParticleType.STRAIGHT, Spell.CastType.CHARGED, Spell.TargetType.ALL, new ManaSpellCost(),
            new SpellEffect().setDamage(5)));

    public static final RegistryObject<Item> WAND = ITEMS.register("wand", () -> new MagicItem(new Item.Properties()));

    public MagicEngine()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        createRegistries(bus);

        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(this::commonSetup);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        NetworkHandler.register();
    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }

    private void createRegistries(IEventBus bus)
    {
        LORES.makeRegistry(RegistryBuilder::new);
        SPELLS.makeRegistry(RegistryBuilder::new);

        LORES.register(bus);
        SPELLS.register(bus);
        ITEMS.register(bus);
    }
}
