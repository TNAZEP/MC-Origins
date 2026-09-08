package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.util.JsonException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.system.MemoryUtil;

public class ShaderLoader {
   private final ShaderLoader.ShaderType field_148061_a;
   private final String field_148059_b;
   private final int field_148060_c;
   private int field_148058_d;

   private ShaderLoader(ShaderLoader.ShaderType var1, int var2, String var3) {
      this.field_148061_a = ☃;
      this.field_148060_c = ☃;
      this.field_148059_b = ☃;
   }

   public void func_148056_a(ShaderManager var1) {
      ++this.field_148058_d;
      OpenGlHelper.func_153178_b(☃.func_147986_h(), this.field_148060_c);
   }

   public void func_195656_a() {
      --this.field_148058_d;
      if (this.field_148058_d <= 0) {
         OpenGlHelper.func_153180_a(this.field_148060_c);
         this.field_148061_a.func_148064_d().remove(this.field_148059_b);
      }
   }

   public String func_148055_a() {
      return this.field_148059_b;
   }

   public static ShaderLoader func_195655_a(IResourceManager var0, ShaderLoader.ShaderType var1, String var2) throws IOException {
      ShaderLoader ☃ = (ShaderLoader)☃.func_148064_d().get(☃);
      if (☃ == null) {
         ResourceLocation ☃x = new ResourceLocation("shaders/program/" + ☃ + ☃.func_148063_b());
         IResource ☃xx = ☃.func_199002_a(☃x);
         ByteBuffer ☃xxx = null;

         try {
            ☃xxx = TextureUtil.func_195724_a(☃xx.func_199027_b());
            int ☃xxxx = ☃xxx.position();
            ☃xxx.rewind();
            int ☃xxxxx = OpenGlHelper.func_153195_b(☃.func_148065_c());
            String ☃xxxxxx = MemoryUtil.memASCII(☃xxx, ☃xxxx);
            OpenGlHelper.func_195918_a(☃xxxxx, ☃xxxxxx);
            OpenGlHelper.func_153170_c(☃xxxxx);
            if (OpenGlHelper.func_153157_c(☃xxxxx, OpenGlHelper.field_153208_p) == 0) {
               String ☃xxxxxxx = StringUtils.trim(OpenGlHelper.func_153158_d(☃xxxxx, 32768));
               JsonException ☃xxxxxxxx = new JsonException("Couldn't compile " + ☃.func_148062_a() + " program: " + ☃xxxxxxx);
               ☃xxxxxxxx.func_151381_b(☃x.func_110623_a());
               throw ☃xxxxxxxx;
            }

            ☃ = new ShaderLoader(☃, ☃xxxxx, ☃);
            ☃.func_148064_d().put(☃, ☃);
         } finally {
            IOUtils.closeQuietly(☃xx);
            if (☃xxx != null) {
               MemoryUtil.memFree(☃xxx);
            }
         }
      }

      return ☃;
   }

   public static enum ShaderType {
      VERTEX("vertex", ".vsh", OpenGlHelper.field_153209_q),
      FRAGMENT("fragment", ".fsh", OpenGlHelper.field_153210_r);

      private final String field_148072_c;
      private final String field_148069_d;
      private final int field_148070_e;
      private final Map<String, ShaderLoader> field_148067_f = Maps.newHashMap();

      private ShaderType(String var3, String var4, int var5) {
         this.field_148072_c = ☃;
         this.field_148069_d = ☃;
         this.field_148070_e = ☃;
      }

      public String func_148062_a() {
         return this.field_148072_c;
      }

      private String func_148063_b() {
         return this.field_148069_d;
      }

      private int func_148065_c() {
         return this.field_148070_e;
      }

      private Map<String, ShaderLoader> func_148064_d() {
         return this.field_148067_f;
      }
   }
}
