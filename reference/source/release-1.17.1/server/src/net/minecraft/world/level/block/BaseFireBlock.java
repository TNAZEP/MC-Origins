package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseFireBlock extends Block {
   private static final int SECONDS_ON_FIRE = 8;
   private final float fireDamage;
   protected static final float AABB_OFFSET = 1.0F;
   protected static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

   public BaseFireBlock(BlockBehaviour.Properties var1, float var2) {
      super(â˜ƒ);
      this.fireDamage = â˜ƒ;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return getState(â˜ƒ.getLevel(), â˜ƒ.getClickedPos());
   }

   public static BlockState getState(BlockGetter var0, BlockPos var1) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return SoulFireBlock.canSurviveOnBlock(â˜ƒx) ? Blocks.SOUL_FIRE.defaultBlockState() : ((FireBlock)Blocks.FIRE).getStateForPlacement(â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return DOWN_AABB;
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(24) == 0) {
         â˜ƒ.playLocalSound(
            (double)â˜ƒ.getX() + 0.5,
            (double)â˜ƒ.getY() + 0.5,
            (double)â˜ƒ.getZ() + 0.5,
            SoundEvents.FIRE_AMBIENT,
            SoundSource.BLOCKS,
            1.0F + â˜ƒ.nextFloat(),
            â˜ƒ.nextFloat() * 0.7F + 0.3F,
            false
         );
      }

      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (!this.canBurn(â˜ƒx) && !â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP)) {
         if (this.canBurn(â˜ƒ.getBlockState(â˜ƒ.west()))) {
            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               double â˜ƒxxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble() * 0.1F;
               double â˜ƒxxxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble();
               double â˜ƒxxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
               â˜ƒ.addParticle(ParticleTypes.LARGE_SMOKE, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.canBurn(â˜ƒ.getBlockState(â˜ƒ.east()))) {
            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               double â˜ƒxxx = (double)(â˜ƒ.getX() + 1) - â˜ƒ.nextDouble() * 0.1F;
               double â˜ƒxxxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble();
               double â˜ƒxxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
               â˜ƒ.addParticle(ParticleTypes.LARGE_SMOKE, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.canBurn(â˜ƒ.getBlockState(â˜ƒ.north()))) {
            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               double â˜ƒxxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
               double â˜ƒxxxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble();
               double â˜ƒxxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble() * 0.1F;
               â˜ƒ.addParticle(ParticleTypes.LARGE_SMOKE, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.canBurn(â˜ƒ.getBlockState(â˜ƒ.south()))) {
            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               double â˜ƒxxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
               double â˜ƒxxxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble();
               double â˜ƒxxxxx = (double)(â˜ƒ.getZ() + 1) - â˜ƒ.nextDouble() * 0.1F;
               â˜ƒ.addParticle(ParticleTypes.LARGE_SMOKE, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.canBurn(â˜ƒ.getBlockState(â˜ƒ.above()))) {
            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               double â˜ƒxxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
               double â˜ƒxxxx = (double)(â˜ƒ.getY() + 1) - â˜ƒ.nextDouble() * 0.1F;
               double â˜ƒxxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
               â˜ƒ.addParticle(ParticleTypes.LARGE_SMOKE, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
            double â˜ƒx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
            double â˜ƒxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble() * 0.5 + 0.5;
            double â˜ƒxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
            â˜ƒ.addParticle(ParticleTypes.LARGE_SMOKE, â˜ƒx, â˜ƒxx, â˜ƒxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   protected abstract boolean canBurn(BlockState var1);

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.fireImmune()) {
         â˜ƒ.setRemainingFireTicks(â˜ƒ.getRemainingFireTicks() + 1);
         if (â˜ƒ.getRemainingFireTicks() == 0) {
            â˜ƒ.setSecondsOnFire(8);
         }

         â˜ƒ.hurt(DamageSource.IN_FIRE, this.fireDamage);
      }

      super.entityInside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (inPortalDimension(â˜ƒ)) {
            Optional<PortalShape> â˜ƒ = PortalShape.findEmptyPortalShape(â˜ƒ, â˜ƒ, Direction.Axis.X);
            if (â˜ƒ.isPresent()) {
               ((PortalShape)â˜ƒ.get()).createPortalBlocks();
               return;
            }
         }

         if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
            â˜ƒ.removeBlock(â˜ƒ, false);
         }
      }
   }

   private static boolean inPortalDimension(Level var0) {
      return â˜ƒ.dimension() == Level.OVERWORLD || â˜ƒ.dimension() == Level.NETHER;
   }

   @Override
   protected void spawnDestroyParticles(Level var1, Player var2, BlockPos var3, BlockState var4) {
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide()) {
         â˜ƒ.levelEvent(null, 1009, â˜ƒ, 0);
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean canBePlacedAt(Level var0, BlockPos var1, Direction var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!â˜ƒ.isAir()) {
         return false;
      } else {
         return getState(â˜ƒ, â˜ƒ).canSurvive(â˜ƒ, â˜ƒ) || isPortal(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static boolean isPortal(Level var0, BlockPos var1, Direction var2) {
      if (!inPortalDimension(â˜ƒ)) {
         return false;
      } else {
         BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
         boolean â˜ƒx = false;

         for(Direction â˜ƒxx : Direction.values()) {
            if (â˜ƒ.getBlockState(â˜ƒ.set(â˜ƒ).move(â˜ƒxx)).is(Blocks.OBSIDIAN)) {
               â˜ƒx = true;
               break;
            }
         }

         if (!â˜ƒx) {
            return false;
         } else {
            Direction.Axis â˜ƒxx = â˜ƒ.getAxis().isHorizontal() ? â˜ƒ.getCounterClockWise().getAxis() : Direction.Plane.HORIZONTAL.getRandomAxis(â˜ƒ.random);
            return PortalShape.findEmptyPortalShape(â˜ƒ, â˜ƒ, â˜ƒxx).isPresent();
         }
      }
   }
}
