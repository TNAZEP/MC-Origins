package net.minecraft.entity.ai.attributes;

import javax.annotation.Nullable;

public abstract class BaseAttribute implements IAttribute {
   private final IAttribute field_180373_a;
   private final String field_111115_a;
   private final double field_111113_b;
   private boolean field_111114_c;

   protected BaseAttribute(@Nullable IAttribute var1, String var2, double var3) {
      this.field_180373_a = ☃;
      this.field_111115_a = ☃;
      this.field_111113_b = ☃;
      if (☃ == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   @Override
   public String func_111108_a() {
      return this.field_111115_a;
   }

   @Override
   public double func_111110_b() {
      return this.field_111113_b;
   }

   @Override
   public boolean func_111111_c() {
      return this.field_111114_c;
   }

   public BaseAttribute func_111112_a(boolean var1) {
      this.field_111114_c = ☃;
      return this;
   }

   @Nullable
   @Override
   public IAttribute func_180372_d() {
      return this.field_180373_a;
   }

   public int hashCode() {
      return this.field_111115_a.hashCode();
   }

   public boolean equals(Object var1) {
      return ☃ instanceof IAttribute && this.field_111115_a.equals(((IAttribute)☃).func_111108_a());
   }
}
