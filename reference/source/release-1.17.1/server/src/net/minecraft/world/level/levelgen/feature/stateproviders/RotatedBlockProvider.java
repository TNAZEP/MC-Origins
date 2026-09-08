package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RotatedBlockProvider extends BlockStateProvider {
   public static final Codec<RotatedBlockProvider> CODEC = BlockState.CODEC
      .fieldOf("state")
      .xmap(BlockBehaviour.BlockStateBase::getBlock, Block::defaultBlockState)
      .<RotatedBlockProvider>xmap(RotatedBlockProvider::new, var0 -> var0.block)
      .codec();
   private final Block block;

   public RotatedBlockProvider(Block var1) {
      this.block = â˜ƒ;
   }

   @Override
   protected BlockStateProviderType<?> type() {
      return BlockStateProviderType.ROTATED_BLOCK_PROVIDER;
   }

   @Override
   public BlockState getState(Random var1, BlockPos var2) {
      Direction.Axis â˜ƒ = Direction.Axis.getRandom(â˜ƒ);
      return this.block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, â˜ƒ);
   }
}
