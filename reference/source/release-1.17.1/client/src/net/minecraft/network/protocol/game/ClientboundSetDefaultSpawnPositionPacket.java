package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetDefaultSpawnPositionPacket implements Packet<ClientGamePacketListener> {
   private final BlockPos pos;
   private final float angle;

   public ClientboundSetDefaultSpawnPositionPacket(BlockPos var1, float var2) {
      this.pos = â˜ƒ;
      this.angle = â˜ƒ;
   }

   public ClientboundSetDefaultSpawnPositionPacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.angle = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeFloat(this.angle);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetSpawn(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public float getAngle() {
      return this.angle;
   }
}
