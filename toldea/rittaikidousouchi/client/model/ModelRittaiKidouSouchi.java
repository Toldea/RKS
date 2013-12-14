package toldea.rittaikidousouchi.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import toldea.rittaikidousouchi.managers.ModelManager;

public class ModelRittaiKidouSouchi extends ModelBiped {
	private IModelCustom modelRittaiKidouSouchi;

	public ModelRittaiKidouSouchi() {
		modelRittaiKidouSouchi = AdvancedModelLoader.loadModel(ModelManager.BACK_PIECE);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GL11.glPushMatrix();
		
		GL11.glRotated(180.0, .0, .0, 1.0);
		GL11.glTranslated(.0, -.55, .3);
		GL11.glScaled(.0625, .0625, .0625);
		
		GL11.glScaled(.55, .55, .55);
		GL11.glRotated(45.0, 1.0, .0, .0);
		
		modelRittaiKidouSouchi.renderAll();
		
		GL11.glPopMatrix();
	}
}
