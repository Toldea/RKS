package toldea.rittaikidousouchi.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook;
import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook.Side;

public class RenderGrappleHook extends Render {

	private static final ResourceLocation textureGrappleHook = new ResourceLocation("textures/particle/particles.png");

	public void doRenderGrappleHook(EntityGrappleHook entityGrappleHook, double par2, double par4, double par6, float par8, float par9) {
		/*
		 * GL11.glPushMatrix(); GL11.glTranslatef((float)par2, (float)par4, (float)par6); GL11.glEnable(GL12.GL_RESCALE_NORMAL); GL11.glScalef(0.5F, 0.5F,
		 * 0.5F); this.bindEntityTexture(entityGrappleHook);
		 */
		Tessellator tessellator = Tessellator.instance;
		/*
		 * byte b0 = 1; byte b1 = 2; float f2 = (float)(b0 * 8 + 0) / 128.0F; float f3 = (float)(b0 * 8 + 8) / 128.0F; float f4 = (float)(b1 * 8 + 0) / 128.0F;
		 * float f5 = (float)(b1 * 8 + 8) / 128.0F; float f6 = 1.0F; float f7 = 0.5F; float f8 = 0.5F; GL11.glRotatef(180.0F - this.renderManager.playerViewY,
		 * 0.0F, 1.0F, 0.0F); GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F); tessellator.startDrawingQuads(); tessellator.setNormal(0.0F,
		 * 1.0F, 0.0F); tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f2, (double)f5);
		 * tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f3, (double)f5); tessellator.addVertexWithUV((double)(f6 - f7),
		 * (double)(1.0F - f8), 0.0D, (double)f3, (double)f4); tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f2,
		 * (double)f4); tessellator.draw(); GL11.glDisable(GL12.GL_RESCALE_NORMAL); GL11.glPopMatrix();
		 */
		EntityPlayer ownerPlayer = entityGrappleHook.getOwner();
		if (ownerPlayer != null) {
			/*
			 * float f9 = ownerPlayer.getSwingProgress(par9); float f10 = MathHelper.sin(MathHelper.sqrt_float(f9) * (float)Math.PI); Vec3 vec3 =
			 * entityGrappleHook.worldObj.getWorldVec3Pool().getVecFromPool(-0.5D, 0.03D, 0.8D); vec3.rotateAroundX(-(ownerPlayer.prevRotationPitch +
			 * (ownerPlayer.rotationPitch - ownerPlayer.prevRotationPitch) * par9) * (float)Math.PI / 180.0F); vec3.rotateAroundY(-(ownerPlayer.prevRotationYaw
			 * + (ownerPlayer.rotationYaw - ownerPlayer.prevRotationYaw) * par9) * (float)Math.PI / 180.0F); vec3.rotateAroundY(f10 * 0.5F);
			 * vec3.rotateAroundX(-f10 * 0.7F); double d3 = ownerPlayer.prevPosX + (ownerPlayer.posX - ownerPlayer.prevPosX) * (double)par9 + vec3.xCoord;
			 * double d4 = ownerPlayer.prevPosY + (ownerPlayer.posY - ownerPlayer.prevPosY) * (double)par9 + vec3.yCoord; double d5 = ownerPlayer.prevPosZ +
			 * (ownerPlayer.posZ - ownerPlayer.prevPosZ) * (double)par9 + vec3.zCoord; double d6 = ownerPlayer == Minecraft.getMinecraft().thePlayer ? 0.0D :
			 * (double)ownerPlayer.getEyeHeight();
			 * 
			 * if (this.renderManager.options.thirdPersonView > 0 || ownerPlayer != Minecraft.getMinecraft().thePlayer) { float f11 =
			 * (ownerPlayer.prevRenderYawOffset + (ownerPlayer.renderYawOffset - ownerPlayer.prevRenderYawOffset) * par9) * (float)Math.PI / 180.0F; double d7 =
			 * (double)MathHelper.sin(f11); double d8 = (double)MathHelper.cos(f11); d3 = ownerPlayer.prevPosX + (ownerPlayer.posX - ownerPlayer.prevPosX) *
			 * (double)par9 - d8 * 0.35D - d7 * 0.85D; d4 = ownerPlayer.prevPosY + d6 + (ownerPlayer.posY - ownerPlayer.prevPosY) * (double)par9 - 0.45D; d5 =
			 * ownerPlayer.prevPosZ + (ownerPlayer.posZ - ownerPlayer.prevPosZ) * (double)par9 - d7 * 0.35D + d8 * 0.85D; }
			 * 
			 * double d9 = entityGrappleHook.prevPosX + (entityGrappleHook.posX - entityGrappleHook.prevPosX) * (double)par9; double d10 =
			 * entityGrappleHook.prevPosY + (entityGrappleHook.posY - entityGrappleHook.prevPosY) * (double)par9 + 0.25D; double d11 =
			 * entityGrappleHook.prevPosZ + (entityGrappleHook.posZ - entityGrappleHook.prevPosZ) * (double)par9; double d12 = (double)((float)(d3 - d9));
			 * double d13 = (double)((float)(d4 - d10)); double d14 = (double)((float)(d5 - d11));
			 */
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			tessellator.startDrawing(3);
			tessellator.setColorOpaque_I(0);
			/*
			 * byte b2 = 16; for (int i = 0; i <= b2; ++i) { float f12 = (float)i / (float)b2; tessellator.addVertex(par2 + d12 * (double)f12, par4 + d13 *
			 * (double)(f12 * f12 + f12) * 0.5D + 0.25D, par6 + d14 * (double)f12); System.out.println("Correct Vector: " + (par2 + d12 * (double)f12) + ", " +
			 * (par4 + d13 * (double)(f12 * f12 + f12) * 0.5D + 0.25D) + ", " + (par6 + d14 * (double)f12) + ")"); }
			 */

			tessellator.addVertex(entityGrappleHook.posX - ownerPlayer.posX, entityGrappleHook.posY - ownerPlayer.posY, entityGrappleHook.posZ
					- ownerPlayer.posZ);

			Side side = entityGrappleHook.getSide();

			if (this.renderManager.options.thirdPersonView > 0 || ownerPlayer != Minecraft.getMinecraft().thePlayer) {
				tessellator.addVertex((side == Side.Left ? -.24 : .24), -.55, 0d);
			} else {
				tessellator.addVertex(0d, 0d, 0d);
			}

			tessellator.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}

	protected void kuroiYami(EntityGrappleHook entityGrappleHook, double par2, double par4, double par6, float par8, float par9) {
		// EntityPlayer ownerPlayer = entityGrappleHook.getOwner();

		if (entityGrappleHook != null) {
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			tessellator.startDrawing(5);
			int i;
			float f2;

			for (i = 0; i <= 24; ++i) {
				if (i % 2 == 0) {
					tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
				} else {
					tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
				}

				f2 = (float) i / 24.0F;
				tessellator.addVertex(par2 * (double) f2 + 0.0D, par4 * (double) (f2 * f2 + f2) * 0.5D + (double) ((24.0F - (float) i) / 18.0F + 0.125F), par6
						* (double) f2);
				tessellator.addVertex(par2 * (double) f2 + 0.025D, par4 * (double) (f2 * f2 + f2) * 0.5D + (double) ((24.0F - (float) i) / 18.0F + 0.125F)
						+ 0.025D, par6 * (double) f2);
			}

			tessellator.draw();
			tessellator.startDrawing(5);

			for (i = 0; i <= 24; ++i) {
				if (i % 2 == 0) {
					tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
				} else {
					tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
				}

				f2 = (float) i / 24.0F;
				tessellator.addVertex(par2 * (double) f2 + 0.0D, par4 * (double) (f2 * f2 + f2) * 0.5D + (double) ((24.0F - (float) i) / 18.0F + 0.125F)
						+ 0.025D, par6 * (double) f2);
				tessellator.addVertex(par2 * (double) f2 + 0.025D, par4 * (double) (f2 * f2 + f2) * 0.5D + (double) ((24.0F - (float) i) / 18.0F + 0.125F),
						par6 * (double) f2 + 0.025D);
			}

			tessellator.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	private double func_110828_a(double par1, double par3, double par5) {
		return par1 + (par3 - par1) * par5;
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.doRenderGrappleHook((EntityGrappleHook) par1Entity, par2, par4, par6, par8, par9);
		this.kuroiYami((EntityGrappleHook) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return textureGrappleHook;
	}

}
