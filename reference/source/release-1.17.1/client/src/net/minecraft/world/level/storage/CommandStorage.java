package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;

public class CommandStorage {
   private static final String ID_PREFIX = "command_storage_";
   private final Map<String, CommandStorage.Container> namespaces = Maps.newHashMap();
   private final DimensionDataStorage storage;

   public CommandStorage(DimensionDataStorage var1) {
      this.storage = â˜ƒ;
   }

   private CommandStorage.Container newStorage(String var1) {
      CommandStorage.Container â˜ƒ = new CommandStorage.Container();
      this.namespaces.put(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public CompoundTag get(ResourceLocation var1) {
      String â˜ƒ = â˜ƒ.getNamespace();
      CommandStorage.Container â˜ƒx = this.storage.get(var2x -> this.newStorage(â˜ƒ).load(var2x), createId(â˜ƒ));
      return â˜ƒx != null ? â˜ƒx.get(â˜ƒ.getPath()) : new CompoundTag();
   }

   public void set(ResourceLocation var1, CompoundTag var2) {
      String â˜ƒ = â˜ƒ.getNamespace();
      this.storage
         .<CommandStorage.Container>computeIfAbsent(var2x -> this.newStorage(â˜ƒ).load(var2x), () -> this.newStorage(â˜ƒ), createId(â˜ƒ))
         .put(â˜ƒ.getPath(), â˜ƒ);
   }

   public Stream<ResourceLocation> keys() {
      return this.namespaces.entrySet().stream().flatMap(var0 -> ((CommandStorage.Container)var0.getValue()).getKeys((String)var0.getKey()));
   }

   private static String createId(String var0) {
      return "command_storage_" + â˜ƒ;
   }

   static class Container extends SavedData {
      private static final String TAG_CONTENTS = "contents";
      private final Map<String, CompoundTag> storage = Maps.newHashMap();

      CommandStorage.Container load(CompoundTag var1) {
         CompoundTag â˜ƒ = â˜ƒ.getCompound("contents");

         for(String â˜ƒx : â˜ƒ.getAllKeys()) {
            this.storage.put(â˜ƒx, â˜ƒ.getCompound(â˜ƒx));
         }

         return this;
      }

      @Override
      public CompoundTag save(CompoundTag var1) {
         CompoundTag â˜ƒ = new CompoundTag();
         this.storage.forEach((var1x, var2x) -> â˜ƒ.put(var1x, var2x.copy()));
         â˜ƒ.put("contents", â˜ƒ);
         return â˜ƒ;
      }

      public CompoundTag get(String var1) {
         CompoundTag â˜ƒ = (CompoundTag)this.storage.get(â˜ƒ);
         return â˜ƒ != null ? â˜ƒ : new CompoundTag();
      }

      public void put(String var1, CompoundTag var2) {
         if (â˜ƒ.isEmpty()) {
            this.storage.remove(â˜ƒ);
         } else {
            this.storage.put(â˜ƒ, â˜ƒ);
         }

         this.setDirty();
      }

      public Stream<ResourceLocation> getKeys(String var1) {
         return this.storage.keySet().stream().map(var1x -> new ResourceLocation(â˜ƒ, var1x));
      }
   }
}
