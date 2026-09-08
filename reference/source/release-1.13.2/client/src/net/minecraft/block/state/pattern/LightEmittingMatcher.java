package net.minecraft.block.state.pattern;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class LightEmittingMatcher implements IBlockMatcherReaderAware<IBlockState> {
   private static final LightEmittingMatcher field_202074_a = new LightEmittingMatcher();

   public boolean test(@Nullable IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃ != null && ☃.func_200016_a(☃, ☃) == 0;
   }

   public static LightEmittingMatcher func_202073_a() {
      return field_202074_a;
   }
}
