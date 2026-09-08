package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlowerPotBlock extends Block {
   private static final Map<Block, Block> POTTED_BY_CONTENT = Maps.<Block, Block>newHashMap();
   public static final float AABB_SIZE = 3.0F;
   protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
   private final Block content;

   public FlowerPotBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.content = â˜ƒ;
      POTTED_BY_CONTENT.put(â˜ƒ, this);
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
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      Item â˜ƒx = â˜ƒ.getItem();
      BlockState â˜ƒxx = (â˜ƒx instanceof BlockItem ? (Block)POTTED_BY_CONTENT.getOrDefault(((BlockItem)â˜ƒx).getBlock(), Blocks.AIR) : Blocks.AIR)
         .defaultBlockState();
      boolean â˜ƒxxx = â˜ƒxx.is(Blocks.AIR);
      boolean â˜ƒxxxx = this.isEmpty();
      if (â˜ƒxxx != â˜ƒxxxx) {
         if (â˜ƒxxxx) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒxx, 3);
            â˜ƒ.awardStat(Stats.POT_FLOWER);
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒ.shrink(1);
            }
         } else {
            ItemStack â˜ƒxxxxx = new ItemStack(this.content);
            if (â˜ƒ.isEmpty()) {
               â˜ƒ.setItemInHand(â˜ƒ, â˜ƒxxxxx);
            } else if (!â˜ƒ.addItem(â˜ƒxxxxx)) {
               â˜ƒ.drop(â˜ƒxxxxx, false);
            }

            â˜ƒ.setBlock(â˜ƒ, Blocks.FLOWER_POT.defaultBlockState(), 3);
         }

         â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_CHANGE, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.CONSUME;
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return this.isEmpty() ? super.getCloneItemStack(â˜ƒ, â˜ƒ, â˜ƒ) : new ItemStack(this.content);
   }

   private boolean isEmpty() {
      return this.content == Blocks.AIR;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.DOWN && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Block getContent() {
      return this.content;
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
