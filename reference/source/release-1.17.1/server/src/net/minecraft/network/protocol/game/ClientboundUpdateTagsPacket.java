package net.minecraft.network.protocol.game;

import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagCollection;

public class ClientboundUpdateTagsPacket implements Packet<ClientGamePacketListener> {
   private final Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> tags;

   public ClientboundUpdateTagsPacket(Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> var1) {
      this.tags = â˜ƒ;
   }

   public ClientboundUpdateTagsPacket(FriendlyByteBuf var1) {
      this.tags = â˜ƒ.readMap(var0 -> ResourceKey.createRegistryKey(var0.readResourceLocation()), TagCollection.NetworkPayload::read);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeMap(this.tags, (var0, var1x) -> var0.writeResourceLocation(var1x.location()), (var0, var1x) -> var1x.write(var0));
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleUpdateTags(this);
   }

   public Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> getTags() {
      return this.tags;
   }
}
