package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ClientboundSelectAdvancementsTabPacket implements Packet<ClientGamePacketListener> {
   @Nullable
   private final ResourceLocation tab;

   public ClientboundSelectAdvancementsTabPacket(@Nullable ResourceLocation var1) {
      this.tab = â˜ƒ;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSelectAdvancementsTab(this);
   }

   public ClientboundSelectAdvancementsTabPacket(FriendlyByteBuf var1) {
      if (â˜ƒ.readBoolean()) {
         this.tab = â˜ƒ.readResourceLocation();
      } else {
         this.tab = null;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBoolean(this.tab != null);
      if (this.tab != null) {
         â˜ƒ.writeResourceLocation(this.tab);
      }
   }

   @Nullable
   public ResourceLocation getTab() {
      return this.tab;
   }
}
