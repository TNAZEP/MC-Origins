package net.minecraft.util;

import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.FontRenderer;

public class EnchantmentNameParts {
   private static final EnchantmentNameParts field_148338_a = new EnchantmentNameParts();
   private final Random field_148336_b = new Random();
   private final String[] field_148337_c = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale phnglui mglwnafh cthulhu rlyeh wgahnagl fhtagnbaguette"
      .split(" ");

   private EnchantmentNameParts() {
   }

   public static EnchantmentNameParts func_178176_a() {
      return field_148338_a;
   }

   public String func_148334_a(FontRenderer var1, int var2) {
      int ☃ = this.field_148336_b.nextInt(2) + 3;
      String ☃x = "";

      for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
         if (☃xx > 0) {
            ☃x = ☃x + " ";
         }

         ☃x = ☃x + this.field_148337_c[this.field_148336_b.nextInt(this.field_148337_c.length)];
      }

      List<String> ☃xx = ☃.func_78271_c(☃x, ☃);
      return org.apache.commons.lang3.StringUtils.join(☃xx.size() >= 2 ? ☃xx.subList(0, 2) : ☃xx, " ");
   }

   public void func_148335_a(long var1) {
      this.field_148336_b.setSeed(☃);
   }
}
