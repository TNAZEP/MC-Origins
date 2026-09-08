package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AlterGroundDecorator extends TreeDecorator {
   public static final Codec<AlterGroundDecorator> CODEC = BlockStateProvider.CODEC
      .fieldOf("provider")
      .<AlterGroundDecorator>xmap(AlterGroundDecorator::new, var0 -> var0.provider)
      .codec();
   private final BlockStateProvider provider;

   public AlterGroundDecorator(BlockStateProvider var1) {
      this.provider = â˜ƒ;
   }

   @Override
   protected TreeDecoratorType<?> type() {
      return TreeDecoratorType.ALTER_GROUND;
   }

   @Override
   public void place(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, List<BlockPos> var4, List<BlockPos> var5) {
      if (!â˜ƒ.isEmpty()) {
         int â˜ƒ = ((BlockPos)â˜ƒ.get(0)).getY();
         â˜ƒ.stream().filter(var1x -> var1x.getY() == â˜ƒ).forEach(var4x -> {
            this.placeCircle(â˜ƒ, â˜ƒ, â˜ƒ, var4x.west().north());
            this.placeCircle(â˜ƒ, â˜ƒ, â˜ƒ, var4x.east(2).north());
            this.placeCircle(â˜ƒ, â˜ƒ, â˜ƒ, var4x.west().south(2));
            this.placeCircle(â˜ƒ, â˜ƒ, â˜ƒ, var4x.east(2).south(2));

            for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
               int â˜ƒx = â˜ƒ.nextInt(64);
               int â˜ƒxx = â˜ƒx % 8;
               int â˜ƒxxx = â˜ƒx / 8;
               if (â˜ƒxx == 0 || â˜ƒxx == 7 || â˜ƒxxx == 0 || â˜ƒxxx == 7) {
                  this.placeCircle(â˜ƒ, â˜ƒ, â˜ƒ, var4x.offset(-3 + â˜ƒxx, 0, -3 + â˜ƒxxx));
               }
            }
         });
      }
   }

   private void placeCircle(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, BlockPos var4) {
      for(int â˜ƒ = -2; â˜ƒ <= 2; ++â˜ƒ) {
         for(int â˜ƒx = -2; â˜ƒx <= 2; ++â˜ƒx) {
            if (Math.abs(â˜ƒ) != 2 || Math.abs(â˜ƒx) != 2) {
               this.placeBlockAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.offset(â˜ƒ, 0, â˜ƒx));
            }
         }
      }
   }

   private void placeBlockAt(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, BlockPos var4) {
      for(int â˜ƒ = 2; â˜ƒ >= -3; --â˜ƒ) {
         BlockPos â˜ƒx = â˜ƒ.above(â˜ƒ);
         if (Feature.isGrassOrDirt(â˜ƒ, â˜ƒx)) {
            â˜ƒ.accept(â˜ƒx, this.provider.getState(â˜ƒ, â˜ƒ));
            break;
         }

         if (!Feature.isAir(â˜ƒ, â˜ƒx) && â˜ƒ < 0) {
            break;
         }
      }
   }
}
