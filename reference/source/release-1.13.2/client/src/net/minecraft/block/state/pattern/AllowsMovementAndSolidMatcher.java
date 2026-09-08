package net.minecraft.block.state.pattern;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AllowsMovementAndSolidMatcher implements IBlockMatcherReaderAware<IBlockState> {
   private static final AllowsMovementAndSolidMatcher field_209403_a = new AllowsMovementAndSolidMatcher();

   public boolean test(@Nullable IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃ != null && !☃.func_185904_a().func_76230_c() && ☃.func_204520_s().func_206888_e();
   }

   public static AllowsMovementAndSolidMatcher func_209402_a() {
      return field_209403_a;
   }
}
