package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ClientboundUpdateAdvancementsPacket implements Packet<ClientGamePacketListener> {
   private final boolean reset;
   private final Map<ResourceLocation, Advancement.Builder> added;
   private final Set<ResourceLocation> removed;
   private final Map<ResourceLocation, AdvancementProgress> progress;

   public ClientboundUpdateAdvancementsPacket(
      boolean var1, Collection<Advancement> var2, Set<ResourceLocation> var3, Map<ResourceLocation, AdvancementProgress> var4
   ) {
      this.reset = â˜ƒ;
      Builder<ResourceLocation, Advancement.Builder> â˜ƒ = ImmutableMap.builder();

      for(Advancement â˜ƒx : â˜ƒ) {
         â˜ƒ.put(â˜ƒx.getId(), â˜ƒx.deconstruct());
      }

      this.added = â˜ƒ.build();
      this.removed = ImmutableSet.copyOf(â˜ƒ);
      this.progress = ImmutableMap.copyOf(â˜ƒ);
   }

   public ClientboundUpdateAdvancementsPacket(FriendlyByteBuf var1) {
      this.reset = â˜ƒ.readBoolean();
      this.added = â˜ƒ.readMap(FriendlyByteBuf::readResourceLocation, Advancement.Builder::fromNetwork);
      this.removed = â˜ƒ.readCollection(Sets::newLinkedHashSetWithExpectedSize, FriendlyByteBuf::readResourceLocation);
      this.progress = â˜ƒ.readMap(FriendlyByteBuf::readResourceLocation, AdvancementProgress::fromNetwork);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBoolean(this.reset);
      â˜ƒ.writeMap(this.added, FriendlyByteBuf::writeResourceLocation, (var0, var1x) -> var1x.serializeToNetwork(var0));
      â˜ƒ.writeCollection(this.removed, FriendlyByteBuf::writeResourceLocation);
      â˜ƒ.writeMap(this.progress, FriendlyByteBuf::writeResourceLocation, (var0, var1x) -> var1x.serializeToNetwork(var0));
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleUpdateAdvancementsPacket(this);
   }

   public Map<ResourceLocation, Advancement.Builder> getAdded() {
      return this.added;
   }

   public Set<ResourceLocation> getRemoved() {
      return this.removed;
   }

   public Map<ResourceLocation, AdvancementProgress> getProgress() {
      return this.progress;
   }

   public boolean shouldReset() {
      return this.reset;
   }
}
