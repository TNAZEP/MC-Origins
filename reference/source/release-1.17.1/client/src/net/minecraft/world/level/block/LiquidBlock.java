package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LiquidBlock extends Block implements BucketPickup {
   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
   protected final FlowingFluid fluid;
   private final List<FluidState> stateCache;
   public static final VoxelShape STABLE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   public static final ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS = ImmutableList.of(
      Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST
   );

   protected LiquidBlock(FlowingFluid var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.fluid = â˜ƒ;
      this.stateCache = Lists.<FluidState>newArrayList();
      this.stateCache.add(â˜ƒ.getSource(false));

      for(int â˜ƒ = 1; â˜ƒ < 8; ++â˜ƒ) {
         this.stateCache.add(â˜ƒ.getFlowing(8 - â˜ƒ, false));
      }

      this.stateCache.add(â˜ƒ.getFlowing(8, true));
      this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return â˜ƒ.isAbove(STABLE_SHAPE, â˜ƒ, true) && â˜ƒ.getValue(LEVEL) == 0 && â˜ƒ.canStandOnFluid(â˜ƒ.getFluidState(â˜ƒ.above()), this.fluid)
         ? STABLE_SHAPE
         : Shapes.empty();
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getFluidState().isRandomlyTicking();
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      â˜ƒ.getFluidState().randomTick(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return false;
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return !this.fluid.is(FluidTags.LAVA);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      int â˜ƒ = â˜ƒ.getValue(LEVEL);
      return (FluidState)this.stateCache.get(Math.min(â˜ƒ, 8));
   }

   @Override
   public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
      return â˜ƒ.getFluidState().getType().isSame(this.fluid);
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.INVISIBLE;
   }

   @Override
   public List<ItemStack> getDrops(BlockState var1, LootContext.Builder var2) {
      return Collections.emptyList();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return Shapes.empty();
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (this.shouldSpreadLiquid(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getFluidState().getType(), this.fluid.getTickDelay(â˜ƒ));
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getFluidState().isSource() || â˜ƒ.getFluidState().isSource()) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getFluidState().getType(), this.fluid.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (this.shouldSpreadLiquid(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getFluidState().getType(), this.fluid.getTickDelay(â˜ƒ));
      }
   }

   private boolean shouldSpreadLiquid(Level var1, BlockPos var2, BlockState var3) {
      if (this.fluid.is(FluidTags.LAVA)) {
         boolean â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.SOUL_SOIL);

         for(Direction â˜ƒx : POSSIBLE_FLOW_DIRECTIONS) {
            BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx.getOpposite());
            if (â˜ƒ.getFluidState(â˜ƒxx).is(FluidTags.WATER)) {
               Block â˜ƒxxx = â˜ƒ.getFluidState(â˜ƒ).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
               â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒxxx.defaultBlockState());
               this.fizz(â˜ƒ, â˜ƒ);
               return false;
            }

            if (â˜ƒ && â˜ƒ.getBlockState(â˜ƒxx).is(Blocks.BLUE_ICE)) {
               â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.BASALT.defaultBlockState());
               this.fizz(â˜ƒ, â˜ƒ);
               return false;
            }
         }
      }

      return true;
   }

   private void fizz(LevelAccessor var1, BlockPos var2) {
      â˜ƒ.levelEvent(1501, â˜ƒ, 0);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LEVEL);
   }

   @Override
   public ItemStack pickupBlock(LevelAccessor var1, BlockPos var2, BlockState var3) {
      if (â˜ƒ.getValue(LEVEL) == 0) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 11);
         return new ItemStack(this.fluid.getBucket());
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public Optional<SoundEvent> getPickupSound() {
      return this.fluid.getPickupSound();
   }
}
