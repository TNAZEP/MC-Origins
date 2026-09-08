package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneLampBlock extends Block {
   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

   public RedstoneLampBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(LIT, Boolean.valueOf(â˜ƒ.getLevel().hasNeighborSignal(â˜ƒ.getClickedPos())));
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide) {
         boolean â˜ƒ = â˜ƒ.getValue(LIT);
         if (â˜ƒ != â˜ƒ.hasNeighborSignal(â˜ƒ)) {
            if (â˜ƒ) {
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 4);
            } else {
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ.cycle(LIT), 2);
            }
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT) && !â˜ƒ.hasNeighborSignal(â˜ƒ)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.cycle(LIT), 2);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LIT);
   }
}
