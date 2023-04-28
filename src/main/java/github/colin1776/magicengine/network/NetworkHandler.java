package github.colin1776.magicengine.network;

import github.colin1776.magicengine.MagicEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler
{
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;

    private static int id()
    {
        return packetID++;
    }

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MagicEngine.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ManaSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaSyncPacket::new)
                .encoder(ManaSyncPacket::toBytes)
                .consumerMainThread(ManaSyncPacket::handle)
                .add();

        net.messageBuilder(MaxManaSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxManaSyncPacket::new)
                .encoder(MaxManaSyncPacket::toBytes)
                .consumerMainThread(MaxManaSyncPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
