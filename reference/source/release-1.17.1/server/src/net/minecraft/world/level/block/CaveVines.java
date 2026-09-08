package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CaveVines {
   VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
   BooleanProperty BERRIES = BlockStateProperties.BERRIES;

   static InteractionResult use(BlockState var0, Level var1, BlockPos var2) {
      if (â˜ƒ.getValue(BERRIES)) {
         Block.popResource(â˜ƒ, â˜ƒ, new ItemStack(Items.GLOW_BERRIES, 1));
         float â˜ƒ = Mth.randomBetween(â˜ƒ.random, 0.8F, 1.2F);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, â˜ƒ);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(BERRIES, Boolean.valueOf(false)), 2);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   static boolean hasGlowBerries(BlockState var0) {
      return â˜ƒ.hasProperty(BERRIES) && â˜ƒ.getValue(BERRIES);
   }

   static ToIntFunction<BlockState> emission(int var0) {
      return var1 -> var1.getValue(BlockStateProperties.BERRIES) ? â˜ƒ : 0;
   }
}
