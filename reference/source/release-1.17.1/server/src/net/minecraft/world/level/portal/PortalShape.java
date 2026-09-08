package net.minecraft.world.level.portal;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class PortalShape {
   private static final int MIN_WIDTH = 2;
   public static final int MAX_WIDTH = 21;
   private static final int MIN_HEIGHT = 3;
   public static final int MAX_HEIGHT = 21;
   private static final BlockBehaviour.StatePredicate FRAME = (var0, var1, var2) -> var0.is(Blocks.OBSIDIAN);
   private final LevelAccessor level;
   private final Direction.Axis axis;
   private final Direction rightDir;
   private int numPortalBlocks;
   @Nullable
   private BlockPos bottomLeft;
   private int height;
   private final int width;

   public static Optional<PortalShape> findEmptyPortalShape(LevelAccessor var0, BlockPos var1, Direction.Axis var2) {
      return findPortalShape(â˜ƒ, â˜ƒ, var0x -> var0x.isValid() && var0x.numPortalBlocks == 0, â˜ƒ);
   }

   public static Optional<PortalShape> findPortalShape(LevelAccessor var0, BlockPos var1, Predicate<PortalShape> var2, Direction.Axis var3) {
      Optional<PortalShape> â˜ƒ = Optional.of(new PortalShape(â˜ƒ, â˜ƒ, â˜ƒ)).filter(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         return â˜ƒ;
      } else {
         Direction.Axis â˜ƒ = â˜ƒ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
         return Optional.of(new PortalShape(â˜ƒ, â˜ƒ, â˜ƒ)).filter(â˜ƒ);
      }
   }

   public PortalShape(LevelAccessor var1, BlockPos var2, Direction.Axis var3) {
      this.level = â˜ƒ;
      this.axis = â˜ƒ;
      this.rightDir = â˜ƒ == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
      this.bottomLeft = this.calculateBottomLeft(â˜ƒ);
      if (this.bottomLeft == null) {
         this.bottomLeft = â˜ƒ;
         this.width = 1;
         this.height = 1;
      } else {
         this.width = this.calculateWidth();
         if (this.width > 0) {
            this.height = this.calculateHeight();
         }
      }
   }

   @Nullable
   private BlockPos calculateBottomLeft(BlockPos var1) {
      int â˜ƒ = Math.max(this.level.getMinBuildHeight(), â˜ƒ.getY() - 21);

      while(â˜ƒ.getY() > â˜ƒ && isEmpty(this.level.getBlockState(â˜ƒ.below()))) {
         â˜ƒ = â˜ƒ.below();
      }

      Direction â˜ƒx = this.rightDir.getOpposite();
      int â˜ƒxx = this.getDistanceUntilEdgeAboveFrame(â˜ƒ, â˜ƒx) - 1;
      return â˜ƒxx < 0 ? null : â˜ƒ.relative(â˜ƒx, â˜ƒxx);
   }

   private int calculateWidth() {
      int â˜ƒ = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
      return â˜ƒ >= 2 && â˜ƒ <= 21 ? â˜ƒ : 0;
   }

   private int getDistanceUntilEdgeAboveFrame(BlockPos var1, Direction var2) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = 0; â˜ƒx <= 21; ++â˜ƒx) {
         â˜ƒ.set(â˜ƒ).move(â˜ƒ, â˜ƒx);
         BlockState â˜ƒxx = this.level.getBlockState(â˜ƒ);
         if (!isEmpty(â˜ƒxx)) {
            if (FRAME.test(â˜ƒxx, this.level, â˜ƒ)) {
               return â˜ƒx;
            }
            break;
         }

         BlockState â˜ƒxx = this.level.getBlockState(â˜ƒ.move(Direction.DOWN));
         if (!FRAME.test(â˜ƒxx, this.level, â˜ƒ)) {
            break;
         }
      }

      return 0;
   }

   private int calculateHeight() {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      int â˜ƒx = this.getDistanceUntilTop(â˜ƒ);
      return â˜ƒx >= 3 && â˜ƒx <= 21 && this.hasTopFrame(â˜ƒ, â˜ƒx) ? â˜ƒx : 0;
   }

   private boolean hasTopFrame(BlockPos.MutableBlockPos var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < this.width; ++â˜ƒ) {
         BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.set(this.bottomLeft).move(Direction.UP, â˜ƒ).move(this.rightDir, â˜ƒ);
         if (!FRAME.test(this.level.getBlockState(â˜ƒx), this.level, â˜ƒx)) {
            return false;
         }
      }

      return true;
   }

   private int getDistanceUntilTop(BlockPos.MutableBlockPos var1) {
      for(int â˜ƒ = 0; â˜ƒ < 21; ++â˜ƒ) {
         â˜ƒ.set(this.bottomLeft).move(Direction.UP, â˜ƒ).move(this.rightDir, -1);
         if (!FRAME.test(this.level.getBlockState(â˜ƒ), this.level, â˜ƒ)) {
            return â˜ƒ;
         }

         â˜ƒ.set(this.bottomLeft).move(Direction.UP, â˜ƒ).move(this.rightDir, this.width);
         if (!FRAME.test(this.level.getBlockState(â˜ƒ), this.level, â˜ƒ)) {
            return â˜ƒ;
         }

         for(int â˜ƒx = 0; â˜ƒx < this.width; ++â˜ƒx) {
            â˜ƒ.set(this.bottomLeft).move(Direction.UP, â˜ƒ).move(this.rightDir, â˜ƒx);
            BlockState â˜ƒxx = this.level.getBlockState(â˜ƒ);
            if (!isEmpty(â˜ƒxx)) {
               return â˜ƒ;
            }

            if (â˜ƒxx.is(Blocks.NETHER_PORTAL)) {
               ++this.numPortalBlocks;
            }
         }
      }

      return 21;
   }

   private static boolean isEmpty(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(BlockTags.FIRE) || â˜ƒ.is(Blocks.NETHER_PORTAL);
   }

   public boolean isValid() {
      return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
   }

   public void createPortalBlocks() {
      BlockState â˜ƒ = Blocks.NETHER_PORTAL.defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
      BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
         .forEach(var2 -> this.level.setBlock(var2, â˜ƒ, 18));
   }

   public boolean isComplete() {
      return this.isValid() && this.numPortalBlocks == this.width * this.height;
   }

   public static Vec3 getRelativePosition(BlockUtil.FoundRectangle var0, Direction.Axis var1, Vec3 var2, EntityDimensions var3) {
      double â˜ƒx = (double)â˜ƒ.axis1Size - (double)â˜ƒ.width;
      double â˜ƒxx = (double)â˜ƒ.axis2Size - (double)â˜ƒ.height;
      BlockPos â˜ƒxxx = â˜ƒ.minCorner;
      double â˜ƒ;
      if (â˜ƒx > 0.0) {
         float â˜ƒxxxx = (float)â˜ƒxxx.get(â˜ƒ) + â˜ƒ.width / 2.0F;
         â˜ƒ = Mth.clamp(Mth.inverseLerp(â˜ƒ.get(â˜ƒ) - (double)â˜ƒxxxx, 0.0, â˜ƒx), 0.0, 1.0);
      } else {
         â˜ƒ = 0.5;
      }

      double â˜ƒ;
      if (â˜ƒxx > 0.0) {
         Direction.Axis â˜ƒx = Direction.Axis.Y;
         â˜ƒ = Mth.clamp(Mth.inverseLerp(â˜ƒ.get(â˜ƒx) - (double)â˜ƒxxx.get(â˜ƒx), 0.0, â˜ƒxx), 0.0, 1.0);
      } else {
         â˜ƒ = 0.0;
      }

      Direction.Axis â˜ƒ = â˜ƒ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
      double â˜ƒx = â˜ƒ.get(â˜ƒ) - ((double)â˜ƒxxx.get(â˜ƒ) + 0.5);
      return new Vec3(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public static PortalInfo createPortalInfo(
      ServerLevel var0, BlockUtil.FoundRectangle var1, Direction.Axis var2, Vec3 var3, EntityDimensions var4, Vec3 var5, float var6, float var7
   ) {
      BlockPos â˜ƒ = â˜ƒ.minCorner;
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      Direction.Axis â˜ƒxx = (Direction.Axis)â˜ƒx.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
      double â˜ƒxxx = (double)â˜ƒ.axis1Size;
      double â˜ƒxxxx = (double)â˜ƒ.axis2Size;
      int â˜ƒxxxxx = â˜ƒ == â˜ƒxx ? 0 : 90;
      Vec3 â˜ƒxxxxxx = â˜ƒ == â˜ƒxx ? â˜ƒ : new Vec3(â˜ƒ.z, â˜ƒ.y, -â˜ƒ.x);
      double â˜ƒxxxxxxx = (double)â˜ƒ.width / 2.0 + (â˜ƒxxx - (double)â˜ƒ.width) * â˜ƒ.x();
      double â˜ƒxxxxxxxx = (â˜ƒxxxx - (double)â˜ƒ.height) * â˜ƒ.y();
      double â˜ƒxxxxxxxxx = 0.5 + â˜ƒ.z();
      boolean â˜ƒxxxxxxxxxx = â˜ƒxx == Direction.Axis.X;
      Vec3 â˜ƒxxxxxxxxxxx = new Vec3(
         (double)â˜ƒ.getX() + (â˜ƒxxxxxxxxxx ? â˜ƒxxxxxxx : â˜ƒxxxxxxxxx),
         (double)â˜ƒ.getY() + â˜ƒxxxxxxxx,
         (double)â˜ƒ.getZ() + (â˜ƒxxxxxxxxxx ? â˜ƒxxxxxxxxx : â˜ƒxxxxxxx)
      );
      return new PortalInfo(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒ + (float)â˜ƒxxxxx, â˜ƒ);
   }
}
