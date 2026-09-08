package net.minecraft.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Screenshot {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
   private int rowHeight;
   private final DataOutputStream outputStream;
   private final byte[] bytes;
   private final int width;
   private final int height;
   private File file;

   public static void grab(File var0, RenderTarget var1, Consumer<Component> var2) {
      grab(â˜ƒ, null, â˜ƒ, â˜ƒ);
   }

   public static void grab(File var0, @Nullable String var1, RenderTarget var2, Consumer<Component> var3) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> _grab(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         _grab(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static void _grab(File var0, @Nullable String var1, RenderTarget var2, Consumer<Component> var3) {
      NativeImage â˜ƒx = takeScreenshot(â˜ƒ);
      File â˜ƒxx = new File(â˜ƒ, "screenshots");
      â˜ƒxx.mkdir();
      File â˜ƒ;
      if (â˜ƒ == null) {
         â˜ƒ = getFile(â˜ƒxx);
      } else {
         â˜ƒ = new File(â˜ƒxx, â˜ƒ);
      }

      Util.ioPool()
         .execute(
            () -> {
               try {
                  â˜ƒ.writeToFile(â˜ƒ);
                  Component â˜ƒ = new TextComponent(â˜ƒ.getName())
                     .withStyle(ChatFormatting.UNDERLINE)
                     .withStyle(var1x -> var1x.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, â˜ƒ.getAbsolutePath())));
                  â˜ƒ.accept(new TranslatableComponent("screenshot.success", â˜ƒ));
               } catch (Exception var7) {
                  LOGGER.warn("Couldn't save screenshot", var7);
                  â˜ƒ.accept(new TranslatableComponent("screenshot.failure", var7.getMessage()));
               } finally {
                  â˜ƒ.close();
               }
            }
         );
   }

   public static NativeImage takeScreenshot(RenderTarget var0) {
      int â˜ƒ = â˜ƒ.width;
      int â˜ƒx = â˜ƒ.height;
      NativeImage â˜ƒxx = new NativeImage(â˜ƒ, â˜ƒx, false);
      RenderSystem.bindTexture(â˜ƒ.getColorTextureId());
      â˜ƒxx.downloadTexture(0, true);
      â˜ƒxx.flipY();
      return â˜ƒxx;
   }

   private static File getFile(File var0) {
      String â˜ƒ = DATE_FORMAT.format(new Date());
      int â˜ƒx = 1;

      while(true) {
         File â˜ƒxx = new File(â˜ƒ, â˜ƒ + (â˜ƒx == 1 ? "" : "_" + â˜ƒx) + ".png");
         if (!â˜ƒxx.exists()) {
            return â˜ƒxx;
         }

         ++â˜ƒx;
      }
   }

   public Screenshot(File var1, int var2, int var3, int var4) throws IOException {
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.rowHeight = â˜ƒ;
      File â˜ƒ = new File(â˜ƒ, "screenshots");
      â˜ƒ.mkdir();
      String â˜ƒx = "huge_" + DATE_FORMAT.format(new Date());
      int â˜ƒxx = 1;

      while((this.file = new File(â˜ƒ, â˜ƒx + (â˜ƒxx == 1 ? "" : "_" + â˜ƒxx) + ".tga")).exists()) {
         ++â˜ƒxx;
      }

      byte[] â˜ƒxxx = new byte[18];
      â˜ƒxxx[2] = 2;
      â˜ƒxxx[12] = (byte)(â˜ƒ % 256);
      â˜ƒxxx[13] = (byte)(â˜ƒ / 256);
      â˜ƒxxx[14] = (byte)(â˜ƒ % 256);
      â˜ƒxxx[15] = (byte)(â˜ƒ / 256);
      â˜ƒxxx[16] = 24;
      this.bytes = new byte[â˜ƒ * â˜ƒ * 3];
      this.outputStream = new DataOutputStream(new FileOutputStream(this.file));
      this.outputStream.write(â˜ƒxxx);
   }

   public void addRegion(ByteBuffer var1, int var2, int var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ;
      int â˜ƒx = â˜ƒ;
      if (â˜ƒ > this.width - â˜ƒ) {
         â˜ƒ = this.width - â˜ƒ;
      }

      if (â˜ƒ > this.height - â˜ƒ) {
         â˜ƒx = this.height - â˜ƒ;
      }

      this.rowHeight = â˜ƒx;

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒx; ++â˜ƒ) {
         â˜ƒ.position((â˜ƒ - â˜ƒx) * â˜ƒ * 3 + â˜ƒ * â˜ƒ * 3);
         int â˜ƒx = (â˜ƒ + â˜ƒ * this.width) * 3;
         â˜ƒ.get(this.bytes, â˜ƒx, â˜ƒ * 3);
      }
   }

   public void saveRow() throws IOException {
      this.outputStream.write(this.bytes, 0, this.width * 3 * this.rowHeight);
   }

   public File close() throws IOException {
      this.outputStream.close();
      return this.file;
   }
}
