package net.minecraft.world.level.block.state;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BlockBehaviour {
   protected static final Direction[] UPDATE_SHAPE_ORDER = new Direction[]{
      Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP
   };
   protected final Material material;
   protected final boolean hasCollision;
   protected final float explosionResistance;
   protected final boolean isRandomlyTicking;
   protected final SoundType soundType;
   protected final float friction;
   protected final float speedFactor;
   protected final float jumpFactor;
   protected final boolean dynamicShape;
   protected final BlockBehaviour.Properties properties;
   @Nullable
   protected ResourceLocation drops;

   public BlockBehaviour(BlockBehaviour.Properties var1) {
      this.material = â˜ƒ.material;
      this.hasCollision = â˜ƒ.hasCollision;
      this.drops = â˜ƒ.drops;
      this.explosionResistance = â˜ƒ.explosionResistance;
      this.isRandomlyTicking = â˜ƒ.isRandomlyTicking;
      this.soundType = â˜ƒ.soundType;
      this.friction = â˜ƒ.friction;
      this.speedFactor = â˜ƒ.speedFactor;
      this.jumpFactor = â˜ƒ.jumpFactor;
      this.dynamicShape = â˜ƒ.dynamicShape;
      this.properties = â˜ƒ;
   }

   @Deprecated
   public void updateIndirectNeighbourShapes(BlockState var1, LevelAccessor var2, BlockPos var3, int var4, int var5) {
   }

   @Deprecated
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      switch(â˜ƒ) {
         case LAND:
            return !â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ);
         case WATER:
            return â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER);
         case AIR:
            return !â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ);
         default:
            return false;
      }
   }

   @Deprecated
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ;
   }

   @Deprecated
   public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
      return false;
   }

   @Deprecated
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      DebugPackets.sendNeighborsUpdatePacket(â˜ƒ, â˜ƒ);
   }

   @Deprecated
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
   }

   @Deprecated
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (â˜ƒ.hasBlockEntity() && !â˜ƒ.is(â˜ƒ.getBlock())) {
         â˜ƒ.removeBlockEntity(â˜ƒ);
      }
   }

   @Deprecated
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      return InteractionResult.PASS;
   }

   @Deprecated
   public boolean triggerEvent(BlockState var1, Level var2, BlockPos var3, int var4, int var5) {
      return false;
   }

   @Deprecated
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Deprecated
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return false;
   }

   @Deprecated
   public boolean isSignalSource(BlockState var1) {
      return false;
   }

   @Deprecated
   public PushReaction getPistonPushReaction(BlockState var1) {
      return this.material.getPushReaction();
   }

   @Deprecated
   public FluidState getFluidState(BlockState var1) {
      return Fluids.EMPTY.defaultFluidState();
   }

   @Deprecated
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return false;
   }

   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.NONE;
   }

   public float getMaxHorizontalOffset() {
      return 0.25F;
   }

   public float getMaxVerticalOffset() {
      return 0.2F;
   }

   @Deprecated
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ;
   }

   @Deprecated
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ;
   }

   @Deprecated
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      return this.material.isReplaceable() && (â˜ƒ.getItemInHand().isEmpty() || !â˜ƒ.getItemInHand().is(this.asItem()));
   }

   @Deprecated
   public boolean canBeReplaced(BlockState var1, Fluid var2) {
      return this.material.isReplaceable() || !this.material.isSolid();
   }

   @Deprecated
   public List<ItemStack> getDrops(BlockState var1, LootContext.Builder var2) {
      ResourceLocation â˜ƒ = this.getLootTable();
      if (â˜ƒ == BuiltInLootTables.EMPTY) {
         return Collections.emptyList();
      } else {
         LootContext â˜ƒ = â˜ƒ.withParameter(LootContextParams.BLOCK_STATE, â˜ƒ).create(LootContextParamSets.BLOCK);
         ServerLevel â˜ƒx = â˜ƒ.getLevel();
         LootTable â˜ƒxx = â˜ƒx.getServer().getLootTables().get(â˜ƒ);
         return â˜ƒxx.getRandomItems(â˜ƒ);
      }
   }

   @Deprecated
   public long getSeed(BlockState var1, BlockPos var2) {
      return Mth.getSeed(â˜ƒ);
   }

   @Deprecated
   public VoxelShape getOcclusionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.getShape(â˜ƒ, â˜ƒ);
   }

   @Deprecated
   public VoxelShape getBlockSupportShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return this.getCollisionShape(â˜ƒ, â˜ƒ, â˜ƒ, CollisionContext.empty());
   }

   @Deprecated
   public VoxelShape getInteractionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return Shapes.empty();
   }

   @Deprecated
   public int getLightBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
      if (â˜ƒ.isSolidRender(â˜ƒ, â˜ƒ)) {
         return â˜ƒ.getMaxLightLevel();
      } else {
         return â˜ƒ.propagatesSkylightDown(â˜ƒ, â˜ƒ) ? 0 : 1;
      }
   }

   @Nullable
   @Deprecated
   public MenuProvider getMenuProvider(BlockState var1, Level var2, BlockPos var3) {
      return null;
   }

   @Deprecated
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return true;
   }

   @Deprecated
   public float getShadeBrightness(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ) ? 0.2F : 1.0F;
   }

   @Deprecated
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return 0;
   }

   @Deprecated
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return Shapes.block();
   }

   @Deprecated
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.hasCollision ? â˜ƒ.getShape(â˜ƒ, â˜ƒ) : Shapes.empty();
   }

   @Deprecated
   public boolean isCollisionShapeFullBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
      return Block.isShapeFullBlock(â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ));
   }

   @Deprecated
   public VoxelShape getVisualShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.getCollisionShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Deprecated
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.tick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Deprecated
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
   }

   @Deprecated
   public float getDestroyProgress(BlockState var1, Player var2, BlockGetter var3, BlockPos var4) {
      float â˜ƒ = â˜ƒ.getDestroySpeed(â˜ƒ, â˜ƒ);
      if (â˜ƒ == -1.0F) {
         return 0.0F;
      } else {
         int â˜ƒ = â˜ƒ.hasCorrectToolForDrops(â˜ƒ) ? 30 : 100;
         return â˜ƒ.getDestroySpeed(â˜ƒ) / â˜ƒ / (float)â˜ƒ;
      }
   }

   @Deprecated
   public void spawnAfterBreak(BlockState var1, ServerLevel var2, BlockPos var3, ItemStack var4) {
   }

   @Deprecated
   public void attack(BlockState var1, Level var2, BlockPos var3, Player var4) {
   }

   @Deprecated
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return 0;
   }

   @Deprecated
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
   }

   @Deprecated
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return 0;
   }

   public final ResourceLocation getLootTable() {
      if (this.drops == null) {
         ResourceLocation â˜ƒ = Registry.BLOCK.getKey(this.asBlock());
         this.drops = new ResourceLocation(â˜ƒ.getNamespace(), "blocks/" + â˜ƒ.getPath());
      }

      return this.drops;
   }

   @Deprecated
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
   }

   public abstract Item asItem();

   protected abstract Block asBlock();

   public MaterialColor defaultMaterialColor() {
      return (MaterialColor)this.properties.materialColor.apply(this.asBlock().defaultBlockState());
   }

   public float defaultDestroyTime() {
      return this.properties.destroyTime;
   }

   public abstract static class BlockStateBase extends StateHolder<Block, BlockState> {
      private final int lightEmission;
      private final boolean useShapeForLightOcclusion;
      private final boolean isAir;
      private final Material material;
      private final MaterialColor materialColor;
      private final float destroySpeed;
      private final boolean requiresCorrectToolForDrops;
      private final boolean canOcclude;
      private final BlockBehaviour.StatePredicate isRedstoneConductor;
      private final BlockBehaviour.StatePredicate isSuffocating;
      private final BlockBehaviour.StatePredicate isViewBlocking;
      private final BlockBehaviour.StatePredicate hasPostProcess;
      private final BlockBehaviour.StatePredicate emissiveRendering;
      @Nullable
      protected BlockBehaviour.BlockStateBase.Cache cache;

      protected BlockStateBase(Block var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<BlockState> var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         BlockBehaviour.Properties â˜ƒ = â˜ƒ.properties;
         this.lightEmission = â˜ƒ.lightEmission.applyAsInt(this.asState());
         this.useShapeForLightOcclusion = â˜ƒ.useShapeForLightOcclusion(this.asState());
         this.isAir = â˜ƒ.isAir;
         this.material = â˜ƒ.material;
         this.materialColor = (MaterialColor)â˜ƒ.materialColor.apply(this.asState());
         this.destroySpeed = â˜ƒ.destroyTime;
         this.requiresCorrectToolForDrops = â˜ƒ.requiresCorrectToolForDrops;
         this.canOcclude = â˜ƒ.canOcclude;
         this.isRedstoneConductor = â˜ƒ.isRedstoneConductor;
         this.isSuffocating = â˜ƒ.isSuffocating;
         this.isViewBlocking = â˜ƒ.isViewBlocking;
         this.hasPostProcess = â˜ƒ.hasPostProcess;
         this.emissiveRendering = â˜ƒ.emissiveRendering;
      }

      public void initCache() {
         if (!this.getBlock().hasDynamicShape()) {
            this.cache = new BlockBehaviour.BlockStateBase.Cache(this.asState());
         }
      }

      public Block getBlock() {
         return this.owner;
      }

      public Material getMaterial() {
         return this.material;
      }

      public boolean isValidSpawn(BlockGetter var1, BlockPos var2, EntityType<?> var3) {
         return this.getBlock().properties.isValidSpawn.test(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean propagatesSkylightDown(BlockGetter var1, BlockPos var2) {
         return this.cache != null ? this.cache.propagatesSkylightDown : this.getBlock().propagatesSkylightDown(this.asState(), â˜ƒ, â˜ƒ);
      }

      public int getLightBlock(BlockGetter var1, BlockPos var2) {
         return this.cache != null ? this.cache.lightBlock : this.getBlock().getLightBlock(this.asState(), â˜ƒ, â˜ƒ);
      }

      public VoxelShape getFaceOcclusionShape(BlockGetter var1, BlockPos var2, Direction var3) {
         return this.cache != null && this.cache.occlusionShapes != null
            ? this.cache.occlusionShapes[â˜ƒ.ordinal()]
            : Shapes.getFaceShape(this.getOcclusionShape(â˜ƒ, â˜ƒ), â˜ƒ);
      }

      public VoxelShape getOcclusionShape(BlockGetter var1, BlockPos var2) {
         return this.getBlock().getOcclusionShape(this.asState(), â˜ƒ, â˜ƒ);
      }

      public boolean hasLargeCollisionShape() {
         return this.cache == null || this.cache.largeCollisionShape;
      }

      public boolean useShapeForLightOcclusion() {
         return this.useShapeForLightOcclusion;
      }

      public int getLightEmission() {
         return this.lightEmission;
      }

      public boolean isAir() {
         return this.isAir;
      }

      public MaterialColor getMapColor(BlockGetter var1, BlockPos var2) {
         return this.materialColor;
      }

      public BlockState rotate(Rotation var1) {
         return this.getBlock().rotate(this.asState(), â˜ƒ);
      }

      public BlockState mirror(Mirror var1) {
         return this.getBlock().mirror(this.asState(), â˜ƒ);
      }

      public RenderShape getRenderShape() {
         return this.getBlock().getRenderShape(this.asState());
      }

      public boolean emissiveRendering(BlockGetter var1, BlockPos var2) {
         return this.emissiveRendering.test(this.asState(), â˜ƒ, â˜ƒ);
      }

      public float getShadeBrightness(BlockGetter var1, BlockPos var2) {
         return this.getBlock().getShadeBrightness(this.asState(), â˜ƒ, â˜ƒ);
      }

      public boolean isRedstoneConductor(BlockGetter var1, BlockPos var2) {
         return this.isRedstoneConductor.test(this.asState(), â˜ƒ, â˜ƒ);
      }

      public boolean isSignalSource() {
         return this.getBlock().isSignalSource(this.asState());
      }

      public int getSignal(BlockGetter var1, BlockPos var2, Direction var3) {
         return this.getBlock().getSignal(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean hasAnalogOutputSignal() {
         return this.getBlock().hasAnalogOutputSignal(this.asState());
      }

      public int getAnalogOutputSignal(Level var1, BlockPos var2) {
         return this.getBlock().getAnalogOutputSignal(this.asState(), â˜ƒ, â˜ƒ);
      }

      public float getDestroySpeed(BlockGetter var1, BlockPos var2) {
         return this.destroySpeed;
      }

      public float getDestroyProgress(Player var1, BlockGetter var2, BlockPos var3) {
         return this.getBlock().getDestroyProgress(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public int getDirectSignal(BlockGetter var1, BlockPos var2, Direction var3) {
         return this.getBlock().getDirectSignal(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public PushReaction getPistonPushReaction() {
         return this.getBlock().getPistonPushReaction(this.asState());
      }

      public boolean isSolidRender(BlockGetter var1, BlockPos var2) {
         if (this.cache != null) {
            return this.cache.solidRender;
         } else {
            BlockState â˜ƒ = this.asState();
            return â˜ƒ.canOcclude() ? Block.isShapeFullBlock(â˜ƒ.getOcclusionShape(â˜ƒ, â˜ƒ)) : false;
         }
      }

      public boolean canOcclude() {
         return this.canOcclude;
      }

      public boolean skipRendering(BlockState var1, Direction var2) {
         return this.getBlock().skipRendering(this.asState(), â˜ƒ, â˜ƒ);
      }

      public VoxelShape getShape(BlockGetter var1, BlockPos var2) {
         return this.getShape(â˜ƒ, â˜ƒ, CollisionContext.empty());
      }

      public VoxelShape getShape(BlockGetter var1, BlockPos var2, CollisionContext var3) {
         return this.getBlock().getShape(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public VoxelShape getCollisionShape(BlockGetter var1, BlockPos var2) {
         return this.cache != null ? this.cache.collisionShape : this.getCollisionShape(â˜ƒ, â˜ƒ, CollisionContext.empty());
      }

      public VoxelShape getCollisionShape(BlockGetter var1, BlockPos var2, CollisionContext var3) {
         return this.getBlock().getCollisionShape(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public VoxelShape getBlockSupportShape(BlockGetter var1, BlockPos var2) {
         return this.getBlock().getBlockSupportShape(this.asState(), â˜ƒ, â˜ƒ);
      }

      public VoxelShape getVisualShape(BlockGetter var1, BlockPos var2, CollisionContext var3) {
         return this.getBlock().getVisualShape(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public VoxelShape getInteractionShape(BlockGetter var1, BlockPos var2) {
         return this.getBlock().getInteractionShape(this.asState(), â˜ƒ, â˜ƒ);
      }

      public final boolean entityCanStandOn(BlockGetter var1, BlockPos var2, Entity var3) {
         return this.entityCanStandOnFace(â˜ƒ, â˜ƒ, â˜ƒ, Direction.UP);
      }

      public final boolean entityCanStandOnFace(BlockGetter var1, BlockPos var2, Entity var3, Direction var4) {
         return Block.isFaceFull(this.getCollisionShape(â˜ƒ, â˜ƒ, CollisionContext.of(â˜ƒ)), â˜ƒ);
      }

      public Vec3 getOffset(BlockGetter var1, BlockPos var2) {
         Block â˜ƒ = this.getBlock();
         BlockBehaviour.OffsetType â˜ƒx = â˜ƒ.getOffsetType();
         if (â˜ƒx == BlockBehaviour.OffsetType.NONE) {
            return Vec3.ZERO;
         } else {
            long â˜ƒ = Mth.getSeed(â˜ƒ.getX(), 0, â˜ƒ.getZ());
            float â˜ƒx = â˜ƒ.getMaxHorizontalOffset();
            double â˜ƒxx = Mth.clamp(((double)((float)(â˜ƒ & 15L) / 15.0F) - 0.5) * 0.5, (double)(-â˜ƒx), (double)â˜ƒx);
            double â˜ƒxxx = â˜ƒx == BlockBehaviour.OffsetType.XYZ
               ? ((double)((float)(â˜ƒ >> 4 & 15L) / 15.0F) - 1.0) * (double)â˜ƒ.getMaxVerticalOffset()
               : 0.0;
            double â˜ƒxxxx = Mth.clamp(((double)((float)(â˜ƒ >> 8 & 15L) / 15.0F) - 0.5) * 0.5, (double)(-â˜ƒx), (double)â˜ƒx);
            return new Vec3(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         }
      }

      public boolean triggerEvent(Level var1, BlockPos var2, int var3, int var4) {
         return this.getBlock().triggerEvent(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void neighborChanged(Level var1, BlockPos var2, Block var3, BlockPos var4, boolean var5) {
         this.getBlock().neighborChanged(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public final void updateNeighbourShapes(LevelAccessor var1, BlockPos var2, int var3) {
         this.updateNeighbourShapes(â˜ƒ, â˜ƒ, â˜ƒ, 512);
      }

      public final void updateNeighbourShapes(LevelAccessor var1, BlockPos var2, int var3, int var4) {
         this.getBlock();
         BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

         for(Direction â˜ƒx : BlockBehaviour.UPDATE_SHAPE_ORDER) {
            â˜ƒ.setWithOffset(â˜ƒ, â˜ƒx);
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
            BlockState â˜ƒxxx = â˜ƒxx.updateShape(â˜ƒx.getOpposite(), this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
            Block.updateOrDestroy(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      public final void updateIndirectNeighbourShapes(LevelAccessor var1, BlockPos var2, int var3) {
         this.updateIndirectNeighbourShapes(â˜ƒ, â˜ƒ, â˜ƒ, 512);
      }

      public void updateIndirectNeighbourShapes(LevelAccessor var1, BlockPos var2, int var3, int var4) {
         this.getBlock().updateIndirectNeighbourShapes(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void onPlace(Level var1, BlockPos var2, BlockState var3, boolean var4) {
         this.getBlock().onPlace(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void onRemove(Level var1, BlockPos var2, BlockState var3, boolean var4) {
         this.getBlock().onRemove(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void tick(ServerLevel var1, BlockPos var2, Random var3) {
         this.getBlock().tick(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void randomTick(ServerLevel var1, BlockPos var2, Random var3) {
         this.getBlock().randomTick(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void entityInside(Level var1, BlockPos var2, Entity var3) {
         this.getBlock().entityInside(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void spawnAfterBreak(ServerLevel var1, BlockPos var2, ItemStack var3) {
         this.getBlock().spawnAfterBreak(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public List<ItemStack> getDrops(LootContext.Builder var1) {
         return this.getBlock().getDrops(this.asState(), â˜ƒ);
      }

      public InteractionResult use(Level var1, Player var2, InteractionHand var3, BlockHitResult var4) {
         return this.getBlock().use(this.asState(), â˜ƒ, â˜ƒ.getBlockPos(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void attack(Level var1, BlockPos var2, Player var3) {
         this.getBlock().attack(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean isSuffocating(BlockGetter var1, BlockPos var2) {
         return this.isSuffocating.test(this.asState(), â˜ƒ, â˜ƒ);
      }

      public boolean isViewBlocking(BlockGetter var1, BlockPos var2) {
         return this.isViewBlocking.test(this.asState(), â˜ƒ, â˜ƒ);
      }

      public BlockState updateShape(Direction var1, BlockState var2, LevelAccessor var3, BlockPos var4, BlockPos var5) {
         return this.getBlock().updateShape(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean isPathfindable(BlockGetter var1, BlockPos var2, PathComputationType var3) {
         return this.getBlock().isPathfindable(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean canBeReplaced(BlockPlaceContext var1) {
         return this.getBlock().canBeReplaced(this.asState(), â˜ƒ);
      }

      public boolean canBeReplaced(Fluid var1) {
         return this.getBlock().canBeReplaced(this.asState(), â˜ƒ);
      }

      public boolean canSurvive(LevelReader var1, BlockPos var2) {
         return this.getBlock().canSurvive(this.asState(), â˜ƒ, â˜ƒ);
      }

      public boolean hasPostProcess(BlockGetter var1, BlockPos var2) {
         return this.hasPostProcess.test(this.asState(), â˜ƒ, â˜ƒ);
      }

      @Nullable
      public MenuProvider getMenuProvider(Level var1, BlockPos var2) {
         return this.getBlock().getMenuProvider(this.asState(), â˜ƒ, â˜ƒ);
      }

      public boolean is(Tag<Block> var1) {
         return â˜ƒ.contains(this.getBlock());
      }

      public boolean is(Tag<Block> var1, Predicate<BlockBehaviour.BlockStateBase> var2) {
         return this.is(â˜ƒ) && â˜ƒ.test(this);
      }

      public boolean hasBlockEntity() {
         return this.getBlock() instanceof EntityBlock;
      }

      @Nullable
      public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockEntityType<T> var2) {
         return this.getBlock() instanceof EntityBlock ? ((EntityBlock)this.getBlock()).getTicker(â˜ƒ, this.asState(), â˜ƒ) : null;
      }

      public boolean is(Block var1) {
         return this.getBlock() == â˜ƒ;
      }

      public FluidState getFluidState() {
         return this.getBlock().getFluidState(this.asState());
      }

      public boolean isRandomlyTicking() {
         return this.getBlock().isRandomlyTicking(this.asState());
      }

      public long getSeed(BlockPos var1) {
         return this.getBlock().getSeed(this.asState(), â˜ƒ);
      }

      public SoundType getSoundType() {
         return this.getBlock().getSoundType(this.asState());
      }

      public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
         this.getBlock().onProjectileHit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean isFaceSturdy(BlockGetter var1, BlockPos var2, Direction var3) {
         return this.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ, SupportType.FULL);
      }

      public boolean isFaceSturdy(BlockGetter var1, BlockPos var2, Direction var3, SupportType var4) {
         return this.cache != null ? this.cache.isFaceSturdy(â˜ƒ, â˜ƒ) : â˜ƒ.isSupporting(this.asState(), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public boolean isCollisionShapeFullBlock(BlockGetter var1, BlockPos var2) {
         return this.cache != null ? this.cache.isCollisionShapeFullBlock : this.getBlock().isCollisionShapeFullBlock(this.asState(), â˜ƒ, â˜ƒ);
      }

      protected abstract BlockState asState();

      public boolean requiresCorrectToolForDrops() {
         return this.requiresCorrectToolForDrops;
      }

      static final class Cache {
         private static final Direction[] DIRECTIONS = Direction.values();
         private static final int SUPPORT_TYPE_COUNT = SupportType.values().length;
         protected final boolean solidRender;
         final boolean propagatesSkylightDown;
         final int lightBlock;
         @Nullable
         final VoxelShape[] occlusionShapes;
         protected final VoxelShape collisionShape;
         protected final boolean largeCollisionShape;
         private final boolean[] faceSturdy;
         protected final boolean isCollisionShapeFullBlock;

         Cache(BlockState var1) {
            Block â˜ƒ = â˜ƒ.getBlock();
            this.solidRender = â˜ƒ.isSolidRender(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
            this.propagatesSkylightDown = â˜ƒ.propagatesSkylightDown(â˜ƒ, EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
            this.lightBlock = â˜ƒ.getLightBlock(â˜ƒ, EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
            if (!â˜ƒ.canOcclude()) {
               this.occlusionShapes = null;
            } else {
               this.occlusionShapes = new VoxelShape[DIRECTIONS.length];
               VoxelShape â˜ƒ = â˜ƒ.getOcclusionShape(â˜ƒ, EmptyBlockGetter.INSTANCE, BlockPos.ZERO);

               for(Direction â˜ƒx : DIRECTIONS) {
                  this.occlusionShapes[â˜ƒx.ordinal()] = Shapes.getFaceShape(â˜ƒ, â˜ƒx);
               }
            }

            this.collisionShape = â˜ƒ.getCollisionShape(â˜ƒ, EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
            if (!this.collisionShape.isEmpty() && â˜ƒ.getOffsetType() != BlockBehaviour.OffsetType.NONE) {
               throw new IllegalStateException(
                  String.format("%s has a collision shape and an offset type, but is not marked as dynamicShape in its properties.", Registry.BLOCK.getKey(â˜ƒ))
               );
            } else {
               this.largeCollisionShape = Arrays.stream(Direction.Axis.values())
                  .anyMatch(var1x -> this.collisionShape.min(var1x) < 0.0 || this.collisionShape.max(var1x) > 1.0);
               this.faceSturdy = new boolean[DIRECTIONS.length * SUPPORT_TYPE_COUNT];

               for(Direction â˜ƒ : DIRECTIONS) {
                  for(SupportType â˜ƒx : SupportType.values()) {
                     this.faceSturdy[getFaceSupportIndex(â˜ƒ, â˜ƒx)] = â˜ƒx.isSupporting(â˜ƒ, EmptyBlockGetter.INSTANCE, BlockPos.ZERO, â˜ƒ);
                  }
               }

               this.isCollisionShapeFullBlock = Block.isShapeFullBlock(â˜ƒ.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
            }
         }

         public boolean isFaceSturdy(Direction var1, SupportType var2) {
            return this.faceSturdy[getFaceSupportIndex(â˜ƒ, â˜ƒ)];
         }

         private static int getFaceSupportIndex(Direction var0, SupportType var1) {
            return â˜ƒ.ordinal() * SUPPORT_TYPE_COUNT + â˜ƒ.ordinal();
         }
      }
   }

   public static enum OffsetType {
      NONE,
      XZ,
      XYZ;
   }

   public static class Properties {
      Material material;
      Function<BlockState, MaterialColor> materialColor;
      boolean hasCollision = true;
      SoundType soundType = SoundType.STONE;
      ToIntFunction<BlockState> lightEmission = var0 -> 0;
      float explosionResistance;
      float destroyTime;
      boolean requiresCorrectToolForDrops;
      boolean isRandomlyTicking;
      float friction = 0.6F;
      float speedFactor = 1.0F;
      float jumpFactor = 1.0F;
      ResourceLocation drops;
      boolean canOcclude = true;
      boolean isAir;
      BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn = (var0, var1x, var2x, var3) -> var0.isFaceSturdy(var1x, var2x, Direction.UP)
            && var0.getLightEmission() < 14;
      BlockBehaviour.StatePredicate isRedstoneConductor = (var0, var1x, var2x) -> var0.getMaterial().isSolidBlocking()
            && var0.isCollisionShapeFullBlock(var1x, var2x);
      BlockBehaviour.StatePredicate isSuffocating = (var1x, var2x, var3) -> this.material.blocksMotion() && var1x.isCollisionShapeFullBlock(var2x, var3);
      BlockBehaviour.StatePredicate isViewBlocking = this.isSuffocating;
      BlockBehaviour.StatePredicate hasPostProcess = (var0, var1x, var2x) -> false;
      BlockBehaviour.StatePredicate emissiveRendering = (var0, var1x, var2x) -> false;
      boolean dynamicShape;

      private Properties(Material var1, MaterialColor var2) {
         this(â˜ƒ, var1x -> â˜ƒ);
      }

      private Properties(Material var1, Function<BlockState, MaterialColor> var2) {
         this.material = â˜ƒ;
         this.materialColor = â˜ƒ;
      }

      public static BlockBehaviour.Properties of(Material var0) {
         return of(â˜ƒ, â˜ƒ.getColor());
      }

      public static BlockBehaviour.Properties of(Material var0, DyeColor var1) {
         return of(â˜ƒ, â˜ƒ.getMaterialColor());
      }

      public static BlockBehaviour.Properties of(Material var0, MaterialColor var1) {
         return new BlockBehaviour.Properties(â˜ƒ, â˜ƒ);
      }

      public static BlockBehaviour.Properties of(Material var0, Function<BlockState, MaterialColor> var1) {
         return new BlockBehaviour.Properties(â˜ƒ, â˜ƒ);
      }

      public static BlockBehaviour.Properties copy(BlockBehaviour var0) {
         BlockBehaviour.Properties â˜ƒ = new BlockBehaviour.Properties(â˜ƒ.material, â˜ƒ.properties.materialColor);
         â˜ƒ.material = â˜ƒ.properties.material;
         â˜ƒ.destroyTime = â˜ƒ.properties.destroyTime;
         â˜ƒ.explosionResistance = â˜ƒ.properties.explosionResistance;
         â˜ƒ.hasCollision = â˜ƒ.properties.hasCollision;
         â˜ƒ.isRandomlyTicking = â˜ƒ.properties.isRandomlyTicking;
         â˜ƒ.lightEmission = â˜ƒ.properties.lightEmission;
         â˜ƒ.materialColor = â˜ƒ.properties.materialColor;
         â˜ƒ.soundType = â˜ƒ.properties.soundType;
         â˜ƒ.friction = â˜ƒ.properties.friction;
         â˜ƒ.speedFactor = â˜ƒ.properties.speedFactor;
         â˜ƒ.dynamicShape = â˜ƒ.properties.dynamicShape;
         â˜ƒ.canOcclude = â˜ƒ.properties.canOcclude;
         â˜ƒ.isAir = â˜ƒ.properties.isAir;
         â˜ƒ.requiresCorrectToolForDrops = â˜ƒ.properties.requiresCorrectToolForDrops;
         return â˜ƒ;
      }

      public BlockBehaviour.Properties noCollission() {
         this.hasCollision = false;
         this.canOcclude = false;
         return this;
      }

      public BlockBehaviour.Properties noOcclusion() {
         this.canOcclude = false;
         return this;
      }

      public BlockBehaviour.Properties friction(float var1) {
         this.friction = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties speedFactor(float var1) {
         this.speedFactor = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties jumpFactor(float var1) {
         this.jumpFactor = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties sound(SoundType var1) {
         this.soundType = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties lightLevel(ToIntFunction<BlockState> var1) {
         this.lightEmission = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties strength(float var1, float var2) {
         return this.destroyTime(â˜ƒ).explosionResistance(â˜ƒ);
      }

      public BlockBehaviour.Properties instabreak() {
         return this.strength(0.0F);
      }

      public BlockBehaviour.Properties strength(float var1) {
         this.strength(â˜ƒ, â˜ƒ);
         return this;
      }

      public BlockBehaviour.Properties randomTicks() {
         this.isRandomlyTicking = true;
         return this;
      }

      public BlockBehaviour.Properties dynamicShape() {
         this.dynamicShape = true;
         return this;
      }

      public BlockBehaviour.Properties noDrops() {
         this.drops = BuiltInLootTables.EMPTY;
         return this;
      }

      public BlockBehaviour.Properties dropsLike(Block var1) {
         this.drops = â˜ƒ.getLootTable();
         return this;
      }

      public BlockBehaviour.Properties air() {
         this.isAir = true;
         return this;
      }

      public BlockBehaviour.Properties isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> var1) {
         this.isValidSpawn = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties isRedstoneConductor(BlockBehaviour.StatePredicate var1) {
         this.isRedstoneConductor = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties isSuffocating(BlockBehaviour.StatePredicate var1) {
         this.isSuffocating = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties isViewBlocking(BlockBehaviour.StatePredicate var1) {
         this.isViewBlocking = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties hasPostProcess(BlockBehaviour.StatePredicate var1) {
         this.hasPostProcess = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties emissiveRendering(BlockBehaviour.StatePredicate var1) {
         this.emissiveRendering = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties requiresCorrectToolForDrops() {
         this.requiresCorrectToolForDrops = true;
         return this;
      }

      public BlockBehaviour.Properties color(MaterialColor var1) {
         this.materialColor = var1x -> â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties destroyTime(float var1) {
         this.destroyTime = â˜ƒ;
         return this;
      }

      public BlockBehaviour.Properties explosionResistance(float var1) {
         this.explosionResistance = Math.max(0.0F, â˜ƒ);
         return this;
      }
   }

   public interface StateArgumentPredicate<A> {
      boolean test(BlockState var1, BlockGetter var2, BlockPos var3, A var4);
   }

   public interface StatePredicate {
      boolean test(BlockState var1, BlockGetter var2, BlockPos var3);
   }
}
