package committee.nova.mods.cmdkey.net;

import committee.nova.mods.cmdkey.CmdKey;
import cpw.mods.fml.common.network.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 00:01
 * @Description:
 */
public class MacroPacket extends BasePacket{
    public String json;

    public MacroPacket() {}

    public MacroPacket(String json) {
        this.json = json;
    }
    @Override
    public void readPacketData(DataInputStream data, Player p) throws IOException {
        this.json = data.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        data.writeUTF(json);
    }

    @Override
    public void processPacket() {
        CmdKey.proxy.handleMacros(this);
    }

    @Override
    public String getChannel() {
        return "cmdkey:macros";
    }
}
