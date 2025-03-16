package committee.nova.mods.cmdkey.core;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/15 13:16
 * @Description:
 */
public class Macro {
    public String id;
    public String key;
    public String cmd;
    public boolean op;

    @Override
    public String toString() {
        return "Macro{" +
                "id='" + id + '\'' +
                ", key=" + key +
                ", cmd='" + cmd + '\'' +
                ", op=" + op +
                '}';
    }

    public Macro(String id, String key, String cmd, boolean op) {
        this.id = id;
        this.key = key;
        this.cmd = cmd;
        this.op = op;
    }

}
