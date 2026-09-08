package net.minecraft.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.resources.SimpleResource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenShotHelper {
   private static final Logger field_148261_a = LogManager.getLogger();
   private static final DateFormat field_74295_a = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

   public static void func_148260_a(File var0, int var1, int var2, Framebuffer var3, Consumer<ITextComponent> var4) {
      func_148259_a(☃, null, ☃, ☃, ☃, ☃);
   }

   public static void func_148259_a(File var0, @Nullable String var1, int var2, int var3, Framebuffer var4, Consumer<ITextComponent> var5) {
      NativeImage ☃x = func_198052_a(☃, ☃, ☃);
      File ☃xx = new File(☃, "screenshots");
      ☃xx.mkdir();
      File ☃;
      if (☃ == null) {
         ☃ = func_74290_a(☃xx);
      } else {
         ☃ = new File(☃xx, ☃);
      }

      SimpleResource.field_199031_a
         .execute(
            () -> {
               try {
                  ☃.func_209271_a(☃);
                  ITextComponent ☃ = new TextComponentString(☃.getName())
                     .func_211708_a(TextFormatting.UNDERLINE)
                     .func_211710_a(var1x -> var1x.func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_FILE, ☃.getAbsolutePath())));
                  ☃.accept(new TextComponentTranslation("screenshot.success", ☃));
               } catch (Exception var7xx) {
                  field_148261_a.warn("Couldn't save screenshot", var7xx);
                  ☃.accept(new TextComponentTranslation("screenshot.failure", var7xx.getMessage()));
               } finally {
                  ☃.close();
               }
            }
         );
   }

   public static NativeImage func_198052_a(int var0, int var1, Framebuffer var2) {
      if (OpenGlHelper.func_148822_b()) {
         ☃ = ☃.field_147622_a;
         ☃ = ☃.field_147620_b;
      }

      NativeImage ☃ = new NativeImage(☃, ☃, false);
      if (OpenGlHelper.func_148822_b()) {
         GlStateManager.func_179144_i(☃.field_147617_g);
         ☃.func_195717_a(0, true);
      } else {
         ☃.func_195701_a(true);
      }

      ☃.func_195710_e();
      return ☃;
   }

   private static File func_74290_a(File var0) {
      String ☃ = field_74295_a.format(new Date());
      int ☃x = 1;

      while(true) {
         File ☃xx = new File(☃, ☃ + (☃x == 1 ? "" : "_" + ☃x) + ".png");
         if (!☃xx.exists()) {
            return ☃xx;
         }

         ++☃x;
      }
   }
}
