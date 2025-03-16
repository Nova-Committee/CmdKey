package committee.nova.mods.cmdkey.config;

import com.google.common.base.Stopwatch;
import com.google.gson.*;
import committee.nova.mods.cmdkey.CmdKey;
import committee.nova.mods.cmdkey.core.Macro;
import committee.nova.mods.cmdkey.utils.Log;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static committee.nova.mods.cmdkey.CmdKey.GSON;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/15 13:17
 * @Description:
 */
public class ModConfig {
    public static Map<String, Macro> SERVER_MACROS = new HashMap<String, Macro>();
    public static String MACROS_JSON = "";


    public static void load(File mod_folder) {
        Stopwatch stopwatch = new Stopwatch().start();

        clear();

        writeDefault(mod_folder);


        if (!CmdKey.MOD_CONFIG_FOLDER.mkdirs() && CmdKey.MOD_CONFIG_FOLDER.isDirectory()) {
            loadFiles(mod_folder);
        }

        stopwatch.stop();

        Log.info(String.format("Server Loaded %s custom cmd(s) in %s ms", SERVER_MACROS.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
    }

    public static void clear() {
        SERVER_MACROS.clear();
    }

    private static void writeDefault(File mod_folder) {
        if (!mod_folder.exists() && mod_folder.mkdirs()) {
            JsonObject cmd1 = new JsonObject();
            cmd1.addProperty("id", "creative");
            cmd1.addProperty("key", "Y");
            cmd1.addProperty("cmd", "gamemode 0");
            cmd1.addProperty("op", true);
            JsonArray custom = new JsonArray();
            custom.add(cmd1);
            JsonObject main = new JsonObject();
            main.add("custom", custom);

            FileWriter writer = null;

            try {
                File file = new File(mod_folder, "cmd.json");
                writer = new FileWriter(file);

                GSON.toJson(main, writer);
                writer.close();
            } catch (Exception e) {
                Log.error("生成默认自定义命令时出错", e);
            } finally {
                IOUtils.closeQuietly(writer);
            }
        }
    }


    private static void loadFiles(File mod_folder) {
        File CMD_FILE = new File(mod_folder, "cmd.json");

            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(CMD_FILE), "UTF-8");
                JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                MACROS_JSON = json.toString();
                JsonArray customs = json.get("custom").getAsJsonArray();
                if (customs != null) {
                    for (JsonElement e : customs){
                        if (e.isJsonObject()){
                            Macro cmd = GSON.fromJson(e.getAsJsonObject(), Macro.class);
                            SERVER_MACROS.put(cmd.id, cmd);
                        }
                    }
                }
            } catch (IOException e) {
                Log.error("读取文件 {} 出错", CMD_FILE.getName(), e);
            } catch (JsonParseException e) {
                Log.error("解析 JSON 文件 {} 出错", CMD_FILE.getName(), e);
            } catch (Exception e) {
                Log.error("加载自定义命令出错，请检查文件 {}", CMD_FILE.getName(), e);
            }
    }
}
