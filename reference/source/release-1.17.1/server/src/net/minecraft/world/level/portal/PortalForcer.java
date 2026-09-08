package net.minecraft.world.level.portal;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

public class PortalForcer {
   private static final int TICKET_RADIUS = 3;
   private static final int SEARCH_RADIUS = 128;
   private static final int CREATE_RADIUS = 16;
   private static final int FRAME_HEIGHT = 5;
   private static final int FRAME_WIDTH = 4;
   private static final int FRAME_BOX = 3;
   private static final int FRAME_HEIGHT_START = -1;
   private static final int FRAME_HEIGHT_END = 4;
   private static final int FRAME_WIDTH_START = -1;
   private static final int FRAME_WIDTH_END = 3;
   private static final int FRAME_BOX_START = -1;
   private static final int FRAME_BOX_END = 2;
   private static final int NOTHING_FOUND = -1;
   private final ServerLevel level;

   public PortalForcer(ServerLevel var1) {
      this.level = â˜ƒ;
   }

   public Optional<BlockUtil.FoundRectangle> findPortalAround(BlockPos var1, boolean var2) {
      PoiManager â˜ƒ = this.level.getPoiManager();
      int â˜ƒx = â˜ƒ ? 16 : 128;
      â˜ƒ.ensureLoadedAndValid(this.level, â˜ƒ, â˜ƒx);
      Optional<PoiRecord> â˜ƒxx = â˜ƒ.getInSquare(var0 -> var0 == PoiType.NETHER_PORTAL, â˜ƒ, â˜ƒx, PoiManager.Occupancy.ANY)
         .sorted(Comparator.comparingDouble(var1x -> var1x.getPos().distSqr(â˜ƒ)).thenComparingInt(var0 -> var0.getPos().getY()))
         .filter(var1x -> this.level.getBlockState(var1x.getPos()).hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
         .findFirst();
      return â˜ƒxx.map(
         var1x -> {
            BlockPos â˜ƒ = var1x.getPos();
            this.level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(â˜ƒ), 3, â˜ƒ);
            BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
            return BlockUtil.getLargestRectangleAround(
               â˜ƒ, â˜ƒx.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, var2x -> this.level.getBlockState(var2x) == â˜ƒ
            );
         }
      );
   }

   public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos var1, Direction.Axis var2) {
      Direction â˜ƒ = Direction.get(Direction.AxisDirection.POSITIVE, â˜ƒ);
      double â˜ƒx = -1.0;
      BlockPos â˜ƒxx = null;
      double â˜ƒxxx = -1.0;
      BlockPos â˜ƒxxxx = null;
      WorldBorder â˜ƒxxxxx = this.level.getWorldBorder();
      int â˜ƒxxxxxx = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
      BlockPos.MutableBlockPos â˜ƒxxxxxxx = â˜ƒ.mutable();

      for(BlockPos.MutableBlockPos â˜ƒxxxxxxxx : BlockPos.spiralAround(â˜ƒ, 16, Direction.EAST, Direction.SOUTH)) {
         int â˜ƒxxxxxxxxx = Math.min(â˜ƒxxxxxx, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, â˜ƒxxxxxxxx.getX(), â˜ƒxxxxxxxx.getZ()));
         int â˜ƒxxxxxxxxxx = 1;
         if (â˜ƒxxxxx.isWithinBounds(â˜ƒxxxxxxxx) && â˜ƒxxxxx.isWithinBounds(â˜ƒxxxxxxxx.move(â˜ƒ, 1))) {
            â˜ƒxxxxxxxx.move(â˜ƒ.getOpposite(), 1);

            for(int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx; â˜ƒxxxxxxxxxxx >= this.level.getMinBuildHeight(); --â˜ƒxxxxxxxxxxx) {
               â˜ƒxxxxxxxx.setY(â˜ƒxxxxxxxxxxx);
               if (this.level.isEmptyBlock(â˜ƒxxxxxxxx)) {
                  int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx;

                  while(â˜ƒxxxxxxxxxxx > this.level.getMinBuildHeight() && this.level.isEmptyBlock(â˜ƒxxxxxxxx.move(Direction.DOWN))) {
                     --â˜ƒxxxxxxxxxxx;
                  }

                  if (â˜ƒxxxxxxxxxxx + 4 <= â˜ƒxxxxxx) {
                     int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxxxxxx;
                     if (â˜ƒxxxxxxxxxxxxx <= 0 || â˜ƒxxxxxxxxxxxxx >= 3) {
                        â˜ƒxxxxxxxx.setY(â˜ƒxxxxxxxxxxx);
                        if (this.canHostFrame(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒ, 0)) {
                           double â˜ƒxxxxxxxxxxxxxx = â˜ƒ.distSqr(â˜ƒxxxxxxxx);
                           if (this.canHostFrame(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒ, -1)
                              && this.canHostFrame(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒ, 1)
                              && (â˜ƒx == -1.0 || â˜ƒx > â˜ƒxxxxxxxxxxxxxx)) {
                              â˜ƒx = â˜ƒxxxxxxxxxxxxxx;
                              â˜ƒxx = â˜ƒxxxxxxxx.immutable();
                           }

                           if (â˜ƒx == -1.0 && (â˜ƒxxx == -1.0 || â˜ƒxxx > â˜ƒxxxxxxxxxxxxxx)) {
                              â˜ƒxxx = â˜ƒxxxxxxxxxxxxxx;
                              â˜ƒxxxx = â˜ƒxxxxxxxx.immutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (â˜ƒx == -1.0 && â˜ƒxxx != -1.0) {
         â˜ƒxx = â˜ƒxxxx;
         â˜ƒx = â˜ƒxxx;
      }

      if (â˜ƒx == -1.0) {
         int â˜ƒxxxxxxxx = Math.max(this.level.getMinBuildHeight() - -1, 70);
         int â˜ƒxxxxxxxxx = â˜ƒxxxxxx - 9;
         if (â˜ƒxxxxxxxxx < â˜ƒxxxxxxxx) {
            return Optional.empty();
         }

         â˜ƒxx = new BlockPos(â˜ƒ.getX(), Mth.clamp(â˜ƒ.getY(), â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx), â˜ƒ.getZ()).immutable();
         Direction â˜ƒxxxxxxxx = â˜ƒ.getClockWise();
         if (!â˜ƒxxxxx.isWithinBounds(â˜ƒxx)) {
            return Optional.empty();
         }

         for(int â˜ƒxxxxxxxx = -1; â˜ƒxxxxxxxx < 2; ++â˜ƒxxxxxxxx) {
            for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 2; ++â˜ƒxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxx = -1; â˜ƒxxxxxxxxxx < 3; ++â˜ƒxxxxxxxxxx) {
                  BlockState â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx < 0 ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.AIR.defaultBlockState();
                  â˜ƒxxxxxxx.setWithOffset(
                     â˜ƒxx,
                     â˜ƒxxxxxxxxx * â˜ƒ.getStepX() + â˜ƒxxxxxxxx * â˜ƒxxxxxxxx.getStepX(),
                     â˜ƒxxxxxxxxxx,
                     â˜ƒxxxxxxxxx * â˜ƒ.getStepZ() + â˜ƒxxxxxxxx * â˜ƒxxxxxxxx.getStepZ()
                  );
                  this.level.setBlockAndUpdate(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx);
               }
            }
         }
      }

      for(int â˜ƒxxxxxxxx = -1; â˜ƒxxxxxxxx < 3; ++â˜ƒxxxxxxxx) {
         for(int â˜ƒxxxxxxxxx = -1; â˜ƒxxxxxxxxx < 4; ++â˜ƒxxxxxxxxx) {
            if (â˜ƒxxxxxxxx == -1 || â˜ƒxxxxxxxx == 2 || â˜ƒxxxxxxxxx == -1 || â˜ƒxxxxxxxxx == 3) {
               â˜ƒxxxxxxx.setWithOffset(â˜ƒxx, â˜ƒxxxxxxxx * â˜ƒ.getStepX(), â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx * â˜ƒ.getStepZ());
               this.level.setBlock(â˜ƒxxxxxxx, Blocks.OBSIDIAN.defaultBlockState(), 3);
            }
         }
      }

      BlockState â˜ƒxxxxxxxx = Blocks.NETHER_PORTAL.defaultBlockState().setValue(NetherPortalBlock.AXIS, â˜ƒ);

      for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 2; ++â˜ƒxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < 3; ++â˜ƒxxxxxxxxxx) {
            â˜ƒxxxxxxx.setWithOffset(â˜ƒxx, â˜ƒxxxxxxxxx * â˜ƒ.getStepX(), â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx * â˜ƒ.getStepZ());
            this.level.setBlock(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 18);
         }
      }

      return Optional.of(new BlockUtil.FoundRectangle(â˜ƒxx.immutable(), 2, 3));
   }

   private boolean canHostFrame(BlockPos var1, BlockPos.MutableBlockPos var2, Direction var3, int var4) {
      Direction â˜ƒ = â˜ƒ.getClockWise();

      for(int â˜ƒx = -1; â˜ƒx < 3; ++â˜ƒx) {
         for(int â˜ƒxx = -1; â˜ƒxx < 4; ++â˜ƒxx) {
            â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ.getStepX() * â˜ƒx + â˜ƒ.getStepX() * â˜ƒ, â˜ƒxx, â˜ƒ.getStepZ() * â˜ƒx + â˜ƒ.getStepZ() * â˜ƒ);
            if (â˜ƒxx < 0 && !this.level.getBlockState(â˜ƒ).getMaterial().isSolid()) {
               return false;
            }

            if (â˜ƒxx >= 0 && !this.level.isEmptyBlock(â˜ƒ)) {
               return false;
            }
         }
      }

      return true;
   }
}
