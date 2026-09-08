package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceBlock extends CrossCollisionBlock {
   private final VoxelShape[] occlusionByIndex;

   public FenceBlock(BlockBehaviour.Properties var1) {
      super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(NORTH, Boolean.valueOf(false))
            .setValue(EAST, Boolean.valueOf(false))
            .setValue(SOUTH, Boolean.valueOf(false))
            .setValue(WEST, Boolean.valueOf(false))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
      this.occlusionByIndex = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   @Override
   public VoxelShape getOcclusionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return this.occlusionByIndex[this.getAABBIndex(â˜ƒ)];
   }

   @Override
   public VoxelShape getVisualShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.getShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   public boolean connectsTo(BlockState var1, boolean var2, Direction var3) {
      Block â˜ƒ = â˜ƒ.getBlock();
      boolean â˜ƒx = this.isSameFence(â˜ƒ);
      boolean â˜ƒxx = â˜ƒ instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(â˜ƒ, â˜ƒ);
      return !isExceptionForConnection(â˜ƒ) && â˜ƒ || â˜ƒx || â˜ƒxx;
   }

   private boolean isSameFence(BlockState var1) {
      return â˜ƒ.is(BlockTags.FENCES) && â˜ƒ.is(BlockTags.WOODEN_FENCES) == this.defaultBlockState().is(BlockTags.WOODEN_FENCES);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
         return â˜ƒ.is(Items.LEAD) ? InteractionResult.SUCCESS : InteractionResult.PASS;
      } else {
         return LeadItem.bindPlayerMobs(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      FluidState â˜ƒxx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      BlockPos â˜ƒxxx = â˜ƒx.north();
      BlockPos â˜ƒxxxx = â˜ƒx.east();
      BlockPos â˜ƒxxxxx = â˜ƒx.south();
      BlockPos â˜ƒxxxxxx = â˜ƒx.west();
      BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
      BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
      BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
      BlockState â˜ƒxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
      return super.getStateForPlacement(â˜ƒ)
         .setValue(NORTH, Boolean.valueOf(this.connectsTo(â˜ƒxxxxxxx, â˜ƒxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxx, Direction.SOUTH), Direction.SOUTH)))
         .setValue(EAST, Boolean.valueOf(this.connectsTo(â˜ƒxxxxxxxx, â˜ƒxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxx, Direction.WEST), Direction.WEST)))
         .setValue(SOUTH, Boolean.valueOf(this.connectsTo(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxxx, Direction.NORTH), Direction.NORTH)))
         .setValue(WEST, Boolean.valueOf(this.connectsTo(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxxxx, Direction.EAST), Direction.EAST)))
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxx.getType() == Fluids.WATER));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return â˜ƒ.getAxis().getPlane() == Direction.Plane.HORIZONTAL
         ? â˜ƒ.setValue(
            (Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), Boolean.valueOf(this.connectsTo(â˜ƒ, â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite()), â˜ƒ.getOpposite()))
         )
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
   }
}
