package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.Queue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

public class SpongeBlock extends Block {
   public static final int MAX_DEPTH = 6;
   public static final int MAX_COUNT = 64;

   protected SpongeBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.tryAbsorbWater(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      this.tryAbsorbWater(â˜ƒ, â˜ƒ);
      super.neighborChanged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void tryAbsorbWater(Level var1, BlockPos var2) {
      if (this.removeWaterBreadthFirstSearch(â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.WET_SPONGE.defaultBlockState(), 2);
         â˜ƒ.levelEvent(2001, â˜ƒ, Block.getId(Blocks.WATER.defaultBlockState()));
      }
   }

   private boolean removeWaterBreadthFirstSearch(Level var1, BlockPos var2) {
      Queue<Tuple<BlockPos, Integer>> â˜ƒ = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
      â˜ƒ.add(new Tuple<>(â˜ƒ, 0));
      int â˜ƒx = 0;

      while(!â˜ƒ.isEmpty()) {
         Tuple<BlockPos, Integer> â˜ƒxx = (Tuple)â˜ƒ.poll();
         BlockPos â˜ƒxxx = â˜ƒxx.getA();
         int â˜ƒxxxx = â˜ƒxx.getB();

         for(Direction â˜ƒxxxxx : Direction.values()) {
            BlockPos â˜ƒxxxxxx = â˜ƒxxx.relative(â˜ƒxxxxx);
            BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
            FluidState â˜ƒxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxx);
            Material â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.getMaterial();
            if (â˜ƒxxxxxxxx.is(FluidTags.WATER)) {
               if (â˜ƒxxxxxxx.getBlock() instanceof BucketPickup && !((BucketPickup)â˜ƒxxxxxxx.getBlock()).pickupBlock(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxx).isEmpty()) {
                  ++â˜ƒx;
                  if (â˜ƒxxxx < 6) {
                     â˜ƒ.add(new Tuple<>(â˜ƒxxxxxx, â˜ƒxxxx + 1));
                  }
               } else if (â˜ƒxxxxxxx.getBlock() instanceof LiquidBlock) {
                  â˜ƒ.setBlock(â˜ƒxxxxxx, Blocks.AIR.defaultBlockState(), 3);
                  ++â˜ƒx;
                  if (â˜ƒxxxx < 6) {
                     â˜ƒ.add(new Tuple<>(â˜ƒxxxxxx, â˜ƒxxxx + 1));
                  }
               } else if (â˜ƒxxxxxxxxx == Material.WATER_PLANT || â˜ƒxxxxxxxxx == Material.REPLACEABLE_WATER_PLANT) {
                  BlockEntity â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx.hasBlockEntity() ? â˜ƒ.getBlockEntity(â˜ƒxxxxxx) : null;
                  dropResources(â˜ƒxxxxxxx, â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx);
                  â˜ƒ.setBlock(â˜ƒxxxxxx, Blocks.AIR.defaultBlockState(), 3);
                  ++â˜ƒx;
                  if (â˜ƒxxxx < 6) {
                     â˜ƒ.add(new Tuple<>(â˜ƒxxxxxx, â˜ƒxxxx + 1));
                  }
               }
            }
         }

         if (â˜ƒx > 64) {
            break;
         }
      }

      return â˜ƒx > 0;
   }
}
