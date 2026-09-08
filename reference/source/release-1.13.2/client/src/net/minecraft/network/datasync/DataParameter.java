package net.minecraft.network.datasync;

public class DataParameter<T> {
   private final int field_187157_a;
   private final DataSerializer<T> field_187158_b;

   public DataParameter(int var1, DataSerializer<T> var2) {
      this.field_187157_a = ☃;
      this.field_187158_b = ☃;
   }

   public int func_187155_a() {
      return this.field_187157_a;
   }

   public DataSerializer<T> func_187156_b() {
      return this.field_187158_b;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         DataParameter<?> ☃ = (DataParameter)☃;
         return this.field_187157_a == ☃.field_187157_a;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_187157_a;
   }
}
