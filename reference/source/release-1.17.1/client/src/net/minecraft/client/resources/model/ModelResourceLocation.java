package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;

public class ModelResourceLocation extends ResourceLocation {
   @VisibleForTesting
   static final char VARIANT_SEPARATOR = '#';
   private final String variant;

   protected ModelResourceLocation(String[] var1) {
      super(â˜ƒ);
      this.variant = â˜ƒ[2].toLowerCase(Locale.ROOT);
   }

   public ModelResourceLocation(String var1, String var2, String var3) {
      this(new String[]{â˜ƒ, â˜ƒ, â˜ƒ});
   }

   public ModelResourceLocation(String var1) {
      this(decompose(â˜ƒ));
   }

   public ModelResourceLocation(ResourceLocation var1, String var2) {
      this(â˜ƒ.toString(), â˜ƒ);
   }

   public ModelResourceLocation(String var1, String var2) {
      this(decompose(â˜ƒ + "#" + â˜ƒ));
   }

   protected static String[] decompose(String var0) {
      String[] â˜ƒ = new String[]{null, â˜ƒ, ""};
      int â˜ƒx = â˜ƒ.indexOf(35);
      String â˜ƒxx = â˜ƒ;
      if (â˜ƒx >= 0) {
         â˜ƒ[2] = â˜ƒ.substring(â˜ƒx + 1, â˜ƒ.length());
         if (â˜ƒx > 1) {
            â˜ƒxx = â˜ƒ.substring(0, â˜ƒx);
         }
      }

      System.arraycopy(ResourceLocation.decompose(â˜ƒxx, ':'), 0, â˜ƒ, 0, 2);
      return â˜ƒ;
   }

   public String getVariant() {
      return this.variant;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof ModelResourceLocation â˜ƒ && super.equals(â˜ƒ) ? this.variant.equals(â˜ƒ.variant) : false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.variant.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + "#" + this.variant;
   }
}
