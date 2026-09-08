package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TurtleEggBlock extends Block {
   public static final int MAX_HATCH_LEVEL = 2;
   public static final int MIN_EGGS = 1;
   public static final int MAX_EGGS = 4;
   private static final VoxelShape ONE_EGG_AABB = Block.box(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
   private static final VoxelShape MULTIPLE_EGGS_AABB = Block.box(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
   public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
   public static final IntegerProperty EGGS = BlockStateProperties.EGGS;

   public TurtleEggBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(EGGS, Integer.valueOf(1)));
   }

   @Override
   public void stepOn(Level var1, BlockPos var2, BlockState var3, Entity var4) {
      this.destroyEgg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 100);
      super.stepOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      if (!(â˜ƒ instanceof Zombie)) {
         this.destroyEgg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 3);
      }

      super.fallOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void destroyEgg(Level var1, BlockState var2, BlockPos var3, Entity var4, int var5) {
      if (this.canDestroyEgg(â˜ƒ, â˜ƒ)) {
         if (!â˜ƒ.isClientSide && â˜ƒ.random.nextInt(â˜ƒ) == 0 && â˜ƒ.is(Blocks.TURTLE_EGG)) {
            this.decreaseEggs(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private void decreaseEggs(Level var1, BlockPos var2, BlockState var3) {
      â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + â˜ƒ.random.nextFloat() * 0.2F);
      int â˜ƒ = â˜ƒ.getValue(EGGS);
      if (â˜ƒ <= 1) {
         â˜ƒ.destroyBlock(â˜ƒ, false);
      } else {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(EGGS, Integer.valueOf(â˜ƒ - 1)), 2);
         â˜ƒ.levelEvent(2001, â˜ƒ, Block.getId(â˜ƒ));
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (this.shouldUpdateHatchLevel(â˜ƒ) && onSand(â˜ƒ, â˜ƒ)) {
         int â˜ƒ = â˜ƒ.getValue(HATCH);
         if (â˜ƒ < 2) {
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + â˜ƒ.nextFloat() * 0.2F);
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(HATCH, Integer.valueOf(â˜ƒ + 1)), 2);
         } else {
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + â˜ƒ.nextFloat() * 0.2F);
            â˜ƒ.removeBlock(â˜ƒ, false);

            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getValue(EGGS); ++â˜ƒ) {
               â˜ƒ.levelEvent(2001, â˜ƒ, Block.getId(â˜ƒ));
               Turtle â˜ƒx = EntityType.TURTLE.create(â˜ƒ);
               â˜ƒx.setAge(-24000);
               â˜ƒx.setHomePos(â˜ƒ);
               â˜ƒx.moveTo((double)â˜ƒ.getX() + 0.3 + (double)â˜ƒ * 0.2, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.3, 0.0F, 0.0F);
               â˜ƒ.addFreshEntity(â˜ƒx);
            }
         }
      }
   }

   public static boolean onSand(BlockGetter var0, BlockPos var1) {
      return isSand(â˜ƒ, â˜ƒ.below());
   }

   public static boolean isSand(BlockGetter var0, BlockPos var1) {
      return â˜ƒ.getBlockState(â˜ƒ).is(BlockTags.SAND);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (onSand(â˜ƒ, â˜ƒ) && !â˜ƒ.isClientSide) {
         â˜ƒ.levelEvent(2005, â˜ƒ, 0);
      }
   }

   private boolean shouldUpdateHatchLevel(Level var1) {
      float â˜ƒ = â˜ƒ.getTimeOfDay(1.0F);
      if ((double)â˜ƒ < 0.69 && (double)â˜ƒ > 0.65) {
         return true;
      } else {
         return â˜ƒ.random.nextInt(500) == 0;
      }
   }

   @Override
   public void playerDestroy(Level var1, Player var2, BlockPos var3, BlockState var4, @Nullable BlockEntity var5, ItemStack var6) {
      super.playerDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.decreaseEggs(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      return !â˜ƒ.isSecondaryUseActive() && â˜ƒ.getItemInHand().is(this.asItem()) && â˜ƒ.getValue(EGGS) < 4 ? true : super.canBeReplaced(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos());
      return â˜ƒ.is(this) ? â˜ƒ.setValue(EGGS, Integer.valueOf(Math.min(4, â˜ƒ.getValue(EGGS) + 1))) : super.getStateForPlacement(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return â˜ƒ.getValue(EGGS) > 1 ? MULTIPLE_EGGS_AABB : ONE_EGG_AABB;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(HATCH, EGGS);
   }

   private boolean canDestroyEgg(Level var1, Entity var2) {
      if (â˜ƒ instanceof Turtle || â˜ƒ instanceof Bat) {
         return false;
      } else if (!(â˜ƒ instanceof LivingEntity)) {
         return false;
      } else {
         return â˜ƒ instanceof Player || â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
      }
   }
}
