package committee.nova.mods.cmdkey;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import committee.nova.mods.cmdkey.config.ModConfig;
import committee.nova.mods.cmdkey.config.ServerConfig;
import committee.nova.mods.cmdkey.net.MacroPacket;
import committee.nova.mods.cmdkey.net.PktHandler;
import committee.nova.mods.cmdkey.proxy.CommonProxy;
import committee.nova.mods.cmdkey.utils.IFunction;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

@Mod(modid = CmdKey.MODID, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class CmdKey {

    @SidedProxy(clientSide = "committee.nova.mods.cmdkey.proxy.ClientProxy", serverSide = "committee.nova.mods.cmdkey.proxy.ServerProxy")
    public static CommonProxy proxy;
    public static Logger LOGGER = Logger.getLogger("CmdKey");
    public static final String MODID = "cmdkey";
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();
    public static File CONFIG_FOLDER;
    public static File MOD_CONFIG_FOLDER;
    public static final Map<String, IFunction<EntityPlayer, String>> placeholderMap = Maps.newHashMap();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        CONFIG_FOLDER = event.getModConfigurationDirectory();
        MOD_CONFIG_FOLDER = new File(CONFIG_FOLDER, "cmdkey");

        PktHandler packet = new PktHandler();
        //NetworkRegistry.instance().registerChannel(packet, "cmdkey:verify");
        NetworkRegistry.instance().registerChannel(packet, "cmdkey:macros", Side.CLIENT);
        NetworkRegistry.instance().registerChannel(packet, "cmdkey:perform", Side.SERVER);

        if (event.getSide() == Side.SERVER) {
            ServerConfig.init(event.getSuggestedConfigurationFile());
            registerPlaceholder();
            if (ServerConfig.key.equals("a9$V$B#7farNZd")){
                ModConfig.load(MOD_CONFIG_FOLDER);
            } else {
                return;
            }
        }

        MinecraftForge.EVENT_BUS.register(this);
    }


    @ForgeSubscribe
    public void joinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof Player) {
            PacketDispatcher.sendPacketToPlayer(new MacroPacket(ModConfig.MACROS_JSON).buildPacket(), (Player) event.entity);
        }
    }
    private void registerPlaceholder(){
        placeholderMap.put("player_name", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return player.getDisplayName();
            }
        });
        placeholderMap.put("player_pos_x", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.posX);
            }
        });
        placeholderMap.put("player_pos_y", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.posY);
            }
        });
        placeholderMap.put("player_pos_z", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.posZ);
            }
        });
        placeholderMap.put("player_pos", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.format("%s %s %s", player.posX, player.posY, player.posZ);
            }
        });
        placeholderMap.put("player_health", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.getHealth());
            }
        });
        placeholderMap.put("player_food", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.getFoodStats().getFoodLevel());
            }
        });
        placeholderMap.put("player_exp", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.experienceTotal);
            }
        });
        placeholderMap.put("player_level", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.experienceLevel);
            }
        });
        placeholderMap.put("player_dimension", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.dimension);
            }
        });
        placeholderMap.put("player_x_rot", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.rotationPitch);
            }
        });
        placeholderMap.put("player_y_rot", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return String.valueOf(player.rotationYaw);
            }
        });
        placeholderMap.put("player_uuid", new IFunction<EntityPlayer, String>() {
            @Override
            public String apply(EntityPlayer player) {
                return player.getUniqueID().toString();
            }
        });
    }
}
