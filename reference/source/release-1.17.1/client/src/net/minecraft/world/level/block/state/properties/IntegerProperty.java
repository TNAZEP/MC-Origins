package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class IntegerProperty extends Property<Integer> {
   private final ImmutableSet<Integer> values;

   protected IntegerProperty(String var1, int var2, int var3) {
      super(â˜ƒ, Integer.class);
      if (â˜ƒ < 0) {
         throw new IllegalArgumentException("Min value of " + â˜ƒ + " must be 0 or greater");
      } else if (â˜ƒ <= â˜ƒ) {
         throw new IllegalArgumentException("Max value of " + â˜ƒ + " must be greater than min (" + â˜ƒ + ")");
      } else {
         Set<Integer> â˜ƒ = Sets.newHashSet();

         for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
            â˜ƒ.add(â˜ƒx);
         }

         this.values = ImmutableSet.copyOf(â˜ƒ);
      }
   }

   @Override
   public Collection<Integer> getPossibleValues() {
      return this.values;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof IntegerProperty â˜ƒ && super.equals(â˜ƒ) ? this.values.equals(â˜ƒ.values) : false;
      }
   }

   @Override
   public int generateHashCode() {
      return 31 * super.generateHashCode() + this.values.hashCode();
   }

   public static IntegerProperty create(String var0, int var1, int var2) {
      return new IntegerProperty(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public Optional<Integer> getValue(String var1) {
      try {
         Integer â˜ƒ = Integer.valueOf(â˜ƒ);
         return this.values.contains(â˜ƒ) ? Optional.of(â˜ƒ) : Optional.empty();
      } catch (NumberFormatException var3) {
         return Optional.empty();
      }
   }

   public String getName(Integer var1) {
      return â˜ƒ.toString();
   }
}
