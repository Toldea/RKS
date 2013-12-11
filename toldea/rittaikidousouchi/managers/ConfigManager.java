package toldea.rittaikidousouchi.managers;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigManager {
	public static int itemRKSArmorId;
	public static int itemRKSHandgripsId;
	
	private ConfigManager() {
	}

	public static void loadConfig(File file) {
		Configuration config = new Configuration(file);

		config.load();
		
		itemRKSArmorId = config.getItem("itemRKSArmor", 1750).getInt();
		itemRKSHandgripsId = config.getItem("itemRKSHandgrips", 1751).getInt();

		config.save();
	}
}
