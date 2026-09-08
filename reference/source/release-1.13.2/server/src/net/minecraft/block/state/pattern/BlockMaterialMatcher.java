package net.minecraft.block.state.pattern;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockMaterialMatcher implements Predicate<IBlockState> {
   private static final BlockMaterialMatcher field_196961_a = new BlockMaterialMatcher(Material.field_151579_a) {
      @Override
      public boolean test(@Nullable IBlockState var1) {
         return ☃ != null && ☃.func_196958_f();
      }
   };
   private final Material field_189887_a;

   private BlockMaterialMatcher(Material var1) {
      this.field_189887_a = ☃;
   }

   public static BlockMaterialMatcher func_189886_a(Material var0) {
      return ☃ == Material.field_151579_a ? field_196961_a : new BlockMaterialMatcher(☃);
   }

   public boolean test(@Nullable IBlockState var1) {
      return ☃ != null && ☃.func_185904_a() == this.field_189887_a;
   }
}
