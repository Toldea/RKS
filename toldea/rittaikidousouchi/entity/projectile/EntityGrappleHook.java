package toldea.rittaikidousouchi.entity.projectile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGrappleHook extends EntityArrow {
	public enum Side {
		Left, Right
	};

	// EntityArrow private variables.
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile;
	private int inData;
	private boolean inGround;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;
	private int knockbackStrength;
	//

	public static final float MAX_ROPE_LENGTH = 10.0f;
	public static final float MIN_ROPE_LENGTH = .5f;
	private float ropeLength = MAX_ROPE_LENGTH;
	private EntityPlayer ownerEntity;
	private boolean activated = false;

	public EntityGrappleHook(World world) {
		super(world);
	}

	public EntityGrappleHook(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityGrappleHook(World world, EntityPlayer entityPlayer, float speed, Side side) {
		super(world, entityPlayer, speed);
		this.ownerEntity = entityPlayer;
		dataWatcher.updateObject(17, ownerEntity.entityId);
		if (side == Side.Right) {
			dataWatcher.updateObject(19, (byte) 1);
		}
	}

	protected void entityInit() {
		super.entityInit();
		// Initialize field 17 to keep track of the owner player entity id.
		dataWatcher.addObject(17, -1);
		// Field 18 holds the rope's length.
		dataWatcher.addObject(18, MAX_ROPE_LENGTH);
		// Field 19 holds the rope's 'side' or from which of the two grapple hook shooters it was shot from.
		dataWatcher.addObject(19, (byte) 0);
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

	public Side getSide() {
		byte sideByte = dataWatcher.getWatchableObjectByte(19);
		return (sideByte == 0 ? Side.Left : Side.Right);
	}

	@Override
	public void onUpdate() {
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}

		int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

		if (i > 0) {
			Block.blocksList[i].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

			if (axisalignedbb != null && axisalignedbb.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ))) {
				this.inGround = true;
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.inGround) {
			int j = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
			int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

			if (j == this.inTile && k == this.inData) {
				++this.ticksInGround;

				if (this.ticksInGround == 1200) {
					this.setDead();
				}
			} else {
				this.inGround = false;
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		} else {
			++this.ticksInAir;
			Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
			vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (movingobjectposition != null) {
				vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
						movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int l;
			float f1;

			for (l = 0; l < list.size(); ++l) {
				Entity entity1 = (Entity) list.get(l);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null) {
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}

			float f2;
			float f3;

			if (movingobjectposition != null) {
				if (movingobjectposition.entityHit != null) {
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int i1 = MathHelper.ceiling_double_int((double) f2 * this.damage);

					if (this.getIsCritical()) {
						i1 += this.rand.nextInt(i1 / 2 + 2);
					}

					DamageSource damagesource = null;

					if (this.shootingEntity == null) {
						damagesource = DamageSource.causeArrowDamage(this, this);
					} else {
						damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
					}

					if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
						movingobjectposition.entityHit.setFire(5);
					}

					if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) i1)) {
						if (movingobjectposition.entityHit instanceof EntityLivingBase) {
							EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

							if (!this.worldObj.isRemote) {
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
							}

							if (this.knockbackStrength > 0) {
								f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f3 > 0.0F) {
									movingobjectposition.entityHit.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6000000238418579D
											/ (double) f3, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6000000238418579D / (double) f3);
								}
							}

							if (this.shootingEntity != null) {
								EnchantmentThorns.func_92096_a(this.shootingEntity, entitylivingbase, this.rand);
							}

							if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity
									&& movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
							}
						}

						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

						if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
							this.setDead();
						}
					} else {
						this.motionX *= -0.10000000149011612D;
						this.motionY *= -0.10000000149011612D;
						this.motionZ *= -0.10000000149011612D;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.ticksInAir = 0;
					}
				} else {
					this.xTile = movingobjectposition.blockX;
					this.yTile = movingobjectposition.blockY;
					this.zTile = movingobjectposition.blockZ;
					this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
					this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
					this.motionX = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
					this.motionY = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
					this.motionZ = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / (double) f2 * 0.05000000074505806D;
					this.posY -= this.motionY / (double) f2 * 0.05000000074505806D;
					this.posZ -= this.motionZ / (double) f2 * 0.05000000074505806D;
					this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.inGround = true;
					this.arrowShake = 7;
					this.setIsCritical(false);

					if (this.inTile != 0) {
						Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
					}
				}
			}

			if (this.getIsCritical()) {
				for (l = 0; l < 4; ++l) {
					this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double) l / 4.0D, this.posY + this.motionY * (double) l / 4.0D, this.posZ
							+ this.motionZ * (double) l / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f4 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater()) {
				for (int j1 = 0; j1 < 4; ++j1) {
					f3 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f3, this.posY - this.motionY * (double) f3, this.posZ
							- this.motionZ * (double) f3, this.motionX, this.motionY, this.motionZ);
				}

				f4 = 0.8F;
			}

			this.motionX *= (double) f4;
			this.motionY *= (double) f4;
			this.motionZ *= (double) f4;
			this.motionY -= (double) f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}

		grapplingHookUpdate();
	}

	public void grapplingHookUpdate() {
		if (getOwner() != null) {
			if (activated) {
				reelInGrappleHook();
			}
			
			double dx = this.posX - ownerEntity.posX;
			double dy = this.posY - ownerEntity.posY;
			double dz = this.posZ - ownerEntity.posZ;
			double length = (double) MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
			float currentRopeLength = dataWatcher.getWatchableObjectFloat(18);
			// Constrain either the player or the hook's position to the length of the rope.
			// When stuck into something, the player will be constrained to the hook.
			// When still in flight, the hook obviously wouldn't have that power so constrain it to the player instead.
			if (length > currentRopeLength) {
				// Normalize and multiply by the length to get the 'theoretical max position'.
				Vec3 vec = Vec3.createVectorHelper(dx, dy, dz);
				vec = vec.normalize();
				vec.xCoord *= currentRopeLength;
				vec.yCoord *= currentRopeLength;
				vec.zCoord *= currentRopeLength;
				// Subtract the actual current deltas to get the adjustment we need to make.
				vec.xCoord -= dx;
				vec.yCoord -= dy;
				vec.zCoord -= dz;

				if (this.inGround) {
					// Update the player's position with this adjustment.
					ownerEntity.setPosition(ownerEntity.posX - vec.xCoord, ownerEntity.posY - vec.yCoord, ownerEntity.posZ - vec.zCoord);
				} else {
					// The hook isn't secured into something yet, so constrain the hook to the rope's length.
					this.setPosition(this.posX + vec.xCoord, this.posY + vec.yCoord, this.posZ + vec.zCoord);
				}
			}
		}
	}

	private void reelInGrappleHook() {
		float currentRopeLength = dataWatcher.getWatchableObjectFloat(18);
		if (currentRopeLength > MIN_ROPE_LENGTH) {
			currentRopeLength -= .2;
			if (currentRopeLength < MIN_ROPE_LENGTH) {
				currentRopeLength = MIN_ROPE_LENGTH;
			}
			dataWatcher.updateObject(18, currentRopeLength);
		}
	}
	
	public void activateGrappleHook() {
		activated = true;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
		/*
		 * //if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) if (this.inGround && this.arrowShake <= 0) { if (active) { active = false;
		 * System.out.println("Collided with grappling hook, setting inactive!"); } }
		 */
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		// Default arrow NBT
		tagCompound.setShort("xTile", (short) this.xTile);
		tagCompound.setShort("yTile", (short) this.yTile);
		tagCompound.setShort("zTile", (short) this.zTile);
		tagCompound.setByte("inTile", (byte) this.inTile);
		tagCompound.setByte("inData", (byte) this.inData);
		tagCompound.setByte("shake", (byte) this.arrowShake);
		tagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		tagCompound.setByte("pickup", (byte) this.canBePickedUp);
		tagCompound.setDouble("damage", this.damage);

		// Grapple hook NBT
		tagCompound.setFloat("ropeLength", ropeLength);
		tagCompound.setShort("ownerEntityId", (short) ownerEntity.entityId);
		tagCompound.setByte("side", dataWatcher.getWatchableObjectByte(19));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		// Default arrow NBT
		this.xTile = tagCompound.getShort("xTile");
		this.yTile = tagCompound.getShort("yTile");
		this.zTile = tagCompound.getShort("zTile");
		this.inTile = tagCompound.getByte("inTile") & 255;
		this.inData = tagCompound.getByte("inData") & 255;
		this.arrowShake = tagCompound.getByte("shake") & 255;
		this.inGround = tagCompound.getByte("inGround") == 1;
		if (tagCompound.hasKey("damage")) {
			this.damage = tagCompound.getDouble("damage");
		}
		if (tagCompound.hasKey("pickup")) {
			this.canBePickedUp = tagCompound.getByte("pickup");
		} else if (tagCompound.hasKey("player")) {
			this.canBePickedUp = tagCompound.getBoolean("player") ? 1 : 0;
		}

		// Grapple hook NBT
		this.ropeLength = tagCompound.getFloat("ropeLenght");
		int ownerEntityId = tagCompound.getShort("ownerEntityId");
		Entity entity = worldObj.getEntityByID(ownerEntityId);
		if (entity != null && entity instanceof EntityPlayer) {
			this.ownerEntity = (EntityPlayer) entity;
		} else {
			this.setDead();
		}
		byte sideByte = tagCompound.getByte("side");
		dataWatcher.updateObject(19, sideByte);
	}
}