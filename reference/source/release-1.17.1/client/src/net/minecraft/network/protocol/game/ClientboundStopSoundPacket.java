package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

public class ClientboundStopSoundPacket implements Packet<ClientGamePacketListener> {
   private static final int HAS_SOURCE = 1;
   private static final int HAS_SOUND = 2;
   @Nullable
   private final ResourceLocation name;
   @Nullable
   private final SoundSource source;

   public ClientboundStopSoundPacket(@Nullable ResourceLocation var1, @Nullable SoundSource var2) {
      this.name = â˜ƒ;
      this.source = â˜ƒ;
   }

   public ClientboundStopSoundPacket(FriendlyByteBuf var1) {
      int â˜ƒ = â˜ƒ.readByte();
      if ((â˜ƒ & 1) > 0) {
         this.source = â˜ƒ.readEnum(SoundSource.class);
      } else {
         this.source = null;
      }

      if ((â˜ƒ & 2) > 0) {
         this.name = â˜ƒ.readResourceLocation();
      } else {
         this.name = null;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      if (this.source != null) {
         if (this.name != null) {
            â˜ƒ.writeByte(3);
            â˜ƒ.writeEnum(this.source);
            â˜ƒ.writeResourceLocation(this.name);
         } else {
            â˜ƒ.writeByte(1);
            â˜ƒ.writeEnum(this.source);
         }
      } else if (this.name != null) {
         â˜ƒ.writeByte(2);
         â˜ƒ.writeResourceLocation(this.name);
      } else {
         â˜ƒ.writeByte(0);
      }
   }

   @Nullable
   public ResourceLocation getName() {
      return this.name;
   }

   @Nullable
   public SoundSource getSource() {
      return this.source;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleStopSoundEvent(this);
   }
}
