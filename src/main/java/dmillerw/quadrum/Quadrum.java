package dmillerw.quadrum;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.quadrum.common.core.CommonProxy;
import dmillerw.quadrum.common.lib.data.Effect;
import dmillerw.quadrum.common.lib.data.EffectDeserializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author dmillerw
 */
@Mod(modid = "Quadrum", name = "Quadrum", version = "%MOD_VERSION%", dependencies = "required-after:Forge@[%FORGE_VERSION%,)")
public class Quadrum {

    @SidedProxy(serverSide = "dmillerw.quadrum.common.core.CommonProxy", clientSide = "dmillerw.quadrum.client.core.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance("Quadrum")
    public static Quadrum instance;

    public static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Effect.class, new EffectDeserializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public static Logger logger;

    public static File configDir;
    public static File blockDir;
    public static File itemDir;

    public static File blockLangDir;
    public static File itemLangDir;

    @SideOnly(Side.CLIENT)
    public static File blockTextureDir;
    @SideOnly(Side.CLIENT)
    public static File itemTextureDir;

    public static boolean textureStackTrace;
    public static boolean dumpBlockMap;
    public static boolean dumpItemMap;

    public static void log(Level level, String message, Object ... args) {
        logger.log(level, String.format(message, args));
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        configDir = new File(event.getModConfigurationDirectory(), "Quadrum/");
        blockDir = new File(configDir, "block/");
        itemDir = new File(configDir, "item/");

        if (!blockDir.exists()) {
            blockDir.mkdirs();
        }

        if (!itemDir.exists()) {
            itemDir.mkdirs();
        }

        blockLangDir = new File(blockDir, "lang/");
        itemLangDir = new File(itemDir, "lang/");

        if (!blockLangDir.exists()) {
            blockLangDir.mkdirs();
        }

        if (!itemLangDir.exists()) {
            itemLangDir.mkdirs();
        }

        if (event.getSide() == Side.CLIENT) {
            blockTextureDir = new File(blockDir, "textures/");
            itemTextureDir = new File(itemDir, "textures/");

            if (!blockTextureDir.exists()) {
                blockTextureDir.mkdirs();
            }

            if (!itemTextureDir.exists()) {
                itemTextureDir.mkdirs();
            }
        }

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
