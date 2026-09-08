package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MushroomBlock extends BushBlock implements BonemealableBlock {
   protected static final float AABB_OFFSET = 3.0F;
   protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
   private final Supplier<ConfiguredFeature<?, ?>> featureSupplier;

   public MushroomBlock(BlockBehaviour.Properties var1, Supplier<ConfiguredFeature<?, ?>> var2) {
      super(â˜ƒ);
      this.featureSupplier = â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(25) == 0) {
         int â˜ƒ = 5;
         int â˜ƒx = 4;

         for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒ.offset(-4, -1, -4), â˜ƒ.offset(4, 1, 4))) {
            if (â˜ƒ.getBlockState(â˜ƒxx).is(this)) {
               if (--â˜ƒ <= 0) {
                  return;
               }
            }
         }

         BlockPos â˜ƒxx = â˜ƒ.offset(â˜ƒ.nextInt(3) - 1, â˜ƒ.nextInt(2) - â˜ƒ.nextInt(2), â˜ƒ.nextInt(3) - 1);

         for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
            if (â˜ƒ.isEmptyBlock(â˜ƒxx) && â˜ƒ.canSurvive(â˜ƒ, â˜ƒxx)) {
               â˜ƒ = â˜ƒxx;
            }

            â˜ƒxx = â˜ƒ.offset(â˜ƒ.nextInt(3) - 1, â˜ƒ.nextInt(2) - â˜ƒ.nextInt(2), â˜ƒ.nextInt(3) - 1);
         }

         if (â˜ƒ.isEmptyBlock(â˜ƒxx) && â˜ƒ.canSurvive(â˜ƒ, â˜ƒxx)) {
            â˜ƒ.setBlock(â˜ƒxx, â˜ƒ, 2);
         }
      }
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.isSolidRender(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
         return true;
      } else {
         return â˜ƒ.getRawBrightness(â˜ƒ, 0) < 13 && this.mayPlaceOn(â˜ƒx, â˜ƒ, â˜ƒ);
      }
   }

   public boolean growMushroom(ServerLevel var1, BlockPos var2, BlockState var3, Random var4) {
      â˜ƒ.removeBlock(â˜ƒ, false);
      if (((ConfiguredFeature)this.featureSupplier.get()).place(â˜ƒ, â˜ƒ.getChunkSource().getGenerator(), â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
         return false;
      }
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return (double)â˜ƒ.nextFloat() < 0.4;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      this.growMushroom(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
