package github.colin1776.magicengine.capability;

import net.minecraft.nbt.CompoundTag;

public class Mana
{
    // TODO finish implementing max mana

    private int mana;
    private int maxMana;

    public int getMana()
    {
        return mana;
    }

    public void setMana(int mana)
    {
        this.mana = Math.min(mana, maxMana);
    }

    public int getMaxMana()
    {
        return maxMana;
    }

    public void setMaxMana(int maxMana)
    {
        this.maxMana = maxMana;
    }

    public void copyFrom(Mana source)
    {
        mana = source.mana;
        maxMana = source.maxMana;
    }

    public void saveNBTData(CompoundTag tag)
    {
        tag.putInt("mana", mana);
        tag.putInt("maxMana", maxMana);
    }

    public void loadNBTData(CompoundTag tag)
    {
        mana = tag.getInt("mana");
        maxMana = tag.getInt("maxMana");
    }
}
