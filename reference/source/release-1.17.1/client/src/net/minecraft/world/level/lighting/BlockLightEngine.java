package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

public final class BlockLightEngine extends LayerLightEngine<BlockLightSectionStorage.BlockDataLayerStorageMap, BlockLightSectionStorage> {
   private static final Direction[] DIRECTIONS = Direction.values();
   private final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

   public BlockLightEngine(LightChunkGetter var1) {
      super(â˜ƒ, LightLayer.BLOCK, new BlockLightSectionStorage(â˜ƒ));
   }

   private int getLightEmission(long var1) {
      int â˜ƒ = BlockPos.getX(â˜ƒ);
      int â˜ƒx = BlockPos.getY(â˜ƒ);
      int â˜ƒxx = BlockPos.getZ(â˜ƒ);
      BlockGetter â˜ƒxxx = this.chunkSource.getChunkForLighting(SectionPos.blockToSectionCoord(â˜ƒ), SectionPos.blockToSectionCoord(â˜ƒxx));
      return â˜ƒxxx != null ? â˜ƒxxx.getLightEmission(this.pos.set(â˜ƒ, â˜ƒx, â˜ƒxx)) : 0;
   }

   @Override
   protected int computeLevelFromNeighbor(long var1, long var3, int var5) {
      if (â˜ƒ == Long.MAX_VALUE) {
         return 15;
      } else if (â˜ƒ == Long.MAX_VALUE) {
         return â˜ƒ + 15 - this.getLightEmission(â˜ƒ);
      } else if (â˜ƒ >= 15) {
         return â˜ƒ;
      } else {
         int â˜ƒ = Integer.signum(BlockPos.getX(â˜ƒ) - BlockPos.getX(â˜ƒ));
         int â˜ƒx = Integer.signum(BlockPos.getY(â˜ƒ) - BlockPos.getY(â˜ƒ));
         int â˜ƒxx = Integer.signum(BlockPos.getZ(â˜ƒ) - BlockPos.getZ(â˜ƒ));
         Direction â˜ƒxxx = Direction.fromNormal(â˜ƒ, â˜ƒx, â˜ƒxx);
         if (â˜ƒxxx == null) {
            return 15;
         } else {
            MutableInt â˜ƒ = new MutableInt();
            BlockState â˜ƒx = this.getStateAndOpacity(â˜ƒ, â˜ƒ);
            if (â˜ƒ.getValue() >= 15) {
               return 15;
            } else {
               BlockState â˜ƒ = this.getStateAndOpacity(â˜ƒ, null);
               VoxelShape â˜ƒx = this.getShape(â˜ƒ, â˜ƒ, â˜ƒxxx);
               VoxelShape â˜ƒxx = this.getShape(â˜ƒx, â˜ƒ, â˜ƒxxx.getOpposite());
               return Shapes.faceShapeOccludes(â˜ƒx, â˜ƒxx) ? 15 : â˜ƒ + Math.max(1, â˜ƒ.getValue());
            }
         }
      }
   }

   @Override
   protected void checkNeighborsAfterUpdate(long var1, int var3, boolean var4) {
      long â˜ƒ = SectionPos.blockToSection(â˜ƒ);

      for(Direction â˜ƒx : DIRECTIONS) {
         long â˜ƒxx = BlockPos.offset(â˜ƒ, â˜ƒx);
         long â˜ƒxxx = SectionPos.blockToSection(â˜ƒxx);
         if (â˜ƒ == â˜ƒxxx || this.storage.storingLightForSection(â˜ƒxxx)) {
            this.checkNeighbor(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   protected int getComputedLevel(long var1, long var3, int var5) {
      int â˜ƒ = â˜ƒ;
      if (Long.MAX_VALUE != â˜ƒ) {
         int â˜ƒx = this.computeLevelFromNeighbor(Long.MAX_VALUE, â˜ƒ, 0);
         if (â˜ƒ > â˜ƒx) {
            â˜ƒ = â˜ƒx;
         }

         if (â˜ƒ == 0) {
            return â˜ƒ;
         }
      }

      long â˜ƒ = SectionPos.blockToSection(â˜ƒ);
      DataLayer â˜ƒx = this.storage.getDataLayer(â˜ƒ, true);

      for(Direction â˜ƒxx : DIRECTIONS) {
         long â˜ƒxxx = BlockPos.offset(â˜ƒ, â˜ƒxx);
         if (â˜ƒxxx != â˜ƒ) {
            long â˜ƒxxxxx = SectionPos.blockToSection(â˜ƒxxx);
            DataLayer â˜ƒxxxx;
            if (â˜ƒ == â˜ƒxxxxx) {
               â˜ƒxxxx = â˜ƒx;
            } else {
               â˜ƒxxxx = this.storage.getDataLayer(â˜ƒxxxxx, true);
            }

            if (â˜ƒxxxx != null) {
               int â˜ƒxxxx = this.computeLevelFromNeighbor(â˜ƒxxx, â˜ƒ, this.getLevel(â˜ƒxxxx, â˜ƒxxx));
               if (â˜ƒ > â˜ƒxxxx) {
                  â˜ƒ = â˜ƒxxxx;
               }

               if (â˜ƒ == 0) {
                  return â˜ƒ;
               }
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   public void onBlockEmissionIncrease(BlockPos var1, int var2) {
      this.storage.runAllUpdates();
      this.checkEdge(Long.MAX_VALUE, â˜ƒ.asLong(), 15 - â˜ƒ, true);
   }
}
