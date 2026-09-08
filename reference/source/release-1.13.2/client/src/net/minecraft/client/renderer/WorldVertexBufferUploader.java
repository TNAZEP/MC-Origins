package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class WorldVertexBufferUploader {
   public void func_181679_a(BufferBuilder var1) {
      if (☃.func_178989_h() > 0) {
         VertexFormat ☃ = ☃.func_178973_g();
         int ☃x = ☃.func_177338_f();
         ByteBuffer ☃xx = ☃.func_178966_f();
         List<VertexFormatElement> ☃xxx = ☃.func_177343_g();

         for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
            VertexFormatElement ☃xxxxx = (VertexFormatElement)☃xxx.get(☃xxxx);
            VertexFormatElement.EnumUsage ☃xxxxxx = ☃xxxxx.func_177375_c();
            int ☃xxxxxxx = ☃xxxxx.func_177367_b().func_177397_c();
            int ☃xxxxxxxx = ☃xxxxx.func_177369_e();
            ☃xx.position(☃.func_181720_d(☃xxxx));
            switch(☃xxxxxx) {
               case POSITION:
                  GlStateManager.func_187427_b(☃xxxxx.func_177370_d(), ☃xxxxxxx, ☃x, ☃xx);
                  GlStateManager.func_187410_q(32884);
                  break;
               case UV:
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a + ☃xxxxxxxx);
                  GlStateManager.func_187404_a(☃xxxxx.func_177370_d(), ☃xxxxxxx, ☃x, ☃xx);
                  GlStateManager.func_187410_q(32888);
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                  break;
               case COLOR:
                  GlStateManager.func_187400_c(☃xxxxx.func_177370_d(), ☃xxxxxxx, ☃x, ☃xx);
                  GlStateManager.func_187410_q(32886);
                  break;
               case NORMAL:
                  GlStateManager.func_187446_a(☃xxxxxxx, ☃x, ☃xx);
                  GlStateManager.func_187410_q(32885);
            }
         }

         GlStateManager.func_187439_f(☃.func_178979_i(), 0, ☃.func_178989_h());
         int ☃xxxx = 0;

         for(int ☃xxxxx = ☃xxx.size(); ☃xxxx < ☃xxxxx; ++☃xxxx) {
            VertexFormatElement ☃xxxxxx = (VertexFormatElement)☃xxx.get(☃xxxx);
            VertexFormatElement.EnumUsage ☃xxxxxxx = ☃xxxxxx.func_177375_c();
            int ☃xxxxxxxx = ☃xxxxxx.func_177369_e();
            switch(☃xxxxxxx) {
               case POSITION:
                  GlStateManager.func_187429_p(32884);
                  break;
               case UV:
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a + ☃xxxxxxxx);
                  GlStateManager.func_187429_p(32888);
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                  break;
               case COLOR:
                  GlStateManager.func_187429_p(32886);
                  GlStateManager.func_179117_G();
                  break;
               case NORMAL:
                  GlStateManager.func_187429_p(32885);
            }
         }
      }

      ☃.func_178965_a();
   }
}
