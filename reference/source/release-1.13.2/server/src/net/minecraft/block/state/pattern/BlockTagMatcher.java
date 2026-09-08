package net.minecraft.block.state.pattern;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockTagMatcher implements IBlockMatcherReaderAware<IBlockState> {
   private final Tag<Block> field_206905_a;

   public BlockTagMatcher(Tag<Block> var1) {
      this.field_206905_a = ☃;
   }

   public static BlockTagMatcher func_206904_a(Tag<Block> var0) {
      return new BlockTagMatcher(☃);
   }

   public boolean test(@Nullable IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃ != null && ☃.func_203425_a(this.field_206905_a);
   }
}
