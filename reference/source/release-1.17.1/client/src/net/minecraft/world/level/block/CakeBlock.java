package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CakeBlock extends Block {
   public static final int MAX_BITES = 6;
   public static final IntegerProperty BITES = BlockStateProperties.BITES;
   public static final int FULL_CAKE_SIGNAL = getOutputSignal(0);
   protected static final float AABB_OFFSET = 1.0F;
   protected static final float AABB_SIZE_PER_BITE = 2.0F;
   protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
      Block.box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.box(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.box(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.box(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.box(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.box(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.box(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)
   };

   protected CakeBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(BITES, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE_BY_BITE[â˜ƒ.getValue(BITES)];
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      Item â˜ƒx = â˜ƒ.getItem();
      if (â˜ƒ.is(ItemTags.CANDLES) && â˜ƒ.getValue(BITES) == 0) {
         Block â˜ƒxx = Block.byItem(â˜ƒx);
         if (â˜ƒxx instanceof CandleBlock) {
            if (!â˜ƒ.isCreative()) {
               â˜ƒ.shrink(1);
            }

            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            â˜ƒ.setBlockAndUpdate(â˜ƒ, CandleCakeBlock.byCandle(â˜ƒxx));
            â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_CHANGE, â˜ƒ);
            â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒx));
            return InteractionResult.SUCCESS;
         }
      }

      if (â˜ƒ.isClientSide) {
         if (eat(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).consumesAction()) {
            return InteractionResult.SUCCESS;
         }

         if (â˜ƒ.isEmpty()) {
            return InteractionResult.CONSUME;
         }
      }

      return eat(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected static InteractionResult eat(LevelAccessor var0, BlockPos var1, BlockState var2, Player var3) {
      if (!â˜ƒ.canEat(false)) {
         return InteractionResult.PASS;
      } else {
         â˜ƒ.awardStat(Stats.EAT_CAKE_SLICE);
         â˜ƒ.getFoodData().eat(2, 0.1F);
         int â˜ƒ = â˜ƒ.getValue(BITES);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.EAT, â˜ƒ);
         if (â˜ƒ < 6) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(BITES, Integer.valueOf(â˜ƒ + 1)), 3);
         } else {
            â˜ƒ.removeBlock(â˜ƒ, false);
            â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_DESTROY, â˜ƒ);
         }

         return InteractionResult.SUCCESS;
      }
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
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(BITES);
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return getOutputSignal(â˜ƒ.getValue(BITES));
   }

   public static int getOutputSignal(int var0) {
      return (7 - â˜ƒ) * 2;
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
