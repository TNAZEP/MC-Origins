package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;

public class BooleanProperty extends Property<Boolean> {
   private final ImmutableSet<Boolean> values = ImmutableSet.of(true, false);

   protected BooleanProperty(String var1) {
      super(â˜ƒ, Boolean.class);
   }

   @Override
   public Collection<Boolean> getPossibleValues() {
      return this.values;
   }

   public static BooleanProperty create(String var0) {
      return new BooleanProperty(â˜ƒ);
   }

   @Override
   public Optional<Boolean> getValue(String var1) {
      return !"true".equals(â˜ƒ) && !"false".equals(â˜ƒ) ? Optional.empty() : Optional.of(Boolean.valueOf(â˜ƒ));
   }

   public String getName(Boolean var1) {
      return â˜ƒ.toString();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof BooleanProperty â˜ƒ && super.equals(â˜ƒ) ? this.values.equals(â˜ƒ.values) : false;
      }
   }

   @Override
   public int generateHashCode() {
      return 31 * super.generateHashCode() + this.values.hashCode();
   }
}
