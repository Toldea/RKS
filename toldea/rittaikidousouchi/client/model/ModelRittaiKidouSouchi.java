package toldea.rittaikidousouchi.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import toldea.rittaikidousouchi.managers.ModelManager;

public class ModelRittaiKidouSouchi extends ModelBiped {
	private IModelCustom modelBackpiece;
	private IModelCustom modelScabbard;
	private IModelCustom modelHookShooter;

	public ModelRittaiKidouSouchi() {
		modelBackpiece = AdvancedModelLoader.loadModel(ModelManager.BACK_PIECE);
		modelScabbard = AdvancedModelLoader.loadModel(ModelManager.SCABBARD);
		modelHookShooter = AdvancedModelLoader.loadModel(ModelManager.HOOK_SHOOTER);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		renderBackPiece(); // Back Piece
		renderScabbard(.3, 5.0); // Right Scabbard
		renderScabbard(-.3, -5.0); // Left Scabbard
		renderHookShooter(.24, 1.0); // Right Hook Shooter
		renderHookShooter(-.2, -1.0); // Left Hook Shooter
	}

	public void renderBackPiece() {
		GL11.glPushMatrix();

		GL11.glRotated(180.0, .0, .0, 1.0);
		GL11.glTranslated(.0, -.55, .3);
		GL11.glScaled(.0625, .0625, .0625);

		GL11.glScaled(.55, .55, .55);
		GL11.glRotated(45.0, 1.0, .0, .0);

		modelBackpiece.renderAll();

		GL11.glPopMatrix();
	}

	public void renderScabbard(double xOffset, double yRotation) {
		GL11.glPushMatrix();

		GL11.glRotated(180.0, .0, .0, 1.0);
		GL11.glTranslated(xOffset, -.8, .1);
		GL11.glScaled(.0625, .0625, .0625);

		GL11.glScaled(.55, .55, .55);
		GL11.glRotated(15.0, 1.0, .0, .0);
		GL11.glRotated(yRotation, .0, 1.0, .0);

		modelScabbard.renderAll();

		GL11.glPopMatrix();
	}
	
	public void renderHookShooter(double xOffset, double xScale) {
		GL11.glPushMatrix();

		GL11.glRotated(180.0, .0, .0, 1.0);
		GL11.glTranslated(xOffset, -.55, .0);
		GL11.glScaled(.0625, .0625, .0625);

		GL11.glScaled(.55 * xScale, .55, .55);

		modelHookShooter.renderAll();

		GL11.glPopMatrix();
	}
}
