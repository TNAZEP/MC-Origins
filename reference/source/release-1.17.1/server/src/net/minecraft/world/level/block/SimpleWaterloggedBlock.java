package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface SimpleWaterloggedBlock extends BucketPickup, LiquidBlockContainer {
   @Override
   default boolean canPlaceLiquid(BlockGetter var1, BlockPos var2, BlockState var3, Fluid var4) {
      return !â˜ƒ.getValue(BlockStateProperties.WATERLOGGED) && â˜ƒ == Fluids.WATER;
   }

   @Override
   default boolean placeLiquid(LevelAccessor var1, BlockPos var2, BlockState var3, FluidState var4) {
      if (!â˜ƒ.getValue(BlockStateProperties.WATERLOGGED) && â˜ƒ.getType() == Fluids.WATER) {
         if (!â˜ƒ.isClientSide()) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 3);
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getType(), â˜ƒ.getType().getTickDelay(â˜ƒ));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   default ItemStack pickupBlock(LevelAccessor var1, BlockPos var2, BlockState var3) {
      if (â˜ƒ.getValue(BlockStateProperties.WATERLOGGED)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)), 3);
         if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
            â˜ƒ.destroyBlock(â˜ƒ, true);
         }

         return new ItemStack(Items.WATER_BUCKET);
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   default Optional<SoundEvent> getPickupSound() {
      return Fluids.WATER.getPickupSound();
   }
}
