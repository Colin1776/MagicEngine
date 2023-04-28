package github.colin1776.magicengine.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
    public static Capability<Mana> MANA = CapabilityManager.get(new CapabilityToken<>(){});

    private Mana mana = null;
    private final LazyOptional<Mana> opt = LazyOptional.of(this::createMana);

    private Mana createMana()
    {
        if (mana == null)
        {
            mana = new Mana();
        }

        return mana;
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(Capability<T> cap)
    {
        if (cap == MANA)
            return opt.cast();

        return LazyOptional.empty();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        createMana().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag)
    {
        createMana().loadNBTData(tag);
    }
}
