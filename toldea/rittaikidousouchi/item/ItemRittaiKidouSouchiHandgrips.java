package toldea.rittaikidousouchi.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook;
import toldea.rittaikidousouchi.managers.CreativeTabsManager;

public class ItemRittaiKidouSouchiHandgrips extends Item {
	EntityGrappleHook leftGrappleHook;
	EntityGrappleHook rightGrappleHook;

	private enum Side {
		Left, Right
	};

	public ItemRittaiKidouSouchiHandgrips(int id) {
		super(id);
		this.setCreativeTab(CreativeTabsManager.tabRittaiKidouSouchi);
		this.maxStackSize = 1;
		rightGrappleHook = leftGrappleHook = null;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			if (leftGrappleHook != null) {
				leftGrappleHook.reelInGrappleHook();
			}
			if (rightGrappleHook != null) {
				rightGrappleHook.reelInGrappleHook();
			}
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		System.out.println("onLeftClickEntity");
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		// Check if the left button is pressed.
		if (Mouse.isButtonDown(0)) {
			onGrappleHookInteract(Side.Left, entityLiving.worldObj, (EntityPlayer) entityLiving);
		}
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		System.out.println("getItemUseAction");
		return EnumAction.none;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer player) {
		System.out.println("onBlockStartBreak");
		return true;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		System.out.println("onItemUseFirst");
		if (Mouse.isButtonDown(2)) {
			onGrappleHookInteract(Side.Left, world, player);
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
		System.out.println("onItemRightClick");
		onGrappleHookInteract(Side.Right, world, entityPlayer);
		return itemstack;
	}

	public void onGrappleHookInteract(Side side, World world, EntityPlayer entityPlayer) {
		System.out.println("onGrappleHookInteract (side: " + (side == Side.Left ? "left" : "right" + ")"));
		boolean spawnedHook = false;
		if (!world.isRemote) {
			if ((side == Side.Left ? leftGrappleHook : rightGrappleHook) == null) {
				if (side == Side.Left) {
					leftGrappleHook = new EntityGrappleHook(world, entityPlayer, 1.0f);
					world.spawnEntityInWorld(leftGrappleHook);
					spawnedHook = true;
				} else if (side == Side.Right) {
					rightGrappleHook = new EntityGrappleHook(world, entityPlayer, 1.0f);
					world.spawnEntityInWorld(rightGrappleHook);
					spawnedHook = true;
				}
			} else {
				if (side == Side.Left) {
					leftGrappleHook.setDead();
					leftGrappleHook = null;
				} else if (side == Side.Right) {
					rightGrappleHook.setDead();
					rightGrappleHook = null;
				}
			}
		}
		if (spawnedHook) {
			world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		}
	}
}
