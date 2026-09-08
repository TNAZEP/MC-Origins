package net.minecraft.world.entity.vehicle;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DismountHelper {
   public static int[][] offsetsForDirection(Direction var0) {
      Direction â˜ƒ = â˜ƒ.getClockWise();
      Direction â˜ƒx = â˜ƒ.getOpposite();
      Direction â˜ƒxx = â˜ƒ.getOpposite();
      return new int[][]{
         {â˜ƒ.getStepX(), â˜ƒ.getStepZ()},
         {â˜ƒx.getStepX(), â˜ƒx.getStepZ()},
         {â˜ƒxx.getStepX() + â˜ƒ.getStepX(), â˜ƒxx.getStepZ() + â˜ƒ.getStepZ()},
         {â˜ƒxx.getStepX() + â˜ƒx.getStepX(), â˜ƒxx.getStepZ() + â˜ƒx.getStepZ()},
         {â˜ƒ.getStepX() + â˜ƒ.getStepX(), â˜ƒ.getStepZ() + â˜ƒ.getStepZ()},
         {â˜ƒ.getStepX() + â˜ƒx.getStepX(), â˜ƒ.getStepZ() + â˜ƒx.getStepZ()},
         {â˜ƒxx.getStepX(), â˜ƒxx.getStepZ()},
         {â˜ƒ.getStepX(), â˜ƒ.getStepZ()}
      };
   }

   public static boolean isBlockFloorValid(double var0) {
      return !Double.isInfinite(â˜ƒ) && â˜ƒ < 1.0;
   }

   public static boolean canDismountTo(CollisionGetter var0, LivingEntity var1, AABB var2) {
      return â˜ƒ.getBlockCollisions(â˜ƒ, â˜ƒ).allMatch(VoxelShape::isEmpty);
   }

   public static boolean canDismountTo(CollisionGetter var0, Vec3 var1, LivingEntity var2, Pose var3) {
      return canDismountTo(â˜ƒ, â˜ƒ, â˜ƒ.getLocalBoundsForPose(â˜ƒ).move(â˜ƒ));
   }

   public static VoxelShape nonClimbableShape(BlockGetter var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return !â˜ƒ.is(BlockTags.CLIMBABLE) && (!(â˜ƒ.getBlock() instanceof TrapDoorBlock) || !â˜ƒ.getValue(TrapDoorBlock.OPEN))
         ? â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ)
         : Shapes.empty();
   }

   public static double findCeilingFrom(BlockPos var0, int var1, Function<BlockPos, VoxelShape> var2) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      int â˜ƒx = 0;

      while(â˜ƒx < â˜ƒ) {
         VoxelShape â˜ƒxx = (VoxelShape)â˜ƒ.apply(â˜ƒ);
         if (!â˜ƒxx.isEmpty()) {
            return (double)(â˜ƒ.getY() + â˜ƒx) + â˜ƒxx.min(Direction.Axis.Y);
         }

         ++â˜ƒx;
         â˜ƒ.move(Direction.UP);
      }

      return Double.POSITIVE_INFINITY;
   }

   @Nullable
   public static Vec3 findSafeDismountLocation(EntityType<?> var0, CollisionGetter var1, BlockPos var2, boolean var3) {
      if (â˜ƒ && â˜ƒ.isBlockDangerous(â˜ƒ.getBlockState(â˜ƒ))) {
         return null;
      } else {
         double â˜ƒ = â˜ƒ.getBlockFloorHeight(nonClimbableShape(â˜ƒ, â˜ƒ), () -> nonClimbableShape(â˜ƒ, â˜ƒ.below()));
         if (!isBlockFloorValid(â˜ƒ)) {
            return null;
         } else if (â˜ƒ && â˜ƒ <= 0.0 && â˜ƒ.isBlockDangerous(â˜ƒ.getBlockState(â˜ƒ.below()))) {
            return null;
         } else {
            Vec3 â˜ƒ = Vec3.upFromBottomCenterOf(â˜ƒ, â˜ƒ);
            return â˜ƒ.getBlockCollisions(null, â˜ƒ.getDimensions().makeBoundingBox(â˜ƒ)).allMatch(VoxelShape::isEmpty) ? â˜ƒ : null;
         }
      }
   }
}
