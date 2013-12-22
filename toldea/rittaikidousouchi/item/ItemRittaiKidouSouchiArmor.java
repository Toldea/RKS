package toldea.rittaikidousouchi.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import toldea.rittaikidousouchi.client.model.ModelRittaiKidouSouchi;
import toldea.rittaikidousouchi.managers.CreativeTabsManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRittaiKidouSouchiArmor extends ItemArmor {
	ResourceLocation textureLoricaSegmentata = new ResourceLocation("rittaikidousouchi", "textures/models/armor/rks_backpiece.png");
	private ModelBiped armorModel;
	
	public ItemRittaiKidouSouchiArmor(int id, EnumArmorMaterial material, int renderIndex, int armorType) {
		super(id, material, renderIndex, armorType);
		armorModel = new ModelRittaiKidouSouchi();
		this.setCreativeTab(CreativeTabsManager.tabRittaiKidouSouchi);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return armorModel;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return textureLoricaSegmentata.toString();
	}
}
