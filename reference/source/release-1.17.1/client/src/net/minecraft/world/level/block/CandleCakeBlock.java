package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleCakeBlock extends AbstractCandleBlock {
   public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
   protected static final float AABB_OFFSET = 1.0F;
   protected static final VoxelShape CAKE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);
   protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0, 8.0, 7.0, 9.0, 14.0, 9.0);
   protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
   private static final Map<Block, CandleCakeBlock> BY_CANDLE = Maps.<Block, CandleCakeBlock>newHashMap();
   private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.<Vec3>of(new Vec3(0.5, 1.0, 0.5));

   protected CandleCakeBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)));
      BY_CANDLE.put(â˜ƒ, this);
   }

   @Override
   protected Iterable<Vec3> getParticleOffsets(BlockState var1) {
      return PARTICLE_OFFSETS;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.FLINT_AND_STEEL) || â˜ƒ.is(Items.FIRE_CHARGE)) {
         return InteractionResult.PASS;
      } else if (candleHit(â˜ƒ) && â˜ƒ.getItemInHand(â˜ƒ).isEmpty() && â˜ƒ.getValue(LIT)) {
         extinguish(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         InteractionResult â˜ƒ = CakeBlock.eat(â˜ƒ, â˜ƒ, Blocks.CAKE.defaultBlockState(), â˜ƒ);
         if (â˜ƒ.consumesAction()) {
            dropResources(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return â˜ƒ;
      }
   }

   private static boolean candleHit(BlockHitResult var0) {
      return â˜ƒ.getLocation().y - (double)â˜ƒ.getBlockPos().getY() > 0.5;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LIT);
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(Blocks.CAKE);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.DOWN && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).getMaterial().isSolid();
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return CakeBlock.FULL_CAKE_SIGNAL;
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   public static BlockState byCandle(Block var0) {
      return ((CandleCakeBlock)BY_CANDLE.get(â˜ƒ)).defaultBlockState();
   }

   public static boolean canLight(BlockState var0) {
      return â˜ƒ.is(BlockTags.CANDLE_CAKES, var1 -> var1.hasProperty(LIT) && !â˜ƒ.getValue(LIT));
   }
}
