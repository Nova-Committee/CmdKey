package committee.nova.mods.cmdkey.net;

import committee.nova.mods.cmdkey.utils.Log;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.*;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 00:23
 * @Description:
 */
public abstract class BasePacket {

    public abstract void readPacketData(DataInputStream data, Player p) throws IOException;

    public abstract void writePacketData(DataOutputStream data)  throws IOException;

    public abstract void processPacket();

    public abstract String getChannel();

    public final Packet250CustomPayload buildPacket() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try {
            this.writePacketData(data);
        }
        catch (Exception e) {
            Log.error(e.getMessage());
            e.printStackTrace();
        }
        Packet250CustomPayload pack = new Packet250CustomPayload();
        pack.channel = this.getChannel();
        pack.data = bytes.toByteArray();
        pack.length = bytes.size();
        return pack;
    }

    public final void readPacket(Packet250CustomPayload packet, Player p) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
        DataInputStream dis = new DataInputStream(bis);
        this.readPacketData(dis, p);
        this.processPacket();
    }
}
