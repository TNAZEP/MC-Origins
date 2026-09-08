package net.minecraft.world.level;

import com.mojang.datafixers.DataFixUtils;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.FeatureAccess;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureFeatureManager {
   private final LevelAccessor level;
   private final WorldGenSettings worldGenSettings;

   public StructureFeatureManager(LevelAccessor var1, WorldGenSettings var2) {
      this.level = â˜ƒ;
      this.worldGenSettings = â˜ƒ;
   }

   public StructureFeatureManager forWorldGenRegion(WorldGenRegion var1) {
      if (â˜ƒ.getLevel() != this.level) {
         throw new IllegalStateException("Using invalid feature manager (source level: " + â˜ƒ.getLevel() + ", region: " + â˜ƒ);
      } else {
         return new StructureFeatureManager(â˜ƒ, this.worldGenSettings);
      }
   }

   public Stream<? extends StructureStart<?>> startsForFeature(SectionPos var1, StructureFeature<?> var2) {
      return this.level
         .getChunk(â˜ƒ.x(), â˜ƒ.z(), ChunkStatus.STRUCTURE_REFERENCES)
         .getReferencesForFeature(â˜ƒ)
         .stream()
         .map(var1x -> SectionPos.of(new ChunkPos(var1x), this.level.getMinSection()))
         .map(var2x -> this.getStartForFeature(var2x, â˜ƒ, this.level.getChunk(var2x.x(), var2x.z(), ChunkStatus.STRUCTURE_STARTS)))
         .filter(var0 -> var0 != null && var0.isValid());
   }

   @Nullable
   public StructureStart<?> getStartForFeature(SectionPos var1, StructureFeature<?> var2, FeatureAccess var3) {
      return â˜ƒ.getStartForFeature(â˜ƒ);
   }

   public void setStartForFeature(SectionPos var1, StructureFeature<?> var2, StructureStart<?> var3, FeatureAccess var4) {
      â˜ƒ.setStartForFeature(â˜ƒ, â˜ƒ);
   }

   public void addReferenceForFeature(SectionPos var1, StructureFeature<?> var2, long var3, FeatureAccess var5) {
      â˜ƒ.addReferenceForFeature(â˜ƒ, â˜ƒ);
   }

   public boolean shouldGenerateFeatures() {
      return this.worldGenSettings.generateFeatures();
   }

   public StructureStart<?> getStructureAt(BlockPos var1, boolean var2, StructureFeature<?> var3) {
      return DataFixUtils.orElse(
         this.startsForFeature(SectionPos.of(â˜ƒ), â˜ƒ)
            .filter(var2x -> â˜ƒ ? var2x.getPieces().stream().anyMatch(var1x -> var1x.getBoundingBox().isInside(â˜ƒ)) : var2x.getBoundingBox().isInside(â˜ƒ))
            .findFirst(),
         StructureStart.INVALID_START
      );
   }
}
