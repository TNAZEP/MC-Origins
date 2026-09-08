package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

public final class SkyLightEngine extends LayerLightEngine<SkyLightSectionStorage.SkyDataLayerStorageMap, SkyLightSectionStorage> {
   private static final Direction[] DIRECTIONS = Direction.values();
   private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

   public SkyLightEngine(LightChunkGetter var1) {
      super(â˜ƒ, LightLayer.SKY, new SkyLightSectionStorage(â˜ƒ));
   }

   @Override
   protected int computeLevelFromNeighbor(long var1, long var3, int var5) {
      if (â˜ƒ == Long.MAX_VALUE || â˜ƒ == Long.MAX_VALUE) {
         return 15;
      } else if (â˜ƒ >= 15) {
         return â˜ƒ;
      } else {
         MutableInt â˜ƒ = new MutableInt();
         BlockState â˜ƒx = this.getStateAndOpacity(â˜ƒ, â˜ƒ);
         if (â˜ƒ.getValue() >= 15) {
            return 15;
         } else {
            int â˜ƒ = BlockPos.getX(â˜ƒ);
            int â˜ƒx = BlockPos.getY(â˜ƒ);
            int â˜ƒxx = BlockPos.getZ(â˜ƒ);
            int â˜ƒxxx = BlockPos.getX(â˜ƒ);
            int â˜ƒxxxx = BlockPos.getY(â˜ƒ);
            int â˜ƒxxxxx = BlockPos.getZ(â˜ƒ);
            int â˜ƒxxxxxx = Integer.signum(â˜ƒxxx - â˜ƒ);
            int â˜ƒxxxxxxx = Integer.signum(â˜ƒxxxx - â˜ƒx);
            int â˜ƒxxxxxxxx = Integer.signum(â˜ƒxxxxx - â˜ƒxx);
            Direction â˜ƒxxxxxxxxx = Direction.fromNormal(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
            if (â˜ƒxxxxxxxxx == null) {
               throw new IllegalStateException(String.format("Light was spread in illegal direction %d, %d, %d", â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx));
            } else {
               BlockState â˜ƒ = this.getStateAndOpacity(â˜ƒ, null);
               VoxelShape â˜ƒx = this.getShape(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxx);
               VoxelShape â˜ƒxx = this.getShape(â˜ƒx, â˜ƒ, â˜ƒxxxxxxxxx.getOpposite());
               if (Shapes.faceShapeOccludes(â˜ƒx, â˜ƒxx)) {
                  return 15;
               } else {
                  boolean â˜ƒ = â˜ƒ == â˜ƒxxx && â˜ƒxx == â˜ƒxxxxx;
                  boolean â˜ƒx = â˜ƒ && â˜ƒx > â˜ƒxxxx;
                  return â˜ƒx && â˜ƒ == 0 && â˜ƒ.getValue() == 0 ? 0 : â˜ƒ + Math.max(1, â˜ƒ.getValue());
               }
            }
         }
      }
   }

   @Override
   protected void checkNeighborsAfterUpdate(long var1, int var3, boolean var4) {
      long â˜ƒx = SectionPos.blockToSection(â˜ƒ);
      int â˜ƒxx = BlockPos.getY(â˜ƒ);
      int â˜ƒxxx = SectionPos.sectionRelative(â˜ƒxx);
      int â˜ƒxxxx = SectionPos.blockToSectionCoord(â˜ƒxx);
      int â˜ƒ;
      if (â˜ƒxxx != 0) {
         â˜ƒ = 0;
      } else {
         int â˜ƒ = 0;

         while(!this.storage.storingLightForSection(SectionPos.offset(â˜ƒx, 0, -â˜ƒ - 1, 0)) && this.storage.hasSectionsBelow(â˜ƒxxxx - â˜ƒ - 1)) {
            ++â˜ƒ;
         }

         â˜ƒ = â˜ƒ;
      }

      long â˜ƒ = BlockPos.offset(â˜ƒ, 0, -1 - â˜ƒ * 16, 0);
      long â˜ƒx = SectionPos.blockToSection(â˜ƒ);
      if (â˜ƒx == â˜ƒx || this.storage.storingLightForSection(â˜ƒx)) {
         this.checkNeighbor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      long â˜ƒ = BlockPos.offset(â˜ƒ, Direction.UP);
      long â˜ƒx = SectionPos.blockToSection(â˜ƒ);
      if (â˜ƒx == â˜ƒx || this.storage.storingLightForSection(â˜ƒx)) {
         this.checkNeighbor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      for(Direction â˜ƒ : HORIZONTALS) {
         int â˜ƒx = 0;

         do {
            long â˜ƒxx = BlockPos.offset(â˜ƒ, â˜ƒ.getStepX(), -â˜ƒx, â˜ƒ.getStepZ());
            long â˜ƒxxx = SectionPos.blockToSection(â˜ƒxx);
            if (â˜ƒx == â˜ƒxxx) {
               this.checkNeighbor(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
               break;
            }

            if (this.storage.storingLightForSection(â˜ƒxxx)) {
               long â˜ƒxx = BlockPos.offset(â˜ƒ, 0, -â˜ƒx, 0);
               this.checkNeighbor(â˜ƒxx, â˜ƒxx, â˜ƒ, â˜ƒ);
            }
         } while(++â˜ƒx > â˜ƒ * 16);
      }
   }

   @Override
   protected int getComputedLevel(long var1, long var3, int var5) {
      int â˜ƒ = â˜ƒ;
      long â˜ƒx = SectionPos.blockToSection(â˜ƒ);
      DataLayer â˜ƒxx = this.storage.getDataLayer(â˜ƒx, true);

      for(Direction â˜ƒxxx : DIRECTIONS) {
         long â˜ƒxxxx = BlockPos.offset(â˜ƒ, â˜ƒxxx);
         if (â˜ƒxxxx != â˜ƒ) {
            long â˜ƒxxxxxx = SectionPos.blockToSection(â˜ƒxxxx);
            DataLayer â˜ƒxxxxx;
            if (â˜ƒx == â˜ƒxxxxxx) {
               â˜ƒxxxxx = â˜ƒxx;
            } else {
               â˜ƒxxxxx = this.storage.getDataLayer(â˜ƒxxxxxx, true);
            }

            int â˜ƒxxxxx;
            if (â˜ƒxxxxx != null) {
               â˜ƒxxxxx = this.getLevel(â˜ƒxxxxx, â˜ƒxxxx);
            } else {
               if (â˜ƒxxx == Direction.DOWN) {
                  continue;
               }

               â˜ƒxxxxx = 15 - this.storage.getLightValue(â˜ƒxxxx, true);
            }

            int â˜ƒxxxxx = this.computeLevelFromNeighbor(â˜ƒxxxx, â˜ƒ, â˜ƒxxxxx);
            if (â˜ƒ > â˜ƒxxxxx) {
               â˜ƒ = â˜ƒxxxxx;
            }

            if (â˜ƒ == 0) {
               return â˜ƒ;
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   protected void checkNode(long var1) {
      this.storage.runAllUpdates();
      long â˜ƒ = SectionPos.blockToSection(â˜ƒ);
      if (this.storage.storingLightForSection(â˜ƒ)) {
         super.checkNode(â˜ƒ);
      } else {
         for(â˜ƒ = BlockPos.getFlatIndex(â˜ƒ);
            !this.storage.storingLightForSection(â˜ƒ) && !this.storage.isAboveData(â˜ƒ);
            â˜ƒ = BlockPos.offset(â˜ƒ, 0, 16, 0)
         ) {
            â˜ƒ = SectionPos.offset(â˜ƒ, Direction.UP);
         }

         if (this.storage.storingLightForSection(â˜ƒ)) {
            super.checkNode(â˜ƒ);
         }
      }
   }

   @Override
   public String getDebugData(long var1) {
      return super.getDebugData(â˜ƒ) + (this.storage.isAboveData(â˜ƒ) ? "*" : "");
   }
}
