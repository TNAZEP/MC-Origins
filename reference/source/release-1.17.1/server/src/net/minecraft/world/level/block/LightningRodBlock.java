package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class LightningRodBlock extends RodBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private static final int ACTIVATION_TICKS = 8;
   public static final int RANGE = 128;
   private static final int SPARK_CYCLE = 200;

   public LightningRodBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      boolean â˜ƒx = â˜ƒ.getType() == Fluids.WATER;
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getClickedFace()).setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) && â˜ƒ.getValue(FACING) == â˜ƒ ? 15 : 0;
   }

   public void onLightningStrike(BlockState var1, Level var2, BlockPos var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(true)), 3);
      this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 8);
      â˜ƒ.levelEvent(3002, â˜ƒ, ((Direction)â˜ƒ.getValue(FACING)).getAxis().ordinal());
   }

   private void updateNeighbours(BlockState var1, Level var2, BlockPos var3) {
      â˜ƒ.updateNeighborsAt(â˜ƒ.relative(((Direction)â˜ƒ.getValue(FACING)).getOpposite()), this);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)), 3);
      this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.isThundering()
         && (long)â˜ƒ.random.nextInt(200) <= â˜ƒ.getGameTime() % 200L
         && â˜ƒ.getY() == â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒ.getX(), â˜ƒ.getZ()) - 1) {
         ParticleUtils.spawnParticlesAlongAxis(((Direction)â˜ƒ.getValue(FACING)).getAxis(), â˜ƒ, â˜ƒ, 0.125, ParticleTypes.ELECTRIC_SPARK, UniformInt.of(1, 2));
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(POWERED)) {
            this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(POWERED) && !â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)), 18);
         }
      }
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      if (â˜ƒ.isThundering() && â˜ƒ instanceof ThrownTrident && ((ThrownTrident)â˜ƒ).isChanneling()) {
         BlockPos â˜ƒ = â˜ƒ.getBlockPos();
         if (â˜ƒ.canSeeSky(â˜ƒ)) {
            LightningBolt â˜ƒx = EntityType.LIGHTNING_BOLT.create(â˜ƒ);
            â˜ƒx.moveTo(Vec3.atBottomCenterOf(â˜ƒ.above()));
            Entity â˜ƒxx = â˜ƒ.getOwner();
            â˜ƒx.setCause(â˜ƒxx instanceof ServerPlayer ? (ServerPlayer)â˜ƒxx : null);
            â˜ƒ.addFreshEntity(â˜ƒx);
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TRIDENT_THUNDER, SoundSource.WEATHER, 5.0F, 1.0F);
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, POWERED, WATERLOGGED);
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }
}
