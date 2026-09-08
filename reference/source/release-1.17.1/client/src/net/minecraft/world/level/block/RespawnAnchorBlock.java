package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RespawnAnchorBlock extends Block {
   public static final int MIN_CHARGES = 0;
   public static final int MAX_CHARGES = 4;
   public static final IntegerProperty CHARGE = BlockStateProperties.RESPAWN_ANCHOR_CHARGES;
   private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(
      new Vec3i(0, 0, -1),
      new Vec3i(-1, 0, 0),
      new Vec3i(0, 0, 1),
      new Vec3i(1, 0, 0),
      new Vec3i(-1, 0, -1),
      new Vec3i(1, 0, -1),
      new Vec3i(-1, 0, 1),
      new Vec3i(1, 0, 1)
   );
   private static final ImmutableList<Vec3i> RESPAWN_OFFSETS = new Builder<Vec3i>()
      .addAll(RESPAWN_HORIZONTAL_OFFSETS)
      .addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::below).iterator())
      .addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::above).iterator())
      .add(new Vec3i(0, 1, 0))
      .build();

   public RespawnAnchorBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(CHARGE, Integer.valueOf(0)));
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ == InteractionHand.MAIN_HAND && !isRespawnFuel(â˜ƒ) && isRespawnFuel(â˜ƒ.getItemInHand(InteractionHand.OFF_HAND))) {
         return InteractionResult.PASS;
      } else if (isRespawnFuel(â˜ƒ) && canBeCharged(â˜ƒ)) {
         charge(â˜ƒ, â˜ƒ, â˜ƒ);
         if (!â˜ƒ.getAbilities().instabuild) {
            â˜ƒ.shrink(1);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else if (â˜ƒ.getValue(CHARGE) == 0) {
         return InteractionResult.PASS;
      } else if (!canSetSpawn(â˜ƒ)) {
         if (!â˜ƒ.isClientSide) {
            this.explode(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         if (!â˜ƒ.isClientSide) {
            ServerPlayer â˜ƒ = (ServerPlayer)â˜ƒ;
            if (â˜ƒ.getRespawnDimension() != â˜ƒ.dimension() || !â˜ƒ.equals(â˜ƒ.getRespawnPosition())) {
               â˜ƒ.setRespawnPosition(â˜ƒ.dimension(), â˜ƒ, 0.0F, false, true);
               â˜ƒ.playSound(
                  null,
                  (double)â˜ƒ.getX() + 0.5,
                  (double)â˜ƒ.getY() + 0.5,
                  (double)â˜ƒ.getZ() + 0.5,
                  SoundEvents.RESPAWN_ANCHOR_SET_SPAWN,
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F
               );
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.CONSUME;
      }
   }

   private static boolean isRespawnFuel(ItemStack var0) {
      return â˜ƒ.is(Items.GLOWSTONE);
   }

   private static boolean canBeCharged(BlockState var0) {
      return â˜ƒ.getValue(CHARGE) < 4;
   }

   private static boolean isWaterThatWouldFlow(BlockPos var0, Level var1) {
      FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
      if (!â˜ƒ.is(FluidTags.WATER)) {
         return false;
      } else if (â˜ƒ.isSource()) {
         return true;
      } else {
         float â˜ƒ = (float)â˜ƒ.getAmount();
         if (â˜ƒ < 2.0F) {
            return false;
         } else {
            FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ.below());
            return !â˜ƒ.is(FluidTags.WATER);
         }
      }
   }

   private void explode(BlockState var1, Level var2, final BlockPos var3) {
      â˜ƒ.removeBlock(â˜ƒ, false);
      boolean â˜ƒ = Direction.Plane.HORIZONTAL.stream().map(â˜ƒ::relative).anyMatch(var1x -> isWaterThatWouldFlow(var1x, â˜ƒ));
      final boolean â˜ƒx = â˜ƒ || â˜ƒ.getFluidState(â˜ƒ.above()).is(FluidTags.WATER);
      ExplosionDamageCalculator â˜ƒxx = new ExplosionDamageCalculator() {
         @Override
         public Optional<Float> getBlockExplosionResistance(Explosion var1, BlockGetter var2, BlockPos var3x, BlockState var4, FluidState var5x) {
            return â˜ƒ.equals(â˜ƒ) && â˜ƒ ? Optional.of(Blocks.WATER.getExplosionResistance()) : super.getBlockExplosionResistance(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      };
      â˜ƒ.explode(
         null,
         DamageSource.badRespawnPointExplosion(),
         â˜ƒxx,
         (double)â˜ƒ.getX() + 0.5,
         (double)â˜ƒ.getY() + 0.5,
         (double)â˜ƒ.getZ() + 0.5,
         5.0F,
         true,
         Explosion.BlockInteraction.DESTROY
      );
   }

   public static boolean canSetSpawn(Level var0) {
      return â˜ƒ.dimensionType().respawnAnchorWorks();
   }

   public static void charge(Level var0, BlockPos var1, BlockState var2) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(CHARGE, Integer.valueOf(â˜ƒ.getValue(CHARGE) + 1)), 3);
      â˜ƒ.playSound(
         null, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F
      );
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(CHARGE) != 0) {
         if (â˜ƒ.nextInt(100) == 0) {
            â˜ƒ.playSound(
               null,
               (double)â˜ƒ.getX() + 0.5,
               (double)â˜ƒ.getY() + 0.5,
               (double)â˜ƒ.getZ() + 0.5,
               SoundEvents.RESPAWN_ANCHOR_AMBIENT,
               SoundSource.BLOCKS,
               1.0F,
               1.0F
            );
         }

         double â˜ƒ = (double)â˜ƒ.getX() + 0.5 + (0.5 - â˜ƒ.nextDouble());
         double â˜ƒx = (double)â˜ƒ.getY() + 1.0;
         double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5 + (0.5 - â˜ƒ.nextDouble());
         double â˜ƒxxx = (double)â˜ƒ.nextFloat() * 0.04;
         â˜ƒ.addParticle(ParticleTypes.REVERSE_PORTAL, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, â˜ƒxxx, 0.0);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(CHARGE);
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   public static int getScaledChargeLevel(BlockState var0, int var1) {
      return Mth.floor((float)(â˜ƒ.getValue(CHARGE) - 0) / 4.0F * (float)â˜ƒ);
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return getScaledChargeLevel(â˜ƒ, 15);
   }

   public static Optional<Vec3> findStandUpPosition(EntityType<?> var0, CollisionGetter var1, BlockPos var2) {
      Optional<Vec3> â˜ƒ = findStandUpPosition(â˜ƒ, â˜ƒ, â˜ƒ, true);
      return â˜ƒ.isPresent() ? â˜ƒ : findStandUpPosition(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   private static Optional<Vec3> findStandUpPosition(EntityType<?> var0, CollisionGetter var1, BlockPos var2, boolean var3) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(Vec3i â˜ƒx : RESPAWN_OFFSETS) {
         â˜ƒ.set(â˜ƒ).move(â˜ƒx);
         Vec3 â˜ƒxx = DismountHelper.findSafeDismountLocation(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒxx != null) {
            return Optional.of(â˜ƒxx);
         }
      }

      return Optional.empty();
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
