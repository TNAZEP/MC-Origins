package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class LegacyStructureDataHandler {
   private static final Map<String, String> CURRENT_TO_LEGACY_MAP = Util.make(Maps.newHashMap(), var0 -> {
      var0.put("Village", "Village");
      var0.put("Mineshaft", "Mineshaft");
      var0.put("Mansion", "Mansion");
      var0.put("Igloo", "Temple");
      var0.put("Desert_Pyramid", "Temple");
      var0.put("Jungle_Pyramid", "Temple");
      var0.put("Swamp_Hut", "Temple");
      var0.put("Stronghold", "Stronghold");
      var0.put("Monument", "Monument");
      var0.put("Fortress", "Fortress");
      var0.put("EndCity", "EndCity");
   });
   private static final Map<String, String> LEGACY_TO_CURRENT_MAP = Util.make(Maps.newHashMap(), var0 -> {
      var0.put("Iglu", "Igloo");
      var0.put("TeDP", "Desert_Pyramid");
      var0.put("TeJP", "Jungle_Pyramid");
      var0.put("TeSH", "Swamp_Hut");
   });
   private final boolean hasLegacyData;
   private final Map<String, Long2ObjectMap<CompoundTag>> dataMap = Maps.newHashMap();
   private final Map<String, StructureFeatureIndexSavedData> indexMap = Maps.newHashMap();
   private final List<String> legacyKeys;
   private final List<String> currentKeys;

   public LegacyStructureDataHandler(@Nullable DimensionDataStorage var1, List<String> var2, List<String> var3) {
      this.legacyKeys = â˜ƒ;
      this.currentKeys = â˜ƒ;
      this.populateCaches(â˜ƒ);
      boolean â˜ƒ = false;

      for(String â˜ƒx : this.currentKeys) {
         â˜ƒ |= this.dataMap.get(â˜ƒx) != null;
      }

      this.hasLegacyData = â˜ƒ;
   }

   public void removeIndex(long var1) {
      for(String â˜ƒ : this.legacyKeys) {
         StructureFeatureIndexSavedData â˜ƒx = (StructureFeatureIndexSavedData)this.indexMap.get(â˜ƒ);
         if (â˜ƒx != null && â˜ƒx.hasUnhandledIndex(â˜ƒ)) {
            â˜ƒx.removeIndex(â˜ƒ);
            â˜ƒx.setDirty();
         }
      }
   }

   public CompoundTag updateFromLegacy(CompoundTag var1) {
      CompoundTag â˜ƒ = â˜ƒ.getCompound("Level");
      ChunkPos â˜ƒx = new ChunkPos(â˜ƒ.getInt("xPos"), â˜ƒ.getInt("zPos"));
      if (this.isUnhandledStructureStart(â˜ƒx.x, â˜ƒx.z)) {
         â˜ƒ = this.updateStructureStart(â˜ƒ, â˜ƒx);
      }

      CompoundTag â˜ƒ = â˜ƒ.getCompound("Structures");
      CompoundTag â˜ƒx = â˜ƒ.getCompound("References");

      for(String â˜ƒxx : this.currentKeys) {
         StructureFeature<?> â˜ƒxxx = (StructureFeature)StructureFeature.STRUCTURES_REGISTRY.get(â˜ƒxx.toLowerCase(Locale.ROOT));
         if (!â˜ƒx.contains(â˜ƒxx, 12) && â˜ƒxxx != null) {
            int â˜ƒxxxx = 8;
            LongList â˜ƒxxxxx = new LongArrayList();

            for(int â˜ƒxxxxxx = â˜ƒx.x - 8; â˜ƒxxxxxx <= â˜ƒx.x + 8; ++â˜ƒxxxxxx) {
               for(int â˜ƒxxxxxxx = â˜ƒx.z - 8; â˜ƒxxxxxxx <= â˜ƒx.z + 8; ++â˜ƒxxxxxxx) {
                  if (this.hasLegacyStart(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxx)) {
                     â˜ƒxxxxx.add(ChunkPos.asLong(â˜ƒxxxxxx, â˜ƒxxxxxxx));
                  }
               }
            }

            â˜ƒx.putLongArray(â˜ƒxx, â˜ƒxxxxx);
         }
      }

      â˜ƒ.put("References", â˜ƒx);
      â˜ƒ.put("Structures", â˜ƒ);
      â˜ƒ.put("Level", â˜ƒ);
      return â˜ƒ;
   }

   private boolean hasLegacyStart(int var1, int var2, String var3) {
      if (!this.hasLegacyData) {
         return false;
      } else {
         return this.dataMap.get(â˜ƒ) != null
            && ((StructureFeatureIndexSavedData)this.indexMap.get(CURRENT_TO_LEGACY_MAP.get(â˜ƒ))).hasStartIndex(ChunkPos.asLong(â˜ƒ, â˜ƒ));
      }
   }

   private boolean isUnhandledStructureStart(int var1, int var2) {
      if (!this.hasLegacyData) {
         return false;
      } else {
         for(String â˜ƒ : this.currentKeys) {
            if (this.dataMap.get(â˜ƒ) != null
               && ((StructureFeatureIndexSavedData)this.indexMap.get(CURRENT_TO_LEGACY_MAP.get(â˜ƒ))).hasUnhandledIndex(ChunkPos.asLong(â˜ƒ, â˜ƒ))) {
               return true;
            }
         }

         return false;
      }
   }

   private CompoundTag updateStructureStart(CompoundTag var1, ChunkPos var2) {
      CompoundTag â˜ƒ = â˜ƒ.getCompound("Level");
      CompoundTag â˜ƒx = â˜ƒ.getCompound("Structures");
      CompoundTag â˜ƒxx = â˜ƒx.getCompound("Starts");

      for(String â˜ƒxxx : this.currentKeys) {
         Long2ObjectMap<CompoundTag> â˜ƒxxxx = (Long2ObjectMap)this.dataMap.get(â˜ƒxxx);
         if (â˜ƒxxxx != null) {
            long â˜ƒxxxxx = â˜ƒ.toLong();
            if (((StructureFeatureIndexSavedData)this.indexMap.get(CURRENT_TO_LEGACY_MAP.get(â˜ƒxxx))).hasUnhandledIndex(â˜ƒxxxxx)) {
               CompoundTag â˜ƒxxxxxx = â˜ƒxxxx.get(â˜ƒxxxxx);
               if (â˜ƒxxxxxx != null) {
                  â˜ƒxx.put(â˜ƒxxx, â˜ƒxxxxxx);
               }
            }
         }
      }

      â˜ƒx.put("Starts", â˜ƒxx);
      â˜ƒ.put("Structures", â˜ƒx);
      â˜ƒ.put("Level", â˜ƒ);
      return â˜ƒ;
   }

   private void populateCaches(@Nullable DimensionDataStorage var1) {
      if (â˜ƒ != null) {
         for(String â˜ƒ : this.legacyKeys) {
            CompoundTag â˜ƒx = new CompoundTag();

            try {
               â˜ƒx = â˜ƒ.readTagFromDisk(â˜ƒ, 1493).getCompound("data").getCompound("Features");
               if (â˜ƒx.isEmpty()) {
                  continue;
               }
            } catch (IOException var13) {
            }

            for(String â˜ƒxx : â˜ƒx.getAllKeys()) {
               CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
               long â˜ƒxxxx = ChunkPos.asLong(â˜ƒxxx.getInt("ChunkX"), â˜ƒxxx.getInt("ChunkZ"));
               ListTag â˜ƒxxxxx = â˜ƒxxx.getList("Children", 10);
               if (!â˜ƒxxxxx.isEmpty()) {
                  String â˜ƒxxxxxx = â˜ƒxxxxx.getCompound(0).getString("id");
                  String â˜ƒxxxxxxx = (String)LEGACY_TO_CURRENT_MAP.get(â˜ƒxxxxxx);
                  if (â˜ƒxxxxxxx != null) {
                     â˜ƒxxx.putString("id", â˜ƒxxxxxxx);
                  }
               }

               String â˜ƒxxx = â˜ƒxxx.getString("id");
               ((Long2ObjectMap)this.dataMap.computeIfAbsent(â˜ƒxxx, var0 -> new Long2ObjectOpenHashMap())).put(â˜ƒxxxx, â˜ƒxxx);
            }

            String â˜ƒxx = â˜ƒ + "_index";
            StructureFeatureIndexSavedData â˜ƒxxx = â˜ƒ.computeIfAbsent(StructureFeatureIndexSavedData::load, StructureFeatureIndexSavedData::new, â˜ƒxx);
            if (!â˜ƒxxx.getAll().isEmpty()) {
               this.indexMap.put(â˜ƒ, â˜ƒxxx);
            } else {
               StructureFeatureIndexSavedData â˜ƒxx = new StructureFeatureIndexSavedData();
               this.indexMap.put(â˜ƒ, â˜ƒxx);

               for(String â˜ƒxxx : â˜ƒx.getAllKeys()) {
                  CompoundTag â˜ƒxxxx = â˜ƒx.getCompound(â˜ƒxxx);
                  â˜ƒxx.addIndex(ChunkPos.asLong(â˜ƒxxxx.getInt("ChunkX"), â˜ƒxxxx.getInt("ChunkZ")));
               }

               â˜ƒxx.setDirty();
            }
         }
      }
   }

   public static LegacyStructureDataHandler getLegacyStructureHandler(ResourceKey<Level> var0, @Nullable DimensionDataStorage var1) {
      if (â˜ƒ == Level.OVERWORLD) {
         return new LegacyStructureDataHandler(
            â˜ƒ,
            ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"),
            ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument")
         );
      } else if (â˜ƒ == Level.NETHER) {
         List<String> â˜ƒ = ImmutableList.of("Fortress");
         return new LegacyStructureDataHandler(â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == Level.END) {
         List<String> â˜ƒ = ImmutableList.of("EndCity");
         return new LegacyStructureDataHandler(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         throw new RuntimeException(String.format("Unknown dimension type : %s", â˜ƒ));
      }
   }
}
