package net.minecraft.state;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class IntegerProperty extends AbstractProperty<Integer> {
   private final ImmutableSet<Integer> field_177720_a;

   protected IntegerProperty(String var1, int var2, int var3) {
      super(☃, Integer.class);
      if (☃ < 0) {
         throw new IllegalArgumentException("Min value of " + ☃ + " must be 0 or greater");
      } else if (☃ <= ☃) {
         throw new IllegalArgumentException("Max value of " + ☃ + " must be greater than min (" + ☃ + ")");
      } else {
         Set<Integer> ☃ = Sets.newHashSet();

         for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
            ☃.add(☃x);
         }

         this.field_177720_a = ImmutableSet.copyOf(☃);
      }
   }

   @Override
   public Collection<Integer> func_177700_c() {
      return this.field_177720_a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ instanceof IntegerProperty && super.equals(☃)) {
         IntegerProperty ☃ = (IntegerProperty)☃;
         return this.field_177720_a.equals(☃.field_177720_a);
      } else {
         return false;
      }
   }

   @Override
   public int func_206906_c() {
      return 31 * super.func_206906_c() + this.field_177720_a.hashCode();
   }

   public static IntegerProperty func_177719_a(String var0, int var1, int var2) {
      return new IntegerProperty(☃, ☃, ☃);
   }

   @Override
   public Optional<Integer> func_185929_b(String var1) {
      try {
         Integer ☃ = Integer.valueOf(☃);
         return this.field_177720_a.contains(☃) ? Optional.of(☃) : Optional.empty();
      } catch (NumberFormatException var3) {
         return Optional.empty();
      }
   }

   public String func_177702_a(Integer var1) {
      return ☃.toString();
   }
}
