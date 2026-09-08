package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneTorchBlock extends TorchBlock {
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   private static final Map<BlockGetter, List<RedstoneTorchBlock.Toggle>> RECENT_TOGGLES = new WeakHashMap();
   public static final int RECENT_TOGGLE_TIMER = 60;
   public static final int MAX_RECENT_TOGGLES = 8;
   public static final int RESTART_DELAY = 160;
   private static final int TOGGLE_DELAY = 2;

   protected RedstoneTorchBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, DustParticleOptions.REDSTONE);
      this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(true)));
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      for(Direction â˜ƒ : Direction.values()) {
         â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒ), this);
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ) {
         for(Direction â˜ƒ : Direction.values()) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒ), this);
         }
      }
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(LIT) && Direction.UP != â˜ƒ ? 15 : 0;
   }

   protected boolean hasNeighborSignal(Level var1, BlockPos var2, BlockState var3) {
      return â˜ƒ.hasSignal(â˜ƒ.below(), Direction.DOWN);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      boolean â˜ƒ = this.hasNeighborSignal(â˜ƒ, â˜ƒ, â˜ƒ);
      List<RedstoneTorchBlock.Toggle> â˜ƒx = (List)RECENT_TOGGLES.get(â˜ƒ);

      while(â˜ƒx != null && !â˜ƒx.isEmpty() && â˜ƒ.getGameTime() - ((RedstoneTorchBlock.Toggle)â˜ƒx.get(0)).when > 60L) {
         â˜ƒx.remove(0);
      }

      if (â˜ƒ.getValue(LIT)) {
         if (â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(LIT, Boolean.valueOf(false)), 3);
            if (isToggledTooFrequently(â˜ƒ, â˜ƒ, true)) {
               â˜ƒ.levelEvent(1502, â˜ƒ, 0);
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, â˜ƒ.getBlockState(â˜ƒ).getBlock(), 160);
            }
         }
      } else if (!â˜ƒ && !isToggledTooFrequently(â˜ƒ, â˜ƒ, false)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(LIT, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (â˜ƒ.getValue(LIT) == this.hasNeighborSignal(â˜ƒ, â˜ƒ, â˜ƒ) && !â˜ƒ.getBlockTicks().willTickThisTick(â˜ƒ, this)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 2);
      }
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ == Direction.DOWN ? â˜ƒ.getSignal(â˜ƒ, â˜ƒ, â˜ƒ) : 0;
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         double â˜ƒ = (double)â˜ƒ.getX() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 0.2;
         double â˜ƒx = (double)â˜ƒ.getY() + 0.7 + (â˜ƒ.nextDouble() - 0.5) * 0.2;
         double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 0.2;
         â˜ƒ.addParticle(this.flameParticle, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LIT);
   }

   private static boolean isToggledTooFrequently(Level var0, BlockPos var1, boolean var2) {
      List<RedstoneTorchBlock.Toggle> â˜ƒ = (List)RECENT_TOGGLES.computeIfAbsent(â˜ƒ, var0x -> Lists.newArrayList());
      if (â˜ƒ) {
         â˜ƒ.add(new RedstoneTorchBlock.Toggle(â˜ƒ.immutable(), â˜ƒ.getGameTime()));
      }

      int â˜ƒ = 0;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         RedstoneTorchBlock.Toggle â˜ƒxx = (RedstoneTorchBlock.Toggle)â˜ƒ.get(â˜ƒx);
         if (â˜ƒxx.pos.equals(â˜ƒ)) {
            if (++â˜ƒ >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class Toggle {
      final BlockPos pos;
      final long when;

      public Toggle(BlockPos var1, long var2) {
         this.pos = â˜ƒ;
         this.when = â˜ƒ;
      }
   }
}
