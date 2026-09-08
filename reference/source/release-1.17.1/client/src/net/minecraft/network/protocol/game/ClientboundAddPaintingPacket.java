package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;

public class ClientboundAddPaintingPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final UUID uuid;
   private final BlockPos pos;
   private final Direction direction;
   private final int motive;

   public ClientboundAddPaintingPacket(Painting var1) {
      this.id = â˜ƒ.getId();
      this.uuid = â˜ƒ.getUUID();
      this.pos = â˜ƒ.getPos();
      this.direction = â˜ƒ.getDirection();
      this.motive = Registry.MOTIVE.getId(â˜ƒ.motive);
   }

   public ClientboundAddPaintingPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.uuid = â˜ƒ.readUUID();
      this.motive = â˜ƒ.readVarInt();
      this.pos = â˜ƒ.readBlockPos();
      this.direction = Direction.from2DDataValue(â˜ƒ.readUnsignedByte());
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeUUID(this.uuid);
      â˜ƒ.writeVarInt(this.motive);
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeByte(this.direction.get2DDataValue());
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddPainting(this);
   }

   public int getId() {
      return this.id;
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public Motive getMotive() {
      return Registry.MOTIVE.byId(this.motive);
   }
}
