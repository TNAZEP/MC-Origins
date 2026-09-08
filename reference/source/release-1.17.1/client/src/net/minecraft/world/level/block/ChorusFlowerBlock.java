package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ChorusFlowerBlock extends Block {
   public static final int DEAD_AGE = 5;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
   private final ChorusPlantBlock plant;

   protected ChorusFlowerBlock(ChorusPlantBlock var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.plant = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getValue(AGE) < 5;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      if (â˜ƒ.isEmptyBlock(â˜ƒ) && â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight()) {
         int â˜ƒx = â˜ƒ.getValue(AGE);
         if (â˜ƒx < 5) {
            boolean â˜ƒxx = false;
            boolean â˜ƒxxx = false;
            BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒ.below());
            if (â˜ƒxxxx.is(Blocks.END_STONE)) {
               â˜ƒxx = true;
            } else if (â˜ƒxxxx.is(this.plant)) {
               int â˜ƒxx = 1;

               for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
                  BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒ.below(â˜ƒxx + 1));
                  if (!â˜ƒxxxx.is(this.plant)) {
                     if (â˜ƒxxxx.is(Blocks.END_STONE)) {
                        â˜ƒxxx = true;
                     }
                     break;
                  }

                  ++â˜ƒxx;
               }

               if (â˜ƒxx < 2 || â˜ƒxx <= â˜ƒ.nextInt(â˜ƒxxx ? 5 : 4)) {
                  â˜ƒxx = true;
               }
            } else if (â˜ƒxxxx.isAir()) {
               â˜ƒxx = true;
            }

            if (â˜ƒxx && allNeighborsEmpty(â˜ƒ, â˜ƒ, null) && â˜ƒ.isEmptyBlock(â˜ƒ.above(2))) {
               â˜ƒ.setBlock(â˜ƒ, this.plant.getStateForPlacement(â˜ƒ, â˜ƒ), 2);
               this.placeGrownFlower(â˜ƒ, â˜ƒ, â˜ƒx);
            } else if (â˜ƒx < 4) {
               int â˜ƒxx = â˜ƒ.nextInt(4);
               if (â˜ƒxxx) {
                  ++â˜ƒxx;
               }

               boolean â˜ƒxx = false;

               for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
                  Direction â˜ƒxxxx = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
                  BlockPos â˜ƒxxxxx = â˜ƒ.relative(â˜ƒxxxx);
                  if (â˜ƒ.isEmptyBlock(â˜ƒxxxxx) && â˜ƒ.isEmptyBlock(â˜ƒxxxxx.below()) && allNeighborsEmpty(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxx.getOpposite())) {
                     this.placeGrownFlower(â˜ƒ, â˜ƒxxxxx, â˜ƒx + 1);
                     â˜ƒxx = true;
                  }
               }

               if (â˜ƒxx) {
                  â˜ƒ.setBlock(â˜ƒ, this.plant.getStateForPlacement(â˜ƒ, â˜ƒ), 2);
               } else {
                  this.placeDeadFlower(â˜ƒ, â˜ƒ);
               }
            } else {
               this.placeDeadFlower(â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   private void placeGrownFlower(Level var1, BlockPos var2, int var3) {
      â˜ƒ.setBlock(â˜ƒ, this.defaultBlockState().setValue(AGE, Integer.valueOf(â˜ƒ)), 2);
      â˜ƒ.levelEvent(1033, â˜ƒ, 0);
   }

   private void placeDeadFlower(Level var1, BlockPos var2) {
      â˜ƒ.setBlock(â˜ƒ, this.defaultBlockState().setValue(AGE, Integer.valueOf(5)), 2);
      â˜ƒ.levelEvent(1034, â˜ƒ, 0);
   }

   private static boolean allNeighborsEmpty(LevelReader var0, BlockPos var1, @Nullable Direction var2) {
      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         if (â˜ƒ != â˜ƒ && !â˜ƒ.isEmptyBlock(â˜ƒ.relative(â˜ƒ))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ != Direction.UP && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      if (!â˜ƒ.is(this.plant) && !â˜ƒ.is(Blocks.END_STONE)) {
         if (!â˜ƒ.isAir()) {
            return false;
         } else {
            boolean â˜ƒx = false;

            for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
               BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒxx));
               if (â˜ƒxxx.is(this.plant)) {
                  if (â˜ƒx) {
                     return false;
                  }

                  â˜ƒx = true;
               } else if (!â˜ƒxxx.isAir()) {
                  return false;
               }
            }

            return â˜ƒx;
         }
      } else {
         return true;
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }

   public static void generatePlant(LevelAccessor var0, BlockPos var1, Random var2, int var3) {
      â˜ƒ.setBlock(â˜ƒ, ((ChorusPlantBlock)Blocks.CHORUS_PLANT).getStateForPlacement(â˜ƒ, â˜ƒ), 2);
      growTreeRecursive(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0);
   }

   private static void growTreeRecursive(LevelAccessor var0, BlockPos var1, Random var2, BlockPos var3, int var4, int var5) {
      ChorusPlantBlock â˜ƒ = (ChorusPlantBlock)Blocks.CHORUS_PLANT;
      int â˜ƒx = â˜ƒ.nextInt(4) + 1;
      if (â˜ƒ == 0) {
         ++â˜ƒx;
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒx; ++â˜ƒ) {
         BlockPos â˜ƒx = â˜ƒ.above(â˜ƒ + 1);
         if (!allNeighborsEmpty(â˜ƒ, â˜ƒx, null)) {
            return;
         }

         â˜ƒ.setBlock(â˜ƒx, â˜ƒ.getStateForPlacement(â˜ƒ, â˜ƒx), 2);
         â˜ƒ.setBlock(â˜ƒx.below(), â˜ƒ.getStateForPlacement(â˜ƒ, â˜ƒx.below()), 2);
      }

      boolean â˜ƒ = false;
      if (â˜ƒ < 4) {
         int â˜ƒx = â˜ƒ.nextInt(4);
         if (â˜ƒ == 0) {
            ++â˜ƒx;
         }

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒx; ++â˜ƒx) {
            Direction â˜ƒxx = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
            BlockPos â˜ƒxxx = â˜ƒ.above(â˜ƒx).relative(â˜ƒxx);
            if (Math.abs(â˜ƒxxx.getX() - â˜ƒ.getX()) < â˜ƒ
               && Math.abs(â˜ƒxxx.getZ() - â˜ƒ.getZ()) < â˜ƒ
               && â˜ƒ.isEmptyBlock(â˜ƒxxx)
               && â˜ƒ.isEmptyBlock(â˜ƒxxx.below())
               && allNeighborsEmpty(â˜ƒ, â˜ƒxxx, â˜ƒxx.getOpposite())) {
               â˜ƒ = true;
               â˜ƒ.setBlock(â˜ƒxxx, â˜ƒ.getStateForPlacement(â˜ƒ, â˜ƒxxx), 2);
               â˜ƒ.setBlock(â˜ƒxxx.relative(â˜ƒxx.getOpposite()), â˜ƒ.getStateForPlacement(â˜ƒ, â˜ƒxxx.relative(â˜ƒxx.getOpposite())), 2);
               growTreeRecursive(â˜ƒ, â˜ƒxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1);
            }
         }
      }

      if (!â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ.above(â˜ƒx), Blocks.CHORUS_FLOWER.defaultBlockState().setValue(AGE, Integer.valueOf(5)), 2);
      }
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      if (!â˜ƒ.isClientSide && â˜ƒ.mayInteract(â˜ƒ, â˜ƒ) && â˜ƒ.getType().is(EntityTypeTags.IMPACT_PROJECTILES)) {
         â˜ƒ.destroyBlock(â˜ƒ, true, â˜ƒ);
      }
   }
}
