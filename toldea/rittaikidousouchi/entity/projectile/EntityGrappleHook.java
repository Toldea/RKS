package toldea.rittaikidousouchi.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import toldea.rittaikidousouchi.managers.PacketManager;

public class EntityGrappleHook extends EntityArrow {
	public static final double MAX_ROPE_LENGTH = 10.0;
	private double ropeLength = MAX_ROPE_LENGTH;
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
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (active) {
			if (ropeLength > .0) {
				ropeLength -= .5;
			}
			if (getOwner() != null) {
				double dx = this.posX - ownerEntity.posX;
				double dy = this.posY - ownerEntity.posY;
				double dz = this.posZ - ownerEntity.posZ;
				double length = (double) MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
				// Constrain the player's position to the max length of rope.
				if (length > ropeLength) {
					// Normalize and multiply by the length to get the 'theoretical max position'.
					Vec3 vec = Vec3.createVectorHelper(dx, dy, dz);
					vec = vec.normalize();
					vec.xCoord *= ropeLength;
					vec.yCoord *= ropeLength;
					vec.zCoord *= ropeLength;
					// Subtract the actual current deltas to get the adjustment we need to make.
					vec.xCoord -= dx;
					vec.yCoord -= dy;
					vec.zCoord -= dz;
					// Update the player's position with this adjustment.
					ownerEntity.setPosition(ownerEntity.posX - vec.xCoord, ownerEntity.posY - vec.yCoord, ownerEntity.posZ - vec.zCoord);
				}
			}
		}
		/*
		if (active) {
			System.out.println("Grappling hook is pulling!");
			double dx = this.posX - ownerEntity.posX;
			double dy = this.posY - ownerEntity.posY;
			double dz = this.posZ - ownerEntity.posZ;
			double length = (double) MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
			double strength = 0.3D;
			ownerEntity.motionX += dx * strength;
			ownerEntity.motionY += dy * strength + (double) MathHelper.sqrt_double(length) * 0.08D;
			ownerEntity.motionZ += dz * strength;
		}
		*/
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
		/*
		//if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
		if (this.inGround && this.arrowShake <= 0) {
			if (active) {
				active = false;
				System.out.println("Collided with grappling hook, setting inactive!");
			}
		}
		*/
	}

	public void pullOwnerEntityTowardsHook() {
		if (!active) {
			ownerEntity = getOwner();
			if (ownerEntity == null) {
				return;
			}
			active = true;
			if (!this.worldObj.isRemote) {
				PacketManager.sendActiveGrappleHookPacketToAllPlayers(this);	
			}
		}
	}
}