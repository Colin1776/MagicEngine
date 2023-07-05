package github.colin1776.magicengine.client;

import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaOverlay
{
    public static final IGuiOverlay HUD_MANA = (gui, poseStack, partialTicks, width, height) ->
    {
        String toDisplay = ClientManaData.mana + " / " + ClientManaData.maxMana;
        gui.getFont().draw(poseStack, toDisplay, 10, 10, 0xffffff);
    };
}
