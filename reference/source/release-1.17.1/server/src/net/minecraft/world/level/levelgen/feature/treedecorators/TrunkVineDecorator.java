package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class TrunkVineDecorator extends TreeDecorator {
   public static final Codec<TrunkVineDecorator> CODEC = Codec.unit((Supplier<TrunkVineDecorator>)(() -> TrunkVineDecorator.INSTANCE));
   public static final TrunkVineDecorator INSTANCE = new TrunkVineDecorator();

   @Override
   protected TreeDecoratorType<?> type() {
      return TreeDecoratorType.TRUNK_VINE;
   }

   @Override
   public void place(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, List<BlockPos> var4, List<BlockPos> var5) {
      â˜ƒ.forEach(var3x -> {
         if (â˜ƒ.nextInt(3) > 0) {
            BlockPos â˜ƒ = var3x.west();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               placeVine(â˜ƒ, â˜ƒ, VineBlock.EAST);
            }
         }

         if (â˜ƒ.nextInt(3) > 0) {
            BlockPos â˜ƒ = var3x.east();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               placeVine(â˜ƒ, â˜ƒ, VineBlock.WEST);
            }
         }

         if (â˜ƒ.nextInt(3) > 0) {
            BlockPos â˜ƒ = var3x.north();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               placeVine(â˜ƒ, â˜ƒ, VineBlock.SOUTH);
            }
         }

         if (â˜ƒ.nextInt(3) > 0) {
            BlockPos â˜ƒ = var3x.south();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               placeVine(â˜ƒ, â˜ƒ, VineBlock.NORTH);
            }
         }
      });
   }
}
