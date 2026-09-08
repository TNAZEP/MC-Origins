package net.minecraft.client.renderer.model;

import java.util.Locale;
import net.minecraft.util.ResourceLocation;

public class ModelResourceLocation extends ResourceLocation {
   private final String field_177519_c;

   protected ModelResourceLocation(String[] var1) {
      super(☃);
      this.field_177519_c = ☃[2].toLowerCase(Locale.ROOT);
   }

   public ModelResourceLocation(String var1) {
      this(func_177517_b(☃));
   }

   public ModelResourceLocation(ResourceLocation var1, String var2) {
      this(☃.toString(), ☃);
   }

   public ModelResourceLocation(String var1, String var2) {
      this(func_177517_b(☃ + '#' + ☃));
   }

   protected static String[] func_177517_b(String var0) {
      String[] ☃ = new String[]{null, ☃, ""};
      int ☃x = ☃.indexOf(35);
      String ☃xx = ☃;
      if (☃x >= 0) {
         ☃[2] = ☃.substring(☃x + 1, ☃.length());
         if (☃x > 1) {
            ☃xx = ☃.substring(0, ☃x);
         }
      }

      System.arraycopy(ResourceLocation.func_195823_b(☃xx, ':'), 0, ☃, 0, 2);
      return ☃;
   }

   public String func_177518_c() {
      return this.field_177519_c;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ instanceof ModelResourceLocation && super.equals(☃)) {
         ModelResourceLocation ☃ = (ModelResourceLocation)☃;
         return this.field_177519_c.equals(☃.field_177519_c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.field_177519_c.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + '#' + this.field_177519_c;
   }
}
