package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GuiUtilRenderComponents {
   public static String func_178909_a(String var0, boolean var1) {
      return !☃ && !Minecraft.func_71410_x().field_71474_y.field_74344_o ? TextFormatting.func_110646_a(☃) : ☃;
   }

   public static List<ITextComponent> func_178908_a(ITextComponent var0, int var1, FontRenderer var2, boolean var3, boolean var4) {
      int ☃ = 0;
      ITextComponent ☃x = new TextComponentString("");
      List<ITextComponent> ☃xx = Lists.<ITextComponent>newArrayList();
      List<ITextComponent> ☃xxx = Lists.<ITextComponent>newArrayList(☃);

      for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
         ITextComponent ☃xxxxx = (ITextComponent)☃xxx.get(☃xxxx);
         String ☃xxxxxx = ☃xxxxx.func_150261_e();
         boolean ☃xxxxxxx = false;
         if (☃xxxxxx.contains("\n")) {
            int ☃xxxxxxxx = ☃xxxxxx.indexOf(10);
            String ☃xxxxxxxxx = ☃xxxxxx.substring(☃xxxxxxxx + 1);
            ☃xxxxxx = ☃xxxxxx.substring(0, ☃xxxxxxxx + 1);
            ITextComponent ☃xxxxxxxxxx = new TextComponentString(☃xxxxxxxxx).func_150255_a(☃xxxxx.func_150256_b().func_150232_l());
            ☃xxx.add(☃xxxx + 1, ☃xxxxxxxxxx);
            ☃xxxxxxx = true;
         }

         String ☃xxxxx = func_178909_a(☃xxxxx.func_150256_b().func_150218_j() + ☃xxxxxx, ☃);
         String ☃xxxxxx = ☃xxxxx.endsWith("\n") ? ☃xxxxx.substring(0, ☃xxxxx.length() - 1) : ☃xxxxx;
         int ☃xxxxxxx = ☃.func_78256_a(☃xxxxxx);
         ITextComponent ☃xxxxxxxx = new TextComponentString(☃xxxxxx).func_150255_a(☃xxxxx.func_150256_b().func_150232_l());
         if (☃ + ☃xxxxxxx > ☃) {
            String ☃xxxxxxxxx = ☃.func_78262_a(☃xxxxx, ☃ - ☃, false);
            String ☃xxxxxxxxxx = ☃xxxxxxxxx.length() < ☃xxxxx.length() ? ☃xxxxx.substring(☃xxxxxxxxx.length()) : null;
            if (☃xxxxxxxxxx != null && !☃xxxxxxxxxx.isEmpty()) {
               int ☃xxxxxxxxxxx = ☃xxxxxxxxx.lastIndexOf(32);
               if (☃xxxxxxxxxxx >= 0 && ☃.func_78256_a(☃xxxxx.substring(0, ☃xxxxxxxxxxx)) > 0) {
                  ☃xxxxxxxxx = ☃xxxxx.substring(0, ☃xxxxxxxxxxx);
                  if (☃) {
                     ++☃xxxxxxxxxxx;
                  }

                  ☃xxxxxxxxxx = ☃xxxxx.substring(☃xxxxxxxxxxx);
               } else if (☃ > 0 && !☃xxxxx.contains(" ")) {
                  ☃xxxxxxxxx = "";
                  ☃xxxxxxxxxx = ☃xxxxx;
               }

               ITextComponent ☃xxxxxxxxxxx = new TextComponentString(☃xxxxxxxxxx).func_150255_a(☃xxxxx.func_150256_b().func_150232_l());
               ☃xxx.add(☃xxxx + 1, ☃xxxxxxxxxxx);
            }

            ☃xxxxxxx = ☃.func_78256_a(☃xxxxxxxxx);
            ☃xxxxxxxx = new TextComponentString(☃xxxxxxxxx);
            ☃xxxxxxxx.func_150255_a(☃xxxxx.func_150256_b().func_150232_l());
            ☃xxxxxxx = true;
         }

         if (☃ + ☃xxxxxxx <= ☃) {
            ☃ += ☃xxxxxxx;
            ☃x.func_150257_a(☃xxxxxxxx);
         } else {
            ☃xxxxxxx = true;
         }

         if (☃xxxxxxx) {
            ☃xx.add(☃x);
            ☃ = 0;
            ☃x = new TextComponentString("");
         }
      }

      ☃xx.add(☃x);
      return ☃xx;
   }
}
