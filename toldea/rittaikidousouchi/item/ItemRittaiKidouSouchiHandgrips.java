package toldea.rittaikidousouchi.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook;
import toldea.rittaikidousouchi.managers.CreativeTabsManager;

public class ItemRittaiKidouSouchiHandgrips extends Item {
	EntityGrappleHook grappleHook;

	public ItemRittaiKidouSouchiHandgrips(int id) {
		super(id);
		this.setCreativeTab(CreativeTabsManager.tabRittaiKidouSouchi);
		this.maxStackSize = 1;
		grappleHook = null;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
		//
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			if (grappleHook != null) {
				grappleHook.setDead();
				grappleHook = null;
			}
			return itemstack;
		}
		//

		if (grappleHook != null) {
			if (!world.isRemote) {
				grappleHook.pullOwnerEntityTowardsHook();
			}
		} else {
			System.out.println("Spawning grapple hook!");
			world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			if (!world.isRemote) {
				grappleHook = new EntityGrappleHook(world, entityPlayer, 1.0f);
				world.spawnEntityInWorld(grappleHook);
			}
			entityPlayer.swingItem();
		}
		return itemstack;
	}
}
