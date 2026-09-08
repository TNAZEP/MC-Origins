package net.minecraft.block.state.pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public final class ReaderAwareMatchers {
   public static <T> IBlockMatcherReaderAware<T> func_202084_a(IBlockMatcherReaderAware<T> var0) {
      return new ReaderAwareMatchers.NotMatcher<>(☃);
   }

   public static <T> IBlockMatcherReaderAware<T> func_202083_b(IBlockMatcherReaderAware<? super T>... var0) {
      return new ReaderAwareMatchers.OrMatcher<>(func_202086_a(☃));
   }

   private static <T> List<T> func_202086_a(T... var0) {
      return func_202085_c(Arrays.asList(☃));
   }

   private static <T> List<T> func_202085_c(Iterable<T> var0) {
      List<T> ☃ = Lists.<T>newArrayList();

      for(T ☃x : ☃) {
         ☃.add(Preconditions.checkNotNull(☃x));
      }

      return ☃;
   }

   static class NotMatcher<T> implements IBlockMatcherReaderAware<T> {
      private final IBlockMatcherReaderAware<T> field_202075_a;

      NotMatcher(IBlockMatcherReaderAware<T> var1) {
         this.field_202075_a = Preconditions.checkNotNull(☃);
      }

      @Override
      public boolean test(@Nullable T var1, IBlockReader var2, BlockPos var3) {
         return !this.field_202075_a.test(☃, ☃, ☃);
      }
   }

   static class OrMatcher<T> implements IBlockMatcherReaderAware<T> {
      private final List<? extends IBlockMatcherReaderAware<? super T>> field_202076_a;

      private OrMatcher(List<? extends IBlockMatcherReaderAware<? super T>> var1) {
         this.field_202076_a = ☃;
      }

      @Override
      public boolean test(@Nullable T var1, IBlockReader var2, BlockPos var3) {
         for(int ☃ = 0; ☃ < this.field_202076_a.size(); ++☃) {
            if (((IBlockMatcherReaderAware)this.field_202076_a.get(☃)).test(☃, ☃, ☃)) {
               return true;
            }
         }

         return false;
      }
   }
}
