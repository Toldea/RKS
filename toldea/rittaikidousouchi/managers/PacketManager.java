package toldea.rittaikidousouchi.managers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketManager implements IPacketHandler {
	public static final String CHANNEL = "ToldeaRKS";

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);

		EntityPlayer entityPlayer = (EntityPlayer) player;

		byte packetId = reader.readByte();
		
		int entityId;
		/*
		switch (packetId) {
		case 0:
			entityId = reader.readInt();
			Entity entity = entityPlayer.worldObj.getEntityByID(entityId);
			if (entity != null && entity instanceof EntityGrappleHook) {
				((EntityGrappleHook) entity).pullOwnerEntityTowardsHook();
			}
			break;
		}*/
	}
	/*
	public static void sendActiveGrappleHookPacketToAllPlayers(EntityGrappleHook entityGrappleHook) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 0);
			dataStream.writeInt(entityGrappleHook.entityId);
			
			PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray()));
		} catch (IOException ex) {
			System.err.append("RomeCraft: Failed to send sendActiveGrappleHook packet!");
		}
	}*/
}
