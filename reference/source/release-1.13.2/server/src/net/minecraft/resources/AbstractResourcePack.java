package net.minecraft.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack {
   private static final Logger field_195772_b = LogManager.getLogger();
   protected final File field_195771_a;

   public AbstractResourcePack(File var1) {
      this.field_195771_a = ☃;
   }

   private static String func_195765_c(ResourcePackType var0, ResourceLocation var1) {
      return String.format("%s/%s/%s", ☃.func_198956_a(), ☃.func_110624_b(), ☃.func_110623_a());
   }

   protected static String func_195767_a(File var0, File var1) {
      return ☃.toURI().relativize(☃.toURI()).getPath();
   }

   @Override
   public InputStream func_195761_a(ResourcePackType var1, ResourceLocation var2) throws IOException {
      return this.func_195766_a(func_195765_c(☃, ☃));
   }

   @Override
   public boolean func_195764_b(ResourcePackType var1, ResourceLocation var2) {
      return this.func_195768_c(func_195765_c(☃, ☃));
   }

   protected abstract InputStream func_195766_a(String var1) throws IOException;

   protected abstract boolean func_195768_c(String var1);

   protected void func_195769_d(String var1) {
      field_195772_b.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", ☃, this.field_195771_a);
   }

   @Nullable
   @Override
   public <T> T func_195760_a(IMetadataSectionSerializer<T> var1) throws IOException {
      return func_195770_a(☃, this.func_195766_a("pack.mcmeta"));
   }

   @Nullable
   public static <T> T func_195770_a(IMetadataSectionSerializer<T> var0, InputStream var1) {
      JsonObject ☃;
      try {
         BufferedReader ☃ = new BufferedReader(new InputStreamReader(☃, StandardCharsets.UTF_8));
         Throwable var4 = null;

         try {
            ☃ = JsonUtils.func_212743_a(☃);
         } catch (Throwable var16) {
            var4 = var16;
            throw var16;
         } finally {
            if (☃ != null) {
               if (var4 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var14) {
                     var4.addSuppressed(var14);
                  }
               } else {
                  ☃.close();
               }
            }
         }
      } catch (JsonParseException | IOException var18) {
         field_195772_b.error("Couldn't load {} metadata", ☃.func_110483_a(), var18);
         return null;
      }

      if (!☃.has(☃.func_110483_a())) {
         return null;
      } else {
         try {
            return ☃.func_195812_a(JsonUtils.func_152754_s(☃, ☃.func_110483_a()));
         } catch (JsonParseException var15) {
            field_195772_b.error("Couldn't load {} metadata", ☃.func_110483_a(), var15);
            return null;
         }
      }
   }

   @Override
   public String func_195762_a() {
      return this.field_195771_a.getName();
   }
}
