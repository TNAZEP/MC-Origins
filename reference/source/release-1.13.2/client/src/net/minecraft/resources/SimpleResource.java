package net.minecraft.resources;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
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
   private boolean field_199036_f;
   private JsonObject field_199037_g;

   public SimpleResource(String var1, ResourceLocation var2, InputStream var3, @Nullable InputStream var4) {
      this.field_199032_b = ☃;
      this.field_199033_c = ☃;
      this.field_199034_d = ☃;
      this.field_199035_e = ☃;
   }

   @Override
   public ResourceLocation func_199029_a() {
      return this.field_199033_c;
   }

   @Override
   public InputStream func_199027_b() {
      return this.field_199034_d;
   }

   @Override
   public boolean func_199030_c() {
      return this.field_199035_e != null;
   }

   @Nullable
   @Override
   public <T> T func_199028_a(IMetadataSectionSerializer<T> var1) {
      if (!this.func_199030_c()) {
         return null;
      } else {
         if (this.field_199037_g == null && !this.field_199036_f) {
            this.field_199036_f = true;
            BufferedReader ☃ = null;

            try {
               ☃ = new BufferedReader(new InputStreamReader(this.field_199035_e, StandardCharsets.UTF_8));
               this.field_199037_g = JsonUtils.func_212743_a(☃);
            } finally {
               IOUtils.closeQuietly(☃);
            }
         }

         if (this.field_199037_g == null) {
            return null;
         } else {
            String ☃ = ☃.func_110483_a();
            return this.field_199037_g.has(☃) ? ☃.func_195812_a(JsonUtils.func_152754_s(this.field_199037_g, ☃)) : null;
         }
      }
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
