package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ChunkTickList;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.ProtoTickList;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkSerializer {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final String TAG_UPGRADE_DATA = "UpgradeData";

   public static ProtoChunk read(ServerLevel var0, StructureManager var1, PoiManager var2, ChunkPos var3, CompoundTag var4) {
      ChunkGenerator â˜ƒ = â˜ƒ.getChunkSource().getGenerator();
      BiomeSource â˜ƒx = â˜ƒ.getBiomeSource();
      CompoundTag â˜ƒxx = â˜ƒ.getCompound("Level");
      ChunkPos â˜ƒxxx = new ChunkPos(â˜ƒxx.getInt("xPos"), â˜ƒxx.getInt("zPos"));
      if (!Objects.equals(â˜ƒ, â˜ƒxxx)) {
         LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", â˜ƒ, â˜ƒ, â˜ƒxxx);
      }

      ChunkBiomeContainer â˜ƒ = new ChunkBiomeContainer(
         â˜ƒ.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx.contains("Biomes", 11) ? â˜ƒxx.getIntArray("Biomes") : null
      );
      UpgradeData â˜ƒx = â˜ƒxx.contains("UpgradeData", 10) ? new UpgradeData(â˜ƒxx.getCompound("UpgradeData"), â˜ƒ) : UpgradeData.EMPTY;
      ProtoTickList<Block> â˜ƒxx = new ProtoTickList<>(var0x -> var0x == null || var0x.defaultBlockState().isAir(), â˜ƒ, â˜ƒxx.getList("ToBeTicked", 9), â˜ƒ);
      ProtoTickList<Fluid> â˜ƒxxx = new ProtoTickList<>(var0x -> var0x == null || var0x == Fluids.EMPTY, â˜ƒ, â˜ƒxx.getList("LiquidsToBeTicked", 9), â˜ƒ);
      boolean â˜ƒxxxx = â˜ƒxx.getBoolean("isLightOn");
      ListTag â˜ƒxxxxx = â˜ƒxx.getList("Sections", 10);
      int â˜ƒxxxxxx = â˜ƒ.getSectionsCount();
      LevelChunkSection[] â˜ƒxxxxxxx = new LevelChunkSection[â˜ƒxxxxxx];
      boolean â˜ƒxxxxxxxx = â˜ƒ.dimensionType().hasSkyLight();
      ChunkSource â˜ƒxxxxxxxxx = â˜ƒ.getChunkSource();
      LevelLightEngine â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.getLightEngine();
      if (â˜ƒxxxx) {
         â˜ƒxxxxxxxxxx.retainData(â˜ƒ, true);
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒxxxxx.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒxxxxx.getCompound(â˜ƒ);
         int â˜ƒxx = â˜ƒx.getByte("Y");
         if (â˜ƒx.contains("Palette", 9) && â˜ƒx.contains("BlockStates", 12)) {
            LevelChunkSection â˜ƒxxx = new LevelChunkSection(â˜ƒxx);
            â˜ƒxxx.getStates().read(â˜ƒx.getList("Palette", 10), â˜ƒx.getLongArray("BlockStates"));
            â˜ƒxxx.recalcBlockCounts();
            if (!â˜ƒxxx.isEmpty()) {
               â˜ƒxxxxxxx[â˜ƒ.getSectionIndexFromSectionY(â˜ƒxx)] = â˜ƒxxx;
            }

            â˜ƒ.checkConsistencyWithBlocks(â˜ƒ, â˜ƒxxx);
         }

         if (â˜ƒxxxx) {
            if (â˜ƒx.contains("BlockLight", 7)) {
               â˜ƒxxxxxxxxxx.queueSectionData(LightLayer.BLOCK, SectionPos.of(â˜ƒ, â˜ƒxx), new DataLayer(â˜ƒx.getByteArray("BlockLight")), true);
            }

            if (â˜ƒxxxxxxxx && â˜ƒx.contains("SkyLight", 7)) {
               â˜ƒxxxxxxxxxx.queueSectionData(LightLayer.SKY, SectionPos.of(â˜ƒ, â˜ƒxx), new DataLayer(â˜ƒx.getByteArray("SkyLight")), true);
            }
         }
      }

      long â˜ƒx = â˜ƒxx.getLong("InhabitedTime");
      ChunkStatus.ChunkType â˜ƒxx = getChunkTypeFromTag(â˜ƒ);
      ChunkAccess â˜ƒ;
      if (â˜ƒxx == ChunkStatus.ChunkType.LEVELCHUNK) {
         TickList<Block> â˜ƒxxx;
         if (â˜ƒxx.contains("TileTicks", 9)) {
            â˜ƒxxx = ChunkTickList.create(â˜ƒxx.getList("TileTicks", 10), Registry.BLOCK::getKey, Registry.BLOCK::get);
         } else {
            â˜ƒxxx = â˜ƒxx;
         }

         TickList<Fluid> â˜ƒxxx;
         if (â˜ƒxx.contains("LiquidTicks", 9)) {
            â˜ƒxxx = ChunkTickList.create(â˜ƒxx.getList("LiquidTicks", 10), Registry.FLUID::getKey, Registry.FLUID::get);
         } else {
            â˜ƒxxx = â˜ƒxxx;
         }

         â˜ƒ = new LevelChunk(â˜ƒ.getLevel(), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxx, â˜ƒx, â˜ƒxxxxxxx, var2x -> postLoadChunk(â˜ƒ, â˜ƒ, var2x));
      } else {
         ProtoChunk â˜ƒ = new ProtoChunk(â˜ƒ, â˜ƒx, â˜ƒxxxxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ);
         â˜ƒ.setBiomes(â˜ƒ);
         â˜ƒ = â˜ƒ;
         â˜ƒ.setInhabitedTime(â˜ƒx);
         â˜ƒ.setStatus(ChunkStatus.byName(â˜ƒxx.getString("Status")));
         if (â˜ƒ.getStatus().isOrAfter(ChunkStatus.FEATURES)) {
            â˜ƒ.setLightEngine(â˜ƒxxxxxxxxxx);
         }

         if (!â˜ƒxxxx && â˜ƒ.getStatus().isOrAfter(ChunkStatus.LIGHT)) {
            for(BlockPos â˜ƒ : BlockPos.betweenClosed(
               â˜ƒ.getMinBlockX(), â˜ƒ.getMinBuildHeight(), â˜ƒ.getMinBlockZ(), â˜ƒ.getMaxBlockX(), â˜ƒ.getMaxBuildHeight() - 1, â˜ƒ.getMaxBlockZ()
            )) {
               if (â˜ƒ.getBlockState(â˜ƒ).getLightEmission() != 0) {
                  â˜ƒ.addLight(â˜ƒ);
               }
            }
         }
      }

      â˜ƒ.setLightCorrect(â˜ƒxxxx);
      CompoundTag â˜ƒ = â˜ƒxx.getCompound("Heightmaps");
      EnumSet<Heightmap.Types> â˜ƒx = EnumSet.noneOf(Heightmap.Types.class);

      for(Heightmap.Types â˜ƒxx : â˜ƒ.getStatus().heightmapsAfter()) {
         String â˜ƒxxx = â˜ƒxx.getSerializationKey();
         if (â˜ƒ.contains(â˜ƒxxx, 12)) {
            â˜ƒ.setHeightmap(â˜ƒxx, â˜ƒ.getLongArray(â˜ƒxxx));
         } else {
            â˜ƒx.add(â˜ƒxx);
         }
      }

      Heightmap.primeHeightmaps(â˜ƒ, â˜ƒx);
      CompoundTag â˜ƒxx = â˜ƒxx.getCompound("Structures");
      â˜ƒ.setAllStarts(unpackStructureStart(â˜ƒ, â˜ƒxx, â˜ƒ.getSeed()));
      â˜ƒ.setAllReferences(unpackStructureReferences(â˜ƒ, â˜ƒxx));
      if (â˜ƒxx.getBoolean("shouldSave")) {
         â˜ƒ.setUnsaved(true);
      }

      ListTag â˜ƒxx = â˜ƒxx.getList("PostProcessing", 9);

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
         ListTag â˜ƒxxxx = â˜ƒxx.getList(â˜ƒxxx);

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx.size(); ++â˜ƒxxxxx) {
            â˜ƒ.addPackedPostProcess(â˜ƒxxxx.getShort(â˜ƒxxxxx), â˜ƒxxx);
         }
      }

      if (â˜ƒxx == ChunkStatus.ChunkType.LEVELCHUNK) {
         return new ImposterProtoChunk((LevelChunk)â˜ƒ);
      } else {
         ProtoChunk â˜ƒxxx = (ProtoChunk)â˜ƒ;
         ListTag â˜ƒxxxx = â˜ƒxx.getList("Entities", 10);

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx.size(); ++â˜ƒxxxxx) {
            â˜ƒxxx.addEntity(â˜ƒxxxx.getCompound(â˜ƒxxxxx));
         }

         ListTag â˜ƒxxxxx = â˜ƒxx.getList("TileEntities", 10);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx.size(); ++â˜ƒxxxxxx) {
            CompoundTag â˜ƒxxxxxxx = â˜ƒxxxxx.getCompound(â˜ƒxxxxxx);
            â˜ƒ.setBlockEntityNbt(â˜ƒxxxxxxx);
         }

         ListTag â˜ƒxxxxxx = â˜ƒxx.getList("Lights", 9);

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxxx.size(); ++â˜ƒxxxxxxx) {
            ListTag â˜ƒxxxxxxxx = â˜ƒxxxxxx.getList(â˜ƒxxxxxxx);

            for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxxxxxxx.size(); ++â˜ƒxxxxxxxxx) {
               â˜ƒxxx.addLight(â˜ƒxxxxxxxx.getShort(â˜ƒxxxxxxxxx), â˜ƒxxxxxxx);
            }
         }

         CompoundTag â˜ƒxxxxxxx = â˜ƒxx.getCompound("CarvingMasks");

         for(String â˜ƒxxxxxxxx : â˜ƒxxxxxxx.getAllKeys()) {
            GenerationStep.Carving â˜ƒxxxxxxxxx = GenerationStep.Carving.valueOf(â˜ƒxxxxxxxx);
            â˜ƒxxx.setCarvingMask(â˜ƒxxxxxxxxx, BitSet.valueOf(â˜ƒxxxxxxx.getByteArray(â˜ƒxxxxxxxx)));
         }

         return â˜ƒxxx;
      }
   }

   public static CompoundTag write(ServerLevel var0, ChunkAccess var1) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      CompoundTag â˜ƒx = new CompoundTag();
      CompoundTag â˜ƒxx = new CompoundTag();
      â˜ƒx.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
      â˜ƒx.put("Level", â˜ƒxx);
      â˜ƒxx.putInt("xPos", â˜ƒ.x);
      â˜ƒxx.putInt("zPos", â˜ƒ.z);
      â˜ƒxx.putLong("LastUpdate", â˜ƒ.getGameTime());
      â˜ƒxx.putLong("InhabitedTime", â˜ƒ.getInhabitedTime());
      â˜ƒxx.putString("Status", â˜ƒ.getStatus().getName());
      UpgradeData â˜ƒxxx = â˜ƒ.getUpgradeData();
      if (!â˜ƒxxx.isEmpty()) {
         â˜ƒxx.put("UpgradeData", â˜ƒxxx.write());
      }

      LevelChunkSection[] â˜ƒ = â˜ƒ.getSections();
      ListTag â˜ƒx = new ListTag();
      LevelLightEngine â˜ƒxx = â˜ƒ.getChunkSource().getLightEngine();
      boolean â˜ƒxxx = â˜ƒ.isLightCorrect();

      for(int â˜ƒxxxx = â˜ƒxx.getMinLightSection(); â˜ƒxxxx < â˜ƒxx.getMaxLightSection(); ++â˜ƒxxxx) {
         int â˜ƒxxxxx = â˜ƒxxxx;
         LevelChunkSection â˜ƒxxxxxx = (LevelChunkSection)Arrays.stream(â˜ƒ)
            .filter(var1x -> var1x != null && SectionPos.blockToSectionCoord(var1x.bottomBlockY()) == â˜ƒ)
            .findFirst()
            .orElse(LevelChunk.EMPTY_SECTION);
         DataLayer â˜ƒxxxxxxx = â˜ƒxx.getLayerListener(LightLayer.BLOCK).getDataLayerData(SectionPos.of(â˜ƒ, â˜ƒxxxxx));
         DataLayer â˜ƒxxxxxxxx = â˜ƒxx.getLayerListener(LightLayer.SKY).getDataLayerData(SectionPos.of(â˜ƒ, â˜ƒxxxxx));
         if (â˜ƒxxxxxx != LevelChunk.EMPTY_SECTION || â˜ƒxxxxxxx != null || â˜ƒxxxxxxxx != null) {
            CompoundTag â˜ƒxxxxxxxxx = new CompoundTag();
            â˜ƒxxxxxxxxx.putByte("Y", (byte)(â˜ƒxxxxx & 0xFF));
            if (â˜ƒxxxxxx != LevelChunk.EMPTY_SECTION) {
               â˜ƒxxxxxx.getStates().write(â˜ƒxxxxxxxxx, "Palette", "BlockStates");
            }

            if (â˜ƒxxxxxxx != null && !â˜ƒxxxxxxx.isEmpty()) {
               â˜ƒxxxxxxxxx.putByteArray("BlockLight", â˜ƒxxxxxxx.getData());
            }

            if (â˜ƒxxxxxxxx != null && !â˜ƒxxxxxxxx.isEmpty()) {
               â˜ƒxxxxxxxxx.putByteArray("SkyLight", â˜ƒxxxxxxxx.getData());
            }

            â˜ƒx.add(â˜ƒxxxxxxxxx);
         }
      }

      â˜ƒxx.put("Sections", â˜ƒx);
      if (â˜ƒxxx) {
         â˜ƒxx.putBoolean("isLightOn", true);
      }

      ChunkBiomeContainer â˜ƒxxxx = â˜ƒ.getBiomes();
      if (â˜ƒxxxx != null) {
         â˜ƒxx.putIntArray("Biomes", â˜ƒxxxx.writeBiomes());
      }

      ListTag â˜ƒxxxx = new ListTag();

      for(BlockPos â˜ƒxxxxx : â˜ƒ.getBlockEntitiesPos()) {
         CompoundTag â˜ƒxxxxxx = â˜ƒ.getBlockEntityNbtForSaving(â˜ƒxxxxx);
         if (â˜ƒxxxxxx != null) {
            â˜ƒxxxx.add(â˜ƒxxxxxx);
         }
      }

      â˜ƒxx.put("TileEntities", â˜ƒxxxx);
      if (â˜ƒ.getStatus().getChunkType() == ChunkStatus.ChunkType.PROTOCHUNK) {
         ProtoChunk â˜ƒxxxxx = (ProtoChunk)â˜ƒ;
         ListTag â˜ƒxxxxxx = new ListTag();
         â˜ƒxxxxxx.addAll(â˜ƒxxxxx.getEntities());
         â˜ƒxx.put("Entities", â˜ƒxxxxxx);
         â˜ƒxx.put("Lights", packOffsets(â˜ƒxxxxx.getPackedLights()));
         CompoundTag â˜ƒxxxxxxx = new CompoundTag();

         for(GenerationStep.Carving â˜ƒxxxxxxxx : GenerationStep.Carving.values()) {
            BitSet â˜ƒxxxxxxxxx = â˜ƒxxxxx.getCarvingMask(â˜ƒxxxxxxxx);
            if (â˜ƒxxxxxxxxx != null) {
               â˜ƒxxxxxxx.putByteArray(â˜ƒxxxxxxxx.toString(), â˜ƒxxxxxxxxx.toByteArray());
            }
         }

         â˜ƒxx.put("CarvingMasks", â˜ƒxxxxxxx);
      }

      TickList<Block> â˜ƒxxxxx = â˜ƒ.getBlockTicks();
      if (â˜ƒxxxxx instanceof ProtoTickList) {
         â˜ƒxx.put("ToBeTicked", ((ProtoTickList)â˜ƒxxxxx).save());
      } else if (â˜ƒxxxxx instanceof ChunkTickList) {
         â˜ƒxx.put("TileTicks", ((ChunkTickList)â˜ƒxxxxx).save());
      } else {
         â˜ƒxx.put("TileTicks", â˜ƒ.getBlockTicks().save(â˜ƒ));
      }

      TickList<Fluid> â˜ƒxxxxx = â˜ƒ.getLiquidTicks();
      if (â˜ƒxxxxx instanceof ProtoTickList) {
         â˜ƒxx.put("LiquidsToBeTicked", ((ProtoTickList)â˜ƒxxxxx).save());
      } else if (â˜ƒxxxxx instanceof ChunkTickList) {
         â˜ƒxx.put("LiquidTicks", ((ChunkTickList)â˜ƒxxxxx).save());
      } else {
         â˜ƒxx.put("LiquidTicks", â˜ƒ.getLiquidTicks().save(â˜ƒ));
      }

      â˜ƒxx.put("PostProcessing", packOffsets(â˜ƒ.getPostProcessing()));
      CompoundTag â˜ƒxxxxx = new CompoundTag();

      for(Entry<Heightmap.Types, Heightmap> â˜ƒxxxxxx : â˜ƒ.getHeightmaps()) {
         if (â˜ƒ.getStatus().heightmapsAfter().contains(â˜ƒxxxxxx.getKey())) {
            â˜ƒxxxxx.put(((Heightmap.Types)â˜ƒxxxxxx.getKey()).getSerializationKey(), new LongArrayTag(((Heightmap)â˜ƒxxxxxx.getValue()).getRawData()));
         }
      }

      â˜ƒxx.put("Heightmaps", â˜ƒxxxxx);
      â˜ƒxx.put("Structures", packStructureData(â˜ƒ, â˜ƒ, â˜ƒ.getAllStarts(), â˜ƒ.getAllReferences()));
      return â˜ƒx;
   }

   public static ChunkStatus.ChunkType getChunkTypeFromTag(@Nullable CompoundTag var0) {
      if (â˜ƒ != null) {
         ChunkStatus â˜ƒ = ChunkStatus.byName(â˜ƒ.getCompound("Level").getString("Status"));
         if (â˜ƒ != null) {
            return â˜ƒ.getChunkType();
         }
      }

      return ChunkStatus.ChunkType.PROTOCHUNK;
   }

   private static void postLoadChunk(ServerLevel var0, CompoundTag var1, LevelChunk var2) {
      if (â˜ƒ.contains("Entities", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("Entities", 10);
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.addLegacyChunkEntities(EntityType.loadEntitiesRecursive(â˜ƒ, â˜ƒ));
         }
      }

      ListTag â˜ƒ = â˜ƒ.getList("TileEntities", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
         boolean â˜ƒxxx = â˜ƒxx.getBoolean("keepPacked");
         if (â˜ƒxxx) {
            â˜ƒ.setBlockEntityNbt(â˜ƒxx);
         } else {
            BlockPos â˜ƒxx = new BlockPos(â˜ƒxx.getInt("x"), â˜ƒxx.getInt("y"), â˜ƒxx.getInt("z"));
            BlockEntity â˜ƒxxx = BlockEntity.loadStatic(â˜ƒxx, â˜ƒ.getBlockState(â˜ƒxx), â˜ƒxx);
            if (â˜ƒxxx != null) {
               â˜ƒ.setBlockEntity(â˜ƒxxx);
            }
         }
      }
   }

   private static CompoundTag packStructureData(
      ServerLevel var0, ChunkPos var1, Map<StructureFeature<?>, StructureStart<?>> var2, Map<StructureFeature<?>, LongSet> var3
   ) {
      CompoundTag â˜ƒ = new CompoundTag();
      CompoundTag â˜ƒx = new CompoundTag();

      for(Entry<StructureFeature<?>, StructureStart<?>> â˜ƒxx : â˜ƒ.entrySet()) {
         â˜ƒx.put(((StructureFeature)â˜ƒxx.getKey()).getFeatureName(), ((StructureStart)â˜ƒxx.getValue()).createTag(â˜ƒ, â˜ƒ));
      }

      â˜ƒ.put("Starts", â˜ƒx);
      CompoundTag â˜ƒxx = new CompoundTag();

      for(Entry<StructureFeature<?>, LongSet> â˜ƒxxx : â˜ƒ.entrySet()) {
         â˜ƒxx.put(((StructureFeature)â˜ƒxxx.getKey()).getFeatureName(), new LongArrayTag((LongSet)â˜ƒxxx.getValue()));
      }

      â˜ƒ.put("References", â˜ƒxx);
      return â˜ƒ;
   }

   private static Map<StructureFeature<?>, StructureStart<?>> unpackStructureStart(ServerLevel var0, CompoundTag var1, long var2) {
      Map<StructureFeature<?>, StructureStart<?>> â˜ƒ = Maps.<StructureFeature<?>, StructureStart<?>>newHashMap();
      CompoundTag â˜ƒx = â˜ƒ.getCompound("Starts");

      for(String â˜ƒxx : â˜ƒx.getAllKeys()) {
         String â˜ƒxxx = â˜ƒxx.toLowerCase(Locale.ROOT);
         StructureFeature<?> â˜ƒxxxx = (StructureFeature)StructureFeature.STRUCTURES_REGISTRY.get(â˜ƒxxx);
         if (â˜ƒxxxx == null) {
            LOGGER.error("Unknown structure start: {}", â˜ƒxxx);
         } else {
            StructureStart<?> â˜ƒxxx = StructureFeature.loadStaticStart(â˜ƒ, â˜ƒx.getCompound(â˜ƒxx), â˜ƒ);
            if (â˜ƒxxx != null) {
               â˜ƒ.put(â˜ƒxxxx, â˜ƒxxx);
            }
         }
      }

      return â˜ƒ;
   }

   private static Map<StructureFeature<?>, LongSet> unpackStructureReferences(ChunkPos var0, CompoundTag var1) {
      Map<StructureFeature<?>, LongSet> â˜ƒ = Maps.<StructureFeature<?>, LongSet>newHashMap();
      CompoundTag â˜ƒx = â˜ƒ.getCompound("References");

      for(String â˜ƒxx : â˜ƒx.getAllKeys()) {
         String â˜ƒxxx = â˜ƒxx.toLowerCase(Locale.ROOT);
         StructureFeature<?> â˜ƒxxxx = (StructureFeature)StructureFeature.STRUCTURES_REGISTRY.get(â˜ƒxxx);
         if (â˜ƒxxxx == null) {
            LOGGER.warn("Found reference to unknown structure '{}' in chunk {}, discarding", â˜ƒxxx, â˜ƒ);
         } else {
            â˜ƒ.put(â˜ƒxxxx, new LongOpenHashSet(Arrays.stream(â˜ƒx.getLongArray(â˜ƒxx)).filter(var2x -> {
               ChunkPos â˜ƒ = new ChunkPos(var2x);
               if (â˜ƒ.getChessboardDistance(â˜ƒ) > 8) {
                  LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", â˜ƒ, â˜ƒ, â˜ƒ);
                  return false;
               } else {
                  return true;
               }
            }).toArray()));
         }
      }

      return â˜ƒ;
   }

   public static ListTag packOffsets(ShortList[] var0) {
      ListTag â˜ƒ = new ListTag();

      for(ShortList â˜ƒx : â˜ƒ) {
         ListTag â˜ƒxx = new ListTag();
         if (â˜ƒx != null) {
            for(Short â˜ƒxxx : â˜ƒx) {
               â˜ƒxx.add(ShortTag.valueOf(â˜ƒxxx));
            }
         }

         â˜ƒ.add(â˜ƒxx);
      }

      return â˜ƒ;
   }
}
