package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CropBlock extends BushBlock implements BonemealableBlock {
   public static final int MAX_AGE = 7;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
      Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected CropBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE_BY_AGE[â˜ƒ.getValue(this.getAgeProperty())];
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.is(Blocks.FARMLAND);
   }

   public IntegerProperty getAgeProperty() {
      return AGE;
   }

   public int getMaxAge() {
      return 7;
   }

   protected int getAge(BlockState var1) {
      return â˜ƒ.getValue(this.getAgeProperty());
   }

   public BlockState getStateForAge(int var1) {
      return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(â˜ƒ));
   }

   public boolean isMaxAge(BlockState var1) {
      return â˜ƒ.getValue(this.getAgeProperty()) >= this.getMaxAge();
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return !this.isMaxAge(â˜ƒ);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getRawBrightness(â˜ƒ, 0) >= 9) {
         int â˜ƒ = this.getAge(â˜ƒ);
         if (â˜ƒ < this.getMaxAge()) {
            float â˜ƒx = getGrowthSpeed(this, â˜ƒ, â˜ƒ);
            if (â˜ƒ.nextInt((int)(25.0F / â˜ƒx) + 1) == 0) {
               â˜ƒ.setBlock(â˜ƒ, this.getStateForAge(â˜ƒ + 1), 2);
            }
         }
      }
   }

   public void growCrops(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = this.getAge(â˜ƒ) + this.getBonemealAgeIncrease(â˜ƒ);
      int â˜ƒx = this.getMaxAge();
      if (â˜ƒ > â˜ƒx) {
         â˜ƒ = â˜ƒx;
      }

      â˜ƒ.setBlock(â˜ƒ, this.getStateForAge(â˜ƒ), 2);
   }

   protected int getBonemealAgeIncrease(Level var1) {
      return Mth.nextInt(â˜ƒ.random, 2, 5);
   }

   protected static float getGrowthSpeed(Block var0, BlockGetter var1, BlockPos var2) {
      float â˜ƒ = 1.0F;
      BlockPos â˜ƒx = â˜ƒ.below();

      for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
         for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
            float â˜ƒxxxx = 0.0F;
            BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒx.offset(â˜ƒxx, 0, â˜ƒxxx));
            if (â˜ƒxxxxx.is(Blocks.FARMLAND)) {
               â˜ƒxxxx = 1.0F;
               if (â˜ƒxxxxx.getValue(FarmBlock.MOISTURE) > 0) {
                  â˜ƒxxxx = 3.0F;
               }
            }

            if (â˜ƒxx != 0 || â˜ƒxxx != 0) {
               â˜ƒxxxx /= 4.0F;
            }

            â˜ƒ += â˜ƒxxxx;
         }
      }

      BlockPos â˜ƒxx = â˜ƒ.north();
      BlockPos â˜ƒxxx = â˜ƒ.south();
      BlockPos â˜ƒxxxx = â˜ƒ.west();
      BlockPos â˜ƒxxxxx = â˜ƒ.east();
      boolean â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx).is(â˜ƒ) || â˜ƒ.getBlockState(â˜ƒxxxxx).is(â˜ƒ);
      boolean â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxx).is(â˜ƒ) || â˜ƒ.getBlockState(â˜ƒxxx).is(â˜ƒ);
      if (â˜ƒxxxxxx && â˜ƒxxxxxxx) {
         â˜ƒ /= 2.0F;
      } else {
         boolean â˜ƒxx = â˜ƒ.getBlockState(â˜ƒxxxx.north()).is(â˜ƒ)
            || â˜ƒ.getBlockState(â˜ƒxxxxx.north()).is(â˜ƒ)
            || â˜ƒ.getBlockState(â˜ƒxxxxx.south()).is(â˜ƒ)
            || â˜ƒ.getBlockState(â˜ƒxxxx.south()).is(â˜ƒ);
         if (â˜ƒxx) {
            â˜ƒ /= 2.0F;
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return (â˜ƒ.getRawBrightness(â˜ƒ, 0) >= 8 || â˜ƒ.canSeeSky(â˜ƒ)) && super.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (â˜ƒ instanceof Ravager && â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         â˜ƒ.destroyBlock(â˜ƒ, true, â˜ƒ);
      }

      super.entityInside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected ItemLike getBaseSeedId() {
      return Items.WHEAT_SEEDS;
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(this.getBaseSeedId());
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return !this.isMaxAge(â˜ƒ);
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      this.growCrops(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }
}
