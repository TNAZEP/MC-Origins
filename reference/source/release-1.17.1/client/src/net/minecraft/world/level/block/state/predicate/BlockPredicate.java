package net.minecraft.world.level.block.state.predicate;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPredicate implements Predicate<BlockState> {
   private final Block block;

   public BlockPredicate(Block var1) {
      this.block = â˜ƒ;
   }

   public static BlockPredicate forBlock(Block var0) {
      return new BlockPredicate(â˜ƒ);
   }

   public boolean test(@Nullable BlockState var1) {
      return â˜ƒ != null && â˜ƒ.is(this.block);
   }
}
