package toldea.rittaikidousouchi.client;

import net.minecraft.world.World;
import toldea.rittaikidousouchi.CommonProxy;
import toldea.rittaikidousouchi.client.renderer.entity.RenderGrappleHook;
import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityGrappleHook.class, new RenderGrappleHook());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}