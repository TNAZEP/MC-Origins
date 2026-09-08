package net.minecraft.resources;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleResource implements IResource {
   private static final Logger field_199884_b = LogManager.getLogger();
   public static final Executor field_199031_a = Executors.newSingleThreadExecutor(
      new ThreadFactoryBuilder()
         .setDaemon(true)
         .setNameFormat("Resource IO {0}")
         .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_199884_b))
         .build()
   );
   private final String field_199032_b;
   private final ResourceLocation field_199033_c;
   private final InputStream field_199034_d;
   private final InputStream field_199035_e;

   public SimpleResource(String var1, ResourceLocation var2, InputStream var3, @Nullable InputStream var4) {
      this.field_199032_b = ☃;
      this.field_199033_c = ☃;
      this.field_199034_d = ☃;
      this.field_199035_e = ☃;
   }

   @Override
   public InputStream func_199027_b() {
      return this.field_199034_d;
   }

   @Override
   public String func_199026_d() {
      return this.field_199032_b;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource ☃ = (SimpleResource)☃;
         if (this.field_199033_c != null ? this.field_199033_c.equals(☃.field_199033_c) : ☃.field_199033_c == null) {
            return this.field_199032_b != null ? this.field_199032_b.equals(☃.field_199032_b) : ☃.field_199032_b == null;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int ☃ = this.field_199032_b != null ? this.field_199032_b.hashCode() : 0;
      return 31 * ☃ + (this.field_199033_c != null ? this.field_199033_c.hashCode() : 0);
   }

   public void close() throws IOException {
      this.field_199034_d.close();
      if (this.field_199035_e != null) {
         this.field_199035_e.close();
      }
   }
}
