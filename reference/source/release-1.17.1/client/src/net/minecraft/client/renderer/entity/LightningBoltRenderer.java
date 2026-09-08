package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LightningBolt;

public class LightningBoltRenderer extends EntityRenderer<LightningBolt> {
   public LightningBoltRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
   }

   public void render(LightningBolt var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      float[] â˜ƒ = new float[8];
      float[] â˜ƒx = new float[8];
      float â˜ƒxx = 0.0F;
      float â˜ƒxxx = 0.0F;
      Random â˜ƒxxxx = new Random(â˜ƒ.seed);

      for(int â˜ƒxxxxx = 7; â˜ƒxxxxx >= 0; --â˜ƒxxxxx) {
         â˜ƒ[â˜ƒxxxxx] = â˜ƒxx;
         â˜ƒx[â˜ƒxxxxx] = â˜ƒxxx;
         â˜ƒxx += (float)(â˜ƒxxxx.nextInt(11) - 5);
         â˜ƒxxx += (float)(â˜ƒxxxx.nextInt(11) - 5);
      }

      VertexConsumer â˜ƒxxxxx = â˜ƒ.getBuffer(RenderType.lightning());
      Matrix4f â˜ƒxxxxxx = â˜ƒ.last().pose();

      for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 4; ++â˜ƒxxxxxxx) {
         Random â˜ƒxxxxxxxx = new Random(â˜ƒ.seed);

         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 3; ++â˜ƒxxxxxxxxx) {
            int â˜ƒxxxxxxxxxx = 7;
            int â˜ƒxxxxxxxxxxx = 0;
            if (â˜ƒxxxxxxxxx > 0) {
               â˜ƒxxxxxxxxxx = 7 - â˜ƒxxxxxxxxx;
            }

            if (â˜ƒxxxxxxxxx > 0) {
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx - 2;
            }

            float â˜ƒxxxxxxxxxx = â˜ƒ[â˜ƒxxxxxxxxxx] - â˜ƒxx;
            float â˜ƒxxxxxxxxxxx = â˜ƒx[â˜ƒxxxxxxxxxx] - â˜ƒxxx;

            for(int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx; â˜ƒxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxx; --â˜ƒxxxxxxxxxxxx) {
               float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx;
               float â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx;
               if (â˜ƒxxxxxxxxx == 0) {
                  â˜ƒxxxxxxxxxx += (float)(â˜ƒxxxxxxxx.nextInt(11) - 5);
                  â˜ƒxxxxxxxxxxx += (float)(â˜ƒxxxxxxxx.nextInt(11) - 5);
               } else {
                  â˜ƒxxxxxxxxxx += (float)(â˜ƒxxxxxxxx.nextInt(31) - 15);
                  â˜ƒxxxxxxxxxxx += (float)(â˜ƒxxxxxxxx.nextInt(31) - 15);
               }

               float â˜ƒxxxxxxxxxxxxx = 0.5F;
               float â˜ƒxxxxxxxxxxxxxx = 0.45F;
               float â˜ƒxxxxxxxxxxxxxxx = 0.45F;
               float â˜ƒxxxxxxxxxxxxxxxx = 0.5F;
               float â˜ƒxxxxxxxxxxxxxxxxx = 0.1F + (float)â˜ƒxxxxxxx * 0.2F;
               if (â˜ƒxxxxxxxxx == 0) {
                  â˜ƒxxxxxxxxxxxxxxxxx = (float)((double)â˜ƒxxxxxxxxxxxxxxxxx * ((double)â˜ƒxxxxxxxxxxxx * 0.1 + 1.0));
               }

               float â˜ƒxxxxxxxxxxxxx = 0.1F + (float)â˜ƒxxxxxxx * 0.2F;
               if (â˜ƒxxxxxxxxx == 0) {
                  â˜ƒxxxxxxxxxxxxx *= (float)(â˜ƒxxxxxxxxxxxx - 1) * 0.1F + 1.0F;
               }

               quad(
                  â˜ƒxxxxxx,
                  â˜ƒxxxxx,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  â˜ƒxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  false,
                  false,
                  true,
                  false
               );
               quad(
                  â˜ƒxxxxxx,
                  â˜ƒxxxxx,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  â˜ƒxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  true,
                  false,
                  true,
                  true
               );
               quad(
                  â˜ƒxxxxxx,
                  â˜ƒxxxxx,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  â˜ƒxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  true,
                  true,
                  false,
                  true
               );
               quad(
                  â˜ƒxxxxxx,
                  â˜ƒxxxxx,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  â˜ƒxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  false,
                  true,
                  false,
                  false
               );
            }
         }
      }
   }

   private static void quad(
      Matrix4f var0,
      VertexConsumer var1,
      float var2,
      float var3,
      int var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      boolean var12,
      boolean var13,
      boolean var14,
      boolean var15
   ) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ), (float)(â˜ƒ * 16), â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ)).color(â˜ƒ, â˜ƒ, â˜ƒ, 0.3F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ), (float)((â˜ƒ + 1) * 16), â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ)).color(â˜ƒ, â˜ƒ, â˜ƒ, 0.3F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ), (float)((â˜ƒ + 1) * 16), â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ)).color(â˜ƒ, â˜ƒ, â˜ƒ, 0.3F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ), (float)(â˜ƒ * 16), â˜ƒ + (â˜ƒ ? â˜ƒ : -â˜ƒ)).color(â˜ƒ, â˜ƒ, â˜ƒ, 0.3F).endVertex();
   }

   public ResourceLocation getTextureLocation(LightningBolt var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
