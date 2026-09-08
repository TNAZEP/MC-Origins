package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.function.ToIntFunction;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleBlock extends AbstractCandleBlock implements SimpleWaterloggedBlock {
   public static final int MIN_CANDLES = 1;
   public static final int MAX_CANDLES = 4;
   public static final IntegerProperty CANDLES = BlockStateProperties.CANDLES;
   public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final ToIntFunction<BlockState> LIGHT_EMISSION = var0 -> var0.getValue(LIT) ? 3 * var0.getValue(CANDLES) : 0;
   private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS = Util.make(() -> {
      Int2ObjectMap<List<Vec3>> â˜ƒ = new Int2ObjectOpenHashMap();
      â˜ƒ.defaultReturnValue(ImmutableList.of());
      â˜ƒ.put(1, ImmutableList.<Vec3>of(new Vec3(0.5, 0.5, 0.5)));
      â˜ƒ.put(2, ImmutableList.<Vec3>of(new Vec3(0.375, 0.44, 0.5), new Vec3(0.625, 0.5, 0.44)));
      â˜ƒ.put(3, ImmutableList.<Vec3>of(new Vec3(0.5, 0.313, 0.625), new Vec3(0.375, 0.44, 0.5), new Vec3(0.56, 0.5, 0.44)));
      â˜ƒ.put(4, ImmutableList.<Vec3>of(new Vec3(0.44, 0.313, 0.56), new Vec3(0.625, 0.44, 0.56), new Vec3(0.375, 0.44, 0.375), new Vec3(0.56, 0.5, 0.375)));
      return Int2ObjectMaps.unmodifiable(â˜ƒ);
   });
   private static final VoxelShape ONE_AABB = Block.box(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);
   private static final VoxelShape TWO_AABB = Block.box(5.0, 0.0, 6.0, 11.0, 6.0, 9.0);
   private static final VoxelShape THREE_AABB = Block.box(5.0, 0.0, 6.0, 10.0, 6.0, 11.0);
   private static final VoxelShape FOUR_AABB = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 10.0);

   public CandleBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(CANDLES, Integer.valueOf(1)).setValue(LIT, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.getAbilities().mayBuild && â˜ƒ.getItemInHand(â˜ƒ).isEmpty() && â˜ƒ.getValue(LIT)) {
         extinguish(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      return !â˜ƒ.isSecondaryUseActive() && â˜ƒ.getItemInHand().getItem() == this.asItem() && â˜ƒ.getValue(CANDLES) < 4 ? true : super.canBeReplaced(â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos());
      if (â˜ƒ.is(this)) {
         return â˜ƒ.cycle(CANDLES);
      } else {
         FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
         boolean â˜ƒx = â˜ƒ.getType() == Fluids.WATER;
         return super.getStateForPlacement(â˜ƒ).setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx));
      }
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
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch(â˜ƒ.getValue(CANDLES)) {
         case 1:
         default:
            return ONE_AABB;
         case 2:
            return TWO_AABB;
         case 3:
            return THREE_AABB;
         case 4:
            return FOUR_AABB;
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(CANDLES, LIT, WATERLOGGED);
   }

   @Override
   public boolean placeLiquid(LevelAccessor var1, BlockPos var2, BlockState var3, FluidState var4) {
      if (!â˜ƒ.getValue(WATERLOGGED) && â˜ƒ.getType() == Fluids.WATER) {
         BlockState â˜ƒ = â˜ƒ.setValue(WATERLOGGED, Boolean.valueOf(true));
         if (â˜ƒ.getValue(LIT)) {
            extinguish(null, â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
         }

         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒ.getType(), â˜ƒ.getType().getTickDelay(â˜ƒ));
         return true;
      } else {
         return false;
      }
   }

   public static boolean canLight(BlockState var0) {
      return â˜ƒ.is(BlockTags.CANDLES, var0x -> var0x.hasProperty(LIT) && var0x.hasProperty(WATERLOGGED)) && !â˜ƒ.getValue(LIT) && !â˜ƒ.getValue(WATERLOGGED);
   }

   @Override
   protected Iterable<Vec3> getParticleOffsets(BlockState var1) {
      return (Iterable<Vec3>)PARTICLE_OFFSETS.get(â˜ƒ.getValue(CANDLES));
   }

   @Override
   protected boolean canBeLit(BlockState var1) {
      return !â˜ƒ.getValue(WATERLOGGED) && super.canBeLit(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return Block.canSupportCenter(â˜ƒ, â˜ƒ.below(), Direction.UP);
   }
}
