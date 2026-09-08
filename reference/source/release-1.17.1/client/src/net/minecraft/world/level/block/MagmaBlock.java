package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaBlock extends Block {
   private static final int BUBBLE_COLUMN_CHECK_DELAY = 20;

   public MagmaBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void stepOn(Level var1, BlockPos var2, BlockState var3, Entity var4) {
      if (!â˜ƒ.fireImmune() && â˜ƒ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)â˜ƒ)) {
         â˜ƒ.hurt(DamageSource.HOT_FLOOR, 1.0F);
      }

      super.stepOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BubbleColumnBlock.updateColumn(â˜ƒ, â˜ƒ.above(), â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == Direction.UP && â˜ƒ.is(Blocks.WATER)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 20);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      if (â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER)) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (â˜ƒ.random.nextFloat() - â˜ƒ.random.nextFloat()) * 0.8F);
         â˜ƒ.sendParticles(ParticleTypes.LARGE_SMOKE, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.25, (double)â˜ƒ.getZ() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 20);
   }
}
