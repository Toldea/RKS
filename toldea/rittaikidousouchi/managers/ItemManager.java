package toldea.rittaikidousouchi.managers;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import toldea.rittaikidousouchi.item.ItemRittaiKidouSouchiArmor;
import toldea.rittaikidousouchi.item.ItemRittaiKidouSouchiHandgrips;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemManager {
	public static Item itemRRKSArmor;
	public static Item itemRKSHandgrips;

	public static void registerItems() {
		// The armor piece
		itemRRKSArmor = new ItemRittaiKidouSouchiArmor(ConfigManager.itemRKSArmorId, EnumArmorMaterial.IRON, 2, 2).setUnlocalizedName("itemRRKSArmor");
		LanguageRegistry.addName(itemRRKSArmor, "Rittai Kidou Souchi Armor");

		// The handgrips
		itemRKSHandgrips = new ItemRittaiKidouSouchiHandgrips(ConfigManager.itemRKSHandgripsId).setUnlocalizedName("itemRKSHandgrips");
		LanguageRegistry.addName(itemRKSHandgrips, "Rittai Kidou Souchi Handgrips");
	}
}
