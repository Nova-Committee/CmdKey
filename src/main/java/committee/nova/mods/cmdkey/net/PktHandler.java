package committee.nova.mods.cmdkey.net;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.util.Hashtable;
import java.util.Map;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 00:14
 * @Description:
 */
public class PktHandler implements IPacketHandler {
    private Map<String, Class> customPackages = new Hashtable<String, Class>();

    public PktHandler() {
        customPackages.put("cmdkey:macros", MacroPacket.class);
        //customPackages.put("cmdkey:verify", VerifyPacket.class);
        customPackages.put("cmdkey:perform", PerformPacket.class);
    }
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        try {
            ((BasePacket)customPackages.get(packet.channel).newInstance()).readPacket(packet, player);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
