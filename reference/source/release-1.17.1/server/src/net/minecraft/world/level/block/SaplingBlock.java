package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaplingBlock extends BushBlock implements BonemealableBlock {
   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
   private final AbstractTreeGrower treeGrower;

   protected SaplingBlock(AbstractTreeGrower var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.treeGrower = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getMaxLocalRawBrightness(â˜ƒ.above()) >= 9 && â˜ƒ.nextInt(7) == 0) {
         this.advanceTree(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void advanceTree(ServerLevel var1, BlockPos var2, BlockState var3, Random var4) {
      if (â˜ƒ.getValue(STAGE) == 0) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.cycle(STAGE), 4);
      } else {
         this.treeGrower.growTree(â˜ƒ, â˜ƒ.getChunkSource().getGenerator(), â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return (double)â˜ƒ.random.nextFloat() < 0.45;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      this.advanceTree(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(STAGE);
   }
}
