package toldea.rittaikidousouchi.item;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import toldea.rittaikidousouchi.managers.CreativeTabsManager;

public class ItemRittaiKidouSouchiArmor extends ItemArmor {
	public ItemRittaiKidouSouchiArmor(int id, EnumArmorMaterial material, int renderIndex, int armorType) {
		super(id, material, renderIndex, armorType);
		this.setCreativeTab(CreativeTabsManager.tabRittaiKidouSouchi);
	}
}
