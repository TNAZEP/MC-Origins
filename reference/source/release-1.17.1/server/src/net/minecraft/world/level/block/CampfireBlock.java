package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CampfireBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   public static final BooleanProperty SIGNAL_FIRE = BlockStateProperties.SIGNAL_FIRE;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   private static final int SMOKE_DISTANCE = 5;
   private final boolean spawnParticles;
   private final int fireDamage;

   public CampfireBlock(boolean var1, int var2, BlockBehaviour.Properties var3) {
      super(â˜ƒ);
      this.spawnParticles = â˜ƒ;
      this.fireDamage = â˜ƒ;
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(LIT, Boolean.valueOf(true))
            .setValue(SIGNAL_FIRE, Boolean.valueOf(false))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
            .setValue(FACING, Direction.NORTH)
      );
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof CampfireBlockEntity â˜ƒ) {
         ItemStack â˜ƒxx = â˜ƒ.getItemInHand(â˜ƒ);
         Optional<CampfireCookingRecipe> â˜ƒxxx = â˜ƒ.getCookableRecipe(â˜ƒxx);
         if (â˜ƒxxx.isPresent()) {
            if (!â˜ƒ.isClientSide
               && â˜ƒ.placeFood(â˜ƒ.getAbilities().instabuild ? â˜ƒxx.copy() : â˜ƒxx, ((CampfireCookingRecipe)â˜ƒxxx.get()).getCookingTime())) {
               â˜ƒ.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
               return InteractionResult.SUCCESS;
            }

            return InteractionResult.CONSUME;
         }
      }

      return InteractionResult.PASS;
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.fireImmune() && â˜ƒ.getValue(LIT) && â˜ƒ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)â˜ƒ)) {
         â˜ƒ.hurt(DamageSource.IN_FIRE, (float)this.fireDamage);
      }

      super.entityInside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof CampfireBlockEntity) {
            Containers.dropContents(â˜ƒ, â˜ƒ, ((CampfireBlockEntity)â˜ƒ).getItems());
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      LevelAccessor â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      boolean â˜ƒxx = â˜ƒ.getFluidState(â˜ƒx).getType() == Fluids.WATER;
      return this.defaultBlockState()
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxx))
         .setValue(SIGNAL_FIRE, Boolean.valueOf(this.isSmokeSource(â˜ƒ.getBlockState(â˜ƒx.below()))))
         .setValue(LIT, Boolean.valueOf(!â˜ƒxx))
         .setValue(FACING, â˜ƒ.getHorizontalDirection());
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return â˜ƒ == Direction.DOWN ? â˜ƒ.setValue(SIGNAL_FIRE, Boolean.valueOf(this.isSmokeSource(â˜ƒ))) : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private boolean isSmokeSource(BlockState var1) {
      return â˜ƒ.is(Blocks.HAY_BLOCK);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         if (â˜ƒ.nextInt(10) == 0) {
            â˜ƒ.playLocalSound(
               (double)â˜ƒ.getX() + 0.5,
               (double)â˜ƒ.getY() + 0.5,
               (double)â˜ƒ.getZ() + 0.5,
               SoundEvents.CAMPFIRE_CRACKLE,
               SoundSource.BLOCKS,
               0.5F + â˜ƒ.nextFloat(),
               â˜ƒ.nextFloat() * 0.7F + 0.6F,
               false
            );
         }

         if (this.spawnParticles && â˜ƒ.nextInt(5) == 0) {
            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.nextInt(1) + 1; ++â˜ƒ) {
               â˜ƒ.addParticle(
                  ParticleTypes.LAVA,
                  (double)â˜ƒ.getX() + 0.5,
                  (double)â˜ƒ.getY() + 0.5,
                  (double)â˜ƒ.getZ() + 0.5,
                  (double)(â˜ƒ.nextFloat() / 2.0F),
                  5.0E-5,
                  (double)(â˜ƒ.nextFloat() / 2.0F)
               );
            }
         }
      }
   }

   public static void dowse(@Nullable Entity var0, LevelAccessor var1, BlockPos var2, BlockState var3) {
      if (â˜ƒ.isClientSide()) {
         for(int â˜ƒ = 0; â˜ƒ < 20; ++â˜ƒ) {
            makeParticles((Level)â˜ƒ, â˜ƒ, â˜ƒ.getValue(SIGNAL_FIRE), true);
         }
      }

      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof CampfireBlockEntity) {
         ((CampfireBlockEntity)â˜ƒ).dowse();
      }

      â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_CHANGE, â˜ƒ);
   }

   @Override
   public boolean placeLiquid(LevelAccessor var1, BlockPos var2, BlockState var3, FluidState var4) {
      if (!â˜ƒ.getValue(BlockStateProperties.WATERLOGGED) && â˜ƒ.getType() == Fluids.WATER) {
         boolean â˜ƒ = â˜ƒ.getValue(LIT);
         if (â˜ƒ) {
            if (!â˜ƒ.isClientSide()) {
               â˜ƒ.playSound(null, â˜ƒ, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            dowse(null, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(LIT, Boolean.valueOf(false)), 3);
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getType(), â˜ƒ.getType().getTickDelay(â˜ƒ));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      if (!â˜ƒ.isClientSide && â˜ƒ.isOnFire() && â˜ƒ.mayInteract(â˜ƒ, â˜ƒ) && !â˜ƒ.getValue(LIT) && !â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
      }
   }

   public static void makeParticles(Level var0, BlockPos var1, boolean var2, boolean var3) {
      Random â˜ƒ = â˜ƒ.getRandom();
      SimpleParticleType â˜ƒx = â˜ƒ ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
      â˜ƒ.addAlwaysVisibleParticle(
         â˜ƒx,
         true,
         (double)â˜ƒ.getX() + 0.5 + â˜ƒ.nextDouble() / 3.0 * (double)(â˜ƒ.nextBoolean() ? 1 : -1),
         (double)â˜ƒ.getY() + â˜ƒ.nextDouble() + â˜ƒ.nextDouble(),
         (double)â˜ƒ.getZ() + 0.5 + â˜ƒ.nextDouble() / 3.0 * (double)(â˜ƒ.nextBoolean() ? 1 : -1),
         0.0,
         0.07,
         0.0
      );
      if (â˜ƒ) {
         â˜ƒ.addParticle(
            ParticleTypes.SMOKE,
            (double)â˜ƒ.getX() + 0.5 + â˜ƒ.nextDouble() / 4.0 * (double)(â˜ƒ.nextBoolean() ? 1 : -1),
            (double)â˜ƒ.getY() + 0.4,
            (double)â˜ƒ.getZ() + 0.5 + â˜ƒ.nextDouble() / 4.0 * (double)(â˜ƒ.nextBoolean() ? 1 : -1),
            0.0,
            0.005,
            0.0
         );
      }
   }

   public static boolean isSmokeyPos(Level var0, BlockPos var1) {
      for(int â˜ƒ = 1; â˜ƒ <= 5; ++â˜ƒ) {
         BlockPos â˜ƒx = â˜ƒ.below(â˜ƒ);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         if (isLitCampfire(â˜ƒxx)) {
            return true;
         }

         boolean â˜ƒx = Shapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, â˜ƒxx.getCollisionShape(â˜ƒ, â˜ƒ, CollisionContext.empty()), BooleanOp.AND);
         if (â˜ƒx) {
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx.below());
            return isLitCampfire(â˜ƒxx);
         }
      }

      return false;
   }

   public static boolean isLitCampfire(BlockState var0) {
      return â˜ƒ.hasProperty(LIT) && â˜ƒ.is(BlockTags.CAMPFIRES) && â˜ƒ.getValue(LIT);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new CampfireBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      if (â˜ƒ.isClientSide) {
         return â˜ƒ.getValue(LIT) ? createTickerHelper(â˜ƒ, BlockEntityType.CAMPFIRE, CampfireBlockEntity::particleTick) : null;
      } else {
         return â˜ƒ.getValue(LIT)
            ? createTickerHelper(â˜ƒ, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cookTick)
            : createTickerHelper(â˜ƒ, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cooldownTick);
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   public static boolean canLight(BlockState var0) {
      return â˜ƒ.is(BlockTags.CAMPFIRES, var0x -> var0x.hasProperty(WATERLOGGED) && var0x.hasProperty(LIT)) && !â˜ƒ.getValue(WATERLOGGED) && !â˜ƒ.getValue(LIT);
   }
}
