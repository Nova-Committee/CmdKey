package committee.nova.mods.cmdkey.client;

import committee.nova.mods.cmdkey.core.Macro;
import committee.nova.mods.cmdkey.net.PerformPacket;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.util.EnumSet;

public class CmdKeyHandler extends KeyBindingRegistry.KeyHandler {
    private final EnumSet<TickType> tickTypes = EnumSet.of(TickType.CLIENT);
    private static boolean hasBeenPressed = false;
    private final Macro macro;
    public CmdKeyHandler(KeyBinding[] keyBindings, Macro macro) {
        super(keyBindings, new boolean[]{false});
        this.macro = macro;
    }

    @Override
    public void keyDown(EnumSet<TickType> var1, KeyBinding var2, boolean var3, boolean var4) {
        if (!hasBeenPressed) {
            PacketDispatcher.sendPacketToServer(new PerformPacket(Minecraft.getMinecraft().thePlayer.username, macro.cmd).buildPacket());
        }
        hasBeenPressed = true;
    }

    @Override
    public void keyUp(EnumSet<TickType> var1, KeyBinding var2, boolean var3) {
        hasBeenPressed = false;
    }

    @Override
    public EnumSet<TickType> ticks() {
        return tickTypes;
    }

    @Override
    public String getLabel() {
        return "Mode Key";
    }
}
