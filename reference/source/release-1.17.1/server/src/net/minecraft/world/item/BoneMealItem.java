package net.minecraft.world.item;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BoneMealItem extends Item {
   public static final int GRASS_SPREAD_WIDTH = 3;
   public static final int GRASS_SPREAD_HEIGHT = 1;
   public static final int GRASS_COUNT_MULTIPLIER = 3;

   public BoneMealItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockPos â˜ƒxx = â˜ƒx.relative(â˜ƒ.getClickedFace());
      if (growCrop(â˜ƒ.getItemInHand(), â˜ƒ, â˜ƒx)) {
         if (!â˜ƒ.isClientSide) {
            â˜ƒ.levelEvent(1505, â˜ƒx, 0);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒx);
         boolean â˜ƒx = â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ.getClickedFace());
         if (â˜ƒx && growWaterPlant(â˜ƒ.getItemInHand(), â˜ƒ, â˜ƒxx, â˜ƒ.getClickedFace())) {
            if (!â˜ƒ.isClientSide) {
               â˜ƒ.levelEvent(1505, â˜ƒxx, 0);
            }

            return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   public static boolean growCrop(ItemStack var0, Level var1, BlockPos var2) {
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.getBlock() instanceof BonemealableBlock â˜ƒ && â˜ƒ.isValidBonemealTarget(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ.isClientSide)) {
         if (â˜ƒ instanceof ServerLevel) {
            if (â˜ƒ.isBonemealSuccess(â˜ƒ, â˜ƒ.random, â˜ƒ, â˜ƒx)) {
               â˜ƒ.performBonemeal((ServerLevel)â˜ƒ, â˜ƒ.random, â˜ƒ, â˜ƒx);
            }

            â˜ƒ.shrink(1);
         }

         return true;
      }

      return false;
   }

   public static boolean growWaterPlant(ItemStack var0, Level var1, BlockPos var2, @Nullable Direction var3) {
      if (â˜ƒ.getBlockState(â˜ƒ).is(Blocks.WATER) && â˜ƒ.getFluidState(â˜ƒ).getAmount() == 8) {
         if (!(â˜ƒ instanceof ServerLevel)) {
            return true;
         } else {
            Random â˜ƒ = â˜ƒ.getRandom();

            label80:
            for(int â˜ƒx = 0; â˜ƒx < 128; ++â˜ƒx) {
               BlockPos â˜ƒxx = â˜ƒ;
               BlockState â˜ƒxxx = Blocks.SEAGRASS.defaultBlockState();

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒx / 16; ++â˜ƒxxxx) {
                  â˜ƒxx = â˜ƒxx.offset(â˜ƒ.nextInt(3) - 1, (â˜ƒ.nextInt(3) - 1) * â˜ƒ.nextInt(3) / 2, â˜ƒ.nextInt(3) - 1);
                  if (â˜ƒ.getBlockState(â˜ƒxx).isCollisionShapeFullBlock(â˜ƒ, â˜ƒxx)) {
                     continue label80;
                  }
               }

               Optional<ResourceKey<Biome>> â˜ƒxxxx = â˜ƒ.getBiomeName(â˜ƒxx);
               if (Objects.equals(â˜ƒxxxx, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(â˜ƒxxxx, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
                  if (â˜ƒx == 0 && â˜ƒ != null && â˜ƒ.getAxis().isHorizontal()) {
                     â˜ƒxxx = BlockTags.WALL_CORALS.getRandomElement(â˜ƒ.random).defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, â˜ƒ);
                  } else if (â˜ƒ.nextInt(4) == 0) {
                     â˜ƒxxx = BlockTags.UNDERWATER_BONEMEALS.getRandomElement(â˜ƒ).defaultBlockState();
                  }
               }

               if (â˜ƒxxx.is(BlockTags.WALL_CORALS)) {
                  for(int â˜ƒxxxx = 0; !â˜ƒxxx.canSurvive(â˜ƒ, â˜ƒxx) && â˜ƒxxxx < 4; ++â˜ƒxxxx) {
                     â˜ƒxxx = â˜ƒxxx.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ));
                  }
               }

               if (â˜ƒxxx.canSurvive(â˜ƒ, â˜ƒxx)) {
                  BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxx);
                  if (â˜ƒxxxx.is(Blocks.WATER) && â˜ƒ.getFluidState(â˜ƒxx).getAmount() == 8) {
                     â˜ƒ.setBlock(â˜ƒxx, â˜ƒxxx, 3);
                  } else if (â˜ƒxxxx.is(Blocks.SEAGRASS) && â˜ƒ.nextInt(10) == 0) {
                     ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxxx);
                  }
               }
            }

            â˜ƒ.shrink(1);
            return true;
         }
      } else {
         return false;
      }
   }

   public static void addGrowthParticles(LevelAccessor var0, BlockPos var1, int var2) {
      if (â˜ƒ == 0) {
         â˜ƒ = 15;
      }

      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!â˜ƒ.isAir()) {
         double â˜ƒxx = 0.5;
         double â˜ƒx;
         if (â˜ƒ.is(Blocks.WATER)) {
            â˜ƒ *= 3;
            â˜ƒx = 1.0;
            â˜ƒxx = 3.0;
         } else if (â˜ƒ.isSolidRender(â˜ƒ, â˜ƒ)) {
            â˜ƒ = â˜ƒ.above();
            â˜ƒ *= 3;
            â˜ƒxx = 3.0;
            â˜ƒx = 1.0;
         } else {
            â˜ƒx = â˜ƒ.getShape(â˜ƒ, â˜ƒ).max(Direction.Axis.Y);
         }

         â˜ƒ.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, 0.0, 0.0, 0.0);
         Random â˜ƒx = â˜ƒ.getRandom();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            double â˜ƒxxx = â˜ƒx.nextGaussian() * 0.02;
            double â˜ƒxxxx = â˜ƒx.nextGaussian() * 0.02;
            double â˜ƒxxxxx = â˜ƒx.nextGaussian() * 0.02;
            double â˜ƒxxxxxx = 0.5 - â˜ƒxx;
            double â˜ƒxxxxxxx = (double)â˜ƒ.getX() + â˜ƒxxxxxx + â˜ƒx.nextDouble() * â˜ƒxx * 2.0;
            double â˜ƒxxxxxxxx = (double)â˜ƒ.getY() + â˜ƒx.nextDouble() * â˜ƒx;
            double â˜ƒxxxxxxxxx = (double)â˜ƒ.getZ() + â˜ƒxxxxxx + â˜ƒx.nextDouble() * â˜ƒxx * 2.0;
            if (!â˜ƒ.getBlockState(new BlockPos(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).below()).isAir()) {
               â˜ƒ.addParticle(ParticleTypes.HAPPY_VILLAGER, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            }
         }
      }
   }
}
