package committee.nova.mods.cmdkey.config;

import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/15 22:41
 * @Description:
 */
public class ServerConfig {
    private static Configuration cfg;
    public static String key;
    public static void init(File file) {
        cfg = new Configuration(file);
        cfg.load();
        key = cfg.get("settings", "key", "",
                "key").getString();
        cfg.save();
    }

    public static Configuration getCfg() {
        return cfg;
    }

}
