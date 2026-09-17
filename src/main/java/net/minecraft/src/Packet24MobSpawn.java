package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet24MobSpawn extends Packet {
	public int entityId;
	public byte type;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte yaw;
	public byte pitch;
	DataWatcher metaData;
	private List receivedMetadata;

	public Packet24MobSpawn() {
	}


	public void readPacketData(DataInputStream var1) throws IOException {
		this.entityId = var1.readInt();
		this.type = var1.readByte();
		this.xPosition = var1.readInt();
		this.yPosition = var1.readInt();
		this.zPosition = var1.readInt();
		this.yaw = var1.readByte();
		this.pitch = var1.readByte();
		this.receivedMetadata = DataWatcher.readWatchableObjects(var1, PacketItemStack.METADATA_CODEC);
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.entityId);
		var1.writeByte(this.type);
		var1.writeInt(this.xPosition);
		var1.writeInt(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.writeByte(this.yaw);
		var1.writeByte(this.pitch);
		this.metaData.writeWatchableObjects(var1);
	}

	public void processPacket(NetHandler var1) {
		var1.handleMobSpawn(this);
	}

	public int getPacketSize() {
		return 20;
	}

	public List getMetadata() {
		return this.receivedMetadata;
	}
}
