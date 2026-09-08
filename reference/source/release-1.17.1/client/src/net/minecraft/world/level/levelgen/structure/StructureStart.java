package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructureStart<C extends FeatureConfiguration> implements StructurePieceAccessor {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final String INVALID_START_ID = "INVALID";
   public static final StructureStart<?> INVALID_START = new StructureStart<MineshaftConfiguration>(null, new ChunkPos(0, 0), 0, 0L) {
      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, MineshaftConfiguration var6, LevelHeightAccessor var7
      ) {
      }

      @Override
      public boolean isValid() {
         return false;
      }
   };
   private final StructureFeature<C> feature;
   protected final List<StructurePiece> pieces = Lists.<StructurePiece>newArrayList();
   private final ChunkPos chunkPos;
   private int references;
   protected final WorldgenRandom random;
   @Nullable
   private BoundingBox cachedBoundingBox;

   public StructureStart(StructureFeature<C> var1, ChunkPos var2, int var3, long var4) {
      this.feature = â˜ƒ;
      this.chunkPos = â˜ƒ;
      this.references = â˜ƒ;
      this.random = new WorldgenRandom();
      this.random.setLargeFeatureSeed(â˜ƒ, â˜ƒ.x, â˜ƒ.z);
   }

   public abstract void generatePieces(
      RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, C var6, LevelHeightAccessor var7
   );

   public final BoundingBox getBoundingBox() {
      if (this.cachedBoundingBox == null) {
         this.cachedBoundingBox = this.createBoundingBox();
      }

      return this.cachedBoundingBox;
   }

   protected BoundingBox createBoundingBox() {
      synchronized(this.pieces) {
         return (BoundingBox)BoundingBox.encapsulatingBoxes(this.pieces.stream().map(StructurePiece::getBoundingBox)::iterator)
            .orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox without pieces"));
      }
   }

   public List<StructurePiece> getPieces() {
      return this.pieces;
   }

   public void placeInChunk(WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6) {
      synchronized(this.pieces) {
         if (!this.pieces.isEmpty()) {
            BoundingBox â˜ƒ = ((StructurePiece)this.pieces.get(0)).boundingBox;
            BlockPos â˜ƒx = â˜ƒ.getCenter();
            BlockPos â˜ƒxx = new BlockPos(â˜ƒx.getX(), â˜ƒ.minY(), â˜ƒx.getZ());
            Iterator<StructurePiece> â˜ƒxxx = this.pieces.iterator();

            while(â˜ƒxxx.hasNext()) {
               StructurePiece â˜ƒxxxx = (StructurePiece)â˜ƒxxx.next();
               if (â˜ƒxxxx.getBoundingBox().intersects(â˜ƒ) && !â˜ƒxxxx.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx)) {
                  â˜ƒxxx.remove();
               }
            }
         }
      }
   }

   public CompoundTag createTag(ServerLevel var1, ChunkPos var2) {
      CompoundTag â˜ƒ = new CompoundTag();
      if (this.isValid()) {
         â˜ƒ.putString("id", Registry.STRUCTURE_FEATURE.getKey(this.getFeature()).toString());
         â˜ƒ.putInt("ChunkX", â˜ƒ.x);
         â˜ƒ.putInt("ChunkZ", â˜ƒ.z);
         â˜ƒ.putInt("references", this.references);
         ListTag var4 = new ListTag();
         synchronized(this.pieces) {
            for(StructurePiece â˜ƒx : this.pieces) {
               var4.add(â˜ƒx.createTag(â˜ƒ));
            }
         }

         â˜ƒ.put("Children", var4);
         return â˜ƒ;
      } else {
         â˜ƒ.putString("id", "INVALID");
         return â˜ƒ;
      }
   }

   protected void moveBelowSeaLevel(int var1, int var2, Random var3, int var4) {
      int â˜ƒ = â˜ƒ - â˜ƒ;
      BoundingBox â˜ƒx = this.getBoundingBox();
      int â˜ƒxx = â˜ƒx.getYSpan() + â˜ƒ + 1;
      if (â˜ƒxx < â˜ƒ) {
         â˜ƒxx += â˜ƒ.nextInt(â˜ƒ - â˜ƒxx);
      }

      int â˜ƒ = â˜ƒxx - â˜ƒx.maxY();
      this.offsetPiecesVertically(â˜ƒ);
   }

   protected void moveInsideHeights(Random var1, int var2, int var3) {
      BoundingBox â˜ƒx = this.getBoundingBox();
      int â˜ƒxx = â˜ƒ - â˜ƒ + 1 - â˜ƒx.getYSpan();
      int â˜ƒ;
      if (â˜ƒxx > 1) {
         â˜ƒ = â˜ƒ + â˜ƒ.nextInt(â˜ƒxx);
      } else {
         â˜ƒ = â˜ƒ;
      }

      int â˜ƒ = â˜ƒ - â˜ƒx.minY();
      this.offsetPiecesVertically(â˜ƒ);
   }

   protected void offsetPiecesVertically(int var1) {
      for(StructurePiece â˜ƒ : this.pieces) {
         â˜ƒ.move(0, â˜ƒ, 0);
      }

      this.invalidateCache();
   }

   private void invalidateCache() {
      this.cachedBoundingBox = null;
   }

   public boolean isValid() {
      return !this.pieces.isEmpty();
   }

   public ChunkPos getChunkPos() {
      return this.chunkPos;
   }

   public BlockPos getLocatePos() {
      return new BlockPos(this.chunkPos.getMinBlockX(), 0, this.chunkPos.getMinBlockZ());
   }

   public boolean canBeReferenced() {
      return this.references < this.getMaxReferences();
   }

   public void addReference() {
      ++this.references;
   }

   public int getReferences() {
      return this.references;
   }

   protected int getMaxReferences() {
      return 1;
   }

   public StructureFeature<?> getFeature() {
      return this.feature;
   }

   @Override
   public void addPiece(StructurePiece var1) {
      this.pieces.add(â˜ƒ);
      this.invalidateCache();
   }

   @Nullable
   @Override
   public StructurePiece findCollisionPiece(BoundingBox var1) {
      return findCollisionPiece(this.pieces, â˜ƒ);
   }

   public void clearPieces() {
      this.pieces.clear();
      this.invalidateCache();
   }

   public boolean hasNoPieces() {
      return this.pieces.isEmpty();
   }

   @Nullable
   public static StructurePiece findCollisionPiece(List<StructurePiece> var0, BoundingBox var1) {
      for(StructurePiece â˜ƒ : â˜ƒ) {
         if (â˜ƒ.getBoundingBox().intersects(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return null;
   }

   protected boolean isInsidePiece(BlockPos var1) {
      for(StructurePiece â˜ƒ : this.pieces) {
         if (â˜ƒ.getBoundingBox().isInside(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }
}
