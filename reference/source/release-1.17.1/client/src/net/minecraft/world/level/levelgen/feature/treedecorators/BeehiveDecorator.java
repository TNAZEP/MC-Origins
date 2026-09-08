package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class BeehiveDecorator extends TreeDecorator {
   public static final Codec<BeehiveDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .<BeehiveDecorator>xmap(BeehiveDecorator::new, var0 -> var0.probability)
      .codec();
   private final float probability;

   public BeehiveDecorator(float var1) {
      this.probability = â˜ƒ;
   }

   @Override
   protected TreeDecoratorType<?> type() {
      return TreeDecoratorType.BEEHIVE;
   }

   @Override
   public void place(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, List<BlockPos> var4, List<BlockPos> var5) {
      if (!(â˜ƒ.nextFloat() >= this.probability)) {
         Direction â˜ƒ = BeehiveBlock.getRandomOffset(â˜ƒ);
         int â˜ƒx = !â˜ƒ.isEmpty()
            ? Math.max(((BlockPos)â˜ƒ.get(0)).getY() - 1, ((BlockPos)â˜ƒ.get(0)).getY())
            : Math.min(((BlockPos)â˜ƒ.get(0)).getY() + 1 + â˜ƒ.nextInt(3), ((BlockPos)â˜ƒ.get(â˜ƒ.size() - 1)).getY());
         List<BlockPos> â˜ƒxx = (List)â˜ƒ.stream().filter(var1x -> var1x.getY() == â˜ƒ).collect(Collectors.toList());
         if (!â˜ƒxx.isEmpty()) {
            BlockPos â˜ƒxxx = (BlockPos)â˜ƒxx.get(â˜ƒ.nextInt(â˜ƒxx.size()));
            BlockPos â˜ƒxxxx = â˜ƒxxx.relative(â˜ƒ);
            if (Feature.isAir(â˜ƒ, â˜ƒxxxx) && Feature.isAir(â˜ƒ, â˜ƒxxxx.relative(Direction.SOUTH))) {
               â˜ƒ.accept(â˜ƒxxxx, Blocks.BEE_NEST.defaultBlockState().setValue(BeehiveBlock.FACING, Direction.SOUTH));
               â˜ƒ.getBlockEntity(â˜ƒxxxx, BlockEntityType.BEEHIVE).ifPresent(var1x -> {
                  int â˜ƒ = 2 + â˜ƒ.nextInt(2);

                  for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
                     CompoundTag â˜ƒxx = new CompoundTag();
                     â˜ƒxx.putString("id", Registry.ENTITY_TYPE.getKey(EntityType.BEE).toString());
                     var1x.storeBee(â˜ƒxx, â˜ƒ.nextInt(599), false);
                  }
               });
            }
         }
      }
   }
}
