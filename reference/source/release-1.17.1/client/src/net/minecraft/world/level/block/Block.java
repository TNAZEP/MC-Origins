package net.minecraft.world.level.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.IdMapper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends BlockBehaviour implements ItemLike {
   protected static final Logger LOGGER = LogManager.getLogger();
   public static final IdMapper<BlockState> BLOCK_STATE_REGISTRY = new IdMapper<>();
   private static final LoadingCache<VoxelShape, Boolean> SHAPE_FULL_BLOCK_CACHE = CacheBuilder.newBuilder()
      .maximumSize(512L)
      .weakKeys()
      .build(new CacheLoader<VoxelShape, Boolean>() {
         public Boolean load(VoxelShape var1) {
            return !Shapes.joinIsNotEmpty(Shapes.block(), â˜ƒ, BooleanOp.NOT_SAME);
         }
      });
   public static final int UPDATE_NEIGHBORS = 1;
   public static final int UPDATE_CLIENTS = 2;
   public static final int UPDATE_INVISIBLE = 4;
   public static final int UPDATE_IMMEDIATE = 8;
   public static final int UPDATE_KNOWN_SHAPE = 16;
   public static final int UPDATE_SUPPRESS_DROPS = 32;
   public static final int UPDATE_MOVE_BY_PISTON = 64;
   public static final int UPDATE_SUPPRESS_LIGHT = 128;
   public static final int UPDATE_NONE = 4;
   public static final int UPDATE_ALL = 3;
   public static final int UPDATE_ALL_IMMEDIATE = 11;
   public static final float INDESTRUCTIBLE = -1.0F;
   public static final float INSTANT = 0.0F;
   public static final int UPDATE_LIMIT = 512;
   protected final StateDefinition<Block, BlockState> stateDefinition;
   private BlockState defaultBlockState;
   @Nullable
   private String descriptionId;
   @Nullable
   private Item item;
   private static final int CACHE_SIZE = 2048;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> â˜ƒ = new Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>(2048, 0.25F) {
         @Override
         protected void rehash(int var1) {
         }
      };
      â˜ƒ.defaultReturnValue((byte)127);
      return â˜ƒ;
   });

   public static int getId(@Nullable BlockState var0) {
      if (â˜ƒ == null) {
         return 0;
      } else {
         int â˜ƒ = BLOCK_STATE_REGISTRY.getId(â˜ƒ);
         return â˜ƒ == -1 ? 0 : â˜ƒ;
      }
   }

   public static BlockState stateById(int var0) {
      BlockState â˜ƒ = BLOCK_STATE_REGISTRY.byId(â˜ƒ);
      return â˜ƒ == null ? Blocks.AIR.defaultBlockState() : â˜ƒ;
   }

   public static Block byItem(@Nullable Item var0) {
      return â˜ƒ instanceof BlockItem ? ((BlockItem)â˜ƒ).getBlock() : Blocks.AIR;
   }

   public static BlockState pushEntitiesUp(BlockState var0, BlockState var1, Level var2, BlockPos var3) {
      VoxelShape â˜ƒ = Shapes.joinUnoptimized(â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ), â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ), BooleanOp.ONLY_SECOND)
         .move((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
      if (â˜ƒ.isEmpty()) {
         return â˜ƒ;
      } else {
         for(Entity â˜ƒ : â˜ƒ.getEntities(null, â˜ƒ.bounds())) {
            double â˜ƒx = Shapes.collide(Direction.Axis.Y, â˜ƒ.getBoundingBox().move(0.0, 1.0, 0.0), Stream.of(â˜ƒ), -1.0);
            â˜ƒ.teleportTo(â˜ƒ.getX(), â˜ƒ.getY() + 1.0 + â˜ƒx, â˜ƒ.getZ());
         }

         return â˜ƒ;
      }
   }

   public static VoxelShape box(double var0, double var2, double var4, double var6, double var8, double var10) {
      return Shapes.box(â˜ƒ / 16.0, â˜ƒ / 16.0, â˜ƒ / 16.0, â˜ƒ / 16.0, â˜ƒ / 16.0, â˜ƒ / 16.0);
   }

   public static BlockState updateFromNeighbourShapes(BlockState var0, LevelAccessor var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(Direction â˜ƒxx : UPDATE_SHAPE_ORDER) {
         â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxx);
         â˜ƒ = â˜ƒ.updateShape(â˜ƒxx, â˜ƒ.getBlockState(â˜ƒx), â˜ƒ, â˜ƒ, â˜ƒx);
      }

      return â˜ƒ;
   }

   public static void updateOrDestroy(BlockState var0, BlockState var1, LevelAccessor var2, BlockPos var3, int var4) {
      updateOrDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 512);
   }

   public static void updateOrDestroy(BlockState var0, BlockState var1, LevelAccessor var2, BlockPos var3, int var4, int var5) {
      if (â˜ƒ != â˜ƒ) {
         if (â˜ƒ.isAir()) {
            if (!â˜ƒ.isClientSide()) {
               â˜ƒ.destroyBlock(â˜ƒ, (â˜ƒ & 32) == 0, null, â˜ƒ);
            }
         } else {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, â˜ƒ & -33, â˜ƒ);
         }
      }
   }

   public Block(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      StateDefinition.Builder<Block, BlockState> â˜ƒ = new StateDefinition.Builder<>(this);
      this.createBlockStateDefinition(â˜ƒ);
      this.stateDefinition = â˜ƒ.create(Block::defaultBlockState, BlockState::new);
      this.registerDefaultState(this.stateDefinition.any());
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         String â˜ƒx = this.getClass().getSimpleName();
         if (!â˜ƒx.endsWith("Block")) {
            LOGGER.error("Block classes should end with Block and {} doesn't.", â˜ƒx);
         }
      }
   }

   public static boolean isExceptionForConnection(BlockState var0) {
      return â˜ƒ.getBlock() instanceof LeavesBlock
         || â˜ƒ.is(Blocks.BARRIER)
         || â˜ƒ.is(Blocks.CARVED_PUMPKIN)
         || â˜ƒ.is(Blocks.JACK_O_LANTERN)
         || â˜ƒ.is(Blocks.MELON)
         || â˜ƒ.is(Blocks.PUMPKIN)
         || â˜ƒ.is(BlockTags.SHULKER_BOXES);
   }

   public boolean isRandomlyTicking(BlockState var1) {
      return this.isRandomlyTicking;
   }

   public static boolean shouldRenderFace(BlockState var0, BlockGetter var1, BlockPos var2, Direction var3, BlockPos var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ.skipRendering(â˜ƒ, â˜ƒ)) {
         return false;
      } else if (â˜ƒ.canOcclude()) {
         Block.BlockStatePairKey â˜ƒ = new Block.BlockStatePairKey(â˜ƒ, â˜ƒ, â˜ƒ);
         Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> â˜ƒx = (Object2ByteLinkedOpenHashMap)OCCLUSION_CACHE.get();
         byte â˜ƒxx = â˜ƒx.getAndMoveToFirst(â˜ƒ);
         if (â˜ƒxx != 127) {
            return â˜ƒxx != 0;
         } else {
            VoxelShape â˜ƒ = â˜ƒ.getFaceOcclusionShape(â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ.isEmpty()) {
               return true;
            } else {
               VoxelShape â˜ƒ = â˜ƒ.getFaceOcclusionShape(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite());
               boolean â˜ƒx = Shapes.joinIsNotEmpty(â˜ƒ, â˜ƒ, BooleanOp.ONLY_FIRST);
               if (â˜ƒx.size() == 2048) {
                  â˜ƒx.removeLastByte();
               }

               â˜ƒx.putAndMoveToFirst(â˜ƒ, (byte)(â˜ƒx ? 1 : 0));
               return â˜ƒx;
            }
         }
      } else {
         return true;
      }
   }

   public static boolean canSupportRigidBlock(BlockGetter var0, BlockPos var1) {
      return â˜ƒ.getBlockState(â˜ƒ).isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP, SupportType.RIGID);
   }

   public static boolean canSupportCenter(LevelReader var0, BlockPos var1, Direction var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ == Direction.DOWN && â˜ƒ.is(BlockTags.UNSTABLE_BOTTOM_CENTER) ? false : â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ, SupportType.CENTER);
   }

   public static boolean isFaceFull(VoxelShape var0, Direction var1) {
      VoxelShape â˜ƒ = â˜ƒ.getFaceShape(â˜ƒ);
      return isShapeFullBlock(â˜ƒ);
   }

   public static boolean isShapeFullBlock(VoxelShape var0) {
      return SHAPE_FULL_BLOCK_CACHE.getUnchecked(â˜ƒ);
   }

   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return !isShapeFullBlock(â˜ƒ.getShape(â˜ƒ, â˜ƒ)) && â˜ƒ.getFluidState().isEmpty();
   }

   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
   }

   public void destroy(LevelAccessor var1, BlockPos var2, BlockState var3) {
   }

   public static List<ItemStack> getDrops(BlockState var0, ServerLevel var1, BlockPos var2, @Nullable BlockEntity var3) {
      LootContext.Builder â˜ƒ = new LootContext.Builder(â˜ƒ)
         .withRandom(â˜ƒ.random)
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(â˜ƒ))
         .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
         .withOptionalParameter(LootContextParams.BLOCK_ENTITY, â˜ƒ);
      return â˜ƒ.getDrops(â˜ƒ);
   }

   public static List<ItemStack> getDrops(BlockState var0, ServerLevel var1, BlockPos var2, @Nullable BlockEntity var3, @Nullable Entity var4, ItemStack var5) {
      LootContext.Builder â˜ƒ = new LootContext.Builder(â˜ƒ)
         .withRandom(â˜ƒ.random)
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(â˜ƒ))
         .withParameter(LootContextParams.TOOL, â˜ƒ)
         .withOptionalParameter(LootContextParams.THIS_ENTITY, â˜ƒ)
         .withOptionalParameter(LootContextParams.BLOCK_ENTITY, â˜ƒ);
      return â˜ƒ.getDrops(â˜ƒ);
   }

   public static void dropResources(BlockState var0, LootContext.Builder var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getParameter(LootContextParams.ORIGIN));
      â˜ƒ.getDrops(â˜ƒ).forEach(var2x -> popResource(â˜ƒ, â˜ƒ, var2x));
      â˜ƒ.spawnAfterBreak(â˜ƒ, â˜ƒx, ItemStack.EMPTY);
   }

   public static void dropResources(BlockState var0, Level var1, BlockPos var2) {
      if (â˜ƒ instanceof ServerLevel) {
         getDrops(â˜ƒ, (ServerLevel)â˜ƒ, â˜ƒ, null).forEach(var2x -> popResource(â˜ƒ, â˜ƒ, var2x));
         â˜ƒ.spawnAfterBreak((ServerLevel)â˜ƒ, â˜ƒ, ItemStack.EMPTY);
      }
   }

   public static void dropResources(BlockState var0, LevelAccessor var1, BlockPos var2, @Nullable BlockEntity var3) {
      if (â˜ƒ instanceof ServerLevel) {
         getDrops(â˜ƒ, (ServerLevel)â˜ƒ, â˜ƒ, â˜ƒ).forEach(var2x -> popResource((ServerLevel)â˜ƒ, â˜ƒ, var2x));
         â˜ƒ.spawnAfterBreak((ServerLevel)â˜ƒ, â˜ƒ, ItemStack.EMPTY);
      }
   }

   public static void dropResources(BlockState var0, Level var1, BlockPos var2, @Nullable BlockEntity var3, Entity var4, ItemStack var5) {
      if (â˜ƒ instanceof ServerLevel) {
         getDrops(â˜ƒ, (ServerLevel)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).forEach(var2x -> popResource(â˜ƒ, â˜ƒ, var2x));
         â˜ƒ.spawnAfterBreak((ServerLevel)â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void popResource(Level var0, BlockPos var1, ItemStack var2) {
      float â˜ƒ = EntityType.ITEM.getHeight() / 2.0F;
      double â˜ƒx = (double)((float)â˜ƒ.getX() + 0.5F) + Mth.nextDouble(â˜ƒ.random, -0.25, 0.25);
      double â˜ƒxx = (double)((float)â˜ƒ.getY() + 0.5F) + Mth.nextDouble(â˜ƒ.random, -0.25, 0.25) - (double)â˜ƒ;
      double â˜ƒxxx = (double)((float)â˜ƒ.getZ() + 0.5F) + Mth.nextDouble(â˜ƒ.random, -0.25, 0.25);
      popResource(â˜ƒ, () -> new ItemEntity(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   public static void popResourceFromFace(Level var0, BlockPos var1, Direction var2, ItemStack var3) {
      int â˜ƒ = â˜ƒ.getStepX();
      int â˜ƒx = â˜ƒ.getStepY();
      int â˜ƒxx = â˜ƒ.getStepZ();
      float â˜ƒxxx = EntityType.ITEM.getWidth() / 2.0F;
      float â˜ƒxxxx = EntityType.ITEM.getHeight() / 2.0F;
      double â˜ƒxxxxx = (double)((float)â˜ƒ.getX() + 0.5F) + (â˜ƒ == 0 ? Mth.nextDouble(â˜ƒ.random, -0.25, 0.25) : (double)((float)â˜ƒ * (0.5F + â˜ƒxxx)));
      double â˜ƒxxxxxx = (double)((float)â˜ƒ.getY() + 0.5F)
         + (â˜ƒx == 0 ? Mth.nextDouble(â˜ƒ.random, -0.25, 0.25) : (double)((float)â˜ƒx * (0.5F + â˜ƒxxxx)))
         - (double)â˜ƒxxxx;
      double â˜ƒxxxxxxx = (double)((float)â˜ƒ.getZ() + 0.5F)
         + (â˜ƒxx == 0 ? Mth.nextDouble(â˜ƒ.random, -0.25, 0.25) : (double)((float)â˜ƒxx * (0.5F + â˜ƒxxx)));
      double â˜ƒxxxxxxxx = â˜ƒ == 0 ? Mth.nextDouble(â˜ƒ.random, -0.1, 0.1) : (double)â˜ƒ * 0.1;
      double â˜ƒxxxxxxxxx = â˜ƒx == 0 ? Mth.nextDouble(â˜ƒ.random, 0.0, 0.1) : (double)â˜ƒx * 0.1 + 0.1;
      double â˜ƒxxxxxxxxxx = â˜ƒxx == 0 ? Mth.nextDouble(â˜ƒ.random, -0.1, 0.1) : (double)â˜ƒxx * 0.1;
      popResource(â˜ƒ, () -> new ItemEntity(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   private static void popResource(Level var0, Supplier<ItemEntity> var1, ItemStack var2) {
      if (!â˜ƒ.isClientSide && !â˜ƒ.isEmpty() && â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
         ItemEntity â˜ƒ = (ItemEntity)â˜ƒ.get();
         â˜ƒ.setDefaultPickUpDelay();
         â˜ƒ.addFreshEntity(â˜ƒ);
      }
   }

   protected void popExperience(ServerLevel var1, BlockPos var2, int var3) {
      if (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
         ExperienceOrb.award(â˜ƒ, Vec3.atCenterOf(â˜ƒ), â˜ƒ);
      }
   }

   public float getExplosionResistance() {
      return this.explosionResistance;
   }

   public void wasExploded(Level var1, BlockPos var2, Explosion var3) {
   }

   public void stepOn(Level var1, BlockPos var2, BlockState var3, Entity var4) {
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState();
   }

   public void playerDestroy(Level var1, Player var2, BlockPos var3, BlockState var4, @Nullable BlockEntity var5, ItemStack var6) {
      â˜ƒ.awardStat(Stats.BLOCK_MINED.get(this));
      â˜ƒ.causeFoodExhaustion(0.005F);
      dropResources(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
   }

   public boolean isPossibleToRespawnInThis() {
      return !this.material.isSolid() && !this.material.isLiquid();
   }

   public MutableComponent getName() {
      return new TranslatableComponent(this.getDescriptionId());
   }

   public String getDescriptionId() {
      if (this.descriptionId == null) {
         this.descriptionId = Util.makeDescriptionId("block", Registry.BLOCK.getKey(this));
      }

      return this.descriptionId;
   }

   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      â˜ƒ.causeFallDamage(â˜ƒ, 1.0F, DamageSource.FALL);
   }

   public void updateEntityAfterFallOn(BlockGetter var1, Entity var2) {
      â˜ƒ.setDeltaMovement(â˜ƒ.getDeltaMovement().multiply(1.0, 0.0, 1.0));
   }

   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(this);
   }

   public void fillItemCategory(CreativeModeTab var1, NonNullList<ItemStack> var2) {
      â˜ƒ.add(new ItemStack(this));
   }

   public float getFriction() {
      return this.friction;
   }

   public float getSpeedFactor() {
      return this.speedFactor;
   }

   public float getJumpFactor() {
      return this.jumpFactor;
   }

   protected void spawnDestroyParticles(Level var1, Player var2, BlockPos var3, BlockState var4) {
      â˜ƒ.levelEvent(â˜ƒ, 2001, â˜ƒ, getId(â˜ƒ));
   }

   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      this.spawnDestroyParticles(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.is(BlockTags.GUARDED_BY_PIGLINS)) {
         PiglinAi.angerNearbyPiglins(â˜ƒ, false);
      }

      â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_DESTROY, â˜ƒ);
   }

   public void handlePrecipitation(BlockState var1, Level var2, BlockPos var3, Biome.Precipitation var4) {
   }

   public boolean dropFromExplosion(Explosion var1) {
      return true;
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
   }

   public StateDefinition<Block, BlockState> getStateDefinition() {
      return this.stateDefinition;
   }

   protected final void registerDefaultState(BlockState var1) {
      this.defaultBlockState = â˜ƒ;
   }

   public final BlockState defaultBlockState() {
      return this.defaultBlockState;
   }

   public final BlockState withPropertiesOf(BlockState var1) {
      BlockState â˜ƒ = this.defaultBlockState();

      for(Property<?> â˜ƒx : â˜ƒ.getBlock().getStateDefinition().getProperties()) {
         if (â˜ƒ.hasProperty(â˜ƒx)) {
            â˜ƒ = copyProperty(â˜ƒ, â˜ƒ, â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   private static <T extends Comparable<T>> BlockState copyProperty(BlockState var0, BlockState var1, Property<T> var2) {
      return â˜ƒ.setValue(â˜ƒ, â˜ƒ.getValue(â˜ƒ));
   }

   public SoundType getSoundType(BlockState var1) {
      return this.soundType;
   }

   @Override
   public Item asItem() {
      if (this.item == null) {
         this.item = Item.byBlock(this);
      }

      return this.item;
   }

   public boolean hasDynamicShape() {
      return this.dynamicShape;
   }

   public String toString() {
      return "Block{" + Registry.BLOCK.getKey(this) + "}";
   }

   public void appendHoverText(ItemStack var1, @Nullable BlockGetter var2, List<Component> var3, TooltipFlag var4) {
   }

   @Override
   protected Block asBlock() {
      return this;
   }

   protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> var1) {
      return (ImmutableMap<BlockState, VoxelShape>)this.stateDefinition
         .getPossibleStates()
         .stream()
         .collect(ImmutableMap.toImmutableMap(Function.identity(), â˜ƒ));
   }

   public static final class BlockStatePairKey {
      private final BlockState first;
      private final BlockState second;
      private final Direction direction;

      public BlockStatePairKey(BlockState var1, BlockState var2, Direction var3) {
         this.first = â˜ƒ;
         this.second = â˜ƒ;
         this.direction = â˜ƒ;
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof Block.BlockStatePairKey)) {
            return false;
         } else {
            Block.BlockStatePairKey â˜ƒ = (Block.BlockStatePairKey)â˜ƒ;
            return this.first == â˜ƒ.first && this.second == â˜ƒ.second && this.direction == â˜ƒ.direction;
         }
      }

      public int hashCode() {
         int â˜ƒ = this.first.hashCode();
         â˜ƒ = 31 * â˜ƒ + this.second.hashCode();
         return 31 * â˜ƒ + this.direction.hashCode();
      }
   }
}
