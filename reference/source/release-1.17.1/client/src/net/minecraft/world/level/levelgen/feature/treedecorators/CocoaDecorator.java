package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class CocoaDecorator extends TreeDecorator {
   public static final Codec<CocoaDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .<CocoaDecorator>xmap(CocoaDecorator::new, var0 -> var0.probability)
      .codec();
   private final float probability;

   public CocoaDecorator(float var1) {
      this.probability = â˜ƒ;
   }

   @Override
   protected TreeDecoratorType<?> type() {
      return TreeDecoratorType.COCOA;
   }

   @Override
   public void place(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, List<BlockPos> var4, List<BlockPos> var5) {
      if (!(â˜ƒ.nextFloat() >= this.probability)) {
         int â˜ƒ = ((BlockPos)â˜ƒ.get(0)).getY();
         â˜ƒ.stream()
            .filter(var1x -> var1x.getY() - â˜ƒ <= 2)
            .forEach(
               var3x -> {
                  for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
                     if (â˜ƒ.nextFloat() <= 0.25F) {
                        Direction â˜ƒx = â˜ƒ.getOpposite();
                        BlockPos â˜ƒxx = var3x.offset(â˜ƒx.getStepX(), 0, â˜ƒx.getStepZ());
                        if (Feature.isAir(â˜ƒ, â˜ƒxx)) {
                           â˜ƒ.accept(
                              â˜ƒxx,
                              Blocks.COCOA.defaultBlockState().setValue(CocoaBlock.AGE, Integer.valueOf(â˜ƒ.nextInt(3))).setValue(CocoaBlock.FACING, â˜ƒ)
                           );
                        }
                     }
                  }
               }
            );
      }
   }
}
