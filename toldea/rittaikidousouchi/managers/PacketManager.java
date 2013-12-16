package toldea.rittaikidousouchi.managers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import toldea.rittaikidousouchi.entity.projectile.EntityGrappleHook.Side;
import toldea.rittaikidousouchi.item.ItemRittaiKidouSouchiHandgrips;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketManager implements IPacketHandler {
	public static final String CHANNEL = "ToldeaRKS";

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);

		EntityPlayer entityPlayer = (EntityPlayer) player;

		byte packetId = reader.readByte();

		byte b;
		int entityId;
		ItemStack itemStack;

		switch (packetId) {
		case 0:
			b = reader.readByte();
			itemStack = entityPlayer.getCurrentEquippedItem();
			if (itemStack.itemID == ItemManager.itemRKSHandgrips.itemID) {
				Side side = (b == 0 ? Side.Left : Side.Right);
				((ItemRittaiKidouSouchiHandgrips) itemStack.getItem()).onGrappleHookInteract(side, entityPlayer.worldObj, entityPlayer);
			}
			break;
		}
	}

	public static void sendGrappleHookInteractPacketToServer(Side side) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 0);
			dataStream.writeByte(side == Side.Left ? (byte) 0 : (byte) 1);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray()));
		} catch (IOException ex) {
			System.err.append("RittaiKidouSouchi: Failed to send GrappleHookInteract packet!");
		}
	}
}
