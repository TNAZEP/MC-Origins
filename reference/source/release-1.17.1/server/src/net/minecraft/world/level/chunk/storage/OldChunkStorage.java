package net.minecraft.world.level.chunk.storage;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.OldDataLayer;

public class OldChunkStorage {
   private static final int DATALAYER_BITS = 7;
   private static final LevelHeightAccessor OLD_LEVEL_HEIGHT = new LevelHeightAccessor() {
      @Override
      public int getMinBuildHeight() {
         return 0;
      }

      @Override
      public int getHeight() {
         return 128;
      }
   };

   public static OldChunkStorage.OldLevelChunk load(CompoundTag var0) {
      int â˜ƒ = â˜ƒ.getInt("xPos");
      int â˜ƒx = â˜ƒ.getInt("zPos");
      OldChunkStorage.OldLevelChunk â˜ƒxx = new OldChunkStorage.OldLevelChunk(â˜ƒ, â˜ƒx);
      â˜ƒxx.blocks = â˜ƒ.getByteArray("Blocks");
      â˜ƒxx.data = new OldDataLayer(â˜ƒ.getByteArray("Data"), 7);
      â˜ƒxx.skyLight = new OldDataLayer(â˜ƒ.getByteArray("SkyLight"), 7);
      â˜ƒxx.blockLight = new OldDataLayer(â˜ƒ.getByteArray("BlockLight"), 7);
      â˜ƒxx.heightmap = â˜ƒ.getByteArray("HeightMap");
      â˜ƒxx.terrainPopulated = â˜ƒ.getBoolean("TerrainPopulated");
      â˜ƒxx.entities = â˜ƒ.getList("Entities", 10);
      â˜ƒxx.blockEntities = â˜ƒ.getList("TileEntities", 10);
      â˜ƒxx.blockTicks = â˜ƒ.getList("TileTicks", 10);

      try {
         â˜ƒxx.lastUpdated = â˜ƒ.getLong("LastUpdate");
      } catch (ClassCastException var5) {
         â˜ƒxx.lastUpdated = (long)â˜ƒ.getInt("LastUpdate");
      }

      return â˜ƒxx;
   }

   public static void convertToAnvilFormat(RegistryAccess.RegistryHolder var0, OldChunkStorage.OldLevelChunk var1, CompoundTag var2, BiomeSource var3) {
      â˜ƒ.putInt("xPos", â˜ƒ.x);
      â˜ƒ.putInt("zPos", â˜ƒ.z);
      â˜ƒ.putLong("LastUpdate", â˜ƒ.lastUpdated);
      int[] â˜ƒ = new int[â˜ƒ.heightmap.length];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.heightmap.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx] = â˜ƒ.heightmap[â˜ƒx];
      }

      â˜ƒ.putIntArray("HeightMap", â˜ƒ);
      â˜ƒ.putBoolean("TerrainPopulated", â˜ƒ.terrainPopulated);
      ListTag â˜ƒx = new ListTag();

      for(int â˜ƒxx = 0; â˜ƒxx < 8; ++â˜ƒxx) {
         boolean â˜ƒxxx = true;

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16 && â˜ƒxxx; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16 && â˜ƒxxx; ++â˜ƒxxxxx) {
               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
                  int â˜ƒxxxxxxx = â˜ƒxxxx << 11 | â˜ƒxxxxxx << 7 | â˜ƒxxxxx + (â˜ƒxx << 4);
                  int â˜ƒxxxxxxxx = â˜ƒ.blocks[â˜ƒxxxxxxx];
                  if (â˜ƒxxxxxxxx != 0) {
                     â˜ƒxxx = false;
                     break;
                  }
               }
            }
         }

         if (!â˜ƒxxx) {
            byte[] â˜ƒxxxx = new byte[4096];
            DataLayer â˜ƒxxxxx = new DataLayer();
            DataLayer â˜ƒxxxxxx = new DataLayer();
            DataLayer â˜ƒxxxxxxx = new DataLayer();

            for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 16; ++â˜ƒxxxxxxxx) {
               for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 16; ++â˜ƒxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < 16; ++â˜ƒxxxxxxxxxx) {
                     int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx << 11 | â˜ƒxxxxxxxxxx << 7 | â˜ƒxxxxxxxxx + (â˜ƒxx << 4);
                     int â˜ƒxxxxxxxxxxxx = â˜ƒ.blocks[â˜ƒxxxxxxxxxxx];
                     â˜ƒxxxx[â˜ƒxxxxxxxxx << 8 | â˜ƒxxxxxxxxxx << 4 | â˜ƒxxxxxxxx] = (byte)(â˜ƒxxxxxxxxxxxx & 0xFF);
                     â˜ƒxxxxx.set(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒ.data.get(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx + (â˜ƒxx << 4), â˜ƒxxxxxxxxxx));
                     â˜ƒxxxxxx.set(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒ.skyLight.get(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx + (â˜ƒxx << 4), â˜ƒxxxxxxxxxx));
                     â˜ƒxxxxxxx.set(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒ.blockLight.get(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx + (â˜ƒxx << 4), â˜ƒxxxxxxxxxx));
                  }
               }
            }

            CompoundTag â˜ƒxxxxxxxx = new CompoundTag();
            â˜ƒxxxxxxxx.putByte("Y", (byte)(â˜ƒxx & 0xFF));
            â˜ƒxxxxxxxx.putByteArray("Blocks", â˜ƒxxxx);
            â˜ƒxxxxxxxx.putByteArray("Data", â˜ƒxxxxx.getData());
            â˜ƒxxxxxxxx.putByteArray("SkyLight", â˜ƒxxxxxx.getData());
            â˜ƒxxxxxxxx.putByteArray("BlockLight", â˜ƒxxxxxxx.getData());
            â˜ƒx.add(â˜ƒxxxxxxxx);
         }
      }

      â˜ƒ.put("Sections", â˜ƒx);
      â˜ƒ.putIntArray(
         "Biomes", new ChunkBiomeContainer(â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY), OLD_LEVEL_HEIGHT, new ChunkPos(â˜ƒ.x, â˜ƒ.z), â˜ƒ).writeBiomes()
      );
      â˜ƒ.put("Entities", â˜ƒ.entities);
      â˜ƒ.put("TileEntities", â˜ƒ.blockEntities);
      if (â˜ƒ.blockTicks != null) {
         â˜ƒ.put("TileTicks", â˜ƒ.blockTicks);
      }

      â˜ƒ.putBoolean("convertedFromAlphaFormat", true);
   }

   public static class OldLevelChunk {
      public long lastUpdated;
      public boolean terrainPopulated;
      public byte[] heightmap;
      public OldDataLayer blockLight;
      public OldDataLayer skyLight;
      public OldDataLayer data;
      public byte[] blocks;
      public ListTag entities;
      public ListTag blockEntities;
      public ListTag blockTicks;
      public final int x;
      public final int z;

      public OldLevelChunk(int var1, int var2) {
         this.x = â˜ƒ;
         this.z = â˜ƒ;
      }
   }
}
