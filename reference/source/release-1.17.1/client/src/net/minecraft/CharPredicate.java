package net.minecraft;

import java.util.Objects;

@FunctionalInterface
public interface CharPredicate {
   boolean test(char var1);

   default CharPredicate and(CharPredicate var1) {
      Objects.requireNonNull(â˜ƒ);
      return var2 -> this.test(var2) && â˜ƒ.test(var2);
   }

   default CharPredicate negate() {
      return var1 -> !this.test(var1);
   }

   default CharPredicate or(CharPredicate var1) {
      Objects.requireNonNull(â˜ƒ);
      return var2 -> this.test(var2) || â˜ƒ.test(var2);
   }
}
