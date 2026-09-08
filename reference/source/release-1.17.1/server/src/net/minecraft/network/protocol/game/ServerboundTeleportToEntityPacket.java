package net.minecraft.network.protocol.game;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class ServerboundTeleportToEntityPacket implements Packet<ServerGamePacketListener> {
   private final UUID uuid;

   public ServerboundTeleportToEntityPacket(UUID var1) {
      this.uuid = â˜ƒ;
   }

   public ServerboundTeleportToEntityPacket(FriendlyByteBuf var1) {
      this.uuid = â˜ƒ.readUUID();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUUID(this.uuid);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleTeleportToEntityPacket(this);
   }

   @Nullable
   public Entity getEntity(ServerLevel var1) {
      return â˜ƒ.getEntity(this.uuid);
   }
}
