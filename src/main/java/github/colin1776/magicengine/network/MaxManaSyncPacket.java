package github.colin1776.magicengine.network;

import github.colin1776.magicengine.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaxManaSyncPacket
{
    private final int maxMana;

    public MaxManaSyncPacket(int maxMana)
    {
        this.maxMana = maxMana;
    }

    public MaxManaSyncPacket(FriendlyByteBuf buf)
    {
        maxMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(maxMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> ClientManaData.maxMana = maxMana);

        return true;
    }
}
