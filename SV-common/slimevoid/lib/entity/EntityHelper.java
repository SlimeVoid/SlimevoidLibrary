package slimevoid.lib.entity;

import net.minecraft.entity.Entity;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityHelper {
	// Get the entity with the given entity ID
	@SideOnly(Side.CLIENT)
	public static Entity getEntityByID(int entityId) {
		if (entityId == ModLoader.getMinecraftInstance().thePlayer.entityId) {
			return ModLoader.getMinecraftInstance().thePlayer;
		} else {
			for (int j = 0; j < ModLoader.getMinecraftInstance().theWorld.loadedEntityList
					.size(); j++) {
				Entity entity = (Entity) ModLoader.getMinecraftInstance().theWorld.loadedEntityList
						.get(j);
				if (entity == null) {
					return null;
				}
				if (entity.entityId == entityId) {
					return entity;
				}
			}
		}
		return null;
	}
}
