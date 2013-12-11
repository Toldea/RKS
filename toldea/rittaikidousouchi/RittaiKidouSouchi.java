package toldea.rittaikidousouchi;

import toldea.rittaikidousouchi.managers.BlockManager;
import toldea.rittaikidousouchi.managers.ConfigManager;
import toldea.rittaikidousouchi.managers.CreativeTabsManager;
import toldea.rittaikidousouchi.managers.EntityManager;
import toldea.rittaikidousouchi.managers.ItemManager;
import toldea.rittaikidousouchi.managers.PacketManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "RittaiKidouSouchiID", name = "Rittai Kidou Souchi", version = "0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { PacketManager.CHANNEL }, packetHandler = PacketManager.class)
public class RittaiKidouSouchi {
	// The instance of your mod that Forge uses.
	@Instance(value = "RittaiKidouSouchiID")
	public static RittaiKidouSouchi instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "toldea.rittaikidousouchi.client.ClientProxy", serverSide = "toldea.rittaikidousouchi.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigManager.loadConfig(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		CreativeTabsManager.registerCreativeTabs();

		ItemManager.registerItems();
		BlockManager.registerBlocks();
		
		EntityManager.registerEntities();

		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}