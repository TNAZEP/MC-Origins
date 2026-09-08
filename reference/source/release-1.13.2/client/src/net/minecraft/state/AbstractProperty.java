package net.minecraft.state;

import com.google.common.base.MoreObjects;

public abstract class AbstractProperty<T extends Comparable<T>> implements IProperty<T> {
   private final Class<T> field_177704_a;
   private final String field_177703_b;
   private Integer field_206907_c;

   protected AbstractProperty(String var1, Class<T> var2) {
      this.field_177704_a = ☃;
      this.field_177703_b = ☃;
   }

   @Override
   public String func_177701_a() {
      return this.field_177703_b;
   }

   @Override
   public Class<T> func_177699_b() {
      return this.field_177704_a;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.field_177703_b).add("clazz", this.field_177704_a).add("values", this.func_177700_c()).toString();
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof AbstractProperty)) {
         return false;
      } else {
         AbstractProperty<?> ☃ = (AbstractProperty)☃;
         return this.field_177704_a.equals(☃.field_177704_a) && this.field_177703_b.equals(☃.field_177703_b);
      }
   }

   public final int hashCode() {
      if (this.field_206907_c == null) {
         this.field_206907_c = this.func_206906_c();
      }

      return this.field_206907_c;
   }

   public int func_206906_c() {
      return 31 * this.field_177704_a.hashCode() + this.field_177703_b.hashCode();
   }
}
