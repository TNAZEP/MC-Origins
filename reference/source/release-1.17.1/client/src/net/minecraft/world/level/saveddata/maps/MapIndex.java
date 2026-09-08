package net.minecraft.world.level.saveddata.maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class MapIndex extends SavedData {
   public static final String FILE_NAME = "idcounts";
   private final Object2IntMap<String> usedAuxIds = new Object2IntOpenHashMap();

   public MapIndex() {
      this.usedAuxIds.defaultReturnValue(-1);
   }

   public static MapIndex load(CompoundTag var0) {
      MapIndex â˜ƒ = new MapIndex();

      for(String â˜ƒx : â˜ƒ.getAllKeys()) {
         if (â˜ƒ.contains(â˜ƒx, 99)) {
            â˜ƒ.usedAuxIds.put(â˜ƒx, â˜ƒ.getInt(â˜ƒx));
         }
      }

      return â˜ƒ;
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      for(Entry<String> â˜ƒ : this.usedAuxIds.object2IntEntrySet()) {
         â˜ƒ.putInt((String)â˜ƒ.getKey(), â˜ƒ.getIntValue());
      }

      return â˜ƒ;
   }

   public int getFreeAuxValueForMap() {
      int â˜ƒ = this.usedAuxIds.getInt("map") + 1;
      this.usedAuxIds.put("map", â˜ƒ);
      this.setDirty();
      return â˜ƒ;
   }
}
