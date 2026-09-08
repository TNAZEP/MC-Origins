package net.minecraft.world.item.context;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlockPlaceContext extends UseOnContext {
   private final BlockPos relativePos;
   protected boolean replaceClicked = true;

   public BlockPlaceContext(Player var1, InteractionHand var2, ItemStack var3, BlockHitResult var4) {
      this(â˜ƒ.level, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public BlockPlaceContext(UseOnContext var1) {
      this(â˜ƒ.getLevel(), â˜ƒ.getPlayer(), â˜ƒ.getHand(), â˜ƒ.getItemInHand(), â˜ƒ.getHitResult());
   }

   protected BlockPlaceContext(Level var1, @Nullable Player var2, InteractionHand var3, ItemStack var4, BlockHitResult var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.relativePos = â˜ƒ.getBlockPos().relative(â˜ƒ.getDirection());
      this.replaceClicked = â˜ƒ.getBlockState(â˜ƒ.getBlockPos()).canBeReplaced(this);
   }

   public static BlockPlaceContext at(BlockPlaceContext var0, BlockPos var1, Direction var2) {
      return new BlockPlaceContext(
         â˜ƒ.getLevel(),
         â˜ƒ.getPlayer(),
         â˜ƒ.getHand(),
         â˜ƒ.getItemInHand(),
         new BlockHitResult(
            new Vec3(
               (double)â˜ƒ.getX() + 0.5 + (double)â˜ƒ.getStepX() * 0.5,
               (double)â˜ƒ.getY() + 0.5 + (double)â˜ƒ.getStepY() * 0.5,
               (double)â˜ƒ.getZ() + 0.5 + (double)â˜ƒ.getStepZ() * 0.5
            ),
            â˜ƒ,
            â˜ƒ,
            false
         )
      );
   }

   @Override
   public BlockPos getClickedPos() {
      return this.replaceClicked ? super.getClickedPos() : this.relativePos;
   }

   public boolean canPlace() {
      return this.replaceClicked || this.getLevel().getBlockState(this.getClickedPos()).canBeReplaced(this);
   }

   public boolean replacingClickedOnBlock() {
      return this.replaceClicked;
   }

   public Direction getNearestLookingDirection() {
      return Direction.orderedByNearest(this.getPlayer())[0];
   }

   public Direction getNearestLookingVerticalDirection() {
      return Direction.getFacingAxis(this.getPlayer(), Direction.Axis.Y);
   }

   public Direction[] getNearestLookingDirections() {
      Direction[] â˜ƒ = Direction.orderedByNearest(this.getPlayer());
      if (this.replaceClicked) {
         return â˜ƒ;
      } else {
         Direction â˜ƒ = this.getClickedFace();
         int â˜ƒx = 0;

         while(â˜ƒx < â˜ƒ.length && â˜ƒ[â˜ƒx] != â˜ƒ.getOpposite()) {
            ++â˜ƒx;
         }

         if (â˜ƒx > 0) {
            System.arraycopy(â˜ƒ, 0, â˜ƒ, 1, â˜ƒx);
            â˜ƒ[0] = â˜ƒ.getOpposite();
         }

         return â˜ƒ;
      }
   }
}
