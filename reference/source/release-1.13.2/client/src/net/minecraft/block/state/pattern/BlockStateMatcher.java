package net.minecraft.block.state.pattern;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;

public class BlockStateMatcher implements Predicate<IBlockState> {
   public static final Predicate<IBlockState> field_185928_a = var0 -> true;
   private final StateContainer<Block, IBlockState> field_177641_a;
   private final Map<IProperty<?>, Predicate<Object>> field_177640_b = Maps.newHashMap();

   private BlockStateMatcher(StateContainer<Block, IBlockState> var1) {
      this.field_177641_a = ☃;
   }

   public static BlockStateMatcher func_177638_a(Block var0) {
      return new BlockStateMatcher(☃.func_176194_O());
   }

   public boolean test(@Nullable IBlockState var1) {
      if (☃ != null && ☃.func_177230_c().equals(this.field_177641_a.func_177622_c())) {
         if (this.field_177640_b.isEmpty()) {
            return true;
         } else {
            for(Entry<IProperty<?>, Predicate<Object>> ☃ : this.field_177640_b.entrySet()) {
               if (!this.func_185927_a(☃, (IProperty)☃.getKey(), (Predicate<Object>)☃.getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected <T extends Comparable<T>> boolean func_185927_a(IBlockState var1, IProperty<T> var2, Predicate<Object> var3) {
      T ☃ = ☃.func_177229_b(☃);
      return ☃.test(☃);
   }

   public <V extends Comparable<V>> BlockStateMatcher func_201028_a(IProperty<V> var1, Predicate<Object> var2) {
      if (!this.field_177641_a.func_177623_d().contains(☃)) {
         throw new IllegalArgumentException(this.field_177641_a + " cannot support property " + ☃);
      } else {
         this.field_177640_b.put(☃, ☃);
         return this;
      }
   }
}
