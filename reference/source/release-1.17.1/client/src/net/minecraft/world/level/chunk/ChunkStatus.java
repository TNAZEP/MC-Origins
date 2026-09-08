package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ChunkStatus {
   private static final EnumSet<Heightmap.Types> PRE_FEATURES = EnumSet.of(Heightmap.Types.OCEAN_FLOOR_WG, Heightmap.Types.WORLD_SURFACE_WG);
   private static final EnumSet<Heightmap.Types> POST_FEATURES = EnumSet.of(
      Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE, Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES
   );
   private static final ChunkStatus.LoadingTask PASSTHROUGH_LOAD_TASK = (var0, var1, var2, var3, var4, var5) -> {
      if (var5 instanceof ProtoChunk && !var5.getStatus().isOrAfter(var0)) {
         ((ProtoChunk)var5).setStatus(var0);
      }

      return CompletableFuture.completedFuture(Either.left(var5));
   };
   public static final ChunkStatus EMPTY = registerSimple(
      "empty", null, -1, PRE_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (var0, var1, var2, var3, var4) -> {
      }
   );
   public static final ChunkStatus STRUCTURE_STARTS = register(
      "structure_starts", EMPTY, 0, PRE_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (var0, var1, var2, var3, var4, var5, var6, var7, var8) -> {
         if (!var8.getStatus().isOrAfter(var0)) {
            if (var2.getServer().getWorldData().worldGenSettings().generateFeatures()) {
               var3.createStructures(var2.registryAccess(), var2.structureFeatureManager(), var8, var4, var2.getSeed());
            }
   
            if (var8 instanceof ProtoChunk) {
               ((ProtoChunk)var8).setStatus(var0);
            }
         }
   
         return CompletableFuture.completedFuture(Either.left(var8));
      }
   );
   public static final ChunkStatus STRUCTURE_REFERENCES = registerSimple(
      "structure_references", STRUCTURE_STARTS, 8, PRE_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (var0, var1, var2, var3, var4) -> {
         WorldGenRegion â˜ƒ = new WorldGenRegion(var1, var3, var0, -1);
         var2.createReferences(â˜ƒ, var1.structureFeatureManager().forWorldGenRegion(â˜ƒ), var4);
      }
   );
   public static final ChunkStatus BIOMES = registerSimple(
      "biomes",
      STRUCTURE_REFERENCES,
      0,
      PRE_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4) -> var2.createBiomes(var1.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY), var4)
   );
   public static final ChunkStatus NOISE = register(
      "noise", BIOMES, 8, PRE_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (var0, var1, var2, var3, var4, var5, var6, var7, var8) -> {
         if (!var8.getStatus().isOrAfter(var0)) {
            WorldGenRegion â˜ƒ = new WorldGenRegion(var2, var7, var0, 0);
            return var3.fillFromNoise(var1, var2.structureFeatureManager().forWorldGenRegion(â˜ƒ), var8).thenApply(var1x -> {
               if (var1x instanceof ProtoChunk) {
                  ((ProtoChunk)var1x).setStatus(var0);
               }
   
               return Either.left(var1x);
            });
         } else {
            return CompletableFuture.completedFuture(Either.left(var8));
         }
      }
   );
   public static final ChunkStatus SURFACE = registerSimple(
      "surface",
      NOISE,
      0,
      PRE_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4) -> var2.buildSurfaceAndBedrock(new WorldGenRegion(var1, var3, var0, 0), var4)
   );
   public static final ChunkStatus CARVERS = registerSimple(
      "carvers",
      SURFACE,
      0,
      PRE_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4) -> var2.applyCarvers(var1.getSeed(), var1.getBiomeManager(), var4, GenerationStep.Carving.AIR)
   );
   public static final ChunkStatus LIQUID_CARVERS = registerSimple(
      "liquid_carvers",
      CARVERS,
      0,
      POST_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4) -> var2.applyCarvers(var1.getSeed(), var1.getBiomeManager(), var4, GenerationStep.Carving.LIQUID)
   );
   public static final ChunkStatus FEATURES = register(
      "features",
      LIQUID_CARVERS,
      8,
      POST_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4, var5, var6, var7, var8) -> {
         ProtoChunk â˜ƒ = (ProtoChunk)var8;
         â˜ƒ.setLightEngine(var5);
         if (!var8.getStatus().isOrAfter(var0)) {
            Heightmap.primeHeightmaps(
               var8,
               EnumSet.of(
                  Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE
               )
            );
            WorldGenRegion â˜ƒx = new WorldGenRegion(var2, var7, var0, 1);
            var3.applyBiomeDecoration(â˜ƒx, var2.structureFeatureManager().forWorldGenRegion(â˜ƒx));
            â˜ƒ.setStatus(var0);
         }
   
         return CompletableFuture.completedFuture(Either.left(var8));
      }
   );
   public static final ChunkStatus LIGHT = register(
      "light",
      FEATURES,
      1,
      POST_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4, var5, var6, var7, var8) -> lightChunk(var0, var5, var8),
      (var0, var1, var2, var3, var4, var5) -> lightChunk(var0, var3, var5)
   );
   public static final ChunkStatus SPAWN = registerSimple(
      "spawn",
      LIGHT,
      0,
      POST_FEATURES,
      ChunkStatus.ChunkType.PROTOCHUNK,
      (var0, var1, var2, var3, var4) -> var2.spawnOriginalMobs(new WorldGenRegion(var1, var3, var0, -1))
   );
   public static final ChunkStatus HEIGHTMAPS = registerSimple(
      "heightmaps", SPAWN, 0, POST_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (var0, var1, var2, var3, var4) -> {
      }
   );
   public static final ChunkStatus FULL = register(
      "full",
      HEIGHTMAPS,
      0,
      POST_FEATURES,
      ChunkStatus.ChunkType.LEVELCHUNK,
      (var0, var1, var2, var3, var4, var5, var6, var7, var8) -> (CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>)var6.apply(var8),
      (var0, var1, var2, var3, var4, var5) -> (CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>)var4.apply(var5)
   );
   private static final List<ChunkStatus> STATUS_BY_RANGE = ImmutableList.of(
      FULL,
      FEATURES,
      LIQUID_CARVERS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS,
      STRUCTURE_STARTS
   );
   private static final IntList RANGE_BY_STATUS = Util.make(new IntArrayList(getStatusList().size()), var0 -> {
      int â˜ƒ = 0;

      for(int â˜ƒx = getStatusList().size() - 1; â˜ƒx >= 0; --â˜ƒx) {
         while(â˜ƒ + 1 < STATUS_BY_RANGE.size() && â˜ƒx <= ((ChunkStatus)STATUS_BY_RANGE.get(â˜ƒ + 1)).getIndex()) {
            ++â˜ƒ;
         }

         var0.add(0, â˜ƒ);
      }
   });
   private final String name;
   private final int index;
   private final ChunkStatus parent;
   private final ChunkStatus.GenerationTask generationTask;
   private final ChunkStatus.LoadingTask loadingTask;
   private final int range;
   private final ChunkStatus.ChunkType chunkType;
   private final EnumSet<Heightmap.Types> heightmapsAfter;

   private static CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> lightChunk(
      ChunkStatus var0, ThreadedLevelLightEngine var1, ChunkAccess var2
   ) {
      boolean â˜ƒ = isLighted(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.getStatus().isOrAfter(â˜ƒ)) {
         ((ProtoChunk)â˜ƒ).setStatus(â˜ƒ);
      }

      return â˜ƒ.lightChunk(â˜ƒ, â˜ƒ).thenApply(Either::left);
   }

   private static ChunkStatus registerSimple(
      String var0, @Nullable ChunkStatus var1, int var2, EnumSet<Heightmap.Types> var3, ChunkStatus.ChunkType var4, ChunkStatus.SimpleGenerationTask var5
   ) {
      return register(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static ChunkStatus register(
      String var0, @Nullable ChunkStatus var1, int var2, EnumSet<Heightmap.Types> var3, ChunkStatus.ChunkType var4, ChunkStatus.GenerationTask var5
   ) {
      return register(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, PASSTHROUGH_LOAD_TASK);
   }

   private static ChunkStatus register(
      String var0,
      @Nullable ChunkStatus var1,
      int var2,
      EnumSet<Heightmap.Types> var3,
      ChunkStatus.ChunkType var4,
      ChunkStatus.GenerationTask var5,
      ChunkStatus.LoadingTask var6
   ) {
      return Registry.register(Registry.CHUNK_STATUS, â˜ƒ, new ChunkStatus(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static List<ChunkStatus> getStatusList() {
      List<ChunkStatus> â˜ƒ = Lists.<ChunkStatus>newArrayList();

      ChunkStatus â˜ƒ;
      for(â˜ƒ = FULL; â˜ƒ.getParent() != â˜ƒ; â˜ƒ = â˜ƒ.getParent()) {
         â˜ƒ.add(â˜ƒ);
      }

      â˜ƒ.add(â˜ƒ);
      Collections.reverse(â˜ƒ);
      return â˜ƒ;
   }

   private static boolean isLighted(ChunkStatus var0, ChunkAccess var1) {
      return â˜ƒ.getStatus().isOrAfter(â˜ƒ) && â˜ƒ.isLightCorrect();
   }

   public static ChunkStatus getStatusAroundFullChunk(int var0) {
      if (â˜ƒ >= STATUS_BY_RANGE.size()) {
         return EMPTY;
      } else {
         return â˜ƒ < 0 ? FULL : (ChunkStatus)STATUS_BY_RANGE.get(â˜ƒ);
      }
   }

   public static int maxDistance() {
      return STATUS_BY_RANGE.size();
   }

   public static int getDistance(ChunkStatus var0) {
      return RANGE_BY_STATUS.getInt(â˜ƒ.getIndex());
   }

   ChunkStatus(
      String var1,
      @Nullable ChunkStatus var2,
      int var3,
      EnumSet<Heightmap.Types> var4,
      ChunkStatus.ChunkType var5,
      ChunkStatus.GenerationTask var6,
      ChunkStatus.LoadingTask var7
   ) {
      this.name = â˜ƒ;
      this.parent = â˜ƒ == null ? this : â˜ƒ;
      this.generationTask = â˜ƒ;
      this.loadingTask = â˜ƒ;
      this.range = â˜ƒ;
      this.chunkType = â˜ƒ;
      this.heightmapsAfter = â˜ƒ;
      this.index = â˜ƒ == null ? 0 : â˜ƒ.getIndex() + 1;
   }

   public int getIndex() {
      return this.index;
   }

   public String getName() {
      return this.name;
   }

   public ChunkStatus getParent() {
      return this.parent;
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> generate(
      Executor var1,
      ServerLevel var2,
      ChunkGenerator var3,
      StructureManager var4,
      ThreadedLevelLightEngine var5,
      Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var6,
      List<ChunkAccess> var7
   ) {
      return this.generationTask.doWork(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (ChunkAccess)â˜ƒ.get(â˜ƒ.size() / 2));
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> load(
      ServerLevel var1,
      StructureManager var2,
      ThreadedLevelLightEngine var3,
      Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var4,
      ChunkAccess var5
   ) {
      return this.loadingTask.doWork(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public int getRange() {
      return this.range;
   }

   public ChunkStatus.ChunkType getChunkType() {
      return this.chunkType;
   }

   public static ChunkStatus byName(String var0) {
      return Registry.CHUNK_STATUS.get(ResourceLocation.tryParse(â˜ƒ));
   }

   public EnumSet<Heightmap.Types> heightmapsAfter() {
      return this.heightmapsAfter;
   }

   public boolean isOrAfter(ChunkStatus var1) {
      return this.getIndex() >= â˜ƒ.getIndex();
   }

   public String toString() {
      return Registry.CHUNK_STATUS.getKey(this).toString();
   }

   public static enum ChunkType {
      PROTOCHUNK,
      LEVELCHUNK;
   }

   interface GenerationTask {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(
         ChunkStatus var1,
         Executor var2,
         ServerLevel var3,
         ChunkGenerator var4,
         StructureManager var5,
         ThreadedLevelLightEngine var6,
         Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var7,
         List<ChunkAccess> var8,
         ChunkAccess var9
      );
   }

   interface LoadingTask {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(
         ChunkStatus var1,
         ServerLevel var2,
         StructureManager var3,
         ThreadedLevelLightEngine var4,
         Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var5,
         ChunkAccess var6
      );
   }

   interface SimpleGenerationTask extends ChunkStatus.GenerationTask {
      @Override
      default CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(
         ChunkStatus var1,
         Executor var2,
         ServerLevel var3,
         ChunkGenerator var4,
         StructureManager var5,
         ThreadedLevelLightEngine var6,
         Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var7,
         List<ChunkAccess> var8,
         ChunkAccess var9
      ) {
         if (!â˜ƒ.getStatus().isOrAfter(â˜ƒ)) {
            this.doWork(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ instanceof ProtoChunk) {
               ((ProtoChunk)â˜ƒ).setStatus(â˜ƒ);
            }
         }

         return CompletableFuture.completedFuture(Either.left(â˜ƒ));
      }

      void doWork(ChunkStatus var1, ServerLevel var2, ChunkGenerator var3, List<ChunkAccess> var4, ChunkAccess var5);
   }
}
