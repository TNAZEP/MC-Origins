package net.minecraft.block.state.pattern;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockMatcher implements Predicate<IBlockState> {
   private final Block field_177644_a;

   public BlockMatcher(Block var1) {
      this.field_177644_a = ☃;
   }

   public static BlockMatcher func_177642_a(Block var0) {
      return new BlockMatcher(☃);
   }

   public boolean test(@Nullable IBlockState var1) {
      return ☃ != null && ☃.func_177230_c() == this.field_177644_a;
   }
}
