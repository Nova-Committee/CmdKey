package committee.nova.mods.cmdkey.proxy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import committee.nova.mods.cmdkey.client.CmdKeyHandler;
import committee.nova.mods.cmdkey.core.Macro;
import committee.nova.mods.cmdkey.net.MacroPacket;
import committee.nova.mods.cmdkey.net.VerifyPacket;
import committee.nova.mods.cmdkey.utils.Log;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

import static committee.nova.mods.cmdkey.CmdKey.GSON;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 00:27
 * @Description:
 */
public class ClientProxy extends CommonProxy{
    public Map<String, Macro> CLIENT_MACROS = new HashMap<String, Macro>();
    public static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void handleMacros(MacroPacket packet) {
        if (!packet.json.isEmpty()) {
            JsonObject main = new JsonParser().parse(packet.json).getAsJsonObject();
            JsonArray customs = main.get("custom").getAsJsonArray();
            if (customs != null) {
                for (JsonElement e : customs){
                    if (e.isJsonObject()){
                        Macro cmd = GSON.fromJson(e.getAsJsonObject(), Macro.class);
                        CLIENT_MACROS.put(cmd.id, cmd);
                        Log.info("注册按键: " + cmd);

                        KeyBindingRegistry.registerKeyBinding(new CmdKeyHandler(new KeyBinding[]{new KeyBinding("key.cmdkey." + cmd.id, Keyboard.getKeyIndex(cmd.key))}, cmd));
                        KeyBindingRegistry.instance().uploadKeyBindingsToGame(mc.gameSettings);

                    }
                }
            }
        }
    }
    @Override
    public void handleVerify(VerifyPacket packet) {}


    @Override
    public void registerKeyBindings() {}
}
