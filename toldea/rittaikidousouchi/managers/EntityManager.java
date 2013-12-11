package toldea.rittaikidousouchi.managers;

import toldea.rittaikidousouchi.RittaiKidouSouchi;
import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook;
import cpw.mods.fml.common.registry.EntityRegistry;

public abstract class EntityManager {
	private static int nextModEntityId = 0;
	
	public static void registerEntities() {
		EntityRegistry.registerModEntity(EntityGrappleHook.class, "entityGrappleHook", nextModEntityId++, RittaiKidouSouchi.instance, 32, 5, true);
	}
}
