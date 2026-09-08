package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderManager implements AutoCloseable {
   private static final Logger field_148003_a = LogManager.getLogger();
   private static final ShaderDefault field_148001_b = new ShaderDefault();
   private static ShaderManager field_148002_c;
   private static int field_147999_d = -1;
   private final Map<String, Object> field_147997_f = Maps.newHashMap();
   private final List<String> field_147998_g = Lists.newArrayList();
   private final List<Integer> field_148010_h = Lists.newArrayList();
   private final List<ShaderUniform> field_148011_i = Lists.<ShaderUniform>newArrayList();
   private final List<Integer> field_148008_j = Lists.newArrayList();
   private final Map<String, ShaderUniform> field_148009_k = Maps.newHashMap();
   private final int field_148006_l;
   private final String field_148007_m;
   private final boolean field_148004_n;
   private boolean field_148005_o;
   private final JsonBlendingMode field_148016_p;
   private final List<Integer> field_148015_q;
   private final List<String> field_148014_r;
   private final ShaderLoader field_148013_s;
   private final ShaderLoader field_148012_t;

   public ShaderManager(IResourceManager var1, String var2) throws IOException {
      ResourceLocation ☃ = new ResourceLocation("shaders/program/" + ☃ + ".json");
      this.field_148007_m = ☃;
      IResource ☃x = null;

      try {
         ☃x = ☃.func_199002_a(☃);
         JsonObject ☃xx = JsonUtils.func_212743_a(new InputStreamReader(☃x.func_199027_b(), StandardCharsets.UTF_8));
         String ☃xxx = JsonUtils.func_151200_h(☃xx, "vertex");
         String ☃xxxx = JsonUtils.func_151200_h(☃xx, "fragment");
         JsonArray ☃xxxxx = JsonUtils.func_151213_a(☃xx, "samplers", null);
         if (☃xxxxx != null) {
            int ☃xxxxxx = 0;

            for(JsonElement ☃xxxxxxx : ☃xxxxx) {
               try {
                  this.func_147996_a(☃xxxxxxx);
               } catch (Exception var24) {
                  JsonException ☃xxxxxxxx = JsonException.func_151379_a(var24);
                  ☃xxxxxxxx.func_151380_a("samplers[" + ☃xxxxxx + "]");
                  throw ☃xxxxxxxx;
               }

               ++☃xxxxxx;
            }
         }

         JsonArray ☃xx = JsonUtils.func_151213_a(☃xx, "attributes", null);
         if (☃xx != null) {
            int ☃xxx = 0;
            this.field_148015_q = Lists.newArrayListWithCapacity(☃xx.size());
            this.field_148014_r = Lists.newArrayListWithCapacity(☃xx.size());

            for(JsonElement ☃xxxx : ☃xx) {
               try {
                  this.field_148014_r.add(JsonUtils.func_151206_a(☃xxxx, "attribute"));
               } catch (Exception var23) {
                  JsonException ☃xxxxx = JsonException.func_151379_a(var23);
                  ☃xxxxx.func_151380_a("attributes[" + ☃xxx + "]");
                  throw ☃xxxxx;
               }

               ++☃xxx;
            }
         } else {
            this.field_148015_q = null;
            this.field_148014_r = null;
         }

         JsonArray ☃xx = JsonUtils.func_151213_a(☃xx, "uniforms", null);
         if (☃xx != null) {
            int ☃xxx = 0;

            for(JsonElement ☃xxxx : ☃xx) {
               try {
                  this.func_147987_b(☃xxxx);
               } catch (Exception var22) {
                  JsonException ☃xxxxx = JsonException.func_151379_a(var22);
                  ☃xxxxx.func_151380_a("uniforms[" + ☃xxx + "]");
                  throw ☃xxxxx;
               }

               ++☃xxx;
            }
         }

         this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.func_151218_a(☃xx, "blend", null));
         this.field_148004_n = JsonUtils.func_151209_a(☃xx, "cull", true);
         this.field_148013_s = ShaderLoader.func_195655_a(☃, ShaderLoader.ShaderType.VERTEX, ☃xxx);
         this.field_148012_t = ShaderLoader.func_195655_a(☃, ShaderLoader.ShaderType.FRAGMENT, ☃xxxx);
         this.field_148006_l = ShaderLinkHelper.func_148074_b().func_148078_c();
         ShaderLinkHelper.func_148074_b().func_148075_b(this);
         this.func_147990_i();
         if (this.field_148014_r != null) {
            for(String ☃xx : this.field_148014_r) {
               int ☃xxx = OpenGlHelper.func_153164_b(this.field_148006_l, ☃xx);
               this.field_148015_q.add(☃xxx);
            }
         }
      } catch (Exception var25) {
         JsonException ☃xx = JsonException.func_151379_a(var25);
         ☃xx.func_151381_b(☃.func_110623_a());
         throw ☃xx;
      } finally {
         IOUtils.closeQuietly(☃x);
      }

      this.func_147985_d();
   }

   public void close() {
      for(ShaderUniform ☃ : this.field_148011_i) {
         ☃.close();
      }

      ShaderLinkHelper.func_148074_b().func_148077_a(this);
   }

   public void func_147993_b() {
      OpenGlHelper.func_153161_d(0);
      field_147999_d = -1;
      field_148002_c = null;

      for(int ☃ = 0; ☃ < this.field_148010_h.size(); ++☃) {
         if (this.field_147997_f.get(this.field_147998_g.get(☃)) != null) {
            GlStateManager.func_179138_g(OpenGlHelper.field_77478_a + ☃);
            GlStateManager.func_179144_i(0);
         }
      }
   }

   public void func_147995_c() {
      this.field_148005_o = false;
      field_148002_c = this;
      this.field_148016_p.func_148109_a();
      if (this.field_148006_l != field_147999_d) {
         OpenGlHelper.func_153161_d(this.field_148006_l);
         field_147999_d = this.field_148006_l;
      }

      if (this.field_148004_n) {
         GlStateManager.func_179089_o();
      } else {
         GlStateManager.func_179129_p();
      }

      for(int ☃ = 0; ☃ < this.field_148010_h.size(); ++☃) {
         if (this.field_147997_f.get(this.field_147998_g.get(☃)) != null) {
            GlStateManager.func_179138_g(OpenGlHelper.field_77478_a + ☃);
            GlStateManager.func_179098_w();
            Object ☃x = this.field_147997_f.get(this.field_147998_g.get(☃));
            int ☃xx = -1;
            if (☃x instanceof Framebuffer) {
               ☃xx = ((Framebuffer)☃x).field_147617_g;
            } else if (☃x instanceof ITextureObject) {
               ☃xx = ((ITextureObject)☃x).func_110552_b();
            } else if (☃x instanceof Integer) {
               ☃xx = (Integer)☃x;
            }

            if (☃xx != -1) {
               GlStateManager.func_179144_i(☃xx);
               OpenGlHelper.func_153163_f(OpenGlHelper.func_153194_a(this.field_148006_l, (CharSequence)this.field_147998_g.get(☃)), ☃);
            }
         }
      }

      for(ShaderUniform ☃ : this.field_148011_i) {
         ☃.func_148093_b();
      }
   }

   public void func_147985_d() {
      this.field_148005_o = true;
   }

   @Nullable
   public ShaderUniform func_147991_a(String var1) {
      return (ShaderUniform)this.field_148009_k.get(☃);
   }

   public ShaderDefault func_195653_b(String var1) {
      ShaderUniform ☃ = this.func_147991_a(☃);
      return (ShaderDefault)(☃ == null ? field_148001_b : ☃);
   }

   private void func_147990_i() {
      int ☃ = 0;

      for(int ☃x = 0; ☃ < this.field_147998_g.size(); ++☃x) {
         String ☃xx = (String)this.field_147998_g.get(☃);
         int ☃xxx = OpenGlHelper.func_153194_a(this.field_148006_l, ☃xx);
         if (☃xxx == -1) {
            field_148003_a.warn("Shader {}could not find sampler named {} in the specified shader program.", this.field_148007_m, ☃xx);
            this.field_147997_f.remove(☃xx);
            this.field_147998_g.remove(☃x);
            --☃x;
         } else {
            this.field_148010_h.add(☃xxx);
         }

         ++☃;
      }

      for(ShaderUniform ☃x : this.field_148011_i) {
         String ☃xx = ☃x.func_148086_a();
         int ☃xxx = OpenGlHelper.func_153194_a(this.field_148006_l, ☃xx);
         if (☃xxx == -1) {
            field_148003_a.warn("Could not find uniform named {} in the specified shader program.", ☃xx);
         } else {
            this.field_148008_j.add(☃xxx);
            ☃x.func_148084_b(☃xxx);
            this.field_148009_k.put(☃xx, ☃x);
         }
      }
   }

   private void func_147996_a(JsonElement var1) {
      JsonObject ☃ = JsonUtils.func_151210_l(☃, "sampler");
      String ☃x = JsonUtils.func_151200_h(☃, "name");
      if (!JsonUtils.func_151205_a(☃, "file")) {
         this.field_147997_f.put(☃x, null);
         this.field_147998_g.add(☃x);
      } else {
         this.field_147998_g.add(☃x);
      }
   }

   public void func_147992_a(String var1, Object var2) {
      if (this.field_147997_f.containsKey(☃)) {
         this.field_147997_f.remove(☃);
      }

      this.field_147997_f.put(☃, ☃);
      this.func_147985_d();
   }

   private void func_147987_b(JsonElement var1) throws JsonException {
      JsonObject ☃ = JsonUtils.func_151210_l(☃, "uniform");
      String ☃x = JsonUtils.func_151200_h(☃, "name");
      int ☃xx = ShaderUniform.func_148085_a(JsonUtils.func_151200_h(☃, "type"));
      int ☃xxx = JsonUtils.func_151203_m(☃, "count");
      float[] ☃xxxx = new float[Math.max(☃xxx, 16)];
      JsonArray ☃xxxxx = JsonUtils.func_151214_t(☃, "values");
      if (☃xxxxx.size() != ☃xxx && ☃xxxxx.size() > 1) {
         throw new JsonException("Invalid amount of values specified (expected " + ☃xxx + ", found " + ☃xxxxx.size() + ")");
      } else {
         int ☃ = 0;

         for(JsonElement ☃x : ☃xxxxx) {
            try {
               ☃xxxx[☃] = JsonUtils.func_151220_d(☃x, "value");
            } catch (Exception var13) {
               JsonException ☃xx = JsonException.func_151379_a(var13);
               ☃xx.func_151380_a("values[" + ☃ + "]");
               throw ☃xx;
            }

            ++☃;
         }

         if (☃xxx > 1 && ☃xxxxx.size() == 1) {
            while(☃ < ☃xxx) {
               ☃xxxx[☃] = ☃xxxx[0];
               ++☃;
            }
         }

         int ☃x = ☃xxx > 1 && ☃xxx <= 4 && ☃xx < 8 ? ☃xxx - 1 : 0;
         ShaderUniform ☃xx = new ShaderUniform(☃x, ☃xx + ☃x, ☃xxx, this);
         if (☃xx <= 3) {
            ☃xx.func_148083_a((int)☃xxxx[0], (int)☃xxxx[1], (int)☃xxxx[2], (int)☃xxxx[3]);
         } else if (☃xx <= 7) {
            ☃xx.func_148092_b(☃xxxx[0], ☃xxxx[1], ☃xxxx[2], ☃xxxx[3]);
         } else {
            ☃xx.func_148097_a(☃xxxx);
         }

         this.field_148011_i.add(☃xx);
      }
   }

   public ShaderLoader func_147989_e() {
      return this.field_148013_s;
   }

   public ShaderLoader func_147994_f() {
      return this.field_148012_t;
   }

   public int func_147986_h() {
      return this.field_148006_l;
   }
}
