package net.minecraft.src;

import java.util.zip.Deflater;

/** Converts host entities and worlds into shared Beta packet values. */
public final class PacketFactory {
	private PacketFactory() {}



	public static Packet18Animation createPacket18Animation() {
		Packet18Animation packet = new Packet18Animation();
		return packet;
	}

	public static Packet18Animation createPacket18Animation(Entity var1, int var2) {
		Packet18Animation packet = new Packet18Animation();
		packet.entityId = var1.entityId;
		packet.animate = var2;
		return packet;
	}

	public static Packet19EntityAction createPacket19EntityAction() {
		Packet19EntityAction packet = new Packet19EntityAction();
		return packet;
	}

	public static Packet19EntityAction createPacket19EntityAction(Entity var1, int var2) {
		Packet19EntityAction packet = new Packet19EntityAction();
		packet.entityId = var1.entityId;
		packet.state = var2;
		return packet;
	}

	public static Packet20NamedEntitySpawn createPacket20NamedEntitySpawn() {
		Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn();
		return packet;
	}

	public static Packet20NamedEntitySpawn createPacket20NamedEntitySpawn(EntityPlayer var1) {
		Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn();
		packet.entityId = var1.entityId;
		packet.name = var1.username;
		packet.xPosition = MathHelper.floor_double(var1.posX * 32.0D);
		packet.yPosition = MathHelper.floor_double(var1.posY * 32.0D);
		packet.zPosition = MathHelper.floor_double(var1.posZ * 32.0D);
		packet.rotation = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
		packet.pitch = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
		ItemStack var2 = var1.inventory.getCurrentItem();
		packet.currentItem = var2 == null ? 0 : var2.itemID;
		return packet;
	}

	public static Packet21PickupSpawn createPacket21PickupSpawn() {
		Packet21PickupSpawn packet = new Packet21PickupSpawn();
		return packet;
	}

	public static Packet21PickupSpawn createPacket21PickupSpawn(EntityItem var1) {
		Packet21PickupSpawn packet = new Packet21PickupSpawn();
		packet.entityId = var1.entityId;
		packet.itemID = var1.item.itemID;
		packet.count = var1.item.stackSize;
		packet.itemDamage = var1.item.getItemDamage();
		packet.xPosition = MathHelper.floor_double(var1.posX * 32.0D);
		packet.yPosition = MathHelper.floor_double(var1.posY * 32.0D);
		packet.zPosition = MathHelper.floor_double(var1.posZ * 32.0D);
		packet.rotation = (byte)((int)(var1.motionX * 128.0D));
		packet.pitch = (byte)((int)(var1.motionY * 128.0D));
		packet.roll = (byte)((int)(var1.motionZ * 128.0D));
		return packet;
	}




	public static Packet24MobSpawn createPacket24MobSpawn() {
		Packet24MobSpawn packet = new Packet24MobSpawn();
		return packet;
	}

	public static Packet24MobSpawn createPacket24MobSpawn(EntityLiving var1) {
		Packet24MobSpawn packet = new Packet24MobSpawn();
		packet.entityId = var1.entityId;
		packet.type = (byte)EntityList.getEntityID(var1);
		packet.xPosition = MathHelper.floor_double(var1.posX * 32.0D);
		packet.yPosition = MathHelper.floor_double(var1.posY * 32.0D);
		packet.zPosition = MathHelper.floor_double(var1.posZ * 32.0D);
		packet.yaw = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
		packet.pitch = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
		packet.metaData = var1.getDataWatcher();
		return packet;
	}

	public static Packet25EntityPainting createPacket25EntityPainting() {
		Packet25EntityPainting packet = new Packet25EntityPainting();
		return packet;
	}

	public static Packet25EntityPainting createPacket25EntityPainting(EntityPainting var1) {
		Packet25EntityPainting packet = new Packet25EntityPainting();
		packet.entityId = var1.entityId;
		packet.xPosition = var1.xPosition;
		packet.yPosition = var1.yPosition;
		packet.zPosition = var1.zPosition;
		packet.direction = var1.direction;
		packet.title = var1.art.title;
		return packet;
	}

	public static Packet28EntityVelocity createPacket28EntityVelocity() {
		Packet28EntityVelocity packet = new Packet28EntityVelocity();
		return packet;
	}

	public static Packet28EntityVelocity createPacket28EntityVelocity(Entity var1) {
		return createPacket28EntityVelocity(var1.entityId, var1.motionX, var1.motionY, var1.motionZ);
	}

	public static Packet28EntityVelocity createPacket28EntityVelocity(int var1, double var2, double var4, double var6) {
		Packet28EntityVelocity packet = new Packet28EntityVelocity();
		packet.entityId = var1;
		double var8 = 3.9D;
		if(var2 < -var8) {
			var2 = -var8;
		}

		if(var4 < -var8) {
			var4 = -var8;
		}

		if(var6 < -var8) {
			var6 = -var8;
		}

		if(var2 > var8) {
			var2 = var8;
		}

		if(var4 > var8) {
			var4 = var8;
		}

		if(var6 > var8) {
			var6 = var8;
		}

		packet.motionX = (int)(var2 * 8000.0D);
		packet.motionY = (int)(var4 * 8000.0D);
		packet.motionZ = (int)(var6 * 8000.0D);
		return packet;
	}

	public static Packet34EntityTeleport createPacket34EntityTeleport() {
		Packet34EntityTeleport packet = new Packet34EntityTeleport();
		return packet;
	}

	public static Packet34EntityTeleport createPacket34EntityTeleport(Entity var1) {
		Packet34EntityTeleport packet = new Packet34EntityTeleport();
		packet.entityId = var1.entityId;
		packet.xPosition = MathHelper.floor_double(var1.posX * 32.0D);
		packet.yPosition = MathHelper.floor_double(var1.posY * 32.0D);
		packet.zPosition = MathHelper.floor_double(var1.posZ * 32.0D);
		packet.yaw = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
		packet.pitch = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
		return packet;
	}




	public static Packet51MapChunk createPacket51MapChunk() {
		Packet51MapChunk packet = new Packet51MapChunk();
		packet.isChunkDataPacket = true;
		return packet;
	}


	public static Packet52MultiBlockChange createPacket52MultiBlockChange() {
		Packet52MultiBlockChange packet = new Packet52MultiBlockChange();
		packet.isChunkDataPacket = true;
		return packet;
	}


	public static Packet53BlockChange createPacket53BlockChange() {
		Packet53BlockChange packet = new Packet53BlockChange();
		packet.isChunkDataPacket = true;
		return packet;
	}


	public static Packet71Weather createPacket71Weather() {
		Packet71Weather packet = new Packet71Weather();
		return packet;
	}

	public static Packet71Weather createPacket71Weather(Entity var1) {
		Packet71Weather packet = new Packet71Weather();
		packet.field_27054_a = var1.entityId;
		packet.field_27053_b = MathHelper.floor_double(var1.posX * 32.0D);
		packet.field_27057_c = MathHelper.floor_double(var1.posY * 32.0D);
		packet.field_27056_d = MathHelper.floor_double(var1.posZ * 32.0D);
		if(var1 instanceof EntityLightningBolt) {
			packet.field_27055_e = 1;
		}

		return packet;
	}

	public static Packet17Sleep createPacket17Sleep() {
		Packet17Sleep packet = new Packet17Sleep();
		return packet;
	}

	public static Packet17Sleep createPacket17Sleep(Entity var1, int var2, int var3, int var4, int var5) {
		Packet17Sleep packet = new Packet17Sleep();
		packet.field_22046_e = var2;
		packet.field_22044_b = var3;
		packet.field_22048_c = var4;
		packet.field_22047_d = var5;
		packet.field_22045_a = var1.entityId;
		return packet;
	}

	public static Packet23VehicleSpawn createPacket23VehicleSpawn() {
		Packet23VehicleSpawn packet = new Packet23VehicleSpawn();
		return packet;
	}

	public static Packet23VehicleSpawn createPacket23VehicleSpawn(Entity var1, int var2) {
		return createPacket23VehicleSpawn(var1, var2, 0);
	}

	public static Packet23VehicleSpawn createPacket23VehicleSpawn(Entity var1, int var2, int var3) {
		Packet23VehicleSpawn packet = new Packet23VehicleSpawn();
		packet.entityId = var1.entityId;
		packet.xPosition = MathHelper.floor_double(var1.posX * 32.0D);
		packet.yPosition = MathHelper.floor_double(var1.posY * 32.0D);
		packet.zPosition = MathHelper.floor_double(var1.posZ * 32.0D);
		packet.type = var2;
		packet.field_28044_i = var3;
		if(var3 > 0) {
			double var4 = var1.motionX;
			double var6 = var1.motionY;
			double var8 = var1.motionZ;
			double var10 = 3.9D;
			if(var4 < -var10) {
				var4 = -var10;
			}

			if(var6 < -var10) {
				var6 = -var10;
			}

			if(var8 < -var10) {
				var8 = -var10;
			}

			if(var4 > var10) {
				var4 = var10;
			}

			if(var6 > var10) {
				var6 = var10;
			}

			if(var8 > var10) {
				var8 = var10;
			}

			packet.field_28047_e = (int)(var4 * 8000.0D);
			packet.field_28046_f = (int)(var6 * 8000.0D);
			packet.field_28045_g = (int)(var8 * 8000.0D);
		}

		return packet;
	}

	public static Packet34EntityTeleport createPacket34EntityTeleport(int var1, int var2, int var3, int var4, byte var5, byte var6) {
		Packet34EntityTeleport packet = new Packet34EntityTeleport();
		packet.entityId = var1;
		packet.xPosition = var2;
		packet.yPosition = var3;
		packet.zPosition = var4;
		packet.yaw = var5;
		packet.pitch = var6;
		return packet;
	}

	public static Packet39AttachEntity createPacket39AttachEntity() {
		Packet39AttachEntity packet = new Packet39AttachEntity();
		return packet;
	}

	public static Packet39AttachEntity createPacket39AttachEntity(Entity var1, Entity var2) {
		Packet39AttachEntity packet = new Packet39AttachEntity();
		packet.entityId = var1.entityId;
		packet.vehicleEntityId = var2 != null ? var2.entityId : -1;
		return packet;
	}

	public static Packet51MapChunk createPacket51MapChunk(int var1, int var2, int var3, int var4, int var5, int var6, World var7) {
		Packet51MapChunk packet = new Packet51MapChunk();
		packet.isChunkDataPacket = true;
		packet.xPosition = var1;
		packet.yPosition = var2;
		packet.zPosition = var3;
		packet.xSize = var4;
		packet.ySize = var5;
		packet.zSize = var6;
		byte[] var8 = var7.getChunkData(var1, var2, var3, var4, var5, var6);
		Deflater var9 = new Deflater(-1);

		try {
			var9.setInput(var8);
			var9.finish();
			packet.chunk = new byte[var4 * var5 * var6 * 5 / 2];
			packet.chunkSize = var9.deflate(packet.chunk);
		} finally {
			var9.end();
		}

		return packet;
	}

	public static Packet52MultiBlockChange createPacket52MultiBlockChange(int var1, int var2, short[] var3, int var4, World var5) {
		Packet52MultiBlockChange packet = new Packet52MultiBlockChange();
		packet.isChunkDataPacket = true;
		packet.xPosition = var1;
		packet.zPosition = var2;
		packet.size = var4;
		packet.coordinateArray = new short[var4];
		packet.typeArray = new byte[var4];
		packet.metadataArray = new byte[var4];
		Chunk var6 = var5.getChunkFromChunkCoords(var1, var2);

		for(int var7 = 0; var7 < var4; ++var7) {
			int var8 = var3[var7] >> 12 & 15;
			int var9 = var3[var7] >> 8 & 15;
			int var10 = var3[var7] & 255;
			packet.coordinateArray[var7] = var3[var7];
			packet.typeArray[var7] = (byte)var6.getBlockID(var8, var10, var9);
			packet.metadataArray[var7] = (byte)var6.getBlockMetadata(var8, var10, var9);
		}

		return packet;
	}

	public static Packet53BlockChange createPacket53BlockChange(int var1, int var2, int var3, World var4) {
		Packet53BlockChange packet = new Packet53BlockChange();
		packet.isChunkDataPacket = true;
		packet.xPosition = var1;
		packet.yPosition = var2;
		packet.zPosition = var3;
		packet.type = var4.getBlockId(var1, var2, var3);
		packet.metadata = var4.getBlockMetadata(var1, var2, var3);
		return packet;
	}
}
