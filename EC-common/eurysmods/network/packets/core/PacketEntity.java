package eurysmods.network.packets.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.World;

public abstract class PacketEntity extends PacketUpdate {
	private int entityId;

	public PacketEntity() {
		super(PacketIds.ENTITY);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(this.entityId);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.entityId = data.readInt();
	}

	@Override
	public boolean targetExists(World world) {
		List entities = world.loadedEntityList;
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);
			if (entity != null && entity.entityId == this.getEntityId()) {
				return true;
			}
		}
		return false;
	}

	public Entity getEntity(World world) {
		if (targetExists(world)) {
			List entities = world.loadedEntityList;
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = (Entity) entities.get(i);
				if (entity != null && entity.entityId == this.getEntityId()) {
					return entity;
				}
			}
		}
		return null;
	}
}
