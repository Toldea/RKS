package toldea.rittaikidousouchi.client.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelAdvancedTest {
	private IModelCustom modelAdvancedTest;

	public ModelAdvancedTest() {
		modelAdvancedTest = AdvancedModelLoader.loadModel("/assets/rittaikidousouchi/models/DerpyCube.obj");
	}

	public void render() {
		modelAdvancedTest.renderAll();
	}
}
