package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class ShaderGroup implements AutoCloseable {
   private final Framebuffer field_148035_a;
   private final IResourceManager field_148033_b;
   private final String field_148034_c;
   private final List<Shader> field_148031_d = Lists.<Shader>newArrayList();
   private final Map<String, Framebuffer> field_148032_e = Maps.newHashMap();
   private final List<Framebuffer> field_148029_f = Lists.<Framebuffer>newArrayList();
   private Matrix4f field_148030_g;
   private int field_148038_h;
   private int field_148039_i;
   private float field_148036_j;
   private float field_148037_k;

   public ShaderGroup(TextureManager var1, IResourceManager var2, Framebuffer var3, ResourceLocation var4) throws IOException, JsonSyntaxException {
      this.field_148033_b = ☃;
      this.field_148035_a = ☃;
      this.field_148036_j = 0.0F;
      this.field_148037_k = 0.0F;
      this.field_148038_h = ☃.field_147621_c;
      this.field_148039_i = ☃.field_147618_d;
      this.field_148034_c = ☃.toString();
      this.func_148024_c();
      this.func_152765_a(☃, ☃);
   }

   private void func_152765_a(TextureManager var1, ResourceLocation var2) throws IOException, JsonSyntaxException {
      IResource ☃ = null;

      try {
         ☃ = this.field_148033_b.func_199002_a(☃);
         JsonObject ☃x = JsonUtils.func_212743_a(new InputStreamReader(☃.func_199027_b(), StandardCharsets.UTF_8));
         if (JsonUtils.func_151202_d(☃x, "targets")) {
            JsonArray ☃xx = ☃x.getAsJsonArray("targets");
            int ☃xxx = 0;

            for(JsonElement ☃xxxx : ☃xx) {
               try {
                  this.func_148027_a(☃xxxx);
               } catch (Exception var17) {
                  JsonException ☃xxxxx = JsonException.func_151379_a(var17);
                  ☃xxxxx.func_151380_a("targets[" + ☃xxx + "]");
                  throw ☃xxxxx;
               }

               ++☃xxx;
            }
         }

         if (JsonUtils.func_151202_d(☃x, "passes")) {
            JsonArray ☃x = ☃x.getAsJsonArray("passes");
            int ☃xx = 0;

            for(JsonElement ☃xxx : ☃x) {
               try {
                  this.func_152764_a(☃, ☃xxx);
               } catch (Exception var16) {
                  JsonException ☃xxxx = JsonException.func_151379_a(var16);
                  ☃xxxx.func_151380_a("passes[" + ☃xx + "]");
                  throw ☃xxxx;
               }

               ++☃xx;
            }
         }
      } catch (Exception var18) {
         JsonException ☃x = JsonException.func_151379_a(var18);
         ☃x.func_151381_b(☃.func_110623_a());
         throw ☃x;
      } finally {
         IOUtils.closeQuietly(☃);
      }
   }

   private void func_148027_a(JsonElement var1) throws JsonException {
      if (JsonUtils.func_151211_a(☃)) {
         this.func_148020_a(☃.getAsString(), this.field_148038_h, this.field_148039_i);
      } else {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "target");
         String ☃x = JsonUtils.func_151200_h(☃, "name");
         int ☃xx = JsonUtils.func_151208_a(☃, "width", this.field_148038_h);
         int ☃xxx = JsonUtils.func_151208_a(☃, "height", this.field_148039_i);
         if (this.field_148032_e.containsKey(☃x)) {
            throw new JsonException(☃x + " is already defined");
         }

         this.func_148020_a(☃x, ☃xx, ☃xxx);
      }
   }

   private void func_152764_a(TextureManager var1, JsonElement var2) throws IOException {
      JsonObject ☃ = JsonUtils.func_151210_l(☃, "pass");
      String ☃x = JsonUtils.func_151200_h(☃, "name");
      String ☃xx = JsonUtils.func_151200_h(☃, "intarget");
      String ☃xxx = JsonUtils.func_151200_h(☃, "outtarget");
      Framebuffer ☃xxxx = this.func_148017_a(☃xx);
      Framebuffer ☃xxxxx = this.func_148017_a(☃xxx);
      if (☃xxxx == null) {
         throw new JsonException("Input target '" + ☃xx + "' does not exist");
      } else if (☃xxxxx == null) {
         throw new JsonException("Output target '" + ☃xxx + "' does not exist");
      } else {
         Shader ☃ = this.func_148023_a(☃x, ☃xxxx, ☃xxxxx);
         JsonArray ☃x = JsonUtils.func_151213_a(☃, "auxtargets", null);
         if (☃x != null) {
            int ☃xx = 0;

            for(JsonElement ☃xxx : ☃x) {
               try {
                  JsonObject ☃xxxx = JsonUtils.func_151210_l(☃xxx, "auxtarget");
                  String ☃xxxxx = JsonUtils.func_151200_h(☃xxxx, "name");
                  String ☃xxxxxx = JsonUtils.func_151200_h(☃xxxx, "id");
                  Framebuffer ☃xxxxxxx = this.func_148017_a(☃xxxxxx);
                  if (☃xxxxxxx == null) {
                     ResourceLocation ☃xxxxxxxx = new ResourceLocation("textures/effect/" + ☃xxxxxx + ".png");
                     IResource ☃xxxxxxxxx = null;

                     try {
                        ☃xxxxxxxxx = this.field_148033_b.func_199002_a(☃xxxxxxxx);
                     } catch (FileNotFoundException var29) {
                        throw new JsonException("Render target or texture '" + ☃xxxxxx + "' does not exist");
                     } finally {
                        IOUtils.closeQuietly(☃xxxxxxxxx);
                     }

                     ☃.func_110577_a(☃xxxxxxxx);
                     ITextureObject var20 = ☃.func_110581_b(☃xxxxxxxx);
                     int var21 = JsonUtils.func_151203_m(☃xxxx, "width");
                     int var22 = JsonUtils.func_151203_m(☃xxxx, "height");
                     boolean ☃xxxxxxxxxx = JsonUtils.func_151212_i(☃xxxx, "bilinear");
                     if (☃xxxxxxxxxx) {
                        GlStateManager.func_187421_b(3553, 10241, 9729);
                        GlStateManager.func_187421_b(3553, 10240, 9729);
                     } else {
                        GlStateManager.func_187421_b(3553, 10241, 9728);
                        GlStateManager.func_187421_b(3553, 10240, 9728);
                     }

                     ☃.func_148041_a(☃xxxxx, var20.func_110552_b(), var21, var22);
                  } else {
                     ☃.func_148041_a(☃xxxxx, ☃xxxxxxx, ☃xxxxxxx.field_147622_a, ☃xxxxxxx.field_147620_b);
                  }
               } catch (Exception var31) {
                  JsonException ☃xxxx = JsonException.func_151379_a(var31);
                  ☃xxxx.func_151380_a("auxtargets[" + ☃xx + "]");
                  throw ☃xxxx;
               }

               ++☃xx;
            }
         }

         JsonArray ☃ = JsonUtils.func_151213_a(☃, "uniforms", null);
         if (☃ != null) {
            int ☃x = 0;

            for(JsonElement ☃xx : ☃) {
               try {
                  this.func_148028_c(☃xx);
               } catch (Exception var28) {
                  JsonException ☃xxx = JsonException.func_151379_a(var28);
                  ☃xxx.func_151380_a("uniforms[" + ☃x + "]");
                  throw ☃xxx;
               }

               ++☃x;
            }
         }
      }
   }

   private void func_148028_c(JsonElement var1) throws JsonException {
      JsonObject ☃ = JsonUtils.func_151210_l(☃, "uniform");
      String ☃x = JsonUtils.func_151200_h(☃, "name");
      ShaderUniform ☃xx = ((Shader)this.field_148031_d.get(this.field_148031_d.size() - 1)).func_148043_c().func_147991_a(☃x);
      if (☃xx == null) {
         throw new JsonException("Uniform '" + ☃x + "' does not exist");
      } else {
         float[] ☃ = new float[4];
         int ☃x = 0;

         for(JsonElement ☃xx : JsonUtils.func_151214_t(☃, "values")) {
            try {
               ☃[☃x] = JsonUtils.func_151220_d(☃xx, "value");
            } catch (Exception var12) {
               JsonException ☃xxx = JsonException.func_151379_a(var12);
               ☃xxx.func_151380_a("values[" + ☃x + "]");
               throw ☃xxx;
            }

            ++☃x;
         }

         switch(☃x) {
            case 0:
            default:
               break;
            case 1:
               ☃xx.func_148090_a(☃[0]);
               break;
            case 2:
               ☃xx.func_148087_a(☃[0], ☃[1]);
               break;
            case 3:
               ☃xx.func_148095_a(☃[0], ☃[1], ☃[2]);
               break;
            case 4:
               ☃xx.func_148081_a(☃[0], ☃[1], ☃[2], ☃[3]);
         }
      }
   }

   public Framebuffer func_177066_a(String var1) {
      return (Framebuffer)this.field_148032_e.get(☃);
   }

   public void func_148020_a(String var1, int var2, int var3) {
      Framebuffer ☃ = new Framebuffer(☃, ☃, true);
      ☃.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
      this.field_148032_e.put(☃, ☃);
      if (☃ == this.field_148038_h && ☃ == this.field_148039_i) {
         this.field_148029_f.add(☃);
      }
   }

   public void close() {
      for(Framebuffer ☃ : this.field_148032_e.values()) {
         ☃.func_147608_a();
      }

      for(Shader ☃ : this.field_148031_d) {
         ☃.close();
      }

      this.field_148031_d.clear();
   }

   public Shader func_148023_a(String var1, Framebuffer var2, Framebuffer var3) throws IOException {
      Shader ☃ = new Shader(this.field_148033_b, ☃, ☃, ☃);
      this.field_148031_d.add(this.field_148031_d.size(), ☃);
      return ☃;
   }

   private void func_148024_c() {
      this.field_148030_g = Matrix4f.func_195877_a((float)this.field_148035_a.field_147622_a, (float)this.field_148035_a.field_147620_b, 0.1F, 1000.0F);
   }

   public void func_148026_a(int var1, int var2) {
      this.field_148038_h = this.field_148035_a.field_147622_a;
      this.field_148039_i = this.field_148035_a.field_147620_b;
      this.func_148024_c();

      for(Shader ☃ : this.field_148031_d) {
         ☃.func_195654_a(this.field_148030_g);
      }

      for(Framebuffer ☃ : this.field_148029_f) {
         ☃.func_147613_a(☃, ☃);
      }
   }

   public void func_148018_a(float var1) {
      if (☃ < this.field_148037_k) {
         this.field_148036_j += 1.0F - this.field_148037_k;
         this.field_148036_j += ☃;
      } else {
         this.field_148036_j += ☃ - this.field_148037_k;
      }

      this.field_148037_k = ☃;

      while(this.field_148036_j > 20.0F) {
         this.field_148036_j -= 20.0F;
      }

      for(Shader ☃ : this.field_148031_d) {
         ☃.func_148042_a(this.field_148036_j / 20.0F);
      }
   }

   public final String func_148022_b() {
      return this.field_148034_c;
   }

   private Framebuffer func_148017_a(String var1) {
      if (☃ == null) {
         return null;
      } else {
         return ☃.equals("minecraft:main") ? this.field_148035_a : (Framebuffer)this.field_148032_e.get(☃);
      }
   }
}
