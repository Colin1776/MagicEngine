package github.colin1776.magicengine.network;

import github.colin1776.magicengine.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSyncPacket
{
    private final int mana;

    public ManaSyncPacket(int mana)
    {
        this.mana = mana;
    }

    public ManaSyncPacket(FriendlyByteBuf buf)
    {
        mana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(mana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> ClientManaData.mana = mana);

        return true;
    }
}
