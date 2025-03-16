package committee.nova.mods.cmdkey.net;

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
public class VerifyPacket extends BasePacket{

    @Override
    public void readPacketData(DataInputStream data, Player p) throws IOException {

    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {

    }

    @Override
    public void processPacket() {

    }

    @Override
    public String getChannel() {
        return "cmdkey:verify";
    }
}
