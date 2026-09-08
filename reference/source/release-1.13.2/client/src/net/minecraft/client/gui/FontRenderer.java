package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontRenderer implements AutoCloseable {
   private static final Logger field_195437_c = LogManager.getLogger();
   public int field_78288_b = 9;
   public Random field_78289_c = new Random();
   private final TextureManager field_78298_i;
   private final Font field_211127_e;
   private boolean field_78294_m;

   public FontRenderer(TextureManager var1, Font var2) {
      this.field_78298_i = ☃;
      this.field_211127_e = ☃;
   }

   public void func_211568_a(List<IGlyphProvider> var1) {
      this.field_211127_e.func_211570_a(☃);
   }

   public void close() {
      this.field_211127_e.close();
   }

   public int func_175063_a(String var1, float var2, float var3, int var4) {
      GlStateManager.func_179141_d();
      return this.func_180455_b(☃, ☃, ☃, ☃, true);
   }

   public int func_211126_b(String var1, float var2, float var3, int var4) {
      GlStateManager.func_179141_d();
      return this.func_180455_b(☃, ☃, ☃, ☃, false);
   }

   private String func_147647_b(String var1) {
      try {
         Bidi ☃ = new Bidi(new ArabicShaping(8).shape(☃), 127);
         ☃.setReorderingMode(0);
         return ☃.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return ☃;
      }
   }

   private int func_180455_b(String var1, float var2, float var3, int var4, boolean var5) {
      if (☃ == null) {
         return 0;
      } else {
         if (this.field_78294_m) {
            ☃ = this.func_147647_b(☃);
         }

         if ((☃ & -67108864) == 0) {
            ☃ |= -16777216;
         }

         if (☃) {
            this.func_211843_b(☃, ☃, ☃, ☃, true);
         }

         ☃ = this.func_211843_b(☃, ☃, ☃, ☃, false);
         return (int)☃ + (☃ ? 1 : 0);
      }
   }

   private float func_211843_b(String var1, float var2, float var3, int var4, boolean var5) {
      float ☃ = ☃ ? 0.25F : 1.0F;
      float ☃x = (float)(☃ >> 16 & 0xFF) / 255.0F * ☃;
      float ☃xx = (float)(☃ >> 8 & 0xFF) / 255.0F * ☃;
      float ☃xxx = (float)(☃ & 0xFF) / 255.0F * ☃;
      float ☃xxxx = ☃x;
      float ☃xxxxx = ☃xx;
      float ☃xxxxxx = ☃xxx;
      float ☃xxxxxxx = (float)(☃ >> 24 & 0xFF) / 255.0F;
      Tessellator ☃xxxxxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxxxxx = ☃xxxxxxxx.func_178180_c();
      ResourceLocation ☃xxxxxxxxxx = null;
      ☃xxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      boolean ☃xxxxxxxxxxx = false;
      boolean ☃xxxxxxxxxxxx = false;
      boolean ☃xxxxxxxxxxxxx = false;
      boolean ☃xxxxxxxxxxxxxx = false;
      boolean ☃xxxxxxxxxxxxxxx = false;
      List<FontRenderer.Entry> ☃xxxxxxxxxxxxxxxx = Lists.<FontRenderer.Entry>newArrayList();

      for(int ☃xxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxx < ☃.length(); ++☃xxxxxxxxxxxxxxxxx) {
         char ☃xxxxxxxxxxxxxxxxxx = ☃.charAt(☃xxxxxxxxxxxxxxxxx);
         if (☃xxxxxxxxxxxxxxxxxx == 167 && ☃xxxxxxxxxxxxxxxxx + 1 < ☃.length()) {
            TextFormatting ☃xxxxxxxxxxxxxxxxxxx = TextFormatting.func_211165_a(☃.charAt(☃xxxxxxxxxxxxxxxxx + 1));
            if (☃xxxxxxxxxxxxxxxxxxx != null) {
               if (☃xxxxxxxxxxxxxxxxxxx.func_211166_f()) {
                  ☃xxxxxxxxxxx = false;
                  ☃xxxxxxxxxxxx = false;
                  ☃xxxxxxxxxxxxxxx = false;
                  ☃xxxxxxxxxxxxxx = false;
                  ☃xxxxxxxxxxxxx = false;
                  ☃xxxx = ☃x;
                  ☃xxxxx = ☃xx;
                  ☃xxxxxx = ☃xxx;
               }

               if (☃xxxxxxxxxxxxxxxxxxx.func_211163_e() != null) {
                  int ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_211163_e();
                  ☃xxxx = (float)(☃xxxxxxxxxxxxxxxxxxxx >> 16 & 0xFF) / 255.0F * ☃;
                  ☃xxxxx = (float)(☃xxxxxxxxxxxxxxxxxxxx >> 8 & 0xFF) / 255.0F * ☃;
                  ☃xxxxxx = (float)(☃xxxxxxxxxxxxxxxxxxxx & 0xFF) / 255.0F * ☃;
               } else if (☃xxxxxxxxxxxxxxxxxxx == TextFormatting.OBFUSCATED) {
                  ☃xxxxxxxxxxx = true;
               } else if (☃xxxxxxxxxxxxxxxxxxx == TextFormatting.BOLD) {
                  ☃xxxxxxxxxxxx = true;
               } else if (☃xxxxxxxxxxxxxxxxxxx == TextFormatting.STRIKETHROUGH) {
                  ☃xxxxxxxxxxxxxxx = true;
               } else if (☃xxxxxxxxxxxxxxxxxxx == TextFormatting.UNDERLINE) {
                  ☃xxxxxxxxxxxxxx = true;
               } else if (☃xxxxxxxxxxxxxxxxxxx == TextFormatting.ITALIC) {
                  ☃xxxxxxxxxxxxx = true;
               }
            }

            ++☃xxxxxxxxxxxxxxxxx;
         } else {
            IGlyph ☃xxxxxxxxxxxxxxxxxx = this.field_211127_e.func_211184_b(☃xxxxxxxxxxxxxxxxxx);
            TexturedGlyph ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx && ☃xxxxxxxxxxxxxxxxxx != ' '
               ? this.field_211127_e.func_211188_a(☃xxxxxxxxxxxxxxxxxx)
               : this.field_211127_e.func_211187_a(☃xxxxxxxxxxxxxxxxxx);
            ResourceLocation ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_211233_b();
            if (☃xxxxxxxxxxxxxxxxxxxx != null) {
               if (☃xxxxxxxxxx != ☃xxxxxxxxxxxxxxxxxxxx) {
                  ☃xxxxxxxx.func_78381_a();
                  this.field_78298_i.func_110577_a(☃xxxxxxxxxxxxxxxxxxxx);
                  ☃xxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                  ☃xxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxx;
               }

               float ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx ? ☃xxxxxxxxxxxxxxxxxx.getBoldOffset() : 0.0F;
               float ☃xxxxxxxxxxxxxxxxxxxxxx = ☃ ? ☃xxxxxxxxxxxxxxxxxx.getShadowOffset() : 0.0F;
               this.func_212452_a(
                  ☃xxxxxxxxxxxxxxxxxxx,
                  ☃xxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxxxxxxxxxx,
                  ☃ + ☃xxxxxxxxxxxxxxxxxxxxxx,
                  ☃ + ☃xxxxxxxxxxxxxxxxxxxxxx,
                  ☃xxxxxxxxx,
                  ☃xxxx,
                  ☃xxxxx,
                  ☃xxxxxx,
                  ☃xxxxxxx
               );
            }

            float ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxx.getAdvance(☃xxxxxxxxxxxx);
            float ☃xxxxxxxxxxxxxxxxxxx = ☃ ? 1.0F : 0.0F;
            if (☃xxxxxxxxxxxxxxx) {
               ☃xxxxxxxxxxxxxxxx.add(
                  new FontRenderer.Entry(
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx - 1.0F,
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx + (float)this.field_78288_b / 2.0F,
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx,
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx + (float)this.field_78288_b / 2.0F - 1.0F,
                     ☃xxxx,
                     ☃xxxxx,
                     ☃xxxxxx,
                     ☃xxxxxxx
                  )
               );
            }

            if (☃xxxxxxxxxxxxxx) {
               ☃xxxxxxxxxxxxxxxx.add(
                  new FontRenderer.Entry(
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx - 1.0F,
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx + (float)this.field_78288_b,
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx,
                     ☃ + ☃xxxxxxxxxxxxxxxxxxx + (float)this.field_78288_b - 1.0F,
                     ☃xxxx,
                     ☃xxxxx,
                     ☃xxxxxx,
                     ☃xxxxxxx
                  )
               );
            }

            ☃ += ☃xxxxxxxxxxxxxxxxxx;
         }
      }

      ☃xxxxxxxx.func_78381_a();
      if (!☃xxxxxxxxxxxxxxxx.isEmpty()) {
         GlStateManager.func_179090_x();
         ☃xxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181706_f);

         for(FontRenderer.Entry ☃xxxxxxxxxxxxxxxxx : ☃xxxxxxxxxxxxxxxx) {
            ☃xxxxxxxxxxxxxxxxx.func_211168_a(☃xxxxxxxxx);
         }

         ☃xxxxxxxx.func_78381_a();
         GlStateManager.func_179098_w();
      }

      return ☃;
   }

   private void func_212452_a(
      TexturedGlyph var1, boolean var2, boolean var3, float var4, float var5, float var6, BufferBuilder var7, float var8, float var9, float var10, float var11
   ) {
      ☃.func_211234_a(this.field_78298_i, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (☃) {
         ☃.func_211234_a(this.field_78298_i, ☃, ☃ + ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public int func_78256_a(String var1) {
      if (☃ == null) {
         return 0;
      } else {
         float ☃ = 0.0F;
         boolean ☃x = false;

         for(int ☃xx = 0; ☃xx < ☃.length(); ++☃xx) {
            char ☃xxx = ☃.charAt(☃xx);
            if (☃xxx == 167 && ☃xx < ☃.length() - 1) {
               TextFormatting ☃xxxx = TextFormatting.func_211165_a(☃.charAt(++☃xx));
               if (☃xxxx == TextFormatting.BOLD) {
                  ☃x = true;
               } else if (☃xxxx != null && ☃xxxx.func_211166_f()) {
                  ☃x = false;
               }
            } else {
               ☃ += this.field_211127_e.func_211184_b(☃xxx).getAdvance(☃x);
            }
         }

         return MathHelper.func_76123_f(☃);
      }
   }

   private float func_211125_a(char var1) {
      return ☃ == 167 ? 0.0F : (float)MathHelper.func_76123_f(this.field_211127_e.func_211184_b(☃).getAdvance(false));
   }

   public String func_78269_a(String var1, int var2) {
      return this.func_78262_a(☃, ☃, false);
   }

   public String func_78262_a(String var1, int var2, boolean var3) {
      StringBuilder ☃ = new StringBuilder();
      float ☃x = 0.0F;
      int ☃xx = ☃ ? ☃.length() - 1 : 0;
      int ☃xxx = ☃ ? -1 : 1;
      boolean ☃xxxx = false;
      boolean ☃xxxxx = false;

      for(int ☃xxxxxx = ☃xx; ☃xxxxxx >= 0 && ☃xxxxxx < ☃.length() && ☃x < (float)☃; ☃xxxxxx += ☃xxx) {
         char ☃xxxxxxx = ☃.charAt(☃xxxxxx);
         if (☃xxxx) {
            ☃xxxx = false;
            TextFormatting ☃xxxxxxxx = TextFormatting.func_211165_a(☃xxxxxxx);
            if (☃xxxxxxxx == TextFormatting.BOLD) {
               ☃xxxxx = true;
            } else if (☃xxxxxxxx != null && ☃xxxxxxxx.func_211166_f()) {
               ☃xxxxx = false;
            }
         } else if (☃xxxxxxx == 167) {
            ☃xxxx = true;
         } else {
            ☃x += this.func_211125_a(☃xxxxxxx);
            if (☃xxxxx) {
               ++☃x;
            }
         }

         if (☃x > (float)☃) {
            break;
         }

         if (☃) {
            ☃.insert(0, ☃xxxxxxx);
         } else {
            ☃.append(☃xxxxxxx);
         }
      }

      return ☃.toString();
   }

   private String func_78273_d(String var1) {
      while(☃ != null && ☃.endsWith("\n")) {
         ☃ = ☃.substring(0, ☃.length() - 1);
      }

      return ☃;
   }

   public void func_78279_b(String var1, int var2, int var3, int var4, int var5) {
      ☃ = this.func_78273_d(☃);
      this.func_211124_b(☃, ☃, ☃, ☃, ☃);
   }

   private void func_211124_b(String var1, int var2, int var3, int var4, int var5) {
      for(String ☃ : this.func_78271_c(☃, ☃)) {
         float ☃x = (float)☃;
         if (this.field_78294_m) {
            int ☃xx = this.func_78256_a(this.func_147647_b(☃));
            ☃x += (float)(☃ - ☃xx);
         }

         this.func_180455_b(☃, ☃x, (float)☃, ☃, false);
         ☃ += this.field_78288_b;
      }
   }

   public int func_78267_b(String var1, int var2) {
      return this.field_78288_b * this.func_78271_c(☃, ☃).size();
   }

   public void func_78275_b(boolean var1) {
      this.field_78294_m = ☃;
   }

   public List<String> func_78271_c(String var1, int var2) {
      return Arrays.asList(this.func_78280_d(☃, ☃).split("\n"));
   }

   public String func_78280_d(String var1, int var2) {
      String ☃;
      String ☃;
      for(☃ = ""; !☃.isEmpty(); ☃ = ☃ + ☃ + "\n") {
         int ☃ = this.func_78259_e(☃, ☃);
         if (☃.length() <= ☃) {
            return ☃ + ☃;
         }

         ☃ = ☃.substring(0, ☃);
         char ☃ = ☃.charAt(☃);
         boolean ☃x = ☃ == ' ' || ☃ == '\n';
         ☃ = TextFormatting.func_211164_a(☃) + ☃.substring(☃ + (☃x ? 1 : 0));
      }

      return ☃;
   }

   private int func_78259_e(String var1, int var2) {
      int ☃ = Math.max(1, ☃);
      int ☃x = ☃.length();
      float ☃xx = 0.0F;
      int ☃xxx = 0;
      int ☃xxxx = -1;
      boolean ☃xxxxx = false;

      for(boolean ☃xxxxxx = true; ☃xxx < ☃x; ++☃xxx) {
         char ☃xxxxxxx = ☃.charAt(☃xxx);
         switch(☃xxxxxxx) {
            case '\n':
               --☃xxx;
               break;
            case ' ':
               ☃xxxx = ☃xxx;
            default:
               if (☃xx != 0.0F) {
                  ☃xxxxxx = false;
               }

               ☃xx += this.func_211125_a(☃xxxxxxx);
               if (☃xxxxx) {
                  ++☃xx;
               }
               break;
            case '\u00a7':
               if (☃xxx < ☃x - 1) {
                  TextFormatting ☃xxxxxxxx = TextFormatting.func_211165_a(☃.charAt(++☃xxx));
                  if (☃xxxxxxxx == TextFormatting.BOLD) {
                     ☃xxxxx = true;
                  } else if (☃xxxxxxxx != null && ☃xxxxxxxx.func_211166_f()) {
                     ☃xxxxx = false;
                  }
               }
         }

         if (☃xxxxxxx == '\n') {
            ☃xxxx = ++☃xxx;
            break;
         }

         if (☃xx > (float)☃) {
            if (☃xxxxxx) {
               ++☃xxx;
            }
            break;
         }
      }

      return ☃xxx != ☃x && ☃xxxx != -1 && ☃xxxx < ☃xxx ? ☃xxxx : ☃xxx;
   }

   public boolean func_78260_a() {
      return this.field_78294_m;
   }

   static class Entry {
      protected final float field_211169_a;
      protected final float field_211170_b;
      protected final float field_211171_c;
      protected final float field_211172_d;
      protected final float field_211173_e;
      protected final float field_211174_f;
      protected final float field_211175_g;
      protected final float field_211176_h;

      private Entry(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         this.field_211169_a = ☃;
         this.field_211170_b = ☃;
         this.field_211171_c = ☃;
         this.field_211172_d = ☃;
         this.field_211173_e = ☃;
         this.field_211174_f = ☃;
         this.field_211175_g = ☃;
         this.field_211176_h = ☃;
      }

      public void func_211168_a(BufferBuilder var1) {
         ☃.func_181662_b((double)this.field_211169_a, (double)this.field_211170_b, 0.0)
            .func_181666_a(this.field_211173_e, this.field_211174_f, this.field_211175_g, this.field_211176_h)
            .func_181675_d();
         ☃.func_181662_b((double)this.field_211171_c, (double)this.field_211170_b, 0.0)
            .func_181666_a(this.field_211173_e, this.field_211174_f, this.field_211175_g, this.field_211176_h)
            .func_181675_d();
         ☃.func_181662_b((double)this.field_211171_c, (double)this.field_211172_d, 0.0)
            .func_181666_a(this.field_211173_e, this.field_211174_f, this.field_211175_g, this.field_211176_h)
            .func_181675_d();
         ☃.func_181662_b((double)this.field_211169_a, (double)this.field_211172_d, 0.0)
            .func_181666_a(this.field_211173_e, this.field_211174_f, this.field_211175_g, this.field_211176_h)
            .func_181675_d();
      }
   }
}
