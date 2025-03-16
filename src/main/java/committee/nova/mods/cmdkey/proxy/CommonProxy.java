package committee.nova.mods.cmdkey.proxy;

import committee.nova.mods.cmdkey.CmdKey;
import committee.nova.mods.cmdkey.net.MacroPacket;
import committee.nova.mods.cmdkey.net.PerformPacket;
import committee.nova.mods.cmdkey.net.VerifyPacket;
import committee.nova.mods.cmdkey.utils.IFunction;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 00:27
 * @Description:
 */
public class CommonProxy {
    public void handleMacros(MacroPacket packet) {}
    public void handleVerify(VerifyPacket packet) {}
    public void handlePerform(PerformPacket packet) {
        MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();
        //Log.info("执行命令: " + packet.cmd);
        String command = packet.cmd;
        String[] messages = command.split("\n");
        for (String message : messages) {
            Pattern pattern = Pattern.compile("%([^%]+)%");
            Matcher matcher = pattern.matcher(message);
            String finalCommand = message;
            while (matcher.find()) {
                String group = matcher.group();
                String placeholder = group.substring(1, group.length() - 1);
                IFunction<EntityPlayer, String> playerStringFunction = CmdKey.placeholderMap.get(placeholder);
                if (playerStringFunction != null) {
                    finalCommand = finalCommand.replace(group,playerStringFunction.apply(
                            SERVER.getEntityWorld().getPlayerEntityByName(packet.name)

                    ));
                }
            }
            message = finalCommand;
            SERVER.executeCommand(message);
        }

    }

    public void registerKeyBindings() {}

}
