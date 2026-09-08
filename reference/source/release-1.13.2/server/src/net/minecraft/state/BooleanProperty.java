package net.minecraft.state;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;

public class BooleanProperty extends AbstractProperty<Boolean> {
   private final ImmutableSet<Boolean> field_177717_a = ImmutableSet.of(true, false);

   protected BooleanProperty(String var1) {
      super(☃, Boolean.class);
   }

   @Override
   public Collection<Boolean> func_177700_c() {
      return this.field_177717_a;
   }

   public static BooleanProperty func_177716_a(String var0) {
      return new BooleanProperty(☃);
   }

   @Override
   public Optional<Boolean> func_185929_b(String var1) {
      return !"true".equals(☃) && !"false".equals(☃) ? Optional.empty() : Optional.of(Boolean.valueOf(☃));
   }

   public String func_177702_a(Boolean var1) {
      return ☃.toString();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ instanceof BooleanProperty && super.equals(☃)) {
         BooleanProperty ☃ = (BooleanProperty)☃;
         return this.field_177717_a.equals(☃.field_177717_a);
      } else {
         return false;
      }
   }

   @Override
   public int func_206906_c() {
      return 31 * super.func_206906_c() + this.field_177717_a.hashCode();
   }
}
