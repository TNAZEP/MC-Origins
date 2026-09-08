package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BeehiveBlock extends BaseEntityBlock {
   private static final Direction[] SPAWN_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH};
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final IntegerProperty HONEY_LEVEL = BlockStateProperties.LEVEL_HONEY;
   public static final int MAX_HONEY_LEVELS = 5;
   private static final int SHEARED_HONEYCOMB_COUNT = 3;

   public BeehiveBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(HONEY_LEVEL, Integer.valueOf(0)).setValue(FACING, Direction.NORTH));
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return â˜ƒ.getValue(HONEY_LEVEL);
   }

   @Override
   public void playerDestroy(Level var1, Player var2, BlockPos var3, BlockState var4, @Nullable BlockEntity var5, ItemStack var6) {
      super.playerDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isClientSide && â˜ƒ instanceof BeehiveBlockEntity â˜ƒ) {
         if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, â˜ƒ) == 0) {
            â˜ƒ.emptyAllLivingFromHive(â˜ƒ, â˜ƒ, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, this);
            this.angerNearbyBees(â˜ƒ, â˜ƒ);
         }

         CriteriaTriggers.BEE_NEST_DESTROYED.trigger((ServerPlayer)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getOccupantCount());
      }
   }

   private void angerNearbyBees(Level var1, BlockPos var2) {
      List<Bee> â˜ƒ = â˜ƒ.getEntitiesOfClass(Bee.class, new AABB(â˜ƒ).inflate(8.0, 6.0, 8.0));
      if (!â˜ƒ.isEmpty()) {
         List<Player> â˜ƒx = â˜ƒ.getEntitiesOfClass(Player.class, new AABB(â˜ƒ).inflate(8.0, 6.0, 8.0));
         int â˜ƒxx = â˜ƒx.size();

         for(Bee â˜ƒxxx : â˜ƒ) {
            if (â˜ƒxxx.getTarget() == null) {
               â˜ƒxxx.setTarget((LivingEntity)â˜ƒx.get(â˜ƒ.random.nextInt(â˜ƒxx)));
            }
         }
      }
   }

   public static void dropHoneycomb(Level var0, BlockPos var1) {
      popResource(â˜ƒ, â˜ƒ, new ItemStack(Items.HONEYCOMB, 3));
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      int â˜ƒx = â˜ƒ.getValue(HONEY_LEVEL);
      boolean â˜ƒxx = false;
      if (â˜ƒx >= 5) {
         Item â˜ƒxxx = â˜ƒ.getItem();
         if (â˜ƒ.is(Items.SHEARS)) {
            â˜ƒ.playSound(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);
            dropHoneycomb(â˜ƒ, â˜ƒ);
            â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
            â˜ƒxx = true;
            â˜ƒ.gameEvent(â˜ƒ, GameEvent.SHEAR, â˜ƒ);
         } else if (â˜ƒ.is(Items.GLASS_BOTTLE)) {
            â˜ƒ.shrink(1);
            â˜ƒ.playSound(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (â˜ƒ.isEmpty()) {
               â˜ƒ.setItemInHand(â˜ƒ, new ItemStack(Items.HONEY_BOTTLE));
            } else if (!â˜ƒ.getInventory().add(new ItemStack(Items.HONEY_BOTTLE))) {
               â˜ƒ.drop(new ItemStack(Items.HONEY_BOTTLE), false);
            }

            â˜ƒxx = true;
            â˜ƒ.gameEvent(â˜ƒ, GameEvent.FLUID_PICKUP, â˜ƒ);
         }

         if (!â˜ƒ.isClientSide() && â˜ƒxx) {
            â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒxxx));
         }
      }

      if (â˜ƒxx) {
         if (!CampfireBlock.isSmokeyPos(â˜ƒ, â˜ƒ)) {
            if (this.hiveContainsBees(â˜ƒ, â˜ƒ)) {
               this.angerNearbyBees(â˜ƒ, â˜ƒ);
            }

            this.releaseBeesAndResetHoneyLevel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
         } else {
            this.resetHoneyLevel(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return super.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private boolean hiveContainsBees(Level var1, BlockPos var2) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof BeehiveBlockEntity â˜ƒ) {
         return !â˜ƒ.isEmpty();
      } else {
         return false;
      }
   }

   public void releaseBeesAndResetHoneyLevel(Level var1, BlockState var2, BlockPos var3, @Nullable Player var4, BeehiveBlockEntity.BeeReleaseStatus var5) {
      this.resetHoneyLevel(â˜ƒ, â˜ƒ, â˜ƒ);
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof BeehiveBlockEntity â˜ƒ) {
         â˜ƒ.emptyAllLivingFromHive(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void resetHoneyLevel(Level var1, BlockState var2, BlockPos var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(HONEY_LEVEL, Integer.valueOf(0)), 3);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(HONEY_LEVEL) >= 5) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.nextInt(1) + 1; ++â˜ƒ) {
            this.trySpawnDripParticles(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private void trySpawnDripParticles(Level var1, BlockPos var2, BlockState var3) {
      if (â˜ƒ.getFluidState().isEmpty() && !(â˜ƒ.random.nextFloat() < 0.3F)) {
         VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ);
         double â˜ƒx = â˜ƒ.max(Direction.Axis.Y);
         if (â˜ƒx >= 1.0 && !â˜ƒ.is(BlockTags.IMPERMEABLE)) {
            double â˜ƒxx = â˜ƒ.min(Direction.Axis.Y);
            if (â˜ƒxx > 0.0) {
               this.spawnParticle(â˜ƒ, â˜ƒ, â˜ƒ, (double)â˜ƒ.getY() + â˜ƒxx - 0.05);
            } else {
               BlockPos â˜ƒxx = â˜ƒ.below();
               BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
               VoxelShape â˜ƒxxxx = â˜ƒxxx.getCollisionShape(â˜ƒ, â˜ƒxx);
               double â˜ƒxxxxx = â˜ƒxxxx.max(Direction.Axis.Y);
               if ((â˜ƒxxxxx < 1.0 || !â˜ƒxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxx)) && â˜ƒxxx.getFluidState().isEmpty()) {
                  this.spawnParticle(â˜ƒ, â˜ƒ, â˜ƒ, (double)â˜ƒ.getY() - 0.05);
               }
            }
         }
      }
   }

   private void spawnParticle(Level var1, BlockPos var2, VoxelShape var3, double var4) {
      this.spawnFluidParticle(
         â˜ƒ,
         (double)â˜ƒ.getX() + â˜ƒ.min(Direction.Axis.X),
         (double)â˜ƒ.getX() + â˜ƒ.max(Direction.Axis.X),
         (double)â˜ƒ.getZ() + â˜ƒ.min(Direction.Axis.Z),
         (double)â˜ƒ.getZ() + â˜ƒ.max(Direction.Axis.Z),
         â˜ƒ
      );
   }

   private void spawnFluidParticle(Level var1, double var2, double var4, double var6, double var8, double var10) {
      â˜ƒ.addParticle(
         ParticleTypes.DRIPPING_HONEY, Mth.lerp(â˜ƒ.random.nextDouble(), â˜ƒ, â˜ƒ), â˜ƒ, Mth.lerp(â˜ƒ.random.nextDouble(), â˜ƒ, â˜ƒ), 0.0, 0.0, 0.0
      );
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite());
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(HONEY_LEVEL, FACING);
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new BeehiveBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return â˜ƒ.isClientSide ? null : createTickerHelper(â˜ƒ, BlockEntityType.BEEHIVE, BeehiveBlockEntity::serverTick);
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ.isCreative() && â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
         BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒx instanceof BeehiveBlockEntity â˜ƒ) {
            ItemStack â˜ƒxx = new ItemStack(this);
            int â˜ƒxxx = â˜ƒ.getValue(HONEY_LEVEL);
            boolean â˜ƒxxxx = !â˜ƒ.isEmpty();
            if (â˜ƒxxxx || â˜ƒxxx > 0) {
               if (â˜ƒxxxx) {
                  CompoundTag â˜ƒxxxxx = new CompoundTag();
                  â˜ƒxxxxx.put("Bees", â˜ƒ.writeBees());
                  â˜ƒxx.addTagElement("BlockEntityTag", â˜ƒxxxxx);
               }

               CompoundTag â˜ƒxxxxx = new CompoundTag();
               â˜ƒxxxxx.putInt("honey_level", â˜ƒxxx);
               â˜ƒxx.addTagElement("BlockStateTag", â˜ƒxxxxx);
               ItemEntity â˜ƒxxxxxx = new ItemEntity(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), â˜ƒxx);
               â˜ƒxxxxxx.setDefaultPickUpDelay();
               â˜ƒ.addFreshEntity(â˜ƒxxxxxx);
            }
         }
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public List<ItemStack> getDrops(BlockState var1, LootContext.Builder var2) {
      Entity â˜ƒ = â˜ƒ.getOptionalParameter(LootContextParams.THIS_ENTITY);
      if (â˜ƒ instanceof PrimedTnt || â˜ƒ instanceof Creeper || â˜ƒ instanceof WitherSkull || â˜ƒ instanceof WitherBoss || â˜ƒ instanceof MinecartTNT) {
         BlockEntity â˜ƒxx = â˜ƒ.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
         if (â˜ƒxx instanceof BeehiveBlockEntity â˜ƒx) {
            â˜ƒx.emptyAllLivingFromHive(null, â˜ƒ, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
         }
      }

      return super.getDrops(â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getBlockState(â˜ƒ).getBlock() instanceof FireBlock) {
         BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒx instanceof BeehiveBlockEntity â˜ƒ) {
            â˜ƒ.emptyAllLivingFromHive(null, â˜ƒ, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
         }
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static Direction getRandomOffset(Random var0) {
      return Util.getRandom(SPAWN_DIRECTIONS, â˜ƒ);
   }
}
