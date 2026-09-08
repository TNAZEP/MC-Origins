package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundSetCameraPacket implements Packet<ClientGamePacketListener> {
   private final int cameraId;

   public ClientboundSetCameraPacket(Entity var1) {
      this.cameraId = â˜ƒ.getId();
   }

   public ClientboundSetCameraPacket(FriendlyByteBuf var1) {
      this.cameraId = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.cameraId);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetCamera(this);
   }

   @Nullable
   public Entity getEntity(Level var1) {
      return â˜ƒ.getEntity(this.cameraId);
   }
}
