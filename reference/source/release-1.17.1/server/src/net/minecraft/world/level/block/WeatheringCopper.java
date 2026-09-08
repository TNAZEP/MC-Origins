package net.minecraft.world.level.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.level.block.state.BlockState;

public interface WeatheringCopper extends ChangeOverTimeBlock<WeatheringCopper.WeatherState> {
   Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(
      () -> ImmutableBiMap.<Block, Block>builder()
            .put(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER)
            .put(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER)
            .put(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER)
            .put(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER)
            .put(Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER)
            .put(Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER)
            .put(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB)
            .put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB)
            .put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB)
            .put(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS)
            .put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS)
            .put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS)
            .build()
   );
   Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> ((BiMap)NEXT_BY_BLOCK.get()).inverse());

   static Optional<Block> getPrevious(Block var0) {
      return Optional.ofNullable((Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get(â˜ƒ));
   }

   static Block getFirst(Block var0) {
      Block â˜ƒ = â˜ƒ;

      for(Block â˜ƒx = (Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get(â˜ƒ); â˜ƒx != null; â˜ƒx = (Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get(â˜ƒx)) {
         â˜ƒ = â˜ƒx;
      }

      return â˜ƒ;
   }

   static Optional<BlockState> getPrevious(BlockState var0) {
      return getPrevious(â˜ƒ.getBlock()).map(var1 -> var1.withPropertiesOf(â˜ƒ));
   }

   static Optional<Block> getNext(Block var0) {
      return Optional.ofNullable((Block)((BiMap)NEXT_BY_BLOCK.get()).get(â˜ƒ));
   }

   static BlockState getFirst(BlockState var0) {
      return getFirst(â˜ƒ.getBlock()).withPropertiesOf(â˜ƒ);
   }

   @Override
   default Optional<BlockState> getNext(BlockState var1) {
      return getNext(â˜ƒ.getBlock()).map(var1x -> var1x.withPropertiesOf(â˜ƒ));
   }

   @Override
   default float getChanceModifier() {
      return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
   }

   public static enum WeatherState {
      UNAFFECTED,
      EXPOSED,
      WEATHERED,
      OXIDIZED;
   }
}
