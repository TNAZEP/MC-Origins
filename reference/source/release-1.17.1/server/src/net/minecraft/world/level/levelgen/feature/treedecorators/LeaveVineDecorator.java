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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;

public class LeaveVineDecorator extends TreeDecorator {
   public static final Codec<LeaveVineDecorator> CODEC = Codec.unit((Supplier<LeaveVineDecorator>)(() -> LeaveVineDecorator.INSTANCE));
   public static final LeaveVineDecorator INSTANCE = new LeaveVineDecorator();

   @Override
   protected TreeDecoratorType<?> type() {
      return TreeDecoratorType.LEAVE_VINE;
   }

   @Override
   public void place(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, List<BlockPos> var4, List<BlockPos> var5) {
      â˜ƒ.forEach(var3x -> {
         if (â˜ƒ.nextInt(4) == 0) {
            BlockPos â˜ƒ = var3x.west();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               addHangingVine(â˜ƒ, â˜ƒ, VineBlock.EAST, â˜ƒ);
            }
         }

         if (â˜ƒ.nextInt(4) == 0) {
            BlockPos â˜ƒ = var3x.east();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               addHangingVine(â˜ƒ, â˜ƒ, VineBlock.WEST, â˜ƒ);
            }
         }

         if (â˜ƒ.nextInt(4) == 0) {
            BlockPos â˜ƒ = var3x.north();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               addHangingVine(â˜ƒ, â˜ƒ, VineBlock.SOUTH, â˜ƒ);
            }
         }

         if (â˜ƒ.nextInt(4) == 0) {
            BlockPos â˜ƒ = var3x.south();
            if (Feature.isAir(â˜ƒ, â˜ƒ)) {
               addHangingVine(â˜ƒ, â˜ƒ, VineBlock.NORTH, â˜ƒ);
            }
         }
      });
   }

   private static void addHangingVine(LevelSimulatedReader var0, BlockPos var1, BooleanProperty var2, BiConsumer<BlockPos, BlockState> var3) {
      placeVine(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒ = 4;

      for(BlockPos var5 = â˜ƒ.below(); Feature.isAir(â˜ƒ, var5) && â˜ƒ > 0; --â˜ƒ) {
         placeVine(â˜ƒ, var5, â˜ƒ);
         var5 = var5.below();
      }
   }
}
