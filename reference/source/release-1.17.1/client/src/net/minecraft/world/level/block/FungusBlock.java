package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FungusBlock extends BushBlock implements BonemealableBlock {
   protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
   private static final double BONEMEAL_SUCCESS_PROBABILITY = 0.4;
   private final Supplier<ConfiguredFeature<HugeFungusConfiguration, ?>> feature;

   protected FungusBlock(BlockBehaviour.Properties var1, Supplier<ConfiguredFeature<HugeFungusConfiguration, ?>> var2) {
      super(â˜ƒ);
      this.feature = â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.is(BlockTags.NYLIUM) || â˜ƒ.is(Blocks.MYCELIUM) || â˜ƒ.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      Block â˜ƒ = ((HugeFungusConfiguration)((ConfiguredFeature)this.feature.get()).config).validBaseState.getBlock();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.below());
      return â˜ƒx.is(â˜ƒ);
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return (double)â˜ƒ.nextFloat() < 0.4;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      ((ConfiguredFeature)this.feature.get()).place(â˜ƒ, â˜ƒ.getChunkSource().getGenerator(), â˜ƒ, â˜ƒ);
   }
}
