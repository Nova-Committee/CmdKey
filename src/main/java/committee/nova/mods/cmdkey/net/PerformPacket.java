package committee.nova.mods.cmdkey.net;

import committee.nova.mods.cmdkey.CmdKey;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 00:01
 * @Description:
 */
public class PerformPacket extends BasePacket{
    public String name;
    public String cmd;

    public PerformPacket() {
    }

    public PerformPacket(String name, String cmd) {
        this.name = name;
        this.cmd = cmd;
    }
    @Override
    public void readPacketData(DataInputStream data, Player p) throws IOException {
        this.name = ((EntityPlayer)p).getDisplayName();
        this.cmd = data.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        data.writeUTF(cmd);
    }

    @Override
    public void processPacket() {
        CmdKey.proxy.handlePerform(this);
    }

    @Override
    public String getChannel() {
        return "cmdkey:perform";
    }
}
