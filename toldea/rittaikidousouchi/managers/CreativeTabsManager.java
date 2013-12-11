package toldea.rittaikidousouchi.managers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CreativeTabsManager {
	public static CreativeTabs tabRittaiKidouSouchi = new CreativeTabs("tabRittaiKidouSouchi") {
		public ItemStack getIconItemStack() {
			return new ItemStack(Item.swordIron, 1, 0);
		}
	};

	public static void registerCreativeTabs() {
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabRittaiKidouSouchi", "Rittai Kidou Souchi");
	}
}
