package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StemBlock extends BushBlock implements BonemealableBlock {
   public static final int MAX_AGE = 7;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
   protected static final float AABB_OFFSET = 1.0F;
   protected static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
      Block.box(7.0, 0.0, 7.0, 9.0, 2.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 4.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 12.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
      Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
   };
   private final StemGrownBlock fruit;
   private final Supplier<Item> seedSupplier;

   protected StemBlock(StemGrownBlock var1, Supplier<Item> var2, BlockBehaviour.Properties var3) {
      super(â˜ƒ);
      this.fruit = â˜ƒ;
      this.seedSupplier = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE_BY_AGE[â˜ƒ.getValue(AGE)];
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.is(Blocks.FARMLAND);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getRawBrightness(â˜ƒ, 0) >= 9) {
         float â˜ƒ = CropBlock.getGrowthSpeed(this, â˜ƒ, â˜ƒ);
         if (â˜ƒ.nextInt((int)(25.0F / â˜ƒ) + 1) == 0) {
            int â˜ƒx = â˜ƒ.getValue(AGE);
            if (â˜ƒx < 7) {
               â˜ƒ = â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒx + 1));
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
            } else {
               Direction â˜ƒx = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
               BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
               BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx.below());
               if (â˜ƒ.getBlockState(â˜ƒxx).isAir() && (â˜ƒxxx.is(Blocks.FARMLAND) || â˜ƒxxx.is(BlockTags.DIRT))) {
                  â˜ƒ.setBlockAndUpdate(â˜ƒxx, this.fruit.defaultBlockState());
                  â˜ƒ.setBlockAndUpdate(â˜ƒ, this.fruit.getAttachedStem().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, â˜ƒx));
               }
            }
         }
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack((ItemLike)this.seedSupplier.get());
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return â˜ƒ.getValue(AGE) != 7;
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      int â˜ƒ = Math.min(7, â˜ƒ.getValue(AGE) + Mth.nextInt(â˜ƒ.random, 2, 5));
      BlockState â˜ƒx = â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒ));
      â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 2);
      if (â˜ƒ == 7) {
         â˜ƒx.randomTick(â˜ƒ, â˜ƒ, â˜ƒ.random);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }

   public StemGrownBlock getFruit() {
      return this.fruit;
   }
}
