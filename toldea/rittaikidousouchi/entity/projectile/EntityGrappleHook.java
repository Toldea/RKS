package toldea.rittaikidousouchi.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import toldea.rittaikidousouchi.managers.PacketManager;

public class EntityGrappleHook extends EntityArrow {
	private EntityPlayer ownerEntity;
	boolean active = false;

	public EntityGrappleHook(World world) {
		super(world);
	}

	public EntityGrappleHook(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityGrappleHook(World world, EntityPlayer entityPlayer, float speed) {
		super(world, entityPlayer, speed);
		this.ownerEntity = entityPlayer;
		dataWatcher.updateObject(17, ownerEntity.entityId);
	}

	protected void entityInit() {
		super.entityInit();
		// Initialize field 17 to keep track of the owner player entity id.
		dataWatcher.addObject(17, -1);
	}

	public EntityPlayer getOwner() {
		// If the ownerEntity is invalid, check the dataWatcher to see if the value has been synced and if so retrieve the owner.
		if (ownerEntity == null) {
			int ownerEntityId = dataWatcher.getWatchableObjectInt(17);
			Entity entity = this.worldObj.getEntityByID(ownerEntityId);
			if (entity != null && entity instanceof EntityPlayer) {
				this.ownerEntity = (EntityPlayer) entity;
			}
		}
		return this.ownerEntity;
	}

	public void pullOwnerEntityTowardsHook() {
		if (!active) {
			ownerEntity = getOwner();
			if (ownerEntity == null) {
				return;
			}
			double d0 = this.posX - ownerEntity.posX;
			double d1 = this.posY - ownerEntity.posY;
			double d2 = this.posZ - ownerEntity.posZ;
			double d3 = (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
			double d4 = 0.1D;
			ownerEntity.motionX += d0 * d4;
			ownerEntity.motionY += d1 * d4 + (double) MathHelper.sqrt_double(d3) * 0.08D;
			ownerEntity.motionZ += d2 * d4;
			active = true;
			System.out.println("DISCIPLINE: " + worldObj.isRemote);
			if (!this.worldObj.isRemote) {
				PacketManager.sendActiveGrappleHookPacketToAllPlayers(this);	
			}
		}
	}
}