package net.minecraft.client.util;

import com.google.gson.JsonObject;
import java.util.Locale;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.JsonUtils;

public class JsonBlendingMode {
   private static JsonBlendingMode field_148118_a;
   private final int field_148116_b;
   private final int field_148117_c;
   private final int field_148114_d;
   private final int field_148115_e;
   private final int field_148112_f;
   private final boolean field_148113_g;
   private final boolean field_148119_h;

   private JsonBlendingMode(boolean var1, boolean var2, int var3, int var4, int var5, int var6, int var7) {
      this.field_148113_g = ☃;
      this.field_148116_b = ☃;
      this.field_148114_d = ☃;
      this.field_148117_c = ☃;
      this.field_148115_e = ☃;
      this.field_148119_h = ☃;
      this.field_148112_f = ☃;
   }

   public JsonBlendingMode() {
      this(false, true, 1, 0, 1, 0, 32774);
   }

   public JsonBlendingMode(int var1, int var2, int var3) {
      this(false, false, ☃, ☃, ☃, ☃, ☃);
   }

   public JsonBlendingMode(int var1, int var2, int var3, int var4, int var5) {
      this(true, false, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_148109_a() {
      if (!this.equals(field_148118_a)) {
         if (field_148118_a == null || this.field_148119_h != field_148118_a.func_148111_b()) {
            field_148118_a = this;
            if (this.field_148119_h) {
               GlStateManager.func_179084_k();
               return;
            }

            GlStateManager.func_179147_l();
         }

         GlStateManager.func_187398_d(this.field_148112_f);
         if (this.field_148113_g) {
            GlStateManager.func_179120_a(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
         } else {
            GlStateManager.func_179112_b(this.field_148116_b, this.field_148114_d);
         }
      }
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof JsonBlendingMode)) {
         return false;
      } else {
         JsonBlendingMode ☃ = (JsonBlendingMode)☃;
         if (this.field_148112_f != ☃.field_148112_f) {
            return false;
         } else if (this.field_148115_e != ☃.field_148115_e) {
            return false;
         } else if (this.field_148114_d != ☃.field_148114_d) {
            return false;
         } else if (this.field_148119_h != ☃.field_148119_h) {
            return false;
         } else if (this.field_148113_g != ☃.field_148113_g) {
            return false;
         } else if (this.field_148117_c != ☃.field_148117_c) {
            return false;
         } else {
            return this.field_148116_b == ☃.field_148116_b;
         }
      }
   }

   public int hashCode() {
      int ☃ = this.field_148116_b;
      ☃ = 31 * ☃ + this.field_148117_c;
      ☃ = 31 * ☃ + this.field_148114_d;
      ☃ = 31 * ☃ + this.field_148115_e;
      ☃ = 31 * ☃ + this.field_148112_f;
      ☃ = 31 * ☃ + (this.field_148113_g ? 1 : 0);
      return 31 * ☃ + (this.field_148119_h ? 1 : 0);
   }

   public boolean func_148111_b() {
      return this.field_148119_h;
   }

   public static JsonBlendingMode func_148110_a(JsonObject var0) {
      if (☃ == null) {
         return new JsonBlendingMode();
      } else {
         int ☃ = 32774;
         int ☃x = 1;
         int ☃xx = 0;
         int ☃xxx = 1;
         int ☃xxxx = 0;
         boolean ☃xxxxx = true;
         boolean ☃xxxxxx = false;
         if (JsonUtils.func_151205_a(☃, "func")) {
            ☃ = func_148108_a(☃.get("func").getAsString());
            if (☃ != 32774) {
               ☃xxxxx = false;
            }
         }

         if (JsonUtils.func_151205_a(☃, "srcrgb")) {
            ☃x = func_148107_b(☃.get("srcrgb").getAsString());
            if (☃x != 1) {
               ☃xxxxx = false;
            }
         }

         if (JsonUtils.func_151205_a(☃, "dstrgb")) {
            ☃xx = func_148107_b(☃.get("dstrgb").getAsString());
            if (☃xx != 0) {
               ☃xxxxx = false;
            }
         }

         if (JsonUtils.func_151205_a(☃, "srcalpha")) {
            ☃xxx = func_148107_b(☃.get("srcalpha").getAsString());
            if (☃xxx != 1) {
               ☃xxxxx = false;
            }

            ☃xxxxxx = true;
         }

         if (JsonUtils.func_151205_a(☃, "dstalpha")) {
            ☃xxxx = func_148107_b(☃.get("dstalpha").getAsString());
            if (☃xxxx != 0) {
               ☃xxxxx = false;
            }

            ☃xxxxxx = true;
         }

         if (☃xxxxx) {
            return new JsonBlendingMode();
         } else {
            return ☃xxxxxx ? new JsonBlendingMode(☃x, ☃xx, ☃xxx, ☃xxxx, ☃) : new JsonBlendingMode(☃x, ☃xx, ☃);
         }
      }
   }

   private static int func_148108_a(String var0) {
      String ☃ = ☃.trim().toLowerCase(Locale.ROOT);
      if ("add".equals(☃)) {
         return 32774;
      } else if ("subtract".equals(☃)) {
         return 32778;
      } else if ("reversesubtract".equals(☃)) {
         return 32779;
      } else if ("reverse_subtract".equals(☃)) {
         return 32779;
      } else if ("min".equals(☃)) {
         return 32775;
      } else {
         return "max".equals(☃) ? 32776 : 32774;
      }
   }

   private static int func_148107_b(String var0) {
      String ☃ = ☃.trim().toLowerCase(Locale.ROOT);
      ☃ = ☃.replaceAll("_", "");
      ☃ = ☃.replaceAll("one", "1");
      ☃ = ☃.replaceAll("zero", "0");
      ☃ = ☃.replaceAll("minus", "-");
      if ("0".equals(☃)) {
         return 0;
      } else if ("1".equals(☃)) {
         return 1;
      } else if ("srccolor".equals(☃)) {
         return 768;
      } else if ("1-srccolor".equals(☃)) {
         return 769;
      } else if ("dstcolor".equals(☃)) {
         return 774;
      } else if ("1-dstcolor".equals(☃)) {
         return 775;
      } else if ("srcalpha".equals(☃)) {
         return 770;
      } else if ("1-srcalpha".equals(☃)) {
         return 771;
      } else if ("dstalpha".equals(☃)) {
         return 772;
      } else {
         return "1-dstalpha".equals(☃) ? 773 : -1;
      }
   }
}
