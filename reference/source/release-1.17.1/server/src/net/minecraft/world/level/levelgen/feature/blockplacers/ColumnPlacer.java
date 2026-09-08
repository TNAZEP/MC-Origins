package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class ColumnPlacer extends BlockPlacer {
   public static final Codec<ColumnPlacer> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(IntProvider.NON_NEGATIVE_CODEC.fieldOf("size").forGetter(var0x -> var0x.size)).apply(var0, ColumnPlacer::new)
   );
   private final IntProvider size;

   public ColumnPlacer(IntProvider var1) {
      this.size = â˜ƒ;
   }

   @Override
   protected BlockPlacerType<?> type() {
      return BlockPlacerType.COLUMN_PLACER;
   }

   @Override
   public void place(LevelAccessor var1, BlockPos var2, BlockState var3, Random var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      int â˜ƒx = this.size.sample(â˜ƒ);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
         â˜ƒ.move(Direction.UP);
      }
   }
}
