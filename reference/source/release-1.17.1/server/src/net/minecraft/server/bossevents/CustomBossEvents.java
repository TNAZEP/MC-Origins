package net.minecraft.server.bossevents;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CustomBossEvents {
   private final Map<ResourceLocation, CustomBossEvent> events = Maps.<ResourceLocation, CustomBossEvent>newHashMap();

   @Nullable
   public CustomBossEvent get(ResourceLocation var1) {
      return (CustomBossEvent)this.events.get(â˜ƒ);
   }

   public CustomBossEvent create(ResourceLocation var1, Component var2) {
      CustomBossEvent â˜ƒ = new CustomBossEvent(â˜ƒ, â˜ƒ);
      this.events.put(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public void remove(CustomBossEvent var1) {
      this.events.remove(â˜ƒ.getTextId());
   }

   public Collection<ResourceLocation> getIds() {
      return this.events.keySet();
   }

   public Collection<CustomBossEvent> getEvents() {
      return this.events.values();
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();

      for(CustomBossEvent â˜ƒx : this.events.values()) {
         â˜ƒ.put(â˜ƒx.getTextId().toString(), â˜ƒx.save());
      }

      return â˜ƒ;
   }

   public void load(CompoundTag var1) {
      for(String â˜ƒ : â˜ƒ.getAllKeys()) {
         ResourceLocation â˜ƒx = new ResourceLocation(â˜ƒ);
         this.events.put(â˜ƒx, CustomBossEvent.load(â˜ƒ.getCompound(â˜ƒ), â˜ƒx));
      }
   }

   public void onPlayerConnect(ServerPlayer var1) {
      for(CustomBossEvent â˜ƒ : this.events.values()) {
         â˜ƒ.onPlayerConnect(â˜ƒ);
      }
   }

   public void onPlayerDisconnect(ServerPlayer var1) {
      for(CustomBossEvent â˜ƒ : this.events.values()) {
         â˜ƒ.onPlayerDisconnect(â˜ƒ);
      }
   }
}
