package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface BlockGetter extends LevelHeightAccessor {
   @Nullable
   BlockEntity getBlockEntity(BlockPos var1);

   default <T extends BlockEntity> Optional<T> getBlockEntity(BlockPos var1, BlockEntityType<T> var2) {
      BlockEntity â˜ƒ = this.getBlockEntity(â˜ƒ);
      return â˜ƒ != null && â˜ƒ.getType() == â˜ƒ ? Optional.of(â˜ƒ) : Optional.empty();
   }

   BlockState getBlockState(BlockPos var1);

   FluidState getFluidState(BlockPos var1);

   default int getLightEmission(BlockPos var1) {
      return this.getBlockState(â˜ƒ).getLightEmission();
   }

   default int getMaxLightLevel() {
      return 15;
   }

   default Stream<BlockState> getBlockStates(AABB var1) {
      return BlockPos.betweenClosedStream(â˜ƒ).map(this::getBlockState);
   }

   default BlockHitResult isBlockInLine(ClipBlockStateContext var1) {
      return traverseBlocks(
         â˜ƒ.getFrom(),
         â˜ƒ.getTo(),
         â˜ƒ,
         (var1x, var2) -> {
            BlockState â˜ƒ = this.getBlockState(var2);
            Vec3 â˜ƒx = var1x.getFrom().subtract(var1x.getTo());
            return var1x.isTargetBlock().test(â˜ƒ)
               ? new BlockHitResult(var1x.getTo(), Direction.getNearest(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z), new BlockPos(var1x.getTo()), false)
               : null;
         },
         var0 -> {
            Vec3 â˜ƒ = var0.getFrom().subtract(var0.getTo());
            return BlockHitResult.miss(var0.getTo(), Direction.getNearest(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z), new BlockPos(var0.getTo()));
         }
      );
   }

   default BlockHitResult clip(ClipContext var1) {
      return traverseBlocks(â˜ƒ.getFrom(), â˜ƒ.getTo(), â˜ƒ, (var1x, var2) -> {
         BlockState â˜ƒ = this.getBlockState(var2);
         FluidState â˜ƒx = this.getFluidState(var2);
         Vec3 â˜ƒxx = var1x.getFrom();
         Vec3 â˜ƒxxx = var1x.getTo();
         VoxelShape â˜ƒxxxx = var1x.getBlockShape(â˜ƒ, this, var2);
         BlockHitResult â˜ƒxxxxx = this.clipWithInteractionOverride(â˜ƒxx, â˜ƒxxx, var2, â˜ƒxxxx, â˜ƒ);
         VoxelShape â˜ƒxxxxxx = var1x.getFluidShape(â˜ƒx, this, var2);
         BlockHitResult â˜ƒxxxxxxx = â˜ƒxxxxxx.clip(â˜ƒxx, â˜ƒxxx, var2);
         double â˜ƒxxxxxxxx = â˜ƒxxxxx == null ? Double.MAX_VALUE : var1x.getFrom().distanceToSqr(â˜ƒxxxxx.getLocation());
         double â˜ƒxxxxxxxxx = â˜ƒxxxxxxx == null ? Double.MAX_VALUE : var1x.getFrom().distanceToSqr(â˜ƒxxxxxxx.getLocation());
         return â˜ƒxxxxxxxx <= â˜ƒxxxxxxxxx ? â˜ƒxxxxx : â˜ƒxxxxxxx;
      }, var0 -> {
         Vec3 â˜ƒ = var0.getFrom().subtract(var0.getTo());
         return BlockHitResult.miss(var0.getTo(), Direction.getNearest(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z), new BlockPos(var0.getTo()));
      });
   }

   @Nullable
   default BlockHitResult clipWithInteractionOverride(Vec3 var1, Vec3 var2, BlockPos var3, VoxelShape var4, BlockState var5) {
      BlockHitResult â˜ƒ = â˜ƒ.clip(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         BlockHitResult â˜ƒx = â˜ƒ.getInteractionShape(this, â˜ƒ).clip(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒx != null && â˜ƒx.getLocation().subtract(â˜ƒ).lengthSqr() < â˜ƒ.getLocation().subtract(â˜ƒ).lengthSqr()) {
            return â˜ƒ.withDirection(â˜ƒx.getDirection());
         }
      }

      return â˜ƒ;
   }

   default double getBlockFloorHeight(VoxelShape var1, Supplier<VoxelShape> var2) {
      if (!â˜ƒ.isEmpty()) {
         return â˜ƒ.max(Direction.Axis.Y);
      } else {
         double â˜ƒ = ((VoxelShape)â˜ƒ.get()).max(Direction.Axis.Y);
         return â˜ƒ >= 1.0 ? â˜ƒ - 1.0 : Double.NEGATIVE_INFINITY;
      }
   }

   default double getBlockFloorHeight(BlockPos var1) {
      return this.getBlockFloorHeight(this.getBlockState(â˜ƒ).getCollisionShape(this, â˜ƒ), () -> {
         BlockPos â˜ƒ = â˜ƒ.below();
         return this.getBlockState(â˜ƒ).getCollisionShape(this, â˜ƒ);
      });
   }

   static <T, C> T traverseBlocks(Vec3 var0, Vec3 var1, C var2, BiFunction<C, BlockPos, T> var3, Function<C, T> var4) {
      if (â˜ƒ.equals(â˜ƒ)) {
         return (T)â˜ƒ.apply(â˜ƒ);
      } else {
         double â˜ƒ = Mth.lerp(-1.0E-7, â˜ƒ.x, â˜ƒ.x);
         double â˜ƒx = Mth.lerp(-1.0E-7, â˜ƒ.y, â˜ƒ.y);
         double â˜ƒxx = Mth.lerp(-1.0E-7, â˜ƒ.z, â˜ƒ.z);
         double â˜ƒxxx = Mth.lerp(-1.0E-7, â˜ƒ.x, â˜ƒ.x);
         double â˜ƒxxxx = Mth.lerp(-1.0E-7, â˜ƒ.y, â˜ƒ.y);
         double â˜ƒxxxxx = Mth.lerp(-1.0E-7, â˜ƒ.z, â˜ƒ.z);
         int â˜ƒxxxxxx = Mth.floor(â˜ƒxxx);
         int â˜ƒxxxxxxx = Mth.floor(â˜ƒxxxx);
         int â˜ƒxxxxxxxx = Mth.floor(â˜ƒxxxxx);
         BlockPos.MutableBlockPos â˜ƒxxxxxxxxx = new BlockPos.MutableBlockPos(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
         T â˜ƒxxxxxxxxxx = (T)â˜ƒ.apply(â˜ƒ, â˜ƒxxxxxxxxx);
         if (â˜ƒxxxxxxxxxx != null) {
            return â˜ƒxxxxxxxxxx;
         } else {
            double â˜ƒ = â˜ƒ - â˜ƒxxx;
            double â˜ƒx = â˜ƒx - â˜ƒxxxx;
            double â˜ƒxx = â˜ƒxx - â˜ƒxxxxx;
            int â˜ƒxxx = Mth.sign(â˜ƒ);
            int â˜ƒxxxx = Mth.sign(â˜ƒx);
            int â˜ƒxxxxx = Mth.sign(â˜ƒxx);
            double â˜ƒxxxxxx = â˜ƒxxx == 0 ? Double.MAX_VALUE : (double)â˜ƒxxx / â˜ƒ;
            double â˜ƒxxxxxxx = â˜ƒxxxx == 0 ? Double.MAX_VALUE : (double)â˜ƒxxxx / â˜ƒx;
            double â˜ƒxxxxxxxx = â˜ƒxxxxx == 0 ? Double.MAX_VALUE : (double)â˜ƒxxxxx / â˜ƒxx;
            double â˜ƒxxxxxxxxx = â˜ƒxxxxxx * (â˜ƒxxx > 0 ? 1.0 - Mth.frac(â˜ƒxxx) : Mth.frac(â˜ƒxxx));
            double â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx * (â˜ƒxxxx > 0 ? 1.0 - Mth.frac(â˜ƒxxxx) : Mth.frac(â˜ƒxxxx));
            double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx * (â˜ƒxxxxx > 0 ? 1.0 - Mth.frac(â˜ƒxxxxx) : Mth.frac(â˜ƒxxxxx));

            while(â˜ƒxxxxxxxxx <= 1.0 || â˜ƒxxxxxxxxxx <= 1.0 || â˜ƒxxxxxxxxxxx <= 1.0) {
               if (â˜ƒxxxxxxxxx < â˜ƒxxxxxxxxxx) {
                  if (â˜ƒxxxxxxxxx < â˜ƒxxxxxxxxxxx) {
                     â˜ƒxxxxxx += â˜ƒxxx;
                     â˜ƒxxxxxxxxx += â˜ƒxxxxxx;
                  } else {
                     â˜ƒxxxxxxxx += â˜ƒxxxxx;
                     â˜ƒxxxxxxxxxxx += â˜ƒxxxxxxxx;
                  }
               } else if (â˜ƒxxxxxxxxxx < â˜ƒxxxxxxxxxxx) {
                  â˜ƒxxxxxxx += â˜ƒxxxx;
                  â˜ƒxxxxxxxxxx += â˜ƒxxxxxxx;
               } else {
                  â˜ƒxxxxxxxx += â˜ƒxxxxx;
                  â˜ƒxxxxxxxxxxx += â˜ƒxxxxxxxx;
               }

               T â˜ƒxxxxxxxxxxxx = (T)â˜ƒ.apply(â˜ƒ, â˜ƒxxxxxxxxx.set(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx));
               if (â˜ƒxxxxxxxxxxxx != null) {
                  return â˜ƒxxxxxxxxxxxx;
               }
            }

            return (T)â˜ƒ.apply(â˜ƒ);
         }
      }
   }
}
