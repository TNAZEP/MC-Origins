package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

public abstract class ScatteredFeaturePiece extends StructurePiece {
   protected final int width;
   protected final int height;
   protected final int depth;
   protected int heightPosition = -1;

   protected ScatteredFeaturePiece(StructurePieceType var1, int var2, int var3, int var4, int var5, int var6, int var7, Direction var8) {
      super(â˜ƒ, 0, StructurePiece.makeBoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.depth = â˜ƒ;
      this.setOrientation(â˜ƒ);
   }

   protected ScatteredFeaturePiece(StructurePieceType var1, CompoundTag var2) {
      super(â˜ƒ, â˜ƒ);
      this.width = â˜ƒ.getInt("Width");
      this.height = â˜ƒ.getInt("Height");
      this.depth = â˜ƒ.getInt("Depth");
      this.heightPosition = â˜ƒ.getInt("HPos");
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      â˜ƒ.putInt("Width", this.width);
      â˜ƒ.putInt("Height", this.height);
      â˜ƒ.putInt("Depth", this.depth);
      â˜ƒ.putInt("HPos", this.heightPosition);
   }

   protected boolean updateAverageGroundHeight(LevelAccessor var1, BoundingBox var2, int var3) {
      if (this.heightPosition >= 0) {
         return true;
      } else {
         int â˜ƒ = 0;
         int â˜ƒx = 0;
         BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxx = this.boundingBox.minZ(); â˜ƒxxx <= this.boundingBox.maxZ(); ++â˜ƒxxx) {
            for(int â˜ƒxxxx = this.boundingBox.minX(); â˜ƒxxxx <= this.boundingBox.maxX(); ++â˜ƒxxxx) {
               â˜ƒxx.set(â˜ƒxxxx, 64, â˜ƒxxx);
               if (â˜ƒ.isInside(â˜ƒxx)) {
                  â˜ƒ += â˜ƒ.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, â˜ƒxx).getY();
                  ++â˜ƒx;
               }
            }
         }

         if (â˜ƒx == 0) {
            return false;
         } else {
            this.heightPosition = â˜ƒ / â˜ƒx;
            this.boundingBox.move(0, this.heightPosition - this.boundingBox.minY() + â˜ƒ, 0);
            return true;
         }
      }
   }
}
