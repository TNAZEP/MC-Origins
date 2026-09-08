package net.minecraft.world.level.lighting;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class LayerLightEngine<M extends DataLayerStorageMap<M>, S extends LayerLightSectionStorage<M>>
   extends DynamicGraphMinFixedPoint
   implements LayerLightEventListener {
   public static final long SELF_SOURCE = Long.MAX_VALUE;
   private static final Direction[] DIRECTIONS = Direction.values();
   protected final LightChunkGetter chunkSource;
   protected final LightLayer layer;
   protected final S storage;
   private boolean runningLightUpdates;
   protected final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
   private static final int CACHE_SIZE = 2;
   private final long[] lastChunkPos = new long[2];
   private final BlockGetter[] lastChunk = new BlockGetter[2];

   public LayerLightEngine(LightChunkGetter var1, LightLayer var2, S var3) {
      super(16, 256, 8192);
      this.chunkSource = â˜ƒ;
      this.layer = â˜ƒ;
      this.storage = â˜ƒ;
      this.clearCache();
   }

   @Override
   protected void checkNode(long var1) {
      this.storage.runAllUpdates();
      if (this.storage.storingLightForSection(SectionPos.blockToSection(â˜ƒ))) {
         super.checkNode(â˜ƒ);
      }
   }

   @Nullable
   private BlockGetter getChunk(int var1, int var2) {
      long â˜ƒ = ChunkPos.asLong(â˜ƒ, â˜ƒ);

      for(int â˜ƒx = 0; â˜ƒx < 2; ++â˜ƒx) {
         if (â˜ƒ == this.lastChunkPos[â˜ƒx]) {
            return this.lastChunk[â˜ƒx];
         }
      }

      BlockGetter â˜ƒx = this.chunkSource.getChunkForLighting(â˜ƒ, â˜ƒ);

      for(int â˜ƒxx = 1; â˜ƒxx > 0; --â˜ƒxx) {
         this.lastChunkPos[â˜ƒxx] = this.lastChunkPos[â˜ƒxx - 1];
         this.lastChunk[â˜ƒxx] = this.lastChunk[â˜ƒxx - 1];
      }

      this.lastChunkPos[0] = â˜ƒ;
      this.lastChunk[0] = â˜ƒx;
      return â˜ƒx;
   }

   private void clearCache() {
      Arrays.fill(this.lastChunkPos, ChunkPos.INVALID_CHUNK_POS);
      Arrays.fill(this.lastChunk, null);
   }

   protected BlockState getStateAndOpacity(long var1, @Nullable MutableInt var3) {
      if (â˜ƒ == Long.MAX_VALUE) {
         if (â˜ƒ != null) {
            â˜ƒ.setValue(0);
         }

         return Blocks.AIR.defaultBlockState();
      } else {
         int â˜ƒ = SectionPos.blockToSectionCoord(BlockPos.getX(â˜ƒ));
         int â˜ƒx = SectionPos.blockToSectionCoord(BlockPos.getZ(â˜ƒ));
         BlockGetter â˜ƒxx = this.getChunk(â˜ƒ, â˜ƒx);
         if (â˜ƒxx == null) {
            if (â˜ƒ != null) {
               â˜ƒ.setValue(16);
            }

            return Blocks.BEDROCK.defaultBlockState();
         } else {
            this.pos.set(â˜ƒ);
            BlockState â˜ƒ = â˜ƒxx.getBlockState(this.pos);
            boolean â˜ƒx = â˜ƒ.canOcclude() && â˜ƒ.useShapeForLightOcclusion();
            if (â˜ƒ != null) {
               â˜ƒ.setValue(â˜ƒ.getLightBlock(this.chunkSource.getLevel(), this.pos));
            }

            return â˜ƒx ? â˜ƒ : Blocks.AIR.defaultBlockState();
         }
      }
   }

   protected VoxelShape getShape(BlockState var1, long var2, Direction var4) {
      return â˜ƒ.canOcclude() ? â˜ƒ.getFaceOcclusionShape(this.chunkSource.getLevel(), this.pos.set(â˜ƒ), â˜ƒ) : Shapes.empty();
   }

   public static int getLightBlockInto(BlockGetter var0, BlockState var1, BlockPos var2, BlockState var3, BlockPos var4, Direction var5, int var6) {
      boolean â˜ƒ = â˜ƒ.canOcclude() && â˜ƒ.useShapeForLightOcclusion();
      boolean â˜ƒx = â˜ƒ.canOcclude() && â˜ƒ.useShapeForLightOcclusion();
      if (!â˜ƒ && !â˜ƒx) {
         return â˜ƒ;
      } else {
         VoxelShape â˜ƒ = â˜ƒ ? â˜ƒ.getOcclusionShape(â˜ƒ, â˜ƒ) : Shapes.empty();
         VoxelShape â˜ƒx = â˜ƒx ? â˜ƒ.getOcclusionShape(â˜ƒ, â˜ƒ) : Shapes.empty();
         return Shapes.mergedFaceOccludes(â˜ƒ, â˜ƒx, â˜ƒ) ? 16 : â˜ƒ;
      }
   }

   @Override
   protected boolean isSource(long var1) {
      return â˜ƒ == Long.MAX_VALUE;
   }

   @Override
   protected int getComputedLevel(long var1, long var3, int var5) {
      return 0;
   }

   @Override
   protected int getLevel(long var1) {
      return â˜ƒ == Long.MAX_VALUE ? 0 : 15 - this.storage.getStoredLevel(â˜ƒ);
   }

   protected int getLevel(DataLayer var1, long var2) {
      return 15
         - â˜ƒ.get(
            SectionPos.sectionRelative(BlockPos.getX(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getY(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getZ(â˜ƒ))
         );
   }

   @Override
   protected void setLevel(long var1, int var3) {
      this.storage.setStoredLevel(â˜ƒ, Math.min(15, 15 - â˜ƒ));
   }

   @Override
   protected int computeLevelFromNeighbor(long var1, long var3, int var5) {
      return 0;
   }

   @Override
   public boolean hasLightWork() {
      return this.hasWork() || this.storage.hasWork() || this.storage.hasInconsistencies();
   }

   @Override
   public int runUpdates(int var1, boolean var2, boolean var3) {
      if (!this.runningLightUpdates) {
         if (this.storage.hasWork()) {
            â˜ƒ = this.storage.runUpdates(â˜ƒ);
            if (â˜ƒ == 0) {
               return â˜ƒ;
            }
         }

         this.storage.markNewInconsistencies(this, â˜ƒ, â˜ƒ);
      }

      this.runningLightUpdates = true;
      if (this.hasWork()) {
         â˜ƒ = this.runUpdates(â˜ƒ);
         this.clearCache();
         if (â˜ƒ == 0) {
            return â˜ƒ;
         }
      }

      this.runningLightUpdates = false;
      this.storage.swapSectionMap();
      return â˜ƒ;
   }

   protected void queueSectionData(long var1, @Nullable DataLayer var3, boolean var4) {
      this.storage.queueSectionData(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public DataLayer getDataLayerData(SectionPos var1) {
      return this.storage.getDataLayerData(â˜ƒ.asLong());
   }

   @Override
   public int getLightValue(BlockPos var1) {
      return this.storage.getLightValue(â˜ƒ.asLong());
   }

   public String getDebugData(long var1) {
      return this.storage.getLevel(â˜ƒ) + "";
   }

   @Override
   public void checkBlock(BlockPos var1) {
      long â˜ƒ = â˜ƒ.asLong();
      this.checkNode(â˜ƒ);

      for(Direction â˜ƒx : DIRECTIONS) {
         this.checkNode(BlockPos.offset(â˜ƒ, â˜ƒx));
      }
   }

   @Override
   public void onBlockEmissionIncrease(BlockPos var1, int var2) {
   }

   @Override
   public void updateSectionStatus(SectionPos var1, boolean var2) {
      this.storage.updateSectionStatus(â˜ƒ.asLong(), â˜ƒ);
   }

   @Override
   public void enableLightSources(ChunkPos var1, boolean var2) {
      long â˜ƒ = SectionPos.getZeroNode(SectionPos.asLong(â˜ƒ.x, 0, â˜ƒ.z));
      this.storage.enableLightSources(â˜ƒ, â˜ƒ);
   }

   public void retainData(ChunkPos var1, boolean var2) {
      long â˜ƒ = SectionPos.getZeroNode(SectionPos.asLong(â˜ƒ.x, 0, â˜ƒ.z));
      this.storage.retainData(â˜ƒ, â˜ƒ);
   }
}
