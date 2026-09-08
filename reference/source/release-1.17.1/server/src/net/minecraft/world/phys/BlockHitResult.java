package net.minecraft.world.phys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BlockHitResult extends HitResult {
   private final Direction direction;
   private final BlockPos blockPos;
   private final boolean miss;
   private final boolean inside;

   public static BlockHitResult miss(Vec3 var0, Direction var1, BlockPos var2) {
      return new BlockHitResult(true, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public BlockHitResult(Vec3 var1, Direction var2, BlockPos var3, boolean var4) {
      this(false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private BlockHitResult(boolean var1, Vec3 var2, Direction var3, BlockPos var4, boolean var5) {
      super(â˜ƒ);
      this.miss = â˜ƒ;
      this.direction = â˜ƒ;
      this.blockPos = â˜ƒ;
      this.inside = â˜ƒ;
   }

   public BlockHitResult withDirection(Direction var1) {
      return new BlockHitResult(this.miss, this.location, â˜ƒ, this.blockPos, this.inside);
   }

   public BlockHitResult withPosition(BlockPos var1) {
      return new BlockHitResult(this.miss, this.location, this.direction, â˜ƒ, this.inside);
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public Direction getDirection() {
      return this.direction;
   }

   @Override
   public HitResult.Type getType() {
      return this.miss ? HitResult.Type.MISS : HitResult.Type.BLOCK;
   }

   public boolean isInside() {
      return this.inside;
   }
}
